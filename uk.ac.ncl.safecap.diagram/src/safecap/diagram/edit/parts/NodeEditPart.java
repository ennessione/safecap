package safecap.diagram.edit.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PositionConstants;
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
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import safecap.diagram.edit.policies.NodeItemSemanticEditPolicy;
import safecap.diagram.part.SafecapVisualIDRegistry;
import uk.ac.ncl.safecap.diagram.misc.diagramactions.DissolveNode;
import uk.ac.ncl.safecap.diagram.misc.parts.FNode;
import uk.ac.ncl.safecap.diagram.misc.visual.FixedConnectionAnchor;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackConstants;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

/**
 * @generated
 */
public class NodeEditPart extends AbstractBorderedShapeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2003;

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
	public NodeEditPart(View view) {
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
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new NodeItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart arg0) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = TrackConstants.NODE_WIDTH / 2;
		fixedAnchor.offsetV = TrackConstants.NODE_HEIGHT / 2;
		// it creates an anchor for source on point 120,40
		return fixedAnchor;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {

		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = TrackConstants.NODE_WIDTH / 2;
		fixedAnchor.offsetV = TrackConstants.NODE_HEIGHT / 2;

		return fixedAnchor;

	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart arg0) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = TrackConstants.NODE_WIDTH / 2;
		fixedAnchor.offsetV = TrackConstants.NODE_HEIGHT / 2;

		return fixedAnchor;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		final FixedConnectionAnchor fixedAnchor = new FixedConnectionAnchor(getFigure());
		fixedAnchor.offsetH = TrackConstants.NODE_WIDTH / 2;
		fixedAnchor.offsetV = TrackConstants.NODE_HEIGHT / 2;

		return fixedAnchor;

	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		final FlowLayoutEditPolicy lep = new FlowLayoutEditPolicy() {

			@Override
			protected EditPolicy createChildEditPolicy(EditPart child) {
				final View childView = (View) child.getModel();
				switch (SafecapVisualIDRegistry.getVisualID(childView)) {
				case NodeLabelEditPart.VISUAL_ID:
					return new BorderItemSelectionEditPolicy() {

						@Override
						protected List createSelectionHandles() {
							final MoveHandle mh = new MoveHandle((GraphicalEditPart) getHost());
							mh.setBorder(null);
							return Collections.singletonList(mh);
						}
					};
				}
				return super.createChildEditPolicy(child);
			}

			@Override
			protected Command createAddCommand(EditPart child, EditPart after) {
				return null;
			}

			@Override
			protected Command createMoveChildCommand(EditPart child, EditPart after) {
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
		return primaryShape = new NormalNodeFigure();
	}

	/**
	 * @generated
	 */
	public NormalNodeFigure getPrimaryShape() {
		return (NormalNodeFigure) primaryShape;
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		if (borderItemEditPart instanceof NodeLabelEditPart) {
			final BorderItemLocator locator = new BorderItemLocator(getMainFigure(), PositionConstants.NORTH);
			locator.setBorderItemOffset(new Dimension(5, -5));
			borderItemContainer.add(borderItemEditPart.getFigure(), locator);
		} else {
			super.addBorderItem(borderItemContainer, borderItemEditPart);
		}
	}

	/**
	 * @generated NOT
	 */
	protected NodeFigure createNodePlate() {
		final DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(TrackConstants.NODE_WIDTH, TrackConstants.NODE_HEIGHT);
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
	 * @generated NOT
	 */
	@Override
	protected void setBackgroundColor(Color color) {
		// if (primaryShape != null) {
		// primaryShape.setBackgroundColor(color);
		// }
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void setLineWidth(int width) {
		// if (primaryShape instanceof Shape) {
		// ((Shape) primaryShape).setLineWidth(width);
		// }
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void setLineType(int style) {
		// if (primaryShape instanceof Shape) {
		// ((Shape) primaryShape).setLineStyle(style);
		// }
	}

	/**
	 * @generated
	 */
	@Override
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(SafecapVisualIDRegistry.getType(NodeLabelEditPart.VISUAL_ID));
	}

	/**
	 * @generated NOT
	 */
	public class NormalNodeFigure extends FNode implements MouseListener {

		/**
		 * @generated NOT
		 */
		public NormalNodeFigure() {
			super();
			addMouseListener(this);
			setLayoutManager(new FlowLayout());

			final Node node = (Node) getModel();
			if (node.getElement() instanceof safecap.schema.Node) {
				final safecap.schema.Node n = (safecap.schema.Node) node.getElement();
				final EditPartViewer viewer = getViewer();
				final RenderedDiagramRootEditPart root = (RenderedDiagramRootEditPart) viewer.getRootEditPart();
				setNode(n);
				setZoomManager(root.getZoomManager());
			}
			// this.setAlpha(128);
		}

		/**
		 * @generated NOT
		 */
		@Override
		public void mouseDoubleClicked(MouseEvent arg0) {
		}

		/**
		 * @generated NOT
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {
			if ((arg0.getState() & SafecapConstants.GET_EDITOR_CONTROL_KEY()) == SafecapConstants.GET_EDITOR_CONTROL_KEY()) {
				final Node n = (Node) getModel();
				final safecap.schema.Node node = (safecap.schema.Node) n.getElement();
				final DissolveNode action = new DissolveNode(NodeEditPart.this, node);
				action.run();
				arg0.consume();
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

}
