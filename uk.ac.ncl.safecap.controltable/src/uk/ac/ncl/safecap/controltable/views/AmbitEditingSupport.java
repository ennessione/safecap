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
import safecap.model.ModelPackage;
import safecap.model.Rule;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class AmbitEditingSupport extends EditingSupport {

	private final TreeViewer viewer;

	public AmbitEditingSupport(TreeViewer viewer) {
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
			return LabelProvider.listToString(rule.getClear());
		}

		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		final Rule rule = ((RuleWrap) element).rule;
		final Project project = EmfUtil.getProject(rule);
		final List<Ambit> ambits = parseList(project, (String) value);

		if (ambits == null) {
			return;
		}

		if (!rule.getClear().equals(ambits)) {
			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(rule);
			if (dom != null) {
				dom.getCommandStack().execute(new SetCommand(dom, rule, ModelPackage.eINSTANCE.getRule_Clear(), ambits));
			}
			EmfUtil.saveProject(project);
			VerificationProvider.invalidateVerificationStatus(rule);
		}

//		viewer.refresh();
	}

	private List<Ambit> parseList(Project root, String text) {
		final List<Ambit> result = new ArrayList<>();

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
			final String label = item.trim();
			Ambit ambit;
			try {
				ambit = AmbitUtil.getByLabel(root, label);
				if (ambit == null) {
					MessageDialog.openError(null, "Error parsing ambit list", "No ambit with name " + label);
					return null;
				}
			} catch (final Exception e) {
				MessageDialog.openError(null, "Error parsing ambit list", "No ambit with name " + label);
				return null;
			}

			result.add(ambit);
		}

		return result;
	}

}
