package uk.ac.ncl.safecap.textentry.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (xmldata).
 */

public class ConfWizardPage extends WizardPage {
	private Text containerText;
	private Combo kind;
	private Text fileText;

	private final ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ConfWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Configuration file wizard");
		setDescription("This wizard creates a new definition, contract or configuration file.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		final Label labelType = new Label(container, SWT.NULL);
		labelType.setText("&Kind:");

		kind = new Combo(container, SWT.READ_ONLY);
		kind.setItems("Configuration", "Definition", "Contract");
		final GridData gdType = new GridData(GridData.FILL_HORIZONTAL);
		kind.setLayoutData(gdType);
		kind.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				final String fileName = getFileName();
				if (fileName != null && fileName.indexOf('.') >= 0) {
					final String newFile = fileName.substring(0, fileName.indexOf('.')) + "." + getKind().toLowerCase();
					fileText.setText(newFile);
				}
				dialogChanged();
			}
		});

		new Label(container, SWT.NULL);

		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		final Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false && selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}
			final Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer) {
					container = (IContainer) obj;
				} else {
					container = ((IResource) obj).getParent();
				}
				containerText.setText(container.getFullPath().toString());
			}
		}
		fileText.setText("file.definition");
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for the
	 * container field.
	 */

	private void handleBrowse() {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select new file container");
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		final IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
		final String fileName = getFileName();
		final String kindName = getKind();

		if (kindName == null) {
			updateStatus("Kind must be specified");
			return;
		}

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}

		final int index = fileName.indexOf('.');
		String fileExtension = "";
		if (index >= 0) {
			fileExtension = fileName.substring(index + 1);
		}

		if (!kindName.toLowerCase().equals(fileExtension)) {
			updateStatus("File extension must be " + kindName.toLowerCase());
			return;
		}

		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}

		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}

	public String getKind() {
		if (kind.getSelectionIndex() != -1) {
			return kind.getText();
		} else {
			return null;
		}
	}

}