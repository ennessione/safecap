package uk.ac.ncl.safecap.diagram.misc.postprocess;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class RefreshSegmentRole {
	private final Project root;

	public RefreshSegmentRole(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final RefreshSegmentRole cmd = new RefreshSegmentRole(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		// CompositeCommand compc = new CompositeCommand("RefreshSegmentRole");
		// compc.add(getRefreshNodeKindAndRoleCommand());
		try {
			// OperationHistoryFactory.getOperationHistory().execute(compc,null, null);
			getRefreshNodeKindAndRoleCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getRefreshNodeKindAndRoleCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "RefreshSegmentRole", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				for (final safecap.schema.Segment segment : root.getSegments()) {
					final int role = SegmentUtil.getSegmentRole(segment);

					if (segment.getRole() != role) {
						segment.setRole(role);
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
