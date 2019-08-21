package safecap.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;

import safecap.diagram.edit.policies.NodeWireItemSemanticEditPolicy;

/**
 * @generated
 */
public class NodeWireEditPart extends ConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4004;

	/**
	 * @generated
	 */
	public NodeWireEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new NodeWireItemSemanticEditPolicy());
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
	 * @generated
	 */
	public class WireFigureDes extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		public WireFigureDes() {
			setForegroundColor(ColorConstants.gray);

			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			final PolylineDecoration df = new PolylineDecoration();
			return df;
		}

	}

}
