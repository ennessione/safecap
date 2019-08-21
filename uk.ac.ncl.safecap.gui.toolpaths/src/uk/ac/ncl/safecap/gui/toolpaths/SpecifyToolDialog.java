package uk.ac.ncl.safecap.gui.toolpaths;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import uk.ac.ncl.safecap.common.resources.ToolPathRegistry;

public class SpecifyToolDialog extends Dialog {
	private Text txtName;
	private Text txtPath;
	private final String title;
	private final ToolPathRegistry _registry;

	private String _name, _path;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SpecifyToolDialog(Shell parentShell, String title, ToolPathRegistry registry, String currentName) {
		super(parentShell);
		this.title = title;
		_registry = registry;
		_name = currentName;
		if (_name != null && _name.length() > 0) {
			_path = _registry.get(_name);
		} else {
			_path = "";
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
		gridLayout.numColumns = 3;

		final Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Name");

		txtName = new Text(container, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.setText(_name);
		new Label(container, SWT.NONE);

		final Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Path");

		txtPath = new Text(container, SWT.BORDER);
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtPath.setText(_path);

		final Button btnBrowse = new Button(container, SWT.FLAT);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.*" });
//                dialog.setFilterPath("c:\\temp");
				final String result = dialog.open();
				if (result != null) {
//                    result = result.replace("\\", "\\\\");
					txtPath.setText(result);
				}
			}
		});
		btnBrowse.setText("Browse");

		return container;
	}

	@Override
	public boolean close() {
		_name = txtName.getText();
		_path = txtPath.getText();
		return super.close();
	}

	public String getName() {
		return _name;
	}

	public String getPath() {
		return _path;
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
		return new Point(450, 300);
	}

}
