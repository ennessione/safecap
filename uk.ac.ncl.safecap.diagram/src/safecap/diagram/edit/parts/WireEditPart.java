package safecap.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import safecap.diagram.edit.policies.WireItemSemanticEditPolicy;
import safecap.trackside.Signal;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;

/**
 * @generated
 */
public class WireEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4002;

	/**
	 * @generated
	 */
	public WireEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void handleNotificationEvent(final Notification event) {

		if (event.getNotifier() instanceof Wire && event.getFeature().equals(TracksidePackage.eINSTANCE.getWire_Offset())) {
			super.refresh();
		}

		super.handleNotificationEvent(event);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new WireItemSemanticEditPolicy());
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model so you
	 * may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */

	@Override
	protected Connection createConnectionFigure() {
		return new WireFigureDes();
	}

	/**
	 * @generated
	 */
	public WireFigureDes getPrimaryShape() {
		return (WireFigureDes) getFigure();
	}

	/**
	 * @generated NOT
	 */
	public class WireFigureDes extends PolylineConnectionEx {

		private final ZoomManager zoomManager;
		private final Wire wire;

		/**
		 * @generated NOT
		 */
		public WireFigureDes() {
			setForegroundColor(ColorConstants.gray);

			setTargetDecoration(createTargetDecoration());
			final EditPartViewer viewer = getViewer();
			final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();
			zoomManager = root.getZoomManager();
			final Edge e = (Edge) getModel();
			wire = (Wire) e.getElement();
		}

		/**
		 * @generated NOT
		 */
		@Override
		protected void fillShape(Graphics g) {
		}

		/**
		 * @generated NOT
		 */
		@Override
		protected void outlineShape(Graphics g) {
			if (ZoomLevels.isOutline(zoomManager)) {
				return;
			}

			if (ZoomLevels.isCoarse(zoomManager)) {
				setLineWidth(2);
			}

			super.outlineShape(g);

			if (ZoomLevels.isNormal(zoomManager)) {
				if (wire.getOffset() != 0) {
					final Rectangle r = getBounds();
					final String s = "" + wire.getOffset();
					final Dimension tdim = FigureUtilities.getStringExtents(s, TrackFonts.FONT_SMALL);
					tdim.expand(2, 2);

					final int x = r.x + r.width / 2 - tdim.width / 2;
					final int y = r.y + r.height / 2 - tdim.height / 2;

					g.setBackgroundColor(ColorConstants.darkGray);
					g.setForegroundColor(ColorConstants.yellow);
					g.fillRectangle(x, y, tdim.width, tdim.height);
					g.setFont(TrackFonts.FONT_SMALL);
					g.drawText("" + wire.getOffset(), x + 1, y + 1);
				}
			}
		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			final PolylineDecoration df = new PolylineDecoration();
			return df;
		}

		/**
		 * @generated NOT
		 */
		@Override
		public boolean isVisible() {
			if (wire != null) {
				if (wire.getFrom() instanceof Signal) {
					return ExtensionUtil.isSignalsVisible(wire.getFrom());
				} else if (wire.getFrom() instanceof StopAndGo) {
					return ExtensionUtil.isPlatformsVisible(wire.getFrom());
				} else if (wire.getFrom() instanceof SpeedLimit) {
					return ExtensionUtil.isSpeedLimitsVisible(wire.getFrom());
				}
			}

			return true;
		}
	}

}
