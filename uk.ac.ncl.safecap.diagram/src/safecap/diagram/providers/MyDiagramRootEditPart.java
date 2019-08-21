package safecap.diagram.providers;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.ConnectionLayerEx;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;

import safecap.diagram.part.MyGridLayerEx;

/**
 * @generated NOT
 */
public class MyDiagramRootEditPart extends RenderedDiagramRootEditPart implements ZoomListener {
	private final double[] zoomLevels = { 0.05, .125, .25, .5, 0.75, 1, 1.25, 1.75, 2, 3, 4, 6, 8 };
	// private LayeredPane innerLayers;
	// private LayeredPane printableLayers;
	private MyGridLayerEx gridlayer;

	/**
	 * @generated NOT
	 */
	public MyDiagramRootEditPart(MeasurementUnit mu) {
		super(mu);
	}

	/**
	 * @generated NOT
	 */

	@Override
	protected GridLayer createGridLayer() {
		gridlayer = new MyGridLayerEx();
		return gridlayer;
	}

	/**
	 * @generated NOT
	 */
	@SuppressWarnings("restriction")
	@Override
	protected LayeredPane createPrintableLayers() {
		final FreeformLayeredPane layeredPane = new FreeformLayeredPane();

		layeredPane.add(new MyBorderItemsAwareFreeFormLayer(), PRIMARY_LAYER);
		layeredPane.add(new ConnectionLayerEx(), CONNECTION_LAYER);
		layeredPane.add(new FreeformLayer(), DECORATION_PRINTABLE_LAYER);
		return layeredPane;
	}

	/**
	 * Force no animation
	 */

	@Override
	public ZoomManager getZoomManager() {
		final ZoomManager zm = super.getZoomManager();
		zm.setZoomAnimationStyle(org.eclipse.gef.editparts.ZoomManager.ANIMATE_NEVER);
		zm.setZoomLevels(zoomLevels);
		zm.addZoomListener(gridlayer);
		zm.addZoomListener(this);
		zm.setZoom(1.0);
		return zm;
	}

	@Override
	protected void register() {
		zoomChanged(getZoomManager().getZoom());
		super.register();
	}

	@Override
	public void zoomChanged(double zoom) {
		final Dimension dim = (Dimension) getViewer().getProperty(SnapToGrid.PROPERTY_GRID_SPACING + ".orig");
		if (dim != null) {
			final double gridSpacing = dim.preciseHeight();
			final Dimension dimz = new Dimension((int) (gridSpacing * zoom), (int) (gridSpacing * zoom));
			getViewer().setProperty(SnapToGrid.PROPERTY_GRID_SPACING, dimz);
		}
	}

	@Override
	public void setGridSpacing(double gridSpacing) {
		final double zoom = getZoomManager().getZoom();
		final Dimension dim = new Dimension((int) gridSpacing, (int) gridSpacing);
		// Dimension dimz = new Dimension((int) (gridSpacing * zoom), (int) (gridSpacing
		// * zoom));
		getViewer().setProperty(SnapToGrid.PROPERTY_GRID_SPACING, dim);
		getViewer().setProperty(SnapToGrid.PROPERTY_GRID_SPACING + ".orig", dim);
	}
}
