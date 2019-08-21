package safecap.diagram.edit.parts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import safecap.diagram.edit.policies.LeftSignalItemSemanticEditPolicy;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.diagram.misc.parts.FLeftSignalFigure;
import uk.ac.ncl.safecap.diagram.misc.parts.FRedLeftSignalFigure;
import uk.ac.ncl.safecap.diagram.misc.parts.FSignalFigure;
import uk.ac.ncl.safecap.diagram.misc.visual.FixedConnectionAnchor;

/**
 * @generated
 */
public class RedLeftSignalEditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2008;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public RedLeftSignalEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		return new NonResizableEditPolicyEx();
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new LeftSignalItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
		removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart arg0) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FLeftSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FLeftSignalFigure.ANCHOR_Y;
		// it creates an anchor for source on point 120,40
		return fixedAnchor;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {

		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FLeftSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FLeftSignalFigure.ANCHOR_Y;

		return fixedAnchor;

	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart arg0) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FLeftSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FLeftSignalFigure.ANCHOR_Y;

		return fixedAnchor;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FLeftSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FLeftSignalFigure.ANCHOR_Y;

		return fixedAnchor;

	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		final org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			@Override
			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			@Override
			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			@Override
			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new LeftSignalFigure();
	}

	/**
	 * @generated
	 */
	public LeftSignalFigure getPrimaryShape() {
		return (LeftSignalFigure) primaryShape;
	}

	/**
	 * @generated NOT
	 */
	protected NodeFigure createNodePlate() {
		final DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(FSignalFigure.WIDTH, FSignalFigure.HEIGHT);
		return result;
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
	protected NodeFigure createNodeFigure() {
		final NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		final IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane. Respects layout
	 * one may have set for generated figure.
	 * 
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	@Override
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	@Override
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated NOT
	 */
	public class LeftSignalFigure extends FRedLeftSignalFigure {

		/**
		 * @generated NOT
		 */
		public LeftSignalFigure() {
			final Node node = (Node) getModel();
			final Signal signal = (Signal) node.getElement();
			final EditPartViewer viewer = getViewer();
			final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();

			setSignal(signal);
			setZoomManager(root.getZoomManager());
		}

	}

}
