package uk.ac.ncl.safecap.diagram.misc.actions;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import safecap.Project;
import safecap.schema.Node;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SubRouteSubOverlapDetection;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;

public class OverlapDelete extends AbstractHandler {
	public static final String PARAM_SIGNAL = "uk.ac.ncl.safecap.navigator.overlap.delete.param.signal";
	public static final String COMMAND_ID = "uk.ac.ncl.safecap.navigator.overlap.delete";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Node node = getCurrentEditorContent(editor);
		if (node != null) {
			final Project project = EmfUtil.getProject(node);
			final String param = event.getParameter(PARAM_SIGNAL);
			final String[] parts = param.split(":::");
			if (parts.length == 2) {
				final String signal = parts[0];
				final String overlap = parts[1];
				final Signal signalObject = SignalUtil.getByLabel(project, signal);
				if (signalObject != null) {
					SubRouteSubOverlapDetection.deleteOverlapTransactionally(signalObject, overlap);
				}
			}
		}
		return null;
	}

	public Node getCurrentEditorContent(IEditorPart activeEditor) {
		if (activeEditor instanceof DiagramEditor) {
			final DiagramEditor sde = (DiagramEditor) activeEditor;
			final List<?> selection = sde.getDiagramGraphicalViewer().getSelectedEditParts();
			if (selection.size() == 1 && selection.get(0) instanceof ShapeEditPart) {
				final ShapeEditPart node = (ShapeEditPart) selection.get(0);
				if (node.resolveSemanticElement() instanceof Node) {
					return (Node) node.resolveSemanticElement();
				}
			}
		}

		return null;
	}

}
