package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class VerificationAction extends AbstractHandler {
	public static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();

		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final Project project = getSelectedModel(editor);
		if (project != null && project.getLines().size() > 0) {
			String profileName = event.getParameter("uk.ac.ncl.safecap.gui.verification.commandParameter1");
			if (profileName == null) {
				profileName = getActiveProfileName();
			}
			IVerificationProfile profile;
			if (profileName == null || profileName.length() == 0) {
				return null;
			} else {
				final VerificationProfileRegistry registry = VerificationProfileRegistry.getInstance();
				registry.loadFromPrefs();
				profile = registry.get(profileName);
			}
			if (profile == null) {
				return null;
			}
			setActiveProfileName(profileName);
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			final VerificationDialog dialog = new VerificationDialog(shell, profile, project);
			dialog.create();
			dialog.open();
		}

		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		// TODO Auto-generated method stub
		super.setEnabled(evaluationContext);
	}

	public static String getActiveProfileName() {
		final Preferences preferences = InstanceScope.INSTANCE.getNode("uk.ac.ncl.safecap.gui.verification.ActiveProfile");
		return preferences.get("profile", null);
	}

	public static void setActiveProfileName(String name) {
		try {
			final Preferences preferences = InstanceScope.INSTANCE.getNode("uk.ac.ncl.safecap.gui.verification.ActiveProfile");
			preferences.clear();
			preferences.put("profile", name);
			preferences.flush();
		} catch (final BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
