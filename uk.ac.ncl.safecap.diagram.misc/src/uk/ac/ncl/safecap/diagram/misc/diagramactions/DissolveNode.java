package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class DissolveNode {
	private final safecap.schema.Node node;
	private final Project root;

	public DissolveNode(IGraphicalEditPart nodePart, safecap.schema.Node node) {
		this.node = node;
		root = EmfUtil.getProject(node);
	}

	public void run() {
		final CompositeCommand compc = new CompositeCommand("DissolveNode");
		compc.add(getDissolveCommand());
		try {
			OperationHistoryFactory.getOperationHistory().execute(compc, null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getDissolveCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "dissolve_node", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				// find the segments to the right
				final List<Segment> right = new ArrayList<>(5);

				for (final Segment seg : root.getSegments()) {
					if (seg.getFrom() == node) {
						right.add(seg);
					}
				}

				// find the segments to the left
				final List<Segment> left = new ArrayList<>(5);

				for (final Segment seg : root.getSegments()) {
					if (seg.getTo() == node) {
						left.add(seg);
					}
				}

				if (right.size() == 0 || left.size() == 0) {
					root.getSegments().removeAll(left);
					root.getSegments().removeAll(right);
					root.getNodes().remove(node);
					return CommandResult.newOKCommandResult();
				}

				if (left.size() == 1) {
					final safecap.schema.Node from = left.get(0).getFrom();
					for (final Segment seg : right) {
						seg.setFrom(from);
					}
					root.getSegments().removeAll(left);
					root.getNodes().remove(node);
					return CommandResult.newOKCommandResult();
				}

				if (right.size() == 1) {
					final safecap.schema.Node to = right.get(0).getTo();
					for (final Segment seg : left) {
						seg.setTo(to);
					}
					root.getSegments().removeAll(right);
					root.getNodes().remove(node);
					return CommandResult.newOKCommandResult();
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
