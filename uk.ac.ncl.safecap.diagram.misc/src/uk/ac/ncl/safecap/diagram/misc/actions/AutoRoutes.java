package uk.ac.ncl.safecap.diagram.misc.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import uk.ac.ncl.safecap.diagram.misc.postprocess.BuildRoutes;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class AutoRoutes extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final DiagramEditPart diagram = getSelectedDiagram(editor);
		final Project project = getSelectedModel(editor);
		if (project != null && diagram != null) {
			BuildRoutes.doAutoRoutes(diagram, project, null);
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

	private static DiagramEditPart getSelectedDiagram(IEditorPart editor) {
		if (editor != null && editor instanceof DiagramDocumentEditor) {
			final DiagramDocumentEditor sc_editor = (DiagramDocumentEditor) editor;
			final DiagramEditPart diagram = sc_editor.getDiagramEditPart();
			return diagram;
		}

		return null;
	}

}