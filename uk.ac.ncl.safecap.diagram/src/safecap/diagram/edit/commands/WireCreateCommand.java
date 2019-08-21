package safecap.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import safecap.diagram.edit.policies.SafecapBaseItemSemanticEditPolicy;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Trackside;
import safecap.trackside.TracksideFactory;
import safecap.trackside.Wire;

/**
 * @generated
 */
public class WireCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	private final Trackside container;

	/**
	 * @generated
	 */
	public WireCreateCommand(CreateRelationshipRequest request, EObject source, EObject target) {
		super(request.getLabel(), null, request);
		this.source = source;
		this.target = target;
		container = deduceContainer(source, target);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof Equipment) {
			return false;
		}
		if (target != null && false == target instanceof Segment) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		// target may be null here but it's possible to check constraint
		if (getContainer() == null) {
			return false;
		}
		return SafecapBaseItemSemanticEditPolicy.getLinkConstraints().canCreateWire_4002(getContainer(), getSource(), getTarget());
	}

	/**
	 * @generated
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in create link command"); //$NON-NLS-1$
		}

		final Wire newElement = TracksideFactory.eINSTANCE.createWire();
		getContainer().getWires().add(newElement);
		newElement.setFrom(getSource());
		newElement.setTo(getTarget());
		doConfigure(newElement, monitor, info);
		((CreateElementRequest) getRequest()).setNewElement(newElement);
		return CommandResult.newOKCommandResult(newElement);

	}

	/**
	 * @generated
	 */
	protected void doConfigure(Wire newElement, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		final IElementType elementType = ((CreateElementRequest) getRequest()).getElementType();
		final ConfigureRequest configureRequest = new ConfigureRequest(getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest()).getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		configureRequest.setParameter(CreateRelationshipRequest.SOURCE, getSource());
		configureRequest.setParameter(CreateRelationshipRequest.TARGET, getTarget());
		final ICommand configureCommand = elementType.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected Equipment getSource() {
		return (Equipment) source;
	}

	/**
	 * @generated
	 */
	protected Segment getTarget() {
		return (Segment) target;
	}

	/**
	 * @generated
	 */
	public Trackside getContainer() {
		return container;
	}

	/**
	 * Default approach is to traverse ancestors of the source to find instance of
	 * container. Modify with appropriate logic.
	 * 
	 * @generated
	 */
	private static Trackside deduceContainer(EObject source, EObject target) {
		// Find container element for the new link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null; element = element.eContainer()) {
			if (element instanceof Trackside) {
				return (Trackside) element;
			}
		}
		return null;
	}

}
