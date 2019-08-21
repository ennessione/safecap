package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.List;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class SetSelectionDialogEditor extends DialogCellEditor {
	private final List<?> current, all;

	SetSelectionDialogEditor(Composite parent, List<?> current, List<?> all) {
		super(parent);
		this.current = current;
		this.all = all;
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {

		final SetSelectionDialog dialog = new SetSelectionDialog(cellEditorWindow.getShell(), all, current);
		dialog.open();
		return dialog.getResult();
	}

}
