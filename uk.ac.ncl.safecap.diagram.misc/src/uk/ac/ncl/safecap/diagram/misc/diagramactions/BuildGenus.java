package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;

import safecap.Project;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.postprocess.ModelCleanup;
import uk.ac.ncl.safecap.misc.core.NodeUtil;

public class BuildGenus {
	private final Project root;
	private final DiagramEditPart part;

	public BuildGenus(DiagramEditPart part) {
		this.part = part;
		final View view = (View) part.getModel();
		root = (Project) view.getElement();
	}

	public void run() {
		final ModelCleanup mc = new ModelCleanup(root);

		final CompositeCommand compc = new CompositeCommand("Genus");
		compc.add(getGenusCommand());
		compc.add(mc.getModelCleanupCommand());

		try {

			OperationHistoryFactory.getOperationHistory().execute(compc, null, null);
			final ArrangeRequest arrangeRequest = new ArrangeRequest(ActionIds.ACTION_ARRANGE_ALL);
			final Command arrangeCmd = part.getCommand(arrangeRequest);
			arrangeCmd.execute();

		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getGenusCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Genus", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				final List<Node> nodes_toremove = new ArrayList<>(root.getNodes().size());
				final List<Segment> seg_toremove = new ArrayList<>(root.getSegments().size());

				boolean repeat = true;
				while (repeat) {
					repeat = false;
					outer: for (final Node n : root.getNodes()) {
						if (n.getKind() == NodeKind.NORMAL) {
							final List<Segment> in = NodeUtil.getIncomingSegments(n);
							final List<Segment> out = NodeUtil.getOutgoingSegments(n);

							assert in.size() == 1 && out.size() == 1;
							final Segment ins = in.get(0);
							final Segment outs = out.get(0);
							ins.setTo(outs.getTo());
							ins.setLength(ins.getLength() + outs.getLength());
							nodes_toremove.add(n);
							seg_toremove.add(outs);

							repeat = true;
							break outer;
						}
					}
					root.getNodes().removeAll(nodes_toremove);
					root.getSegments().removeAll(seg_toremove);
				}

				// remove split segment junctions
				seg_toremove.clear();
				for (final Segment s : root.getSegments()) {
					for (final Segment t : root.getSegments()) {
						if (s != t && !seg_toremove.contains(t) && s.getFrom() == t.getFrom() && s.getTo() == t.getTo()) {
							if (s.getLength() > t.getLength()) {
								t.setLength(s.getLength());
							}
							seg_toremove.add(s);
						}
					}
				}

				root.getSegments().removeAll(seg_toremove);

				root.getEquipment().clear();
				root.getWires().clear();
				root.getLines().clear();
				root.getRoutes().clear();
				root.getAmbits().clear();

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private AbstractTransactionalCommand getGlueCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Glue", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
