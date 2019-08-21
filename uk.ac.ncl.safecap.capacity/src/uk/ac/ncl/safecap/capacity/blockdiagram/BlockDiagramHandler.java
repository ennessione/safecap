package uk.ac.ncl.safecap.capacity.blockdiagram;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.capacity.experiment.ExperimentRegistry;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class BlockDiagramHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Shell shell =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		// InProgressDialog dialog = new InProgressDialog(shell);
		// dialog.open();
		//

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Project project = getSelectedModel(editor);
		if (project != null) {
			final SimulationExperiment exp = ExperimentRegistry.getInstance().getExperiment(project);
			if (exp != null) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final BlockDiagramDialog dialog = new BlockDiagramDialog(shell, exp);
				dialog.open();
			}
		}
		return null;
	}

	private static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();

		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

	// @Override
	// public boolean isEnabled()
	// {
	// return false;
	// }
}
