package uk.ac.ncl.safecap.diagram.misc.highlights;

import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.Style;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class HighlightSegments {
	private final Map<Segment, Style> highlight;
	private Project root;
	TransactionalEditingDomain domain;

	public HighlightSegments(Map<Segment, Style> highlight) {
		this.highlight = highlight;

		if (highlight.size() > 0) {
			root = EmfUtil.getProject(highlight.keySet().iterator().next());
			domain = TransactionUtil.getEditingDomain(root);
			// System.out.println("Domain for " + root + " is " + domain);
		}
	}

	public static void highlight(Map<Segment, Style> highlight) {
		final HighlightSegments cmd = new HighlightSegments(highlight);

		if (cmd.root != null && cmd.domain != null) {
			cmd.run();
		}
	}

	public void run() {
		try {
			// OperationHistoryFactory.getOperationHistory().execute(getHighlightSegmentsCommand(),
			// null, null);
			getHighlightSegmentsCommand().execute(null, null);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getHighlightSegmentsCommand() {
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "HighlightSegments", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

//					Style highlighted = SafecapFactory.eINSTANCE.createStyle();
//					highlighted.setForeground(ColorConstants.red);

				for (final Segment s : root.getSegments()) {
					if (highlight.containsKey(s)) {
						s.setStyle(highlight.get(s));
					} else {
						s.setStyle(null);
					}
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
