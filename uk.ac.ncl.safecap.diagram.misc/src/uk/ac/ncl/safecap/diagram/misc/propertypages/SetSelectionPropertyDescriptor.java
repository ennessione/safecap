package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

class SetSelectionPropertyDescriptor extends TextPropertyDescriptor {
	private final List<?> current, all;

	public SetSelectionPropertyDescriptor(Object id, String displayName, List<?> current, List<?> all) {
		super(id, displayName);
		this.current = current;
		this.all = all;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {

		return new SetSelectionDialogEditor(parent, current, all);
	}
}
