package uk.ac.ncl.safecap.diagram.misc.copypaste;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

public class MyEditorPasteCommand extends AbstractCommand implements ICommand {
	private final IGraphicalEditPart targetPart;
	private final EObject target;
//	private Map<EObject, EObject> 	objects;
//	private Map<EObject, EditPart> 	parts;

	public MyEditorPasteCommand(String label, IGraphicalEditPart targetPart) {
		super(label);
		target = targetPart.resolveSemanticElement();
		this.targetPart = targetPart;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

		final List<ObjectCopy> newObjects = MyEditorCopyCommand.getCopied();

		final TransactionalEditingDomain domain = (TransactionalEditingDomain) AdapterFactoryEditingDomain.getEditingDomainFor(target);

		final PasteTransactionalCommand pasteCmd = new PasteTransactionalCommand(domain, newObjects, target, this);
		final LayoutTransactionalCommand layoutCmd = new LayoutTransactionalCommand(domain, newObjects, targetPart, this);

		final CompositeCommand compc = new CompositeCommand("Paste(composite)");
		compc.add(pasteCmd);
		compc.add(layoutCmd);

		OperationHistoryFactory.getOperationHistory().execute(compc, progressMonitor, info);

		// compc.execute(progressMonitor, info);

		// pasteCmd.execute(progressMonitor, info);

		return CommandResult.newOKCommandResult();
	}

	/**
	 * @generated
	 */
	@Override
	public boolean canExecute() {
		return MyEditorCopyCommand.getCopied().size() != 0;

	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

}
