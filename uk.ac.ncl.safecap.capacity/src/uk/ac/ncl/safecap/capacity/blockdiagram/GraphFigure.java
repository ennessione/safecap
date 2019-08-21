package uk.ac.ncl.safecap.capacity.blockdiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import safecap.model.Ambit;
import safecap.model.Point;
import safecap.model.Route;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.LineUtil;
import uk.ac.ncl.safecap.misc.core.RouteWrapper;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.Evolution;

public class GraphFigure extends FreeformLayer implements MouseMotionListener, IZoomInfo, MouseListener {
	public static Font markFont, labelFont;

	static {
		markFont = new Font(Display.getCurrent(), "arial", 8, SWT.BOLD);
		labelFont = new Font(Display.getCurrent(), "arial", 8, SWT.BOLD);
	}

	// private static Color[] COLORS = new Color[]{ColorConstants.darkBlue,
	// ColorConstants.darkGreen, ColorConstants.red, ColorConstants.cyan,
	// ColorConstants.blue, ColorConstants.orange, ColorConstants.yellow};
	// private static Color getColor(int index)
	// {
	// return COLORS[index%COLORS.length];
	// }

	private static int OFFSETY = 100, OFFSETX = 100, POINT_STATE_WIDTH = 10, COLUMN_GAP = 10;
	// meters per pixel
	private float xRatio = 300f / 100f,
			// seconds per pixel
			yRatio = 30f / 100f;

	private final SimulationExperiment _provider;
	private TrainLine _line = null;
	private final Canvas _canvas;
	private BlockDiagramFigure _mainFig;
	private final List<PointFigure> _pointFigures = new ArrayList<>();
	private final List<RouteFigure> _routeFigures = new ArrayList<>();
	private final Map<String, Color> _colors;
	private Rectangle _mainBounds;
	private final Reporter _reporter;

	public GraphFigure(Canvas canvas, SimulationExperiment experiment, Map<String, Color> colors, Reporter reporter) {
		_canvas = canvas;
		_provider = experiment;
		_colors = colors;
		_reporter = reporter;

		setLayoutManager(new XYLayout());
		addMouseMotionListener(this);
		addMouseListener(this);
		// update();
	}

	public void setInput(TrainLine line) {
		_line = line;
		create();
	}

	public void zoom(boolean zoomIn, boolean vertical) {
		final double mult = 0.8;
		if (vertical) {
			if (zoomIn) {
				yRatio *= mult;
			} else {
				yRatio /= mult;
			}
		} else {
			if (zoomIn) {
				xRatio *= mult;
			} else {
				xRatio /= mult;
			}
		}
		myLayout();
	}

	public int toGraphX(double modelX) {
		return (int) Math.round(modelX / xRatio + OFFSETX);
	}

	public int toGraphY(double modelY) {
		return (int) Math.round(modelY / yRatio + OFFSETY);
	}

	private void create() {
		removeAll();

		_pointFigures.clear();
		final List<Point> points = LineUtil.getAllPoints(_line.getSchemaLine());
		for (final Point point : points) {
			final PointFigure fig = new PointFigure(POINT_STATE_WIDTH, this, point, _provider);
			_pointFigures.add(fig);
			add(fig);
		}

		_routeFigures.clear();
		for (final Route route : _line.getSchemaLine().getRoutes()) {
			final RouteFigure fig = new RouteFigure(POINT_STATE_WIDTH, this, new RouteWrapper(route, _line.getSchemaLine()),
					_provider);
			_routeFigures.add(fig);
			add(fig);
		}

		_mainFig = new BlockDiagramFigure(this, _line, _provider, _colors);
		add(_mainFig);

		myLayout();
	}

	public void myLayout() {
		final LayoutManager layoutMgr = getLayoutManager();

		int maxY = 0, maxX = 0;

		double totalTime = 0;
		for (final Evolution ev : _provider.getHistory()) {
			totalTime += ev.getExtent();
		}
		final int height = (int) Math.round(totalTime / yRatio);

		int columns = 0;
		for (final PointFigure fig : _pointFigures) {
			final Rectangle bounds = new Rectangle(OFFSETX + (POINT_STATE_WIDTH + COLUMN_GAP) * columns, OFFSETY,
					POINT_STATE_WIDTH, height);
			// fig.setBounds(bounds);
			fig.setSize(bounds.getSize());
			fig.setLocation(bounds.getLocation());
			layoutMgr.setConstraint(fig, bounds);
			fig.myLayout();
			columns++;
		}

		for (final RouteFigure fig : _routeFigures) {
			fig.myLayout();
			fig.setBounds(
					new Rectangle(OFFSETX + (POINT_STATE_WIDTH + COLUMN_GAP) * columns, OFFSETY, POINT_STATE_WIDTH, height));
			layoutMgr.setConstraint(fig, fig.getBounds());
			columns++;
		}

		_mainFig.myLayout();
		_mainFig.setLocation(new org.eclipse.draw2d.geometry.Point(
				OFFSETX + (POINT_STATE_WIDTH + COLUMN_GAP) * columns + COLUMN_GAP, OFFSETY));
		_mainBounds = _mainFig.getBounds();
		layoutMgr.setConstraint(_mainFig, _mainBounds);

		for (final Object obj : getChildren()) {
			final IFigure fig = (IFigure) obj;
			if (fig.getBounds().getBottomRight().x > maxX) {
				maxX = fig.getBounds().getBottomRight().x;
			}
			if (fig.getBounds().getBottomRight().y > maxY) {
				maxY = fig.getBounds().getBottomRight().y;
			}
		}

		this.setSize(maxX + 20, maxY + 20);
		_canvas.setSize(new org.eclipse.swt.graphics.Point(maxX + 20, maxY + 20));

		invalidateTree();
		// this.revalidate();
		// this.validate();
		myInvalidate();
	}

	public void myInvalidate() {
		final UpdateManager updateMgr = getUpdateManager();
		updateMgr.addDirtyRegion(this, getBounds());
	}

	@Override
	public void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		graphics.setAntialias(1);

		drawGrid(graphics);

		for (final Object obj : getChildren()) {
			final IFigure fig = (IFigure) obj;
			if (fig instanceof ColumnFigure) {
				final ColumnFigure p = (ColumnFigure) fig;
				p.paintLabel(graphics, 3, -10);
			}
			// else if (fig instanceof BlockDiagramFigure)
			// {
			// BlockDiagramFigure p = (BlockDiagramFigure) fig;
			// p.paintLabels(graphics, -20);
			// }
		}

		drawMeasures(graphics);
	}

	private void drawMeasures(Graphics graphics) {
		if (measureMode > 0) {
			drawMeasurePoint(graphics, x1, y1);
		}
		if (measureMode == 2) {
			drawMeasurePoint(graphics, x2, y2);
		}
	}

	private void drawMeasurePoint(Graphics graphics, int x, int y) {
		graphics.setForegroundColor(ColorConstants.red);
		graphics.drawOval(x - 1, y - 1, 3, 3);
	}

	private void drawGrid(Graphics graphics) {
		final int len = _line.getLineLength();
		int drawStep = chooseOsStep(10);
		int nextDrawStep = nextStepAfter(drawStep, OS_STEPS);
		int textDrawStep = chooseOsStep(50);
		// int maxY = toGraphY(minAlt);
		// int initialAltitude = getInitialAltitute();
		// int initialAltitudeInPixels = toGraphY(initialAltitude);
		// int lenInPixels = toGraphX(getSegmentLength());

		graphics.setLineStyle(SWT.LINE_SOLID);
		graphics.setLineWidth(1);
		graphics.setForegroundColor(ColorConstants.lightGray);

		final int osx = 40;
		graphics.drawLine(_mainFig.getBounds().x, osx, _mainFig.getBounds().right(), osx);

		for (int i = 0; i < len; i += drawStep) {
			final int graphX = Math.round(i / xRatio) + _mainFig.getBounds().x;

			if (i % nextDrawStep == 0) {
				graphics.setLineWidth(2);
			} else {
				graphics.setLineWidth(1);
			}
			graphics.drawLine(graphX, osx - 3, graphX, osx + 3);

			if (i % textDrawStep == 0 || i == nextDrawStep) {
				graphics.drawText(i + "m", graphX - 10, osx - 20);
			}
		}

		drawStep = chooseTimeStep(10);
		nextDrawStep = nextStepAfter(drawStep, TIME_STEPS);
		textDrawStep = chooseTimeStep(30);
		graphics.setLineWidth(1);
		final int maxTime = Math.round(_mainFig.getBounds().height * yRatio);
		final int osy = 40;
		graphics.drawLine(osy, _mainFig.getBounds().y, osy, _mainFig.getBounds().bottom());

		for (int i = 0; i <= maxTime; i += drawStep) {
			final int graphY = Math.round(i / yRatio) + _mainFig.getBounds().y;
			graphics.drawLine(osy - 3, graphY, osy + 3, graphY);
			if (i % textDrawStep == 0 || i == nextDrawStep) {
				graphics.drawText(i + "s", osy - 30, graphY - 10);
				// graphics.drawText(i + "s", lenInPixels-40, graphY - 15);
			}
		}
	}

	private static int[] OS_STEPS = { 10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000 };
	private static int[] TIME_STEPS = { 10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000 };

	private int chooseOsStep(int minPixels) {
		final double minMeters = minPixels * xRatio;
		final int minMetersInt = (int) Math.round(minMeters);
		return closestStep(minMetersInt, OS_STEPS);
	}

	private int chooseTimeStep(int minPixels) {
		final double minMeters = minPixels * yRatio;
		final int minMetersInt = (int) Math.round(minMeters);
		return closestStep(minMetersInt, OS_STEPS);
	}

	private int closestStep(int minMeters, int[] steps) {
		for (int i = 0; i < steps.length; i++) {
			if (minMeters <= steps[i]) {
				return steps[i];
			}
		}
		return 1000;
	}

	private int nextStepAfter(int minMeters, int[] steps) {
		for (int i = 0; i < steps.length; i++) {
			if (minMeters <= steps[i]) {
				return steps[i + 1];
			}
		}
		return 1000;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		updateAmbitUnderMouse(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		updateAmbitUnderMouse(arg0);
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	NamedBoxFigure _curFig = null;

	@Override
	public void mouseMoved(MouseEvent arg0) {
		final int y = arg0.y, x = arg0.x;
		final double time = translateToTime(y);
		_reporter.reportTime(time);

		final double pos = translateToPosition(x);
		_reporter.reportLineCoord(pos);

		if (pos >= 0) {
			final Ambit ambit = _line.getSegmentAmbit(_line.getSegment(pos));
			final Route route = _line.getSegmentRoute(_line.getSegment(pos));
			_reporter.reportAmbit(ambit.getLabel());
			_reporter.reportRoute(route.getLabel());
		} else {
			_reporter.reportAmbit(null);
			_reporter.reportRoute(null);
		}

		updateAmbitUnderMouse(arg0);

		arg0.consume();
	}

	private double translateToPosition(int x) {
		double pos;
		if (x >= _mainBounds.x && x < _mainBounds.right()) {
			pos = (x - _mainBounds.x) * xRatio;
		} else {
			pos = -1;
		}
		return pos;
	}

	private double translateToTime(int y) {
		if (y >= _mainBounds.y && y < _mainBounds.bottom()) {
			return (y - _mainBounds.y) * yRatio;
		} else {
			return -1;
		}
	}

	private void updateAmbitUnderMouse(MouseEvent arg0) {
		final int y = arg0.y, x = arg0.x;
		final IFigure fig = findFigureAt(x, y);
		if (fig != _curFig && _curFig != null) {
			_curFig.setInitialColor();
			_curFig = null;
		}
		if (fig instanceof NamedBoxFigure && fig.getParent() instanceof AmbitFigure) {
			_curFig = (NamedBoxFigure) fig;
			_curFig.setBackgroundColor(ColorConstants.darkGray);
		}
	}

	@Override
	public double getXRatio() {
		return xRatio;
	}

	@Override
	public double getYRatio() {
		return yRatio;
	}

	@Override
	public int convertToGraphX(double x) {
		return toGraphX(x);
	}

	@Override
	public int convertToGraphY(double y) {
		return toGraphY(y);
	}

	private int x1, y1, x2, y2;
	private int measureMode = 0;

	@Override
	public void mousePressed(MouseEvent me) {
		if (me.button == 1) {
			if (measureMode == 0 || measureMode == 2) {
				measureMode = 1;
				x1 = me.x;
				y1 = me.y;
			} else if (measureMode == 1) {
				measureMode = 2;
				x2 = me.x;
				y2 = me.y;
			}
			myInvalidate();
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		// TODO Auto-generated method stub

	}
}
