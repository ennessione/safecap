package uk.ac.ncl.safecap.gui.trainconfig;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import servicepattern.ServicepatternPackage;

public class ServicePatternsHandler extends AbstractHandler {
	private IProject extractProjectFromEditor(IEditorPart editor) {
		final IEditorInput input = editor.getEditorInput();
		if (!(input instanceof IFileEditorInput)) {
			return null;
		}
		final IFile file = ((IFileEditorInput) input).getFile();
		if (file.getFileExtension().equals("safecap")) {
			return file.getProject();
		}
		return null;
	}

	private IProject extractProjectFromSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}
		final IStructuredSelection ss = (IStructuredSelection) sel;
		final Object element = ss.getFirstElement();
		if (element instanceof IProject) {
			return (IProject) element;
		}
		if (element instanceof IFile) {
			return ((IFile) element).getProject();
		}
		if (element instanceof IAdaptable) {
			final IAdaptable adaptable = (IAdaptable) element;
			final IResource adapter = adaptable.getAdapter(IResource.class);
			return adapter.getProject();
		}
		return null;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		IProject verProject = null;
		if (editor != null) {
			verProject = extractProjectFromEditor(editor);
		}
		if (verProject == null) {
			final ISelection selection = HandlerUtil.getCurrentSelection(event);
			if (selection != null) {
				verProject = extractProjectFromSelection(selection);
			}
		}

		try {
			if (verProject == null || !verProject.hasNature("uk.ac.ncl.safecap.platform.SafecapNature")) {
				verProject = null;
			}
		} catch (final CoreException e1) {
			verProject = null;
			e1.printStackTrace();
		}

//        Project project = getSelectedModel(editor);
//        if (project != null && project.getLines().size() > 0)
//        {
//            IVerificationProfile profile = SVerificationProfileFactory.getProfile();
//            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//                    .getShell();
//            VerificationDialog dialog = new VerificationDialog(shell,
//                    profile, project);
//            dialog.create();
//            dialog.open();
//        }

		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (verProject != null && verProject.exists()) {
			final IFile file = verProject.getFile("config.servicepattern");
			if (!file.exists()) {
				try {
					// Create a resource set
					//
					final ResourceSet resourceSet = new ResourceSetImpl();
					// Get the URI of the model file.
					//
					final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					// Create a resource for this file.
					//
					final Resource resource = resourceSet.createResource(fileURI);
					// Add the initial model object to the contents.
					//
					final EObject rootObject = ServicepatternPackage.eINSTANCE.getServicepatternFactory().createLibrary();
					if (rootObject != null) {
						resource.getContents().add(rootObject);
					}

					// Save the contents of the resource to the file system.
					//
					final Map<Object, Object> options = new HashMap<>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					resource.save(options);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

			if (file.exists()) {
				try {
					page.openEditor(new FileEditorInput(file), "servicepattern.presentation.ServicepatternEditorID", true);
				} catch (final PartInitException e) {
					e.printStackTrace();
				}
			}
		}

//        IEditorInput input = editor.getEditorInput();
//        IFile file = ((IFileEditorInput)input).getFile();
//        if (file.getFileExtension().equals("safecap"))
//        {
//            try
//            {
//                ResourceSet resourceSet = new ResourceSetImpl();
//                URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
//                Resource resource = resourceSet.createResource(fileURI);
//                Map<Object, Object> options = new HashMap<Object, Object>();
//                options.put(XMLResource.OPTION_ENCODING, "UTF-8");
//                resource.load(options);
//                Project proj = (Project) resource.getContents().get(0);
//                List<Pattern> patterns = TrainConfig.getServicePatterns(proj);
//                for (Pattern pat: patterns)
//                {
//                    System.out.println(pat);
//                }
//            }
//            catch (IOException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }

		return null;
	}
}
