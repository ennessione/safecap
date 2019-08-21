package uk.ac.ncl.safecap.diagram.misc.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.diagram.misc.highlights.Highlight;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class ResetHighlightsHandler extends AbstractHandler {
	private static Project getSelectedModel(IEditorPart editor) {
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
			Highlight.reset(project);
		}
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		// TODO Auto-generated method stub
		super.setEnabled(evaluationContext);
	}
}
