package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public class TrapPointDetection {
	private final Project root;

	public TrapPointDetection(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final TrapPointDetection cmd = new TrapPointDetection(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		try {
			getTrapPointCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getTrapPointCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "TrapPointCommand", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				for (final Node node : root.getNodes()) {
					if (node.getKind() == NodeKind.POINT) {
						processPointNode(node);
					}
				}

				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

	private void processPointNode(Node node) {
		final List<Segment> in = NodeUtil.getIncomingSegments(node);
		final List<Segment> out = NodeUtil.getOutgoingSegments(node);

		if (ExtensionUtil.hasValue(node, SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP)) {
			return;
		}

		if (in.size() + out.size() != 3) {
			return;
		}

		if (hasBufferEnd(node)) {
			node.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP, true));
			// System.out.println("Point " + node.getLabel() + " is detected as trap");
		}
	}

	private boolean hasBufferEnd(Node node) {
		for (final Segment s : NodeUtil.getIncomingSegments(node)) {
			if (NodeUtil.isEdgeNode(s.getFrom())) {
				return true;
			}
		}

		for (final Segment s : NodeUtil.getOutgoingSegments(node)) {
			if (NodeUtil.isEdgeNode(s.getTo())) {
				return true;
			}
		}
		return false;
	}

}
