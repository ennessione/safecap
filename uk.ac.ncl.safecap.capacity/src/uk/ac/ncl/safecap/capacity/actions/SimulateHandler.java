package uk.ac.ncl.safecap.capacity.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class SimulateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Project project = getSelectedModel(editor);
		if (project != null) {
			final SimulationJob sj = new SimulationJob(project);
			sj.schedule();
		}
		return null;
	}

	// private static void performanceTest(Project project)
	// {
	// try {
	// long start = System.currentTimeMillis();
	// SimulationProvider.simulationTest(1000, project);
	// long end = System.currentTimeMillis();
	// double run_time = ((double) (end - start)) / 1000d;
	// System.out.println("Run time:" + run_time);
	// } catch (SimulationException e) {
	// e.printStackTrace();
	// }
	// }

	private static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();

		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

}
