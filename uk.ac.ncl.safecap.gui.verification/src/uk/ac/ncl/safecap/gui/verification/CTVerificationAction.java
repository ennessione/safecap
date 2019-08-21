package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class CTVerificationAction {

	public static void execute(EObject target, String profile_name) throws ExecutionException {
		if (profile_name == null || profile_name.length() == 0 || target == null) {
			return;
		}

		final VerificationProfileRegistry registry = VerificationProfileRegistry.getInstance();
		registry.loadFromPrefs();

		final IVerificationProfile profile = registry.get(profile_name);
		if (profile == null) {
			MessageDialog.openError(null, "Verification aborted", "Default profile '" + profile_name + "' is undefined.");
			return;
		}

		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final VerificationDialog dialog = new VerificationDialog(shell, profile, target);
		dialog.create();
		dialog.open();
	}

}
