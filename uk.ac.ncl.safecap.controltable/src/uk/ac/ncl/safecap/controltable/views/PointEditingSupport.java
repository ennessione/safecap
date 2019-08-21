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
import safecap.model.ModelPackage;
import safecap.model.Point;
import safecap.model.Rule;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.PointUtil;

public class PointEditingSupport extends EditingSupport {
	public static final int NORMAL = 0;
	public static final int REVERSE = 1;

	private final TreeViewer viewer;
	private int kind = NORMAL;

	public PointEditingSupport(TreeViewer viewer, int kind) {
		super(viewer);
		this.viewer = viewer;
		this.kind = kind;
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

			switch (kind) {
			case NORMAL:
				return LabelProvider.listToString(rule.getNormal());
			case REVERSE:
				return LabelProvider.listToString(rule.getReverse());
			}
		}

		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof RuleWrap) {
			final Rule rule = ((RuleWrap) element).rule;
			final Project project = EmfUtil.getProject(rule);
			final List<Point> points = parseList(project, (String) value);

			if (points == null) {
				return;
			}

			final TransactionalEditingDomain dom = TransactionUtil.getEditingDomain(rule);
			switch (kind) {
			case NORMAL:
				if (!rule.getNormal().equals(points)) {
					dom.getCommandStack().execute(new SetCommand(dom, rule, ModelPackage.eINSTANCE.getRule_Normal(), points));
					EmfUtil.saveProject(project);
					VerificationProvider.invalidateVerificationStatus(rule);
				}
				break;
			case REVERSE:
				if (!rule.getReverse().equals(points)) {
					dom.getCommandStack().execute(new SetCommand(dom, rule, ModelPackage.eINSTANCE.getRule_Reverse(), points));
					EmfUtil.saveProject(project);
					VerificationProvider.invalidateVerificationStatus(rule);
				}
				break;
			}

//            viewer.refresh();
		}
	}

	private List<Point> parseList(Project root, String text) {
		final List<Point> result = new ArrayList<>();
		text = text.trim();

		if (text.length() == 0) {
			return result;
		}

		final String[] items = text.split(",");

		for (final String item : items) {
			final String label = item.trim();
			final Point ambit = PointUtil.getByLabel(root, label);
			if (ambit == null) {
				MessageDialog.openError(null, "Error parsing point list", "No point with name " + label);
				return null;
			}

			result.add(ambit);
		}

		return result;
	}
}
