package safecap.diagram.edit.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.KeyEvent;
import org.eclipse.draw2d.KeyListener;
import org.eclipse.draw2d.LayoutAnimator;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableLabelEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemsAwareFreeFormLayer;
import org.eclipse.gmf.runtime.diagram.ui.layout.FreeFormLayoutEx;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;

import safecap.Project;
import safecap.diagram.edit.policies.ProjectCanonicalEditPolicy;
import safecap.diagram.edit.policies.ProjectItemSemanticEditPolicy;
import safecap.diagram.providers.SafecapElementTypes;
import uk.ac.ncl.safecap.diagram.misc.diagramactions.CreateNewNode;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

/**
 * @generated NOT
 */
public class ProjectEditPart extends DiagramEditPart implements MouseListener, KeyListener {

	/**
	 * @generated
	 */
	public final static String MODEL_ID = "Safecap"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1000;

	/**
	 * @generated
	 */
	public ProjectEditPart(View view) {
		super(view);
		super.getFigure().addMouseListener(this);
		super.getContentPane().addKeyListener(this);
		super.getFigure().addKeyListener(this);

	}

	/**
	 * @generated NOT
	 */
	@Override
	protected IFigure createFigure() {
		final IFigure f = new BorderItemsAwareFreeFormLayer();
		f.setLayoutManager(new FreeFormLayoutEx());
		f.addLayoutListener(LayoutAnimator.getDefault());
		f.addKeyListener(this);
		f.setOpaque(true);
		return f;
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ProjectItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new ProjectCanonicalEditPolicy());
		removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
	}

	/**
	 * @generated
	 */
	/* package-local */static class NodeLabelDragPolicy extends NonResizableEditPolicy {

		/**
		 * @generated
		 */
		@Override
		@SuppressWarnings("rawtypes")
		protected List createSelectionHandles() {
			final MoveHandle h = new MoveHandle((GraphicalEditPart) getHost());
			h.setBorder(null);
			return Collections.singletonList(h);
		}

		/**
		 * @generated
		 */
		@Override
		public Command getCommand(Request request) {
			return null;
		}

		/**
		 * @generated
		 */
		@Override
		public boolean understandsRequest(Request request) {
			return false;
		}
	}

	/**
	 * @generated
	 */
	/* package-local */static class LinkLabelDragPolicy extends NonResizableLabelEditPolicy {

		/**
		 * @generated
		 */
		@Override
		@SuppressWarnings("rawtypes")
		protected List createSelectionHandles() {
			final MoveHandle mh = new MoveHandle((GraphicalEditPart) getHost());
			mh.setBorder(null);
			return Collections.singletonList(mh);
		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		// System.out.println("xxx");

//		if ((arg0.getState() & SafecapConstants.GET_EDITOR_CONTROL_KEY()) == SafecapConstants.GET_EDITOR_CONTROL_KEY()) {
//			Point loc = arg0.getLocation();
//			Project prj = (Project) this.getDiagramView().getDiagram().getElement();
//			CreateNewNode creator = new CreateNewNode(this, prj, loc);
//			creator.run();
//
//			List<IElementType> relationshipTypes = Collections.singletonList(SafecapElementTypes.Segment_4001);
//			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
//			tool.setEditDomain(this.getEditDomain());
//			tool.setViewer(getViewer());
//			// tool.activate();
//
//			this.getViewer().getEditDomain().setActiveTool(tool);
//			arg0.consume();
//
//		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {

		// System.out.println("state: " + arg0.getState());
		if ((arg0.getState() & SafecapConstants.GET_EDITOR_CONTROL_KEY()) == SafecapConstants.GET_EDITOR_CONTROL_KEY()) {
			final Point loc = arg0.getLocation();
			final Project prj = (Project) getDiagramView().getDiagram().getElement();
			final CreateNewNode creator = new CreateNewNode(this, prj, loc, null);
			creator.run();

			final List<IElementType> relationshipTypes = Collections.singletonList(SafecapElementTypes.Segment_4001);
			final Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setEditDomain(getEditDomain());
			tool.setViewer(getViewer());
			// tool.activate();

			getViewer().getEditDomain().setActiveTool(tool);
			arg0.consume();

		}
//		if ((arg0.getState() & SafecapConstants.GET_EDITOR_CONTROL_KEY()) == SafecapConstants.GET_EDITOR_CONTROL_KEY()) {
//			arg0.consume();
//		}
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// System.out.println("state: " + ke.keycode);
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub

	}

}
