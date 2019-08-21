package uk.ac.ncl.safecap.gui.verificationreplay;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import safecap.Project;
import uk.ac.ncl.safecap.gui.verification.VerificationAction;

public class ReplayBack extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
//    	Project project = getProject();
//    	CounterExample ce = CounterExampleRegister.getCounterExample(project);
//    	if (ce != null && ce.getPrev() != null)
//    		return super.isEnabled();
//    	else
		return false;
	}

	@Override
	public void setEnabled(Object object) {
		// suppress
	}

	private Project getProject() {
		final IWorkbench wb = PlatformUI.getWorkbench();
		final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		if (win == null) {
			return null;
		}

		final IWorkbenchPage page = win.getActivePage();
		if (page == null) {
			return null;
		}

		final IEditorPart editor = page.getActiveEditor();
		if (editor == null) {
			return null;
		}

		return VerificationAction.getSelectedModel(editor);
	}
}
