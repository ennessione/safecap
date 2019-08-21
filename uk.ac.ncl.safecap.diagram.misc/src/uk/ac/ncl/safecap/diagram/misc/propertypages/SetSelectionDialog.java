package uk.ac.ncl.safecap.diagram.misc.propertypages;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class SetSelectionDialog extends Dialog {
	@SuppressWarnings("rawtypes")
	private final java.util.List available_data, selected_data;
	private List available, selected;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SetSelectionDialog(final Shell parentShell, final java.util.List<?> available, final java.util.List<?> selected) {
		super(parentShell);
		available_data = new ArrayList(available);
		selected_data = new ArrayList(selected);

		available_data.removeAll(selected_data);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite contents = (Composite) super.createDialogArea(parent);

		final GridLayout contentsGridLayout = (GridLayout) contents.getLayout();
		contentsGridLayout.numColumns = 3;

		final GridData contentsGridData = (GridData) contents.getLayoutData();
		contentsGridData.horizontalAlignment = SWT.FILL;
		contentsGridData.verticalAlignment = SWT.FILL;

		available = new List(contents, SWT.V_SCROLL);

		{
			final GridData availableGridData = new GridData();
			availableGridData.widthHint = Display.getCurrent().getBounds().width / 5;
			availableGridData.heightHint = Display.getCurrent().getBounds().height / 3;
			availableGridData.verticalAlignment = SWT.FILL;
			availableGridData.horizontalAlignment = SWT.FILL;
			availableGridData.grabExcessHorizontalSpace = true;
			availableGridData.grabExcessVerticalSpace = true;
			available.setLayoutData(availableGridData);
		}

		for (int i = 0; i < available_data.size(); i++) {
			available.add(available_data.get(i).toString());
		}

		if (available.getItemCount() > 0) {
			available.select(0);
		}

		final Composite controlButtons = new Composite(contents, SWT.NONE);
		final GridData controlButtonsGridData = new GridData();
		controlButtonsGridData.verticalAlignment = SWT.FILL;
		controlButtonsGridData.horizontalAlignment = SWT.FILL;
		controlButtons.setLayoutData(controlButtonsGridData);

		final GridLayout controlsButtonGridLayout = new GridLayout();
		controlButtons.setLayout(controlsButtonGridLayout);

		final Button addButton = new Button(controlButtons, SWT.PUSH);
		addButton.setText("Add");
		final GridData addButtonGridData = new GridData();
		addButtonGridData.verticalAlignment = SWT.FILL;
		addButtonGridData.horizontalAlignment = SWT.FILL;
		addButton.setLayoutData(addButtonGridData);

		final Button removeButton = new Button(controlButtons, SWT.PUSH);
		removeButton.setText("Remove");
		final GridData removeButtonGridData = new GridData();
		removeButtonGridData.verticalAlignment = SWT.FILL;
		removeButtonGridData.horizontalAlignment = SWT.FILL;
		removeButton.setLayoutData(removeButtonGridData);

		selected = new List(contents, SWT.V_SCROLL);

		{
			final GridData selectedGridData = new GridData();
			selectedGridData.widthHint = Display.getCurrent().getBounds().width / 5;
			selectedGridData.heightHint = Display.getCurrent().getBounds().height / 3;
			selectedGridData.verticalAlignment = SWT.FILL;
			selectedGridData.horizontalAlignment = SWT.FILL;
			selectedGridData.grabExcessHorizontalSpace = true;
			selectedGridData.grabExcessVerticalSpace = true;
			selected.setLayoutData(selectedGridData);
		}

		available.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				if (addButton.isEnabled()) {
					addButton.notifyListeners(SWT.Selection, null);
				}
			}

			@Override
			public void mouseDown(final MouseEvent e) {
			}

			@Override
			public void mouseUp(final MouseEvent e) {
			}
		});

		selected.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				if (removeButton.isEnabled()) {
					removeButton.notifyListeners(SWT.Selection, null);
				}
			}

			@Override
			public void mouseDown(final MouseEvent e) {
			}

			@Override
			public void mouseUp(final MouseEvent e) {
			}
		});

		addButton.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(final SelectionEvent e) {

				for (final int i : available.getSelectionIndices()) {
					selected_data.add(available_data.get(i));
					available_data.remove(i);
				}

				rebuildLists();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

		});

		removeButton.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(final SelectionEvent e) {
				for (final int i : selected.getSelectionIndices()) {
					available_data.add(selected_data.get(i));
					selected_data.remove(i);
				}

				rebuildLists();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

		});

		rebuildLists();

		return contents;
	}

	private void rebuildLists() {
		available.removeAll();
		selected.removeAll();

		for (final Object o : available_data) {
			available.add(o.toString());
		}

		for (final Object o : selected_data) {
			selected.add(o.toString());
		}

	}

	public java.util.List<?> getResult() {
		return selected_data;
	}
}
