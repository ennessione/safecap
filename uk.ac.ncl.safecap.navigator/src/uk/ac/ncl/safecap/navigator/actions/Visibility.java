package uk.ac.ncl.safecap.navigator.actions;

import org.eclipse.core.commands.ExecutionException;
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
import safecap.model.Line;
import uk.ac.ncl.safecap.diagram.misc.visibility.HideReveal;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class Visibility implements IObjectActionDelegate {

	private ISelection selection;

	public Visibility() {
	}

	@Override
	public void run(IAction action) {
		final safecap.Visual element = getSelectedElements();

		if (element != null) {
			// System.out.println("Hide/reveal " + element);
			try {
				final Project root = EmfUtil.getProject(element);

				if (root != null) {

					if (element instanceof Line) {
						HideReveal.hideLine(root, (Line) element);
					}

				}

			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		final safecap.Visual element = getSelectedElements();
		if (element != null) {
			action.setChecked(element.isVisible());
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	private safecap.Visual getSelectedElements() {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.getFirstElement() instanceof safecap.Visual) {
				return (safecap.Visual) ssel.getFirstElement();
			}
		}
		return null;
	}

	private AbstractTransactionalCommand getVisibilityCommand(final safecap.Visual element) {
		final Project root = EmfUtil.getProject(element);
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "VisibilityCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				element.setVisible(!element.isVisible());

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

}
