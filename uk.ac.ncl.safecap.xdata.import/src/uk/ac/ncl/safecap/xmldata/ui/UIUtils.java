package uk.ac.ncl.safecap.xmldata.ui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.xml.sax.SAXException;

import safecap.diagram.part.SafecapDiagramEditor;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;

public class UIUtils {
	static class DataLoader implements IRunnableWithProgress {
		private final File file;
		private DataContext context;

		public DataLoader(File file) {
			super();
			this.file = file;
		}

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				if (monitor.isCanceled()) {
					throw new Exception();
				}
				monitor.beginTask("Loading data from file...", 6);
				monitor.subTask("Parsing xml");
				context = DataUtils.getDataContextFromFile(file);
				if (monitor.isCanceled()) {
					throw new Exception();
				}
				monitor.worked(4);
				monitor.subTask("Building types...");
				// context.cookTypes();
				if (monitor.isCanceled()) {
					throw new Exception();
				}
				monitor.worked(1);
				monitor.subTask("Building functions...");
				context.build();
				if (monitor.isCanceled()) {
					throw new Exception();
				}
				monitor.worked(1);
			} catch (final Exception e) {
				context = null;
			}
		}

		public DataContext getContext() {
			return context;
		}

	}

	public static void forceRefreshSafecapEditor(IEditorPart editor) {
		final IEditorInput input = editor.getEditorInput();
		if (input instanceof IFileEditorInput) {
			try {
				final IFileEditorInput fileInput = (IFileEditorInput) input;
				fileInput.getFile().refreshLocal(IResource.DEPTH_ONE, null);
				final SafecapDiagramEditor sceditor = (SafecapDiagramEditor) editor;
				sceditor.setInput(new FileEditorInput(fileInput.getFile()));
			} catch (final CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads a data context file, builds functions and cooks types all the while
	 * showing the busy indicator
	 * 
	 * @param file
	 * @return
	 */
	public static DataContext getPreparedDataContextFromFile(File file) {
		try {
			final DataLoader _loader = new DataLoader(file);
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(_loader);
			return _loader.getContext();
		} catch (final Throwable e) {
			return null;
		}
	}

	// public static DataContext getDataContextFromFile(File file) {
	// try {
	// JAXBContext jaxbContext = JAXBContext.newInstance(DataContext.class,
	// Pair.class, ValueList.class, LocatedValue.class);
	// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	// return (DataContext) jaxbUnmarshaller.unmarshal(file);
	// } catch (JAXBException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	// public static DataContext getDataContextFromFile2(File file) throws
	// SDAImportException {
	// try {
	// JAXBContext jaxbContext = JAXBContext.newInstance(DataContext.class,
	// Pair.class, ValueList.class, LocatedValue.class);
	// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	// return (DataContext) jaxbUnmarshaller.unmarshal(file);
	// } catch (JAXBException e) {
	// throw new SDAImportException(e.getMessage());
	// }
	// }
	//
	// public static void saveDataContextToFile(DataContext dataContext, IFile
	// file) throws JAXBException, CoreException, IOException {
	// JAXBContext jaxbContext = JAXBContext.newInstance(DataContext.class,
	// Pair.class, ValueList.class, LocatedValue.class);
	// Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	//
	// ByteArrayOutputStream os = new ByteArrayOutputStream(64192);
	//
	// // output pretty printed
	// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
	// "http://www.w3.org/2001/XMLSchema-instance");
	// jaxbMarshaller.marshal(dataContext, os);
	// os.flush();
	//
	// file.setContents(new ByteArrayInputStream(os.toByteArray()), IFile.FORCE,
	// null);
	// }

	public static String openXmlFileDialog() {
		final FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.OPEN);
		fd.setText("Open an xml file");
		fd.setFilterExtensions(new String[] { "*.xml" });
		return fd.open();
	}

	public static String openCSVFileDialog() {
		final FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.OPEN);
		fd.setText("Open a csv file");
		fd.setFilterExtensions(new String[] { "*.csv" });
		return fd.open();
	}

	public static String openXmldataFileDialog() {
		final FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.OPEN);
		fd.setText("Open an xmldata project file");
		fd.setFilterExtensions(new String[] { "*.xmldata" });
		return fd.open();
	}

	public static String openFileSaveDialog(String ext) {
		final FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.SAVE);
		fd.setText("Save as");
		fd.setFilterExtensions(new String[] { ext });
		return fd.open();
	}

	public static String openFolderSaveDialog() {
		final FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.SAVE);
		fd.setText("Save at");
		return fd.open();
	}

	public static String openDataDirectoryDialog(Shell shell, String prompt) {
		final DirectoryDialog fd = new DirectoryDialog(shell, SWT.OPEN);
		fd.setMessage(prompt);
		fd.setText(prompt);
		return fd.open();
	}

	public static String openDataFileDialog(Shell shell, String prompt) {
		final FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText(prompt);
		return fd.open();
	}

	public static Resource openResourceDialog(IProject root) throws CoreException {
		IFile candidate = null;
		int count = 0;

		for (final IResource r : root.members()) {
			if (r instanceof IFile) {
				final IFile ifile = (IFile) r;
				if ("safecap".equals(ifile.getFileExtension())) {
					candidate = ifile;
					count++;
				}
			}
		}

		if (count == 1) {
			return EmfUtil.resourceFromFile(candidate);
		}

		final ResourceSelectionDialog dialog = new ResourceSelectionDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), root,
				"Select SafeCap track data file");
		dialog.open();
		final Object[] result = dialog.getResult();
		if (result != null && result.length == 1) {
			final Object z = result[0];
			if (z instanceof IFile) {
				final IFile ifile = (IFile) z;
				if (ifile.getFileExtension().equals("safecap")) {
					try {
						return EmfUtil.resourceFromFile(ifile);
					} catch (final Exception e) {
						return null;
					}
				}
			}
		}
		return null;
	}

	public static DataContext importDataContext(String filename) throws SAXException, IOException, ParserConfigurationException {
		DataContext context = null;
		try {
			final IDataParser parser = SDAUIUtils.getParser();
			if (parser != null) {
				context = DataUtils.importDataContext(parser, filename);
			}
		} catch (final Throwable e) {
			MessageDialog.openError(null, "Import failed", "Failed parsing and processing xml file");
			return null;
		}

		return context;
	}

	public static void openInBrowser(File file) {
		try {
			org.eclipse.swt.program.Program.launch(file.toURI().toURL().toString());
		} catch (final Throwable e) {
		}
	}
	
	
}
