package uk.ac.ncl.safecap.controltable.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.ModelFactory;
import safecap.model.ModelPackage;
import safecap.model.Rule;
import safecap.model.TimedOccupationRule;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class TimedOccupationEditingSupport extends EditingSupport {
	private final TreeViewer viewer;

	public TimedOccupationEditingSupport(TreeViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTree());
	}

	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof RuleWrap) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof RuleWrap) {
			final Rule rule = ((RuleWrap) element).rule;
			return LabelProvider.occlistToString(rule.getOccupied());
		}

		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		final Rule rule = ((RuleWrap) element).rule;
		final Project project = EmfUtil.getProject(rule);
		final List<TimedOccupationRule> result = parseList(project, (String) value);

		if (result == null) {
			return;
		}

		if (!rule.getOccupied().equals(result)) {
			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(rule);
			dom.getCommandStack().execute(new SetCommand(dom, rule, ModelPackage.eINSTANCE.getRule_Occupied(), result));
			EmfUtil.saveProject(project);
			VerificationProvider.invalidateVerificationStatus(rule);
		}

//		viewer.refresh();
	}

	private List<TimedOccupationRule> parseList(Project root, String text) {
		final List<TimedOccupationRule> result = new ArrayList<>();

		if (text != null) {
			text = text.trim();
		} else {
			return null;
		}

		if (text.length() == 0) {
			return result;
		}

		final String[] items = text.split(",");

		for (final String item : items) {
			String label = item.trim();
			double time = 0;

			if (label.contains(":")) { // timed occupation
				final String[] parts = label.split(":");
				if (parts.length != 2) {
					MessageDialog.openError(null, "Error parsing timed ambit expression", "Invalid expression: " + label);
					return null;
				}
				try {
					time = Double.parseDouble(parts[1]);
					if (time <= 0 || Double.isInfinite(time) || Double.isNaN(time)) {
						MessageDialog.openError(null, "Error parsing timed ambit expression", "Invalid number: " + parts[1]);
						return null;
					}
				} catch (final NumberFormatException e) {
					MessageDialog.openError(null, "Error parsing timed ambit expression", "Invalid number format: " + parts[1]);
					return null;
				}

				label = parts[0];
			}

			final Ambit ambit = AmbitUtil.getByLabel(root, label);
			if (ambit == null) {
				MessageDialog.openError(null, "Error parsing timed ambit expression", "No ambit with name " + label);
				return null;
			}

			final TimedOccupationRule to = ModelFactory.eINSTANCE.createTimedOccupationRule();
			to.setAmbit(ambit);
			to.setTime(time);

			result.add(to);
		}

		return result;
	}

}
