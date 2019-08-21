package uk.ac.ncl.safecap.diagram.misc.postprocess;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Labeled;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class AutonameSegments {
	private final Project root;

	public AutonameSegments(Project root) {
		this.root = root;
	}

	public static void refresh(Project root) {
		final AutonameSegments cmd = new AutonameSegments(root);
		cmd.run();
	}

	public void run() {
		try {
			getAutonameCommand().execute(null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static boolean noLabel(Labeled element) {
		return element.getLabel() == null || element.getLabel().length() == 0;
	}

	private AbstractTransactionalCommand getAutonameCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "AutonameSegments", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				int loops = 4;

				// autoname boundary nodes
				for (final Node node : root.getNodes()) {
					if (noLabel(node) && node.getKind() == NodeKind.BOUNDARY) {
						final String newlabel = ScopeB.getUniqueName(root, "B");
						node.setLabel(newlabel);
					}
				}

				// autoname source segments
				for (final Node node : NodeUtil.getSourceNodes(root)) {
					for (final Segment segment : NodeUtil.getIncomingSegments(node)) {
						if (noLabel(segment)) {
							final String newlabel = SegmentUtil.getUniqueName(root);
							segment.setLabel(newlabel);
						}
					}
					for (final Segment segment : NodeUtil.getOutgoingSegments(node)) {
						if (noLabel(segment)) {
							final String newlabel = SegmentUtil.getUniqueName(root);
							segment.setLabel(newlabel);
						}
					}
				}

				// autoname points
				for (final Ambit ambit : root.getAmbits()) {
					if (ambit instanceof Junction) {
						final Junction junction = (Junction) ambit;
						for (final Point point : junction.getPoints()) {
							if (noLabel(point.getNode())) {
								final String newlabel = ScopeB.getUniqueName(root, "P");
								point.getNode().setLabel(newlabel);
							}
						}
					}
				}

				// autoname signals
				for (final Equipment equipment : root.getEquipment()) {
					if (equipment instanceof Signal) {
						final Signal signal = (Signal) equipment;
						if (noLabel(signal)) {
							final String newlabel = ScopeB.getUniqueName(root, "S");
							signal.setLabel(newlabel);
						}
					}
				}

				// autoname other segments
				while (loops-- > 0) {
					for (final Segment segment : root.getSegments()) {
						if (noLabel(segment)) {
							final String newlabel = SegmentUtil.autonameSegment(segment);
							segment.setLabel(newlabel);
						}
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
