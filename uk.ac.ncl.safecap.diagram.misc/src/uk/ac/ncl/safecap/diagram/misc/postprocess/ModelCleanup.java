package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Line;
import safecap.model.Route;
import safecap.schema.RouteSpeed;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.misc.core.LineUtil;

public class ModelCleanup {
	private final Project root;

	public ModelCleanup(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final ModelCleanup cmd = new ModelCleanup(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		try {
			// getModelCleanupCommand().execute(monitor, null);
			OperationHistoryFactory.getOperationHistory().execute(getModelCleanupCommand(), monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	public AbstractTransactionalCommand getModelCleanupCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "ModelCleanup", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				doCleanUp();

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	public void doCleanUp() {
		final List<Segment> toremove_segments = new ArrayList<>(20);
		for (final Segment segment : root.getSegments()) {
			if (segment.getTo() == null || segment.getFrom() == null) {
				toremove_segments.add(segment);
			}
		}
		root.getSegments().removeAll(toremove_segments);

		final List<Wire> toremove_wires = new ArrayList<>();
		for (final Wire wire : root.getWires()) {
			if (wire.getTo() == null || wire.getFrom() == null) {
				toremove_wires.add(wire);
			}
		}
		root.getWires().removeAll(toremove_wires);

		// clean up speed map
		final List<RouteSpeed> toremove_slimit = new ArrayList<>(10);
		for (final Segment s : root.getSegments()) {
			for (final RouteSpeed sl : s.getSpeed()) {
				if (sl.getRoute() == null || !root.getRoutes().contains(sl.getRoute())) {
					toremove_slimit.add(sl);
				}
			}

			s.getSpeed().removeAll(toremove_slimit);
			toremove_slimit.clear();
		}

		// clean up lines with stale routes
		final List<Line> toremove_lines = new ArrayList<>();
		for (final Line l : root.getLines()) {
			for (final Route r : l.getRoutes()) {
				if (r == null || !root.getRoutes().contains(r)) {
					toremove_lines.add(l);
				}
			}
		}
		root.getLines().removeAll(toremove_lines);

		// clean up ambit deleted segments
		for (final Ambit a : root.getAmbits()) {
			toremove_segments.clear();
			for (final Segment s : a.getSegments()) {
				if (!root.getSegments().contains(s)) {
					toremove_segments.add(s);
				}
			}
			a.getSegments().removeAll(toremove_segments);
		}

		// simplify line names
		for (final Line l : root.getLines()) {
			final String label = l.getLabel();
			final int underscore = label.lastIndexOf('_');
			if (underscore != -1) {
				final String trail = label.substring(underscore + 1);
				if (trail.length() > 0 && Character.isDigit(trail.charAt(0))) {
					final String trimmed = label.substring(0, underscore);
					if (LineUtil.getByLabel(root, trimmed) == null) {
						l.setLabel(trimmed);
					}
				}
			}
		}

		// recompute line attachments
		for (final Equipment eqp : root.getEquipment()) {
			if (eqp instanceof SpeedLimit) {
				final SpeedLimit sl = (SpeedLimit) eqp;
				recomputeLines(root, sl.getLine());
			} else if (eqp instanceof StopAndGo) {
				final StopAndGo sl = (StopAndGo) eqp;
				recomputeLines(root, sl.getLine());
			}
		}

		final List<Ambit> toremove_ambit = new ArrayList<>();
		for (final Ambit a : root.getAmbits()) {
			if (a.getSegments().size() == 0) {
				toremove_ambit.add(a);
			}
		}
		root.getAmbits().removeAll(toremove_ambit);

	}

	private void recomputeLines(Project root, EList<Line> lines) {
		final List<Line> to_remove = new ArrayList<>();
		final List<Line> to_add = new ArrayList<>();

		for (final Line l : lines) {
			if (!root.getLines().contains(l)) {
				if (l != null && l.getLabel() != null) {
					Line nl = LineUtil.getByLabel(root, l.getLabel());
					if (nl != null) {
						to_add.add(nl);
					} else {
						nl = LineUtil.getByLabelPrefix(root, l.getLabel());
						if (nl != null) {
							to_add.add(nl);
						}
					}
				}
				to_remove.add(l);
			}
		}

		lines.removeAll(to_remove);
		lines.addAll(to_add);
	}

}
