package uk.ac.ncl.safecap.diagram.misc.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import safecap.Project;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SubRouteSubOverlapDetection;

public class OverlapsGenerate extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final BMap<Project, IGraphicalEditPart> projectInfo = getSelectedModel(editor);
		if (projectInfo != null) {
			SubRouteSubOverlapDetection.buildOverlaps(projectInfo.prj2(), projectInfo.prj1(), null);
		}
		return null;
	}

	private static BMap<Project, IGraphicalEditPart> getSelectedModel(IEditorPart editor) {
		if (editor instanceof DiagramEditor) {
			final DiagramEditor deditor = (DiagramEditor) editor;
			deditor.getDiagramEditPart();
			return new BMap<>((Project) deditor.getDiagram().getElement(), deditor.getDiagramEditPart());

		}
		return null;
	}
}