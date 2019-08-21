package safecap.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;

import safecap.diagram.edit.policies.SafecapBaseItemSemanticEditPolicy;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Trackside;
import safecap.trackside.Wire;

/**
 * @generated
 */
public class WireReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public WireReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	@Override
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof Wire) {
			return false;
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof Equipment && newEnd instanceof Equipment)) {
			return false;
		}
		final Segment target = getLink().getTo();
		if (!(getLink().eContainer() instanceof Trackside)) {
			return false;
		}
		final Trackside container = (Trackside) getLink().eContainer();
		return SafecapBaseItemSemanticEditPolicy.getLinkConstraints().canExistWire_4002(container, getLink(), getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof Segment && newEnd instanceof Segment)) {
			return false;
		}
		final Equipment source = getLink().getFrom();
		if (!(getLink().eContainer() instanceof Trackside)) {
			return false;
		}
		final Trackside container = (Trackside) getLink().eContainer();
		return SafecapBaseItemSemanticEditPolicy.getLinkConstraints().canExistWire_4002(container, getLink(), source, getNewTarget());
	}

	/**
	 * @generated
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setFrom(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTo(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected Wire getLink() {
		return (Wire) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected Equipment getOldSource() {
		return (Equipment) oldEnd;
	}

	/**
	 * @generated
	 */
	protected Equipment getNewSource() {
		return (Equipment) newEnd;
	}

	/**
	 * @generated
	 */
	protected Segment getOldTarget() {
		return (Segment) oldEnd;
	}

	/**
	 * @generated
	 */
	protected Segment getNewTarget() {
		return (Segment) newEnd;
	}
}
