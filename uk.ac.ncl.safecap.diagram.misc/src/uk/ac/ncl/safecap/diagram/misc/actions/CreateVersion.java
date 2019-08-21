package uk.ac.ncl.safecap.diagram.misc.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import uk.ac.ncl.safecap.common.resources.VersionUtil;

public class CreateVersion extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final String editorId = editor.getSite().getId();
		editor.doSave(new NullProgressMonitor());

		final IEditorInput input = editor.getEditorInput();
		if (input instanceof FileEditorInput) {
			try {
				final FileEditorInput fileInput = (FileEditorInput) input;
				final IFile file = fileInput.getFile();
				final IFile newFile = VersionUtil.getNextVersion(file);
				final IPath path = newFile.getFullPath();

				file.copy(path, true, new NullProgressMonitor());

				if (newFile.exists()) {
					try {
						final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						page.openEditor(new FileEditorInput(newFile), editorId, true);
					} catch (final PartInitException e) {
						e.printStackTrace();
					}
				}

			} catch (final CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

//	DiagramEditPart diagram = getSelectedDiagram(editor);
//	Project project = getSelectedModel(editor);
//	if (project != null && diagram != null) {
//		IEditorInput input = editor.getEditorInput();
//		if (input instanceof FileEditorInput)
//		{
//			EmfUtil.saveProject(project);
//			try {
//				FileEditorInput fileInput = (FileEditorInput) input;
//				IFile file = fileInput.getFile();
//				IFile newFile = VersionUtil.getNextVersion(file);
//				IPath path = newFile.getFullPath();
//
//				file.copy(path, true, new NullProgressMonitor());
//
//				if (newFile.exists())
//	            {
//	                try
//	                {
//	                	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//	                    page.openEditor(new FileEditorInput(newFile), "safecap.diagram.part.SafecapDiagramEditorID", true);
//	                }
//	                catch (PartInitException e)
//	                {
//	                    e.printStackTrace();
//	                }
//	            }
//
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	return null;

//    private static Project getSelectedModel(IEditorPart editor)
//    {
//        IEditorInput obj = editor.getEditorInput();
//        if (obj instanceof FileEditorInput) {
//            FileEditorInput input = (FileEditorInput) obj;
//            return EmfUtil.fromFile(input.getFile());
//        }
//        return null;
//    }
//
//    private static DiagramEditPart getSelectedDiagram(IEditorPart editor)
//    {
//    	if (editor != null && editor instanceof DiagramDocumentEditor) {
//			DiagramDocumentEditor sc_editor = (DiagramDocumentEditor) editor;
//			DiagramEditPart diagram = sc_editor.getDiagramEditPart();
//			return diagram;
//    	}
//    	return null;
//    }
//
}
