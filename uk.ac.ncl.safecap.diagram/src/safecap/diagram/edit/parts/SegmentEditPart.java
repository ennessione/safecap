package safecap.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;

import safecap.Project;
import safecap.SafecapPackage;
import safecap.diagram.edit.policies.SegmentItemSemanticEditPolicy;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.diagramactions.InsertNewNode;
import uk.ac.ncl.safecap.diagram.misc.parts.FSegment;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackColours;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackConstants;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

/**
 * @generated
 */
public class SegmentEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4001;

	/**
	 * @generated
	 */
	public SegmentEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new SegmentItemSemanticEditPolicy());
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void handleNotificationEvent(final Notification event) {

		if (event.getNotifier() instanceof Segment) {
			if (event.getFeature().equals(SafecapPackage.eINSTANCE.getVisual_Style())) {
				super.refresh();
			} else if (event.getFeature().equals(SchemaPackage.eINSTANCE.getSegment_Role())) {
				super.refresh();
			} else if (event.getFeature().equals(SchemaPackage.eINSTANCE.getSegment_Length())) {
				// super.refresh();
			} else if (event.getFeature().equals(SafecapPackage.eINSTANCE.getVisual_Intervals())
					|| event.getFeature().equals(SafecapPackage.eINSTANCE.getHighlightedInterval_From())
					|| event.getFeature().equals(SafecapPackage.eINSTANCE.getHighlightedInterval_To())) { // updates
																											// on
																											// train
																											// highlight
																											// for
																											// simulation
																											// replay
				super.refresh();
				// System.out.println("Feature updated: " + event.getFeature());
			}
		}
		super.handleNotificationEvent(event);
	}

	/**
	 * @generated NOT
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof SegmentLabelEditPart) {
			((SegmentLabelEditPart) childEditPart).setLabel(getPrimaryShape().getFigureSegmentLabelFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, index);
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof SegmentLabelEditPart) {
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
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
		return new SegmentFigure();
	}

	/**
	 * @generated
	 */
	public SegmentFigure getPrimaryShape() {
		return (SegmentFigure) getFigure();
	}

	/**
	 * @generated NOT
	 */
	public class SegmentFigure extends FSegment implements MouseListener {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureSegmentLabelFigure;

		/**
		 * @generated NOT
		 */
		public SegmentFigure() {
			final Edge e = (Edge) getModel();
			if (e.getElement() instanceof Segment) {
				final Segment seg = (Segment) e.getElement();
				setSegment(seg);
				setLineWidth(TrackConstants.TRACK_WIDTH);
				setForegroundColor(TrackColours.SegmentBase);
				setLineCap(SWT.CAP_ROUND);

				final EditPartViewer viewer = getViewer();
				final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();
				setZoomManager(root.getZoomManager());

				addMouseListener(this);

				createContents(EmfUtil.getProject(seg), SegmentUtil.needsLabel(seg));
			}
		}

		/**
		 * @generated NOT
		 */
		private void createContents(Project prj, boolean visible) {

			fFigureSegmentLabelFigure = new XWrappingLabel(prj, visible);
			// fFigureSegmentLabelFigure.setFont(TrackFonts.FONT_SMALL);
			this.add(fFigureSegmentLabelFigure);
			fFigureSegmentLabelFigure.setFont(TrackFonts.FONT_SMALLER);
			fFigureSegmentLabelFigure.setLocation(getBounds().getCenter());
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureSegmentLabelFigure() {
			return fFigureSegmentLabelFigure;
		}

		/**
		 * @generated NOT
		 */
		@Override
		public void mouseDoubleClicked(MouseEvent arg0) {
			/*
			 * // if ((arg0.getState() & SWT.ALT) == SWT.ALT || (arg0.getState() & //
			 * SWT.CTRL) == SWT.CTRL) { Point loc = arg0.getLocation(); Edge e = (Edge)
			 * SegmentEditPart.this.getModel(); Segment seg = (Segment) e.getElement();
			 * InsertNewNode creator = new InsertNewNode(SegmentEditPart.this, seg, loc);
			 * creator.run(); // }
			 */
		}

		/**
		 * @generated NOT
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {
			if ((arg0.getState() & SafecapConstants.GET_EDITOR_CONTROL_KEY()) == SafecapConstants.GET_EDITOR_CONTROL_KEY()) {
				final Point loc = arg0.getLocation();
				final Edge e = (Edge) getModel();
				final Segment seg = (Segment) e.getElement();
				final InsertNewNode creator = new InsertNewNode(SegmentEditPart.this, seg, loc);
				creator.run();
				// arg0.consume();
			}
		}

		/**
		 * @generated NOT
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * @generated NOT
	 * @author alex
	 * 
	 */
	class XWrappingLabel extends WrappingLabel {
		private final boolean enabled;
		private ZoomManager zoomManager = null;
		private final Project prj;

		XWrappingLabel(Project prj, boolean enabled) {
			this.prj = prj;
			this.enabled = enabled;
			final EditPartViewer viewer = getViewer();
			final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();
			zoomManager = root.getZoomManager();
			super.setText("");
		}

		@Override
		public void setText(String text) {
			if (enabled) {
				super.setText(text);
			} else {
				// super.setText("*" + text);
			}
		}

		/**
		 * @generated NOT
		 * 
		 */
		@Override
		public boolean isVisible() {

			return ZoomLevels.isNormal(zoomManager) && ExtensionUtil.isTrackLabelsVisible(prj);
		}
	}
}
