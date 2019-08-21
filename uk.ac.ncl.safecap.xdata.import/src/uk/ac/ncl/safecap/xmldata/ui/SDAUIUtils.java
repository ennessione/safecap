package uk.ac.ncl.safecap.xmldata.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

import uk.ac.ncl.safecap.common.resources.VersionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;
import uk.ac.ncl.safecap.xmldata.parsing.ParserCatalog;

public class SDAUIUtils {

	private static class ProjectSearchWrap implements Runnable {
		private final IWorkbench iworkbench;
		IProject project;

		public ProjectSearchWrap(IWorkbench iworkbench) {
			this.iworkbench = iworkbench;
		}

		@Override
		public void run() {
			final IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
			if (iworkbenchwindow == null) {
				return;
			}
			final IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
			if (iworkbenchpage == null) {
				return;
			}
			final IEditorPart editor = iworkbenchpage.getActiveEditor();
			if (editor == null) {
				return;
			}
			final IEditorInput input = editor.getEditorInput();
			if (!(input instanceof IFileEditorInput)) {
				return;
			}
			final IFile file = ((IFileEditorInput) input).getFile();
			if (file == null) {
				return;
			}

			project = file.getProject();
		}
	}

	// Attempts to determine selected project via a current open editor
	public static IProject findSelectedProject() {

		final IWorkbench iworkbench = PlatformUI.getWorkbench();
		if (iworkbench == null) {
			return null;
		}

		if (iworkbench.getDisplay() == null) {
			return null;
		}

		final ProjectSearchWrap wrapper = new ProjectSearchWrap(iworkbench);

		iworkbench.getDisplay().syncExec(wrapper);

		return wrapper.project;
	}

	public static IDataParser getParser() {
		final List<IDataParser> allParsers = ParserCatalog.INSTANCE.getParsers();
		if (allParsers.isEmpty()) {
			MessageDialog.openError(null, "No parsers", "Not a single XML parser is defined");
			return null;
		} else if (allParsers.size() == 1) {
			return allParsers.get(0);
		} else {
			final ListDialog parserDialog = new ListDialog(Display.getDefault().getActiveShell());
			parserDialog.setContentProvider(new ArrayContentProvider());
			parserDialog.setLabelProvider(new LabelProvider());
			parserDialog.setInput(ParserCatalog.INSTANCE.getParsers().toArray());
			parserDialog.setTitle("Select a parser:");

			if (parserDialog.open() == Window.OK) {
				if (parserDialog.getResult().length > 0) {
					return (IDataParser) parserDialog.getResult()[0];
				}
			}
		}

		return null;
	}

	public static IFile getSafecapModel(IProject project) {
		final List<IFile> allModels = getSafecapModels(project);
		if (allModels.isEmpty()) {
			MessageDialog.openError(null, "No schema", "This project does not contain a SafeCap schema");
			return null;
		} else if (allModels.size() == 1) {
			return allModels.get(0);
		} else {
			final ListDialog schemaDialog = new ListDialog(Display.getDefault().getActiveShell());
			schemaDialog.setContentProvider(new ArrayContentProvider());
			schemaDialog.setLabelProvider(new ModelLabelProvider());
			schemaDialog.setInput(allModels.toArray());
			schemaDialog.setTitle("Select the schema:");

			if (schemaDialog.open() == Window.OK) {
				if (schemaDialog.getResult().length > 0) {
					return (IFile) schemaDialog.getResult()[0];
				}
			}
		}

		return null;
	}

	public static IFile getXMLDataModel(IProject project) {
		final List<IFile> allModels = getXMLDataModels(project);
		if (allModels.isEmpty()) {
			MessageDialog.openError(null, "No data files", "This project does not contain a data file");
			return null;
		} else if (allModels.size() == 1) {
			return allModels.get(0);
		} else {
			final ListDialog schemaDialog = new ListDialog(Display.getDefault().getActiveShell());
			schemaDialog.setContentProvider(new ArrayContentProvider());
			schemaDialog.setLabelProvider(new GenericLabelProvider());
			schemaDialog.setInput(allModels.toArray());
			schemaDialog.setTitle("Select data file:");

			if (schemaDialog.open() == Window.OK) {
				if (schemaDialog.getResult().length > 0) {
					return (IFile) schemaDialog.getResult()[0];
				}
			}
		}

		return null;
	}

	public static List<IFile> getSafecapModels(IProject project) {
		return getProjectFiles(project, SafecapConstants.SAFECAP_FILE_EXTENSION);
	}

	public static List<IFile> getXMLDataModels(IProject project) {
		return getProjectFiles(project, SafecapConstants.XMLDATA_EXTENSION);
	}

	public static List<IFile> getCatalogModels(IProject project) {
		return getProjectFiles(project, SafecapConstants.VCATALOG_EXTENSION);
	}

	public static List<IFile> getProjectFiles(IProject project, String extension) {
		try {
			final List<IFile> result = new ArrayList<>();
			final IResource[] resources = project.members();
			for (final IResource res : resources) {
				if (res instanceof IFile) {
					final IFile file = (IFile) res;
					if (file.exists() && file.getFileExtension() != null && file.getFileExtension().equals(extension)) {
						result.add(file);
					}
				}
			}

			return result;
		} catch (final CoreException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	private static class ModelLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof IFile) {
				final IFile file = (IFile) element;
				if (file.exists() && file.getFileExtension().equals(SafecapConstants.SAFECAP_FILE_EXTENSION)) {
					final String ls = file.getFullPath().removeFileExtension().lastSegment();
					return VersionUtil.getModelName(ls) + ":" + VersionUtil.getVersionName(ls);
				}
			}
			return null;
		}
	}

	private static class GenericLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof IFile) {
				final IFile file = (IFile) element;
				if (file.exists()) {
					final String ls = file.getFullPath().removeFileExtension().lastSegment();
					return ls;
				}
			}
			return null;
		}
	}

}
