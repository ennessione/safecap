package uk.ac.ncl.safecap.diagram.misc.postprocess;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.swt.SWT;

import safecap.Project;
import safecap.SafecapFactory;
import safecap.Style;
import safecap.trackside.Equipment;
import safecap.trackside.GenericLocatedEquipment;
import uk.ac.ncl.safecap.diagram.misc.highlights.Highlight;

public class MarkGenericEquipment {
	private static Style balise = SafecapFactory.eINSTANCE.createStyle();
	private static Style aws = SafecapFactory.eINSTANCE.createStyle();

	static {
		balise.setForeground(ColorConstants.black);
		balise.setLinestyle(SWT.LINE_SOLID);
		balise.setLinewidth(6);

		aws.setForeground(ColorConstants.darkGreen);
		aws.setLinestyle(SWT.LINE_SOLID);
		aws.setLinewidth(3);
	}

	private final Project root;

	public MarkGenericEquipment(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final MarkGenericEquipment cmd = new MarkGenericEquipment(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		try {
			getMarkGenericEquipmentCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getMarkGenericEquipmentCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "MarkGenericEquipment", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				Highlight.removeMarkers(root, "geneqp");

				for (final Equipment eqp : root.getEquipment()) {
					if (eqp instanceof GenericLocatedEquipment) {
						final GenericLocatedEquipment gle = (GenericLocatedEquipment) eqp;
						final int len = gle.getSegment().getLength();
						final double offset = (double) gle.getOffset() / (double) len;
						Style style = null;
						if ("AWS".equals(gle.getType())) {
							style = aws;
						} else if ("Balise".equals(gle.getType())) {
							style = balise;
						}
						Highlight.placeMarker(gle.getSegment(), "geneqp", gle.getLabel(), "" + gle.getOffset(), offset, style);
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
