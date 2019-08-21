package safecap.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import safecap.diagram.edit.commands.NodeWireCreateCommand;
import safecap.diagram.edit.commands.NodeWireReorientCommand;
import safecap.diagram.edit.commands.WireCreateCommand;
import safecap.diagram.edit.commands.WireReorientCommand;
import safecap.diagram.edit.parts.NodeWireEditPart;
import safecap.diagram.edit.parts.WireEditPart;
import safecap.diagram.part.SafecapVisualIDRegistry;
import safecap.diagram.providers.SafecapElementTypes;

/**
 * @generated
 */
public class LeftSpeedLimitItemSemanticEditPolicy extends SafecapBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public LeftSpeedLimitItemSemanticEditPolicy() {
		super(SafecapElementTypes.LeftSpeedLimit_2006);
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		final View view = (View) getHost().getModel();
		final CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(getEditingDomain(), null);
		cmd.setTransactionNestingEnabled(false);
		for (final Iterator<?> it = view.getSourceEdges().iterator(); it.hasNext();) {
			final Edge outgoingLink = (Edge) it.next();
			if (SafecapVisualIDRegistry.getVisualID(outgoingLink) == WireEditPart.VISUAL_ID) {
				final DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (SafecapVisualIDRegistry.getVisualID(outgoingLink) == NodeWireEditPart.VISUAL_ID) {
				final DestroyElementRequest r = new DestroyElementRequest(outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
		}
		final EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation == null) {
			// there are indirectly referenced children, need extra commands: false
			addDestroyShortcutsCommand(cmd, view);
			// delete host element
			cmd.add(new DestroyElementCommand(req));
		} else {
			cmd.add(new DeleteCommand(getEditingDomain(), view));
		}
		return getGEFWrapper(cmd.reduce());
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
			return getGEFWrapper(new WireCreateCommand(req, req.getSource(), req.getTarget()));
		}
		if (SafecapElementTypes.NodeWire_4004 == req.getElementType()) {
			return getGEFWrapper(new NodeWireCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (SafecapElementTypes.Wire_4002 == req.getElementType()) {
			return null;
		}
		if (SafecapElementTypes.NodeWire_4004 == req.getElementType()) {
			return null;
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
		case NodeWireEditPart.VISUAL_ID:
			return getGEFWrapper(new NodeWireReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
