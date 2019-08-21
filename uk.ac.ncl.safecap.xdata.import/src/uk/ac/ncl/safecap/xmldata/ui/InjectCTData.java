package uk.ac.ncl.safecap.xmldata.ui;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import safecap.Project;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.xdata.base.steps.InjectTrackDataStep;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataUtils;

public class InjectCTData extends AbstractHandler {

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
			if (data == null) 
				return null;
			

			if (!data.getNamespaces().isEmpty()) {
				if (MessageDialog.openQuestion(null, "Data import", "The target is not empty, clear now?")) {
					data.dropTypes();
					data.dropData();
				}
			}

			try {
				final IFile mfile = SDAUIUtils.getSafecapModel(targetFile.getProject());
				if (mfile == null) 
					return null;
				
				
				final Resource resource = EmfUtil.resourceFromFile(mfile);
				// Resource resource = UIUtils.openResourceDialog(targetFile.getProject());
				if (resource == null) 
					return null;
				
				
				final safecap.Project prj = (Project) resource.getContents().get(0);
				if (prj == null) 
					return null;
				

				InjectTrackDataStep.injectControlTableInfo(data, prj);
				DataUtils.saveDataContextToFile(data, targetFile);
				targetFile.refreshLocal(IResource.DEPTH_INFINITE, null);

			} catch (final Throwable e) {
				e.printStackTrace();
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Data Import Error", e.getMessage());
			}
		}
		return null;
	}

}
