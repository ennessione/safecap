package uk.ac.ncl.safecap.navigator.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import safecap.Labeled;
import safecap.Project;
import safecap.model.Line;
import safecap.model.Route;
import uk.ac.ncl.safecap.diagram.misc.postprocess.ModelCleanup;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class Delete implements IObjectActionDelegate {

	private ISelection selection;

	public Delete() {
	}

	@Override
	public void run(IAction action) {
		final Labeled element = getSelectedElements();

		if (element != null) {

			try {

				if (MessageDialog.openConfirm(null, "Element deletion", "Really remove " + element.getLabel() + "?")) {

					final Project project = EmfUtil.getProject(element);

					final AbstractTransactionalCommand command = getDeleteCommand(element);
					if (command != null) {
						OperationHistoryFactory.getOperationHistory().execute(command, null, null);
					} else {
						deleteAction(element);
					}

					EmfUtil.saveProject(project);
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	private safecap.Labeled getSelectedElements() {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.getFirstElement() instanceof safecap.Labeled) {
				return (safecap.Labeled) ssel.getFirstElement();
			}
		}
		return null;
	}

	private AbstractTransactionalCommand getDeleteCommand(final Labeled element) {
		final Project root = EmfUtil.getProject(element);
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
		if (domain == null) {
			return null;
		}

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "DeleteCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				deleteAction(element);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private void deleteAction(final Labeled element) {
		final Project root = EmfUtil.getProject(element);
		if (element instanceof Line) {
			root.getLines().remove(element);
		} else if (element instanceof Route) {
			root.getRoutes().remove(element);
			final ModelCleanup cleaner = new ModelCleanup(root);
			cleaner.doCleanUp();
		}
	}
}
