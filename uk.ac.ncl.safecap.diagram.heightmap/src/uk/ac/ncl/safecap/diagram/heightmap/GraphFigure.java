package uk.ac.ncl.safecap.diagram.heightmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;

import safecap.diagram.part.SafecapDiagramEditorUtil;
import safecap.model.Line;
import safecap.schema.HeightMap;
import safecap.schema.HeightPoint;
import safecap.schema.Node;
import safecap.schema.SchemaFactory;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.LineUtil;

public class GraphFigure extends FreeformLayer {
	private final Resource _res;
	private final EObject _model;
	private List<Segment> _segments = new ArrayList<>();
	private int _totalLength;
	// Ratios between graph and model coordinates (pixel per meter)
	private final float xRatio = 1200f / 1000f;
	private final float yRatio = 50f / 10f;
	private int minAlt, maxAlt;
	private Canvas _canvas;
	private boolean _draw = true;

	private final Map<EObject, PointFigure> _pointFigures = new Hashtable<>();
	private final List<EObject> _temp = new ArrayList<>();

	private final Adapter _adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			updateFigures();
		}
	};

	public GraphFigure(Canvas canvas, EObject model) {
		if (!(model instanceof Segment || model instanceof Line)) {
			throw new IllegalArgumentException("model is not Segment or Line");
		}
		_model = model;
		_res = _model.eResource();
		_canvas = canvas;
		setLayoutManager(new XYLayout());
		updateFigures();
		for (final Segment segment : _segments) {
			segment.eAdapters().add(_adapter);
			segment.getHeightmap().eAdapters().add(_adapter);
		}
		if (_model instanceof Line) {
			final Line line = (Line) _model;
			line.eAdapters().add(_adapter);
		}

		addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClicked(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(final MouseEvent arg0) {
				if (arg0.button == 1 && (arg0.getState() & SWT.CTRL) == SWT.CTRL) {
					final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(_model);
					final Point p = new Point(toModelX(arg0.x), toModelY(arg0.y));
					final Segment segment = getSegmentUnderModelPos(p.x);
					p.x -= getNodeModelPosition(segment.getFrom());

					final HeightPoint point = SchemaFactory.eINSTANCE.createHeightPoint();
					point.setPosition(p.x);
					point.setAltitude(p.y);

					if (dom != null) {
						dom.getCommandStack().execute(new AddCommand(dom, segment.getHeightmap().getPoints(), point));
						save();
					} else {
						segment.getHeightmap().getPoints().add(point);
						save();
					}
//                    updateFigures();
//                    dom.getCommandStack().execute(new AbstractCommand("new_heightpoint") {
//                        @Override
//                        public void execute()
//                        {
//
//                             HeightMap map = _segment.getHeightmap();
//                             map.getPoints().add(point);
//                        }
//
//                        @Override
//                        public void redo()
//                        {
//                        }
//
//                        @Override
//                        public boolean canExecute()
//                        {
//                            return true;
//                        }
//                    });
					arg0.consume();
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}

	void pointDeleted(PointFigure figure) {
		if (figure instanceof HeightPointFigure) {
			final HeightPointFigure fig = (HeightPointFigure) figure;
			final HeightPoint heightPoint = (HeightPoint) fig.getModel();
			final HeightMap map = (HeightMap) heightPoint.eContainer();
			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(_model);
			if (dom != null) {
				dom.getCommandStack().execute(new RemoveCommand(dom, map, SchemaPackage.eINSTANCE.getHeightMap_Points(), heightPoint));
				save();
			} else {
				map.getPoints().remove(heightPoint);
				save();
			}
		}
	}

	public PointFigure getFigure(EObject obj) {
		return _pointFigures.get(obj);
	}

	public Segment getSegmentUnderModelPos(int x) {
		int pos = 0;
		for (final Segment seg : _segments) {
			if (x < pos + seg.getLength()) {
				return seg;
			}
			pos += seg.getLength();
		}
		return _segments.get(_segments.size() - 1);
	}

	public int getNodeModelPosition(Node node) {
		int pos = 0;
		for (final Segment seg : _segments) {
			if (seg.getFrom().equals(node)) {
				return pos;
			} else if (seg.getTo().equals(node)) {
				return pos + seg.getLength();
			}
			pos += seg.getLength();
		}
		throw new IllegalArgumentException("Node is not found on the line");
	}

	public EObject getModel() {
		return _model;
	}

	public int toGraphX(int modelX) {
		return Math.round(modelX * xRatio);
	}

	public int toModelX(int graphX) {
		final int modelX = Math.round(graphX / xRatio);
		return modelX;
	}

	public int toGraphY(int modelY) {
		return Math.round(getSize().height - (modelY - minAlt) * yRatio);
	}

	public int toModelY(int graphY) {
		return Math.round((getSize().height - graphY) / yRatio + minAlt);
	}

	public void toGraphPosition(Point p) {
		p.x = toGraphX(p.x);
		p.y = toGraphY(p.y);
	}

	public void toModelPosition(Point p) {
		p.x = toModelX(p.x);
		p.y = toModelY(p.y);
	}

	public int getSegmentLength() {
		return _totalLength;
	}

	public int getInitialAltitute() {
		if (!_segments.isEmpty()) {
			return _segments.get(0).getFrom().getAltitude();
		} else {
			return 0;
		}
	}

	private void updateFigures() {
		if (!_draw) {
			return;
		}
		if (_canvas == null) {
			return;
		}

		final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(_model);
//        if (dom == null)
//        {
//            _canvas.setSize(300, 200);
//            return;
//        }

		List<Segment> segments = new ArrayList<>();
		if (_model instanceof Segment) {
			segments.add((Segment) _model);
		} else if (_model instanceof Line) {
			segments = LineUtil.getSegments((Line) _model);
			segments = HeightmapUtil.arrangeSegments(segments);
		}

		_segments = segments;
		_totalLength = 0;

		final List<EObject> points = new ArrayList<>();
		if (!segments.isEmpty()) {
			points.add(segments.get(0).getFrom());
		}
		for (final Segment segment : segments) {
			_totalLength += segment.getLength();
			HeightMap map = segment.getHeightmap();
			if (map == null) {
				map = SchemaFactory.eINSTANCE.createHeightMap();
				if (dom != null) {
					dom.getCommandStack().execute(new SetCommand(dom, segment, SchemaPackage.eINSTANCE.getSegment_Heightmap(), map));
					save();
				} else {
					segment.setHeightmap(map);
					save();
				}
			}
			points.addAll(map.getPoints());
			points.add(segment.getTo());
		}

		_temp.clear();
		_temp.addAll(_pointFigures.keySet());
		minAlt = getInitialAltitute() - 40;
		maxAlt = getInitialAltitute() + 40;
		for (final EObject point : points) {
			if (!_pointFigures.containsKey(point)) {
				PointFigure fig;
				if (point instanceof HeightPoint) {
					fig = new HeightPointFigure((HeightPoint) point);
				} else if (point instanceof Node) {
					fig = new BoundaryPointFigure((Node) point);
				} else {
					throw new IllegalArgumentException("Node and HeightPoint expected");
				}
				add(fig);
				_pointFigures.put(point, fig);
			}
			_temp.remove(point);

			int alt;
			if (point instanceof HeightPoint) {
				alt = ((HeightPoint) point).getAltitude();
			} else {
				alt = ((Node) point).getAltitude();
			}
			if (alt < minAlt) {
				minAlt = alt;
			}
			if (alt > maxAlt) {
				maxAlt = alt;
			}
		}
		for (final EObject deletedPoint : _temp) {
			final PointFigure deletedFigure = _pointFigures.get(deletedPoint);
			remove(deletedFigure);
			_pointFigures.remove(deletedPoint);
		}

		minAlt -= 10;
		maxAlt += 10;

		setSize(Math.round(getSegmentLength() * xRatio), Math.round((maxAlt - minAlt) * yRatio));
		if (_canvas != null) {
			_canvas.setSize(getSize().width, getSize().height);
		}
		_temp.clear();

		final List children = getChildren();
		for (final Object obj : children) {
			if (obj instanceof PointFigure) {
				final PointFigure fig = (PointFigure) obj;
				fig.onModelChanged();
			}
		}
	}

	@Override
	public void paintFigure(Graphics graphics) {
		if (!_draw) {
			return;
		}
		super.paintFigure(graphics);
		graphics.setAntialias(1);

//        TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(_model);
//        if (dom == null)
//        {
//            graphics.drawText("Please open editor for this schema", 30, 30);
//            return;
//        }

		final List children = getChildren();
		final ArrayList<PointFigure> nodes = new ArrayList<>();
		for (final Object child : children) {
			if (child instanceof PointFigure) {
				nodes.add((PointFigure) child);
			}
		}
		Collections.sort(nodes, new Comparator<PointFigure>() {
			@Override
			public int compare(PointFigure arg0, PointFigure arg1) {
				if (arg0.getBounds().x < arg1.getBounds().x) {
					return -1;
				} else if (arg0.getBounds().x > arg1.getBounds().x) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		drawGrid(graphics);

		graphics.setLineWidth(3);
		Point prevPoint = null;
		final Point nextPoint = new Point();
		for (final IFigure node : nodes) {
			getCenter(node.getBounds(), nextPoint);
			drawLabel(graphics, (PointFigure) node, nextPoint);
			if (prevPoint == null) {
				prevPoint = new Point();
			} else {
				graphics.setForegroundColor(ColorConstants.gray);
				graphics.drawLine(prevPoint, nextPoint);
			}
			prevPoint.setLocation(nextPoint);
		}
	}

	private final Point _modelPos = new Point();

	private void drawLabel(Graphics graphics, PointFigure fig, Point center) {
		graphics.setBackgroundColor(ColorConstants.lightGray);
		graphics.setForegroundColor(ColorConstants.black);
		_modelPos.x = toModelX(center.x);
		_modelPos.y = toModelY(center.y);
		int textX = center.x - 10;
		final int textY = center.y - 40;
		if (textX < 5) {
			textX = 5;
		}
		if (textX + 50 >= getSize().width) {
			textX = getSize().width - 50;
		}
		graphics.fillText(" pos: " + _modelPos.x + "m \n alt: " + _modelPos.y + "m ", textX, textY);
	}

	private void getCenter(Rectangle bounds, Point p) {
		p.x = bounds.x + bounds.width / 2;
		p.y = bounds.y + bounds.height / 2;
	}

	private void drawGrid(Graphics graphics) {
		final int len = getSegmentLength();
		int drawStep = 100;
		final int maxY = toGraphY(minAlt);
		final int initialAltitude = getInitialAltitute();
		int initialAltitudeInPixels = toGraphY(initialAltitude);
		final int lenInPixels = toGraphX(getSegmentLength());

		final Node firstNode = _segments.get(0).getFrom();
		final PointFigure firstFig = _pointFigures.get(firstNode);
		initialAltitudeInPixels = firstFig.getLocation().y + firstFig.getSize().height / 2;
		graphics.setLineStyle(SWT.LINE_DASH);
		graphics.setLineWidth(2);
		graphics.setForegroundColor(ColorConstants.black);
		graphics.drawLine(0, initialAltitudeInPixels, lenInPixels, initialAltitudeInPixels);

		graphics.setLineStyle(SWT.LINE_SOLID);
		graphics.setLineWidth(1);
		graphics.setForegroundColor(ColorConstants.lightGray);

		for (int i = drawStep; i < len; i += drawStep) {
			final int graphX = toGraphX(i);

			if (i % 500 == 0) {
				graphics.setLineWidth(2);
			} else {
				graphics.setLineWidth(1);
			}
			graphics.drawLine(graphX, 0, graphX, maxY);

			if (i % 500 == 0 || i == drawStep || i == len - drawStep) {
				graphics.drawText(i + "m", graphX - 10, initialAltitudeInPixels + 5);
			}
		}

		drawStep = 10;
		graphics.setLineWidth(1);
		for (int i = maxAlt / drawStep * drawStep; i > minAlt; i -= drawStep) {
			final int graphY = toGraphY(i);
			graphics.drawLine(0, graphY, lenInPixels, graphY);
			graphics.drawText(i + "m", 10, graphY - 15);
			graphics.drawText(i + "m", lenInPixels - 40, graphY - 15);
		}
	}

	public void stopDrawing() {
		_draw = false;
		_canvas = null;
	}

	public void dispose() {
		_draw = false;

		for (final Segment segment : _segments) {
			segment.eAdapters().remove(_adapter);
			segment.getHeightmap().eAdapters().remove(_adapter);
		}
		if (_model instanceof Line) {
			final Line line = (Line) _model;
			line.eAdapters().remove(_adapter);
		}

		for (final PointFigure fig : _pointFigures.values()) {
			fig.dispose();
		}
		_canvas = null;
	}

	private void save() {
		try {
			_res.save(SafecapDiagramEditorUtil.getSaveOptions());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
