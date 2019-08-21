package uk.ac.ncl.safecap.diagram.misc.postprocess;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.NodeDescriptor;
import uk.ac.ncl.safecap.misc.core.NodeUtil;

public class RefreshNodeKindAndRole {
	private final Project root;

	public RefreshNodeKindAndRole(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final RefreshNodeKindAndRole cmd = new RefreshNodeKindAndRole(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		// CompositeCommand compc = new CompositeCommand("RefreshNodeKindAndRole");
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

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "RefreshNodeKindAndRole", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				for (final safecap.schema.Node node : root.getNodes()) {
					final NodeDescriptor desc = NodeUtil.getNodeDescr(node);

					if (!node.getKind().equals(desc.kind)) {
						node.setKind(desc.kind);
					}

					if (node.getRoles() != desc.role) {
						node.setRoles(desc.role);
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
