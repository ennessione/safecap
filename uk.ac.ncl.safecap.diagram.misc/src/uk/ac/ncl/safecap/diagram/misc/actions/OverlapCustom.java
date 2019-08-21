package uk.ac.ncl.safecap.diagram.misc.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import safecap.Orientation;
import safecap.Project;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.diagram.misc.actions.OverlapsDynamicMenu.PotentialOverlap;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SubRouteSubOverlapDetection;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ISegmentWalkerFilter2;
import uk.ac.ncl.safecap.misc.core.SegmentPath;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.SubRoute;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class OverlapCustom extends AbstractHandler {
	public static final String COMMAND_ID = "uk.ac.ncl.safecap.navigator.overlap.custom";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Node node = getCurrentEditorContent(editor);
		if (node != null) {
			final Project project = EmfUtil.getProject(node);
			final InputDialog dlg = new InputDialog(null, "", "Signal name", "", new SignalValidator(project, node));
			if (dlg.open() == Window.OK) {
				final String signalLabel = dlg.getValue();
				final Signal signal = SignalUtil.getByLabel(project, signalLabel);
				if (signal != null) {
					final List<PotentialOverlap> list = computeOverlaps(node, signal);
					if (list != null && list.size() == 1) {
						final PotentialOverlap overlap = list.get(0);
						final List<SubRoute> path = SubRouteUtil.getSubRoutePath(signal, node);
						int length = SignalUtil.getSignalOffsetFromJoint(project, signal);
						for (final SubRoute sr : path) {
							length += sr.getLength();
						}

						int tag = -1;
						if (!overlap.isIgnoreLength()) {
							tag = overlap.isFull() ? 1 : 0;
						}

						MessageDialog.openConfirm(null, "Custom overlap", "Add overlap " + path.toString() + "?");

						SubRouteSubOverlapDetection.commitOverlapTransactionally(signal, path, length, tag);
					}
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

	private static List<PotentialOverlap> computeOverlaps(Node node, Signal signal) {
		final OverlapFilterCustom filter = new OverlapFilterCustom(signal);

		final List<PotentialOverlap> part1 = OverlapsDynamicMenu.compute(node, Orientation.LEFT, filter);
		final List<PotentialOverlap> part2 = OverlapsDynamicMenu.compute(node, Orientation.RIGHT, filter);
		part1.addAll(part2);
		
		Set<PotentialOverlap> set = new HashSet<PotentialOverlap>(part1);
		
		return new ArrayList<PotentialOverlap>(set);
	}

	private static class SignalValidator implements IInputValidator {
		private final Project project;
		private final Node node;

		public SignalValidator(Project project, Node node) {
			this.project = project;
			this.node = node;
		}

		@Override
		public String isValid(String newText) {
			final Signal signal = SignalUtil.getByLabel(project, newText);
			if (signal == null) {
				return "Not a valid signal name";
			}

			final List<PotentialOverlap> part1 = computeOverlaps(node, signal);

			if (part1.isEmpty()) {
				return "No path to this signal";
			}

			if (part1.size() != 1) {
				return "Ambigous paths: " + part1.toString();
			}

			return null;
		}

	}

	private static class OverlapFilterCustom implements ISegmentWalkerFilter2 {
		private final Signal signal;

		OverlapFilterCustom(Signal signal) {
			this.signal = signal;
		}

		@Override
		public boolean accept(SegmentPath path, Segment current) {
			return SignalUtil.getSignalOn(path.getLast(), path.getOrientation().opposite()) != signal;
		}
	}
}
