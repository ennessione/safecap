package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.derived.MergedJunction;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.ModelFactory;
import safecap.model.Point;
import safecap.model.Section;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class SegmentAmbitLink {
	private final Project root;
	private Set<Segment> processed;

	public SegmentAmbitLink(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final SegmentAmbitLink cmd = new SegmentAmbitLink(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		// CompositeCommand compc = new CompositeCommand("SegmentAmbitLink");
		// compc.add(getRefreshNodeKindAndRoleCommand());
		try {
			// OperationHistoryFactory.getOperationHistory().execute(compc,null, null);
			// getRefreshNodeKindAndRoleCommand().execute(monitor, null);
			OperationHistoryFactory.getOperationHistory().execute(getRefreshNodeKindAndRoleCommand(), monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getRefreshNodeKindAndRoleCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "SegmentAmbitLink", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				processed = new HashSet<>(root.getSegments().size());

				// remove invalid sections and junctions
				final List<Ambit> to_remove = new ArrayList<>();
				for (final Ambit ambit : root.getAmbits()) {
					if (ambit instanceof Junction) {
						final Junction junction = (Junction) ambit;
						if (junction.getPoints().size() == 0) {
							to_remove.add(junction);
							continue;
						}

						for (final Point p : junction.getPoints()) {
							if (p.getNode() != null && !NodeUtil.isJunctionNode(p.getNode())) {
								to_remove.add(junction);
								break;
							}
						}
					}

					if (ambit.getSegments().size() > 0) { // check for a malformed ambit ending on silent nodes
						for (final Segment s : ambit.getSegments()) {
							final Node a = s.getFrom();
							final Node b = s.getTo();

							if (a == null || b == null) {
								continue;
							}

							if (a.getKind() == NodeKind.SILENT) {
								boolean ok = false;
								for (final Segment t : ambit.getSegments()) {
									if (t.getTo() == a) {
										ok = true;
										break;
									}
								}

								if (!ok) {
									to_remove.add(ambit);
									break;
								}
							}

							if (b.getKind() == NodeKind.SILENT) {
								boolean ok = false;
								for (final Segment t : ambit.getSegments()) {
									if (t.getFrom() == a) {
										ok = true;
										break;
									}
								}

								if (!ok) {
									to_remove.add(ambit);
									break;
								}
							}

						}
					} else {
						to_remove.add(ambit);
					}
				}
				root.getAmbits().removeAll(to_remove);
				// System.out.println("1.B: has TPLH ambit: " + AmbitUtil.getByLabel(root,
				// "TPLH"));

				for (final Segment segment : root.getSegments()) {
					recalcLink(segment);
				}

				// System.out.println("1.C: has TPLH ambit: " + AmbitUtil.getByLabel(root,
				// "TPLH"));

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private void recalcLink(Segment segment) {
		// System.out.println("1.B.+: " + segment.getLabel() + " has TPLH ambit: " +
		// AmbitUtil.getByLabel(root, "TPLH"));
		if (processed.contains(segment)) {
			return;
		}

		if (SegmentUtil.isJunctionPart(segment)) {
			recalcJunction(segment);
		} else {
			Ambit ambit = SegmentUtil.getSegmentAmbit(segment);
			if (ambit == null) {
				ambit = getOpenAmbit(segment);
				if (ambit != null) {
					// add to an existing ambit
					ambit.getSegments().add(segment);
				} else {
					// create new section for the segment
					final Section section = ModelFactory.eINSTANCE.createSection();
					section.setLabel(segment.getLabel());
					section.getSegments().add(segment);
					root.getAmbits().add(section);
				}
			}
		}

		// System.out.println("1.B.-: " + segment.getLabel() + " has TPLH ambit: " +
		// AmbitUtil.getByLabel(root, "TPLH"));

	}

	private Ambit getOpenAmbit(Segment segment) {
		if (SegmentUtil.isLeftOpen(segment)) {
			final List<Segment> segs = NodeUtil.getSegments(segment.getFrom());
			for (final Segment s : segs) {
				final Ambit a = SegmentUtil.getSegmentAmbit(s);
				if (a != null) {
					return a;
				}
			}
		} else if (SegmentUtil.isRightOpen(segment)) {
			final List<Segment> segs = NodeUtil.getSegments(segment.getTo());
			for (final Segment s : segs) {
				final Ambit a = SegmentUtil.getSegmentAmbit(s);
				if (a != null) {
					return a;
				}
			}
		}

		return null;
	}

	private void recalcJunction(Segment segment) {
		final List<Segment> junction_seg = SegmentUtil.getRelated(segment);
		final List<Ambit> ambits = SegmentUtil.getSegmentAmbits(junction_seg);

		// leave alone merged junctions
		if (ambits.size() == 1) {
			final Ambit ambit = ambits.get(0);
			if (ambit instanceof MergedJunction) {
				return;
			}
		}

		// test if there an ambit that is too large
		for (final Ambit a : ambits) {
			for (final Segment s : a.getSegments()) {
				if (!junction_seg.contains(s)) {
					// force complete recalc
					root.getAmbits().removeAll(ambits);
					recalcJunction(segment);
					return;
				}
			}
		}

		Junction junction = null;

		if (ambits.size() == 0) { // no existing ambit, create a new one
			junction = ModelFactory.eINSTANCE.createJunction();
			junction.getSegments().addAll(junction_seg);
			root.getAmbits().add(junction);
		} else if (ambits.size() == 1 && ambits.get(0) instanceof Junction) { // one existing junction, just add missing segments
			junction = (Junction) ambits.get(0);
			for (final Segment s : junction_seg) {
				if (!junction.getSegments().contains(s)) {
					junction.getSegments().add(s);
				}
			}
		} else {
			// try to find a junction
			for (final Ambit a : ambits) {
				if (a instanceof Junction) {
					junction = (Junction) a;
					junction.getSegments().clear();
					break;
				}
			}

			// create a new one if not found
			if (junction == null) {
				junction = ModelFactory.eINSTANCE.createJunction();
				junction.getSegments().addAll(junction_seg);
				root.getAmbits().removeAll(ambits);
				root.getAmbits().add(junction);
			} else { // reuse the existing one
				junction.getSegments().clear();
				junction.getSegments().addAll(junction_seg);
				ambits.remove(junction);
				root.getAmbits().removeAll(ambits);
			}

		}

		processed.addAll(junction_seg);
	}
}
