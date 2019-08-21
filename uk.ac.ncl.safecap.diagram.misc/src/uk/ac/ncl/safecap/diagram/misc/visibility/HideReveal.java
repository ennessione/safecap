package uk.ac.ncl.safecap.diagram.misc.visibility;

import java.util.HashSet;
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
import safecap.model.Line;
import safecap.model.Route;
import safecap.schema.Segment;

public class HideReveal {
	private final Set<Segment> hide;
	private final Set<Segment> reveal;
	private final Project root;
	private final Line line;
	TransactionalEditingDomain domain;

	public HideReveal(Project root, Line line, Set<Segment> hide, Set<Segment> reveal) {
		this.hide = hide;
		this.reveal = reveal;
		this.root = root;
		this.line = line;
		domain = TransactionUtil.getEditingDomain(root);
	}

	public static void hideLine(Project root, Line line) {
		if (root == null || line == null) {
			return;
		}

		final Set<Segment> hide = new HashSet<>(root.getSegments().size());
		final Set<Segment> reveal = new HashSet<>(root.getSegments().size());

		hide.addAll(root.getSegments());

		for (final Line l : root.getLines()) {
			if (l != line && l.isVisible() || l == line && !line.isVisible()) {
				for (final Route r : l.getRoutes()) {
					for (final Segment s : r.getSegments()) {
						reveal.add(s);
						hide.remove(s);
					}
				}
			}
		}

		final HideReveal hr = new HideReveal(root, line, hide, reveal);
		hr.run();
	}

	public void run() {
		try {
			OperationHistoryFactory.getOperationHistory().execute(getHideRevealCommand(), null, null);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getHideRevealCommand() {
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "HideReveal", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				try {
					line.setVisible(!line.isVisible());

					for (final Segment s : root.getSegments()) {
						if (hide.contains(s)) {
							s.setVisible(false);
						} else if (reveal.contains(s)) {
							s.setVisible(true);
						}
					}
					return CommandResult.newOKCommandResult();
				} catch (final Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
