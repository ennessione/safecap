package uk.ac.ncl.safecap.controltable.views;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TreeViewer;

import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;

public class VerifyActionEditingSupport extends EditingSupport {

	@SuppressWarnings("unused")
	private final TreeViewer viewer;

	public VerifyActionEditingSupport(TreeViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if (element instanceof Route) {
			onClick((EObject) element);
		} else if (element instanceof RuleWrap) {
			final Rule rule = ((RuleWrap) element).rule;
			if (rule.eContainer() != null && rule.eContainer() instanceof Point) {
				onClick(rule.eContainer());
			}
		}
		return null;
	}

	@Override
	protected boolean canEdit(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
	}

	private void onClick(EObject object) {
		VerificationProvider.onVerificationClick(object);
	}
}
