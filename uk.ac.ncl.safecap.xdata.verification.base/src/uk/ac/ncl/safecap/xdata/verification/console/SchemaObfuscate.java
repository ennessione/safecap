package uk.ac.ncl.safecap.xdata.verification.console;

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
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class SchemaObfuscate extends SchemaBaseCommand {
	public static SchemaObfuscate INSTANCE = new SchemaObfuscate();

	private SchemaObfuscate() {
	}

	@Override
	public String getName() {
		return "schema:obfuscate";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {

		try {
			final Project root = console.getProject();
			final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

			final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Obfuscate", null) {

				@Override
				protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info)
						throws ExecutionException {

					// rename nodes
					for (final Node node : root.getNodes()) {
						if (!noLabel(node)) {
							final String newlabel = ScopeB.getUniqueName(root, "N");
							node.setLabel(newlabel);
						}
					}

					// rename points and clear ambit names
					for (final Ambit ambit : root.getAmbits()) {
						if (ambit instanceof Junction) {
							final Junction junction = (Junction) ambit;
							for (final Point point : junction.getPoints()) {
								if (!noLabel(point.getNode())) {
									final String newlabel = ScopeB.getUniqueName(root, "P");
									point.getNode().setLabel(newlabel);
								}
							}
						} else {
							ambit.setLabel(null);
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

			command.execute(null, null);

			console.out("All done");

		} catch (final Throwable e) {
			e.printStackTrace();
			console.err(e.getMessage());
		}
		return;
	}

	private static boolean noLabel(Labeled element) {
		return element.getLabel() == null || element.getLabel().length() == 0;
	}

}
