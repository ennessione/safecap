package safecap.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import safecap.diagram.edit.commands.WireCreateCommand;
import safecap.diagram.edit.commands.WireReorientCommand;
import safecap.diagram.edit.parts.WireEditPart;
import safecap.diagram.providers.SafecapElementTypes;

/**
 * @generated
 */
public class SegmentItemSemanticEditPolicy extends SafecapBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public SegmentItemSemanticEditPolicy() {
		super(SafecapElementTypes.Segment_4001);
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getGEFWrapper(new DestroyElementCommand(req));
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		final Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (SafecapElementTypes.Wire_4002 == req.getElementType()) {
			return null;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (SafecapElementTypes.Wire_4002 == req.getElementType()) {
			return getGEFWrapper(new WireCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	@Override
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case WireEditPart.VISUAL_ID:
			return getGEFWrapper(new WireReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
