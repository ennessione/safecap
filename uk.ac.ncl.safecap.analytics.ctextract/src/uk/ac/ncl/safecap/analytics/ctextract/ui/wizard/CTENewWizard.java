package uk.ac.ncl.safecap.analytics.ctextract.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import uk.ac.ncl.safecap.analytics.ctextract.main.CTProject;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;
import uk.ac.ncl.safecap.xmldata.ui.SDAUIUtils;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "xmldata".
 * If a sample multi-page editor (also available as a template) is registered
 * for the same extension, it will be able to open it.
 */

public class CTENewWizard extends Wizard implements INewWizard {
	private static final InputStream EmptyStream = new ByteArrayInputStream(new byte[] {});

	private CTEWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for XmlDataNewWizard.
	 */
	public CTENewWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		page = new CTEWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We will
	 * create an operation and run it using wizard as execution context.
	 */
	@Override
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
		final String resourceName = page.getResourceName();
		final String modelName = page.getModelName();

		final IDataParser parser = SDAUIUtils.getParser();
		if (parser == null) {
			return false;
		}

		try {
			doFinish(containerName, fileName, resourceName, modelName, parser);
		} catch (final Throwable e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing or
	 * just replace its contents, and open the editor on the newly created file.
	 */

	private void doFinish(String containerName, String fileName, String resourceName, String modelName, IDataParser parser)
			throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}

		final IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		if (!file.exists()) {
			file.create(EmptyStream, true, null);
		}

		writeInitial(file.getLocation().toFile(), resourceName, modelName, parser.getName());

		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (final PartInitException e) {
				}
			}
		});
	}

	private void writeInitial(File file, String resourceName, String modelName, String parserName) {
		try {
			final JAXBContext jaxbContext = JAXBContext.newInstance(CTProject.class);
			final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			final OutputStream os = new FileOutputStream(file);
			final CTProject project = new CTProject();
			project.addCommonRules();
			project.setTablesModelName(page.getModelName());
			project.setResourceName(resourceName);
			project.setParserName(parserName);
			jaxbMarshaller.marshal(project, os);
			os.close();
		} catch (final Throwable e) {
			e.printStackTrace();
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}

	private void throwCoreException(String message) throws CoreException {
		final IStatus status = new Status(IStatus.ERROR, "uk.ac.ncl.safecap.xmldata", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}