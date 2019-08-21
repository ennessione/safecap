package uk.ac.ncl.safecap.xmldata.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.diagram.misc.postprocess.PostUtil;
import uk.ac.ncl.safecap.mcommon.conf.SchemaConfig;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class ResetSchema extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Project root = getSelectedModel(editor);
		if (root != null) {
			IGraphicalEditPart project = null;
			if (editor != null && editor instanceof DiagramEditor) {
				final DiagramEditor sc_editor = (DiagramEditor) editor;
				project = sc_editor.getDiagramEditPart();
			}

			final SchemaConfig schemaConfig = new SchemaConfig(root);
			schemaConfig.getCommandReset().execute(null, null);

			PostUtil.standardPostProcessor(project, root, null);

			EmfUtil.saveProject(root);
			UIUtils.forceRefreshSafecapEditor(editor);
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

}
