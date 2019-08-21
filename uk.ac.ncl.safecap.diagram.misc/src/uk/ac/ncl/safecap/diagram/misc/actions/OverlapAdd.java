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
import uk.ac.ncl.safecap.mcommon.conf.SubOverlapBuilder;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class OverlapAdd extends AbstractHandler {
	public static final String PARAM_SIGNAL = "uk.ac.ncl.safecap.navigator.overlap.add.param.signal";
	public static final String COMMAND_ID = "uk.ac.ncl.safecap.navigator.overlap.add";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Node node = getCurrentEditorContent(editor);
		if (node != null) {
			final Project project = EmfUtil.getProject(node);
			String signalName = event.getParameter(PARAM_SIGNAL);
			int isFull = -1;
			if (signalName.contains("///")) {
				final int pos = signalName.indexOf("///");
				final String arg = signalName.substring(pos + 3);
				signalName = signalName.substring(0, pos);
				if (arg.equals("full")) {
					isFull = 1;
				} else {
					isFull = 0;
				}
			}
			// System.out.println("Add overlap from signal " + signalName + " to node " +
			// node.getLabel());
			final Signal signal = SignalUtil.getByLabel(project, signalName);
			final List<SubRoute> path = SubRouteUtil.getSubRoutePath(signal, node);
			int length = SignalUtil.getSignalOffsetFromJoint(project, signal);
			for (final SubRoute sr : path) {
				length += sr.getLength();
			}

			SubOverlapBuilder.commitOverlapTransactionally(signal, path, length, isFull);
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
