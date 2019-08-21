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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import safecap.Project;
import safecap.model.Line;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.LineUtil;

public class Clone implements IObjectActionDelegate {

	private ISelection selection;

	public Clone() {
	}

	@Override
	public void run(IAction action) {
		final Line element = getSelectedElements();

		if (element != null) {

			try {

				final AbstractTransactionalCommand command = getCloneCommand(element);
				if (command != null) {
					OperationHistoryFactory.getOperationHistory().execute(command, null, null);
				} else {
					cloneAction(element);
				}

				final Project project = EmfUtil.getProject(element);
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

	private Line getSelectedElements() {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.getFirstElement() instanceof Line) {
				return (Line) ssel.getFirstElement();
			}
		}
		return null;
	}

	private AbstractTransactionalCommand getCloneCommand(final Line element) {
		final Project root = EmfUtil.getProject(element);
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
		if (domain == null) {
			return null;
		}

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "CloneCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				cloneAction(element);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private void cloneAction(final Line element) {
		final Line new_line = safecap.model.ModelFactory.eINSTANCE.createLine();
		new_line.setOrientation(element.getOrientation());
		new_line.setVisible(true);
		new_line.getRoutes().addAll(element.getRoutes());

		final Project root = EmfUtil.getProject(element);
		new_line.setLabel(LineUtil.getUniqueName(root, element.getLabel()));
		root.getLines().add(new_line);
	}
}