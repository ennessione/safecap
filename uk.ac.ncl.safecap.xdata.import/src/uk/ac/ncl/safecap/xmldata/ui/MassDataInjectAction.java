package uk.ac.ncl.safecap.xmldata.ui;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import uk.ac.ncl.safecap.xdata.base.steps.DataInjectStep;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;

public class MassDataInjectAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		final ISelectionService service = window.getSelectionService();
		final IStructuredSelection structured = (IStructuredSelection) service.getSelection();

		// check if it is an IFile
		if (structured.getFirstElement() instanceof IFile) {
			// get the selected file
			final IFile targetFile = (IFile) structured.getFirstElement();

			if (!targetFile.isAccessible()) {
				return null;
			}

			DataUtils.clearCache(targetFile);

			final File jfile = targetFile.getLocation().toFile();
			final DataContext data = DataUtils.getDataContextFromFile(jfile);
			if (data == null) {
				return null;
			}
			final Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
			final String filename = UIUtils.openDataDirectoryDialog(shell, "Open a folder with xml files");
			if (filename == null) {
				return null;
			}

			final IDataParser parser = SDAUIUtils.getParser();
			if (parser == null) {
				return null;
			}

			try {
				DataInjectStep.execute(data, filename, parser);
				DataUtils.saveDataContextToFile(data, targetFile);
				targetFile.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (final Throwable e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Data Import Error", e.getMessage());
			}
		}
		return null;
	}

}
