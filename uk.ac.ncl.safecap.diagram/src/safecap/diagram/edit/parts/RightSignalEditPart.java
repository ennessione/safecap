package safecap.diagram.edit.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import safecap.diagram.edit.policies.LeftSignalItemSemanticEditPolicy;
import safecap.diagram.part.SafecapVisualIDRegistry;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.diagram.misc.parts.FRightSignalFigure;
import uk.ac.ncl.safecap.diagram.misc.parts.FSignalFigure;
import uk.ac.ncl.safecap.diagram.misc.visual.FixedConnectionAnchor;

/**
 * @generated
 */
public class RightSignalEditPart extends AbstractBorderedShapeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2005;

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
	public RightSignalEditPart(View view) {
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
		fixedAnchor.offsetH = FRightSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FRightSignalFigure.ANCHOR_Y;
		return fixedAnchor;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {

		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FRightSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FRightSignalFigure.ANCHOR_Y;
		return fixedAnchor;

	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart arg0) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FRightSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FRightSignalFigure.ANCHOR_Y;
		return fixedAnchor;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = FRightSignalFigure.ANCHOR_X;
		fixedAnchor.offsetV = FRightSignalFigure.ANCHOR_Y;
		return fixedAnchor;

	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		final org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			@Override
			protected EditPolicy createChildEditPolicy(EditPart child) {
				final View childView = (View) child.getModel();
				switch (SafecapVisualIDRegistry.getVisualID(childView)) {
				case RightSignalLabelEditPart.VISUAL_ID:
					return new BorderItemSelectionEditPolicy() {

						@Override
						protected List createSelectionHandles() {
							final MoveHandle mh = new MoveHandle((GraphicalEditPart) getHost());
							mh.setBorder(null);
							return Collections.singletonList(mh);
						}
					};
				}
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
		return primaryShape = new RightSignalFigure();
	}

	/**
	 * @generated
	 */
	public RightSignalFigure getPrimaryShape() {
		return (RightSignalFigure) primaryShape;
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		if (borderItemEditPart instanceof RightSignalLabelEditPart) {
			final BorderItemLocator locator = new BorderItemLocator(getMainFigure(), PositionConstants.WEST);
			locator.setBorderItemOffset(new Dimension(1, 2));
			borderItemContainer.add(borderItemEditPart.getFigure(), locator);
		} else {
			super.addBorderItem(borderItemContainer, borderItemEditPart);
		}
	}

	/**
	 * @generated NOT
	 */
	protected NodeFigure createNodePlate() {
		final DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(FSignalFigure.WIDTH, FRightSignalFigure.HEIGHT);
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
	protected NodeFigure createMainFigure() {
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
	 * @generated
	 */
	@Override
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(SafecapVisualIDRegistry.getType(RightSignalLabelEditPart.VISUAL_ID));
	}

	/**
	 * @generated NOT
	 */
	public class RightSignalFigure extends FRightSignalFigure {

		/**
		 * @generated NOT
		 */
		public RightSignalFigure() {
			final EditPartViewer viewer = getViewer();
			final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();

			final Node node = (Node) getModel();
			final Signal signal = (Signal) node.getElement();
			setSignal(signal);
			setZoomManager(root.getZoomManager());
		}

	}

}
