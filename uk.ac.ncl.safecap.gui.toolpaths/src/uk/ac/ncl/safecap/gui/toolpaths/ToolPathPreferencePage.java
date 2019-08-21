package uk.ac.ncl.safecap.gui.toolpaths;

import java.util.Collection;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import uk.ac.ncl.safecap.common.resources.ToolPathRegistry;

public class ToolPathPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	private Table table;
	private TableViewer tableViewer;
	private Button btnEdit, btnDelete;
	final private ToolPathRegistry _registry = ToolPathRegistry.getInstance();

	/**
	 * @wbp.parser.constructor
	 */
	public ToolPathPreferencePage() {
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		noDefaultAndApplyButton();

		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gl_container = new GridLayout();
		gl_container.numColumns = 2;
		container.setLayout(gl_container);

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.widthHint = 265;
		table.setLayoutData(gd_table);

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnToolName = tableViewerColumn.getColumn();
		tblclmnToolName.setWidth(100);
		tblclmnToolName.setText("Tool name");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return (String) element;
			}
		});

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnToolPath = tableViewerColumn_1.getColumn();
		tblclmnToolPath.setWidth(221);
		tblclmnToolPath.setText("Tool path");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return _registry.get((String) element);
			}
		});

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtonState();
			}
		});

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		final Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final SpecifyToolDialog dialog = new SpecifyToolDialog(shell, "Add new tool path", _registry, "");
				final int res = dialog.open();
				if (res == Window.OK) {
					final String name = dialog.getName(), path = dialog.getPath();
					if (name != null && name.length() > 0 && path != null && path.length() > 0 && _registry.get(name) == null) {
						_registry.put(name, path);
						tableViewer.add(name);
						update();
					}
				}
			}
		});

		btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setText("Edit");
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				final String prevName = (String) selection.getFirstElement();
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final SpecifyToolDialog dialog = new SpecifyToolDialog(shell, "Edit tool path", _registry, prevName);
				final int res = dialog.open();
				if (res == Window.OK) {
					final String name = dialog.getName(), path = dialog.getPath();
					if (name != null && name.length() > 0 && path != null && path.length() > 0) {
						_registry.remove(prevName);
						_registry.put(name, path);
						tableViewer.replace(name, table.getSelectionIndex());
						update();
					}
				}
			}
		});

		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				if (selection.getFirstElement() != null) {
					final String name = (String) selection.getFirstElement();
					_registry.remove(name);
					tableViewer.remove(name);
					update();
				}
			}
		});
		btnDelete.setText("Delete");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		readPrefs();
//        _registry.put("some tool", "C:/asdpfoasdpfo");
//        tableViewer.add("some tool");

//        update();
		updateButtonState();

		return container;
	}

	private void update() {
		table.update();
		updateButtonState();
	}

	private void updateButtonState() {
		final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		if (selection.getFirstElement() != null && selection.getFirstElement() instanceof String) {
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
		} else {
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		}
	}

	/**
	 * Initialize the preference page.
	 */
	@Override
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}

	private void readPrefs() {
		_registry.loadFromPreferences();
		final Collection<String> names = _registry.getAll().keySet();
		for (final String name : names) {
			tableViewer.add(name);
		}
	}

	@Override
	public boolean performOk() {
		final boolean res = super.performOk();
		if (res == false) {
			return res;
		}
		_registry.saveToPreferences();
		return true;
	}
}
