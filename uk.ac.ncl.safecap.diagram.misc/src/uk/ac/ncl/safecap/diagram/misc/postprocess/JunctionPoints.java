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
import uk.ac.ncl.safecap.mcommon.conf.RouteConfig;

public class JunctionPoints {
	private final Project root;

	public JunctionPoints(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final JunctionPoints cmd = new JunctionPoints(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		try {
			OperationHistoryFactory.getOperationHistory().execute(getJunctionPointsCommand(), monitor, null);
			// getJunctionPointsCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getJunctionPointsCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "JunctionPoints", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				RouteConfig.rebuildJunctionPointAssociation(root);

				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

}
