package uk.ac.ncl.safecap.controltable.views;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import safecap.model.Route;
import safecap.model.Rule;

public class RouteLabelEditingSupport extends EditingSupport {
	private final TableViewer viewer;

	public RouteLabelEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTable());
	}

	@Override
	protected boolean canEdit(Object element) {
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof RuleWrap) {
			final Rule rule = ((RuleWrap) element).rule;
			final Route route = (Route) rule.eContainer();
			return route.getLabel();
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		// TODO Auto-generated method stub

	}

}
