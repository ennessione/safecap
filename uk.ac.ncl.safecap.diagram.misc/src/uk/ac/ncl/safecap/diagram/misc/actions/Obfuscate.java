package uk.ac.ncl.safecap.diagram.misc.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Labeled;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class Obfuscate extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final Project project = getSelectedModel(editor);
		if (project != null) {
			final ObfsucateCommand cmd = new ObfsucateCommand(project);
			cmd.run();
		}

		return null;
	}

	private static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();

		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

}

class ObfsucateCommand {
	private final Project root;

	public ObfsucateCommand(Project root) {
		this.root = root;
	}

	public static void refresh(Project root) {
		final ObfsucateCommand cmd = new ObfsucateCommand(root);
		cmd.run();
	}

	public void run() {
		try {
			getCommand().execute(null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static boolean noLabel(Labeled element) {
		return element.getLabel() == null || element.getLabel().length() == 0;
	}

	private AbstractTransactionalCommand getCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Obfuscate", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				// rename nodes
				for (final Node node : root.getNodes()) {
					if (!noLabel(node)) {
						final String newlabel = ScopeB.getUniqueName(root, "N");
						node.setLabel(newlabel);
					}
				}

				// rename points
				for (final Ambit ambit : root.getAmbits()) {
					if (ambit instanceof Junction) {
						final Junction junction = (Junction) ambit;
						for (final Point point : junction.getPoints()) {
							if (!noLabel(point.getNode())) {
								final String newlabel = ScopeB.getUniqueName(root, "P");
								point.getNode().setLabel(newlabel);
							}
						}
					}
				}

				// rename signals
				for (final Equipment equipment : root.getEquipment()) {
					if (equipment instanceof Signal) {
						final Signal signal = (Signal) equipment;
						if (!noLabel(signal)) {
							final String newlabel = ScopeB.getUniqueName(root, "S");
							signal.setLabel(newlabel);
						}
					}
				}

				// rename other segments

				for (final Segment segment : root.getSegments()) {
					if (!noLabel(segment)) {
						final String newlabel = SegmentUtil.getUniqueName(root, "T");
						segment.setLabel(newlabel);
					}
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}