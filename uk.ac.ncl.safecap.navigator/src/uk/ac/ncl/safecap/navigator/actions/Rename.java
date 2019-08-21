package uk.ac.ncl.safecap.navigator.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import safecap.Project;
import safecap.model.Line;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.LineUtil;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.ScopeC;

public class Rename implements IObjectActionDelegate {

	private ISelection selection;

	public Rename() {
	}

	@Override
	public void run(IAction action) {
		final safecap.Labeled element = getSelectedElements();

		if (element != null) {

			try {

				final InputDialog dlg = new InputDialog(null, "", "New label", element.getLabel(), new LabelValidator(element));

				if (dlg.open() == Window.OK) {
					final String new_name = dlg.getValue();

					if (new_name != null && new_name.length() > 0) {
						final AbstractTransactionalCommand command = getRenameCommand(element, new_name);

						if (command != null) {
							OperationHistoryFactory.getOperationHistory().execute(command, null, null);
						} else {
							renameAction(element, new_name);
						}

						final Project project = EmfUtil.getProject(element);
						EmfUtil.saveProject(project);
						final IResource resource = EmfUtil.getPlatformResource(project);
						if (resource != null) {
							resource.refreshLocal(IResource.DEPTH_INFINITE, null);
						}
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

	private AbstractTransactionalCommand getRenameCommand(final safecap.Labeled element, final String new_name) {
		final Project root = EmfUtil.getProject(element);
		assert root != null;

		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
		if (domain == null) {
			return null;
		}

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "RenameCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				renameAction(element, new_name);

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private void renameAction(final safecap.Labeled element, final String new_name) {
		element.setLabel(new_name);
	}
}

class LabelValidator implements IInputValidator {
	private final safecap.Labeled element;
	private final Project root;

	LabelValidator(safecap.Labeled element) {
		this.element = element;
		root = EmfUtil.getProject(element);
	}

	/**
	 * Validates the String. Returns null for no error, or an error message
	 * 
	 * @param newText the String to validate
	 * @return String
	 */
	@Override
	public String isValid(String newText) {

		if (newText.equals(element.getLabel())) {
			return null;
		}

		if (newText.length() == 0) {
			return "The name may not be empty";
		}

		if (!Character.isLetter(newText.charAt(0))) {
			return "The name may not start with a digit or a special character";
		}

		final Line l = LineUtil.getByLabel(root, newText);
		if (l != null) {
			return "The name conflicts with an existing line name";
		}

		EObject o = ScopeB.getByLabel(root, newText);
		if (o != null) {
			return "The name conflicts with an existing node or signal name";
		}

		o = ScopeC.getByLabel(root, newText);
		if (o != null) {
			return "The name conflicts with an existing segment name";
		}

		return null;
	}
}
