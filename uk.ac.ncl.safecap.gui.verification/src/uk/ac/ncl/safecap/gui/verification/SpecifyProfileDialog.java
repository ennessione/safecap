package uk.ac.ncl.safecap.gui.verification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

public class SpecifyProfileDialog extends Dialog {
	private static class MyLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			final IVerificationTool tool = (IVerificationTool) element;
			return tool.getName();
		}
	};

	private Text txtName;
	private Text txtPath;
	private final String title;
	private final VerificationToolRegistry _registry = VerificationToolRegistry.getInstance();

	private String _name;
	private final List<IVerificationTool> _tools = new ArrayList<>();

	private final LabelProvider _labelProvider = new MyLabelProvider();

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SpecifyProfileDialog(Shell parentShell, String title, String currentName, List<IVerificationTool> currentList) {
		super(parentShell);
		this.title = title;
		_name = currentName;
		if (_name == null) {
			_name = "";
		}
		if (currentList != null) {
			_tools.addAll(currentList);
		}
		setShellStyle(SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		final Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Name");

		txtName = new Text(container, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.setText(_name);

		final Label lblVasdfgsdfg = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		final GridData gd_lblVasdfgsdfg = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_lblVasdfgsdfg.widthHint = 413;
		lblVasdfgsdfg.setLayoutData(gd_lblVasdfgsdfg);

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setSize(77, 15);
		lblNewLabel_1.setText("Available tools");
		new Label(composite, SWT.NONE);

		final Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setSize(73, 15);
		lblNewLabel_2.setText("Selected tools");

		final ListViewer listViewerAvailable = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		final org.eclipse.swt.widgets.List listAvailable = listViewerAvailable.getList();
		listAvailable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		listViewerAvailable.setLabelProvider(_labelProvider);

		final Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		final Button btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.setSize(27, 25);
		btnAdd.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/move_right.gif"));

		final Button btnRemove = new Button(composite_1, SWT.NONE);
		btnRemove.setImage(ResourceManager.getPluginImage("uk.ac.ncl.safecap.gui.verification", "icons/move_left.gif"));
		btnRemove.setBounds(0, 0, 75, 25);

		final ListViewer listViewerSelected = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		final org.eclipse.swt.widgets.List list = listViewerSelected.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		listViewerSelected.setLabelProvider(_labelProvider);

		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final IStructuredSelection selection = (IStructuredSelection) listViewerAvailable.getSelection();
				if (!selection.isEmpty()) {
					for (final Iterator iterator = selection.iterator(); iterator.hasNext();) {
						final IVerificationTool tool = (IVerificationTool) iterator.next();
						if (!_tools.contains(tool)) {
							_tools.add(tool);
							listViewerSelected.add(tool);
						}
					}
				}
			}
		});

		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final IStructuredSelection selection = (IStructuredSelection) listViewerSelected.getSelection();
				if (!selection.isEmpty()) {
					for (final Iterator iterator = selection.iterator(); iterator.hasNext();) {
						final IVerificationTool tool = (IVerificationTool) iterator.next();
						if (_tools.contains(tool)) {
							_tools.remove(tool);
							listViewerSelected.remove(tool);
						}
					}
				}
			}
		});

		for (final IVerificationTool tool : _tools) {
			listViewerSelected.add(tool);
		}
		for (final IVerificationTool tool : _registry.tools) {
			listViewerAvailable.add(tool);
		}

		return container;
	}

	@Override
	public boolean close() {
		_name = txtName.getText();
		return super.close();
	}

	public String getName() {
		return _name;
	}

	public List<IVerificationTool> getToolList() {
		return _tools;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(474, 414);
	}
}
