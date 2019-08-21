package uk.ac.ncl.safecap.gui.trainconfig;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import safecap.Project;
import traintable.TraintablePackage;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class TrainTypesHandler extends AbstractHandler {
	private static Project getSelectedModel(IEditorPart editor) {
		final IEditorInput obj = editor.getEditorInput();

		if (obj instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) obj;
			return EmfUtil.fromFile(input.getFile());
		}

		return null;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//        IEditorPart editor = HandlerUtil.getActiveEditor(event);

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
		final IProject verProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Verification");
		if (verProject.exists()) {
			final IFile file = verProject.getFile("config.traintable");
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
					final EObject rootObject = TraintablePackage.eINSTANCE.getTraintableFactory().createTrainTable();
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
					page.openEditor(new FileEditorInput(file), "traintable.presentation.TraintableEditorID", true);
				} catch (final PartInitException e) {
					e.printStackTrace();
				}
			}
		}

//        List<Train> trains = TrainConfig.getTrainTypes();
//        if (trains != null)
//        {
//            for (Train train: trains)
//                System.out.println(train);
//        }

		return null;
	}
}
