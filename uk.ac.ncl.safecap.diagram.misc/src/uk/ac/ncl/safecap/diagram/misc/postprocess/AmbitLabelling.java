package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Section;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.ScopeB;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class AmbitLabelling {
	private final Project root;

	public AmbitLabelling(Project root) {
		this.root = root;
	}

	public static void refresh(Project root, IProgressMonitor monitor) {
		final AmbitLabelling cmd = new AmbitLabelling(root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		try {
			// getAmbitLabellingCommand().execute(monitor, null);
			OperationHistoryFactory.getOperationHistory().execute(getAmbitLabellingCommand(), monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getAmbitLabellingCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "AmbitLabelling", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				final List<Ambit> ambits = new ArrayList<>();
				ambits.addAll(root.getAmbits()); // to handle ambit deletion

				for (final Ambit ambit : ambits) {
					if (root.getAmbits().contains(ambit) && isMissing(ambit.getLabel())) {
						if (ambit instanceof Section) {
							labelSection((Section) ambit);
						} else if (ambit instanceof Junction) {
							labelJunction((Junction) ambit);
						}
					}
				}

				return CommandResult.newOKCommandResult();
			}

			private boolean isMissing(String label) {
				if (label == null) {
					return true;
				}
				if (label.length() == 0) {
					return true;
				}
				if (!Character.isUpperCase(label.charAt(0))) {
					return true;
				}
				return false;
			}

			private void labelJunction(Junction junction) {
				final List<Segment> segments = junction.getSegments();

				if (segments.size() == 0) {
					root.getAmbits().remove(junction);
					return;
				}

				String label = null;
				boolean needsLabel = false;

				for (final Segment s : segments) {
					if (SegmentUtil.needsLabel(s)) {
						label = s.getLabel();
						needsLabel = true;
						break;
					}
				}

				if (!needsLabel) {
					return;
				}

				if (label == null) {
					label = ScopeB.getUniqueName(root, "J");
				}

				for (final Segment s : segments) {
					if (s.getLabel() == null || !s.getLabel().equals(label)) {
						s.setLabel(label);
					}
				}

				junction.setLabel(label);
			}

			private void labelSection(Section section) {
				final List<Segment> segments = section.getSegments();

				if (segments.size() == 0) {
					root.getAmbits().remove(section);
					return;
				}

				final StringBuffer name = new StringBuffer();

				for (int i = 0; i < segments.size(); i++) {
					if (i > 0) {
						if (segments.get(i).getLabel() != null && segments.get(i).getLabel().equals(name.toString())) {
							name.append("_");
							name.append(segments.get(i).getLabel());
						}
					} else {
						name.append(segments.get(i).getLabel());
					}
				}

				final String label = name.toString();

				section.setLabel(label);
			}
		};

		return command;
	}

}
