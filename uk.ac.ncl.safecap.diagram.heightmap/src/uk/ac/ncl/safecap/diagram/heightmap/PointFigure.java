package uk.ac.ncl.safecap.diagram.heightmap;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public abstract class PointFigure extends Ellipse // implements IPointEditPart
{
	private final Dimension dimension = new Dimension(12, 12);

	private Point _location, _figureLocation;
	private Color _color;
	private EObject _model;
	private final Adapter _adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			onModelChanged();
		}
	};

	public PointFigure(EObject model, Color color) {
		_model = model;
		_color = color;
		setBorder(null);
		setForegroundColor(color);
		setBackgroundColor(color);
		setAntialias(1);
		setFill(true);
		setSize(dimension);

		_model.eAdapters().add(_adapter);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (arg0.button == 1) {
					if (_location == null) {
						return;
					}
					_location = null;
					arg0.consume();
					onDragComplete();
				}

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (arg0.button == 1 && (arg0.getState() & SWT.CTRL) == SWT.CTRL) {
					((GraphFigure) getParent()).pointDeleted(PointFigure.this);
					arg0.consume();
				} else if (arg0.button == 1) {
					_location = arg0.getLocation();
					_figureLocation = getLocation().getCopy();
					arg0.consume();
				}
			}

			@Override
			public void mouseDoubleClicked(MouseEvent arg0) {
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseHover(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				setBackgroundColor(_color);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				setBackgroundColor(ColorConstants.white);
			}

			@Override
			public void mouseDragged(MouseEvent event) {
				if (_location == null) {
					return;
				}
				final Point newLocation = event.getLocation();
				if (newLocation == null) {
					return;
				}
				Dimension offset = newLocation.getDifference(_location);

				final UpdateManager updateMgr = getUpdateManager();
				final LayoutManager layoutMgr = getParent().getLayoutManager();
				Rectangle bounds = getBounds();

				bounds = bounds.getCopy();
				bounds.setLocation(_figureLocation);
				bounds.translate(offset.width, offset.height);

				checkBounds(bounds);

				layoutMgr.setConstraint(PointFigure.this, bounds);
				offset = bounds.getLocation().getDifference(getLocation());
				translate(offset.width, offset.height);
				updateMgr.addDirtyRegion(getParent(), getParent().getBounds());
				event.consume();
			}
		});
	}

	protected void checkBounds(Rectangle bounds) {
	}

	public EObject getModel() {
		return _model;
	}

	protected void onDragComplete() {
	}

	public void onModelChanged() {
	}

//    @Override
//    public abstract Point getModelPosition();

	public void dispose() {
		_model.eAdapters().remove(_adapter);
	}
}
