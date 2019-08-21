package uk.ac.ncl.safecap.analytics.ctextract.ui.wizard;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.core.TEPlugin;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (xmldata).
 */

public class CTEWizardPage extends WizardPage {
	private Text containerText;

	private Text fileText;
	private Text resourceText;
	private Combo combo;
	private final ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public CTEWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Extractor wizard");
		setDescription("This wizard creates a new data extractor project.");
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

		final Label label0 = new Label(container, SWT.NULL);
		label0.setText("Concept map:");

		combo = new Combo(container, SWT.READ_ONLY);
		final List<TEPart> parts = TEPlugin.getTERegistry().getAllElements("conceptmap");
		final List<String> names = new ArrayList<>();
		for (final TEPart p : parts) {
			names.add(p.getName());
			combo.setData(p.getName(), p);
		}
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setItems(names.toArray(new String[names.size()]));

		new Label(container, SWT.NULL);

		final Label label1 = new Label(container, SWT.NULL);
		label1.setText("&Resource:");

		resourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		resourceText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		resourceText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		final Button button1 = new Button(container, SWT.PUSH);
		button1.setText("Browse...");
		button1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String file = openXmlFileDialog();
				if (file != null) {
					resourceText.setText(file);
				}
			}
		});

		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		containerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		fileText.setText("project.extractor");
	}

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

//	private void handleResourceBrowse() {
//		String file = openXmlFileDialog();
//		if (file != null)
//			resourceText.setText(file);
//	}

	private static String openXmlFileDialog() {
		final FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.OPEN);
		fd.setText("Open an xml file");
		fd.setFilterExtensions(new String[] { "*.xml" });
		return fd.open();
	}

	/**
	 * Ensures that all fields are set.
	 */

	private void dialogChanged() {
		final IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
		final String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}

		if (getModelName() == null) {
			updateStatus("Model must be specified");
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
		final int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			final String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("extractor") == false) {
				updateStatus("File extension must be \"extractor\"");
				return;
			}
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

	public String getResourceName() {
		return resourceText.getText();
	}

	public String getModelName() {
		if (combo.getSelectionIndex() >= 0) {
			return combo.getText();
		} else {
			return null;
		}
	}
}