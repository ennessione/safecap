package uk.ac.ncl.safecap.navigator.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import safecap.Project;
import uk.ac.ncl.safecap.diagram.misc.postprocess.BuildRoutes;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;

public class RecalculateLines implements IObjectActionDelegate {

	private ISelection selection;

	public RecalculateLines() {
	}

	@Override
	public void run(IAction action) {
		final Project project = getProject();

		if (project != null) {

			try {

				final AbstractTransactionalCommand command = getCloneCommand(project);
				if (command != null) {
					OperationHistoryFactory.getOperationHistory().execute(command, null, null);
				} else {
					recalculateLinesAction(project);
				}

				EmfUtil.saveProject(project);
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

	private Project getProject() {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.getFirstElement() instanceof IFile) {
				final IFile file = (IFile) ssel.getFirstElement();
				return EmfUtil.fromFile(file);
			}
		}
		return null;
	}

	private AbstractTransactionalCommand getCloneCommand(final Project project) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		if (domain == null) {
			return null;
		}

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "CloneCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				ExtensionUtil.delete(project, "uk.ac.ncl.safecap.importroutes");

				recalculateLinesAction(project);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private void recalculateLinesAction(final Project project) {
		project.getLines().clear();
		final BuildRoutes cmd = new BuildRoutes(null, project);
		cmd.refreshLines();
	}
}