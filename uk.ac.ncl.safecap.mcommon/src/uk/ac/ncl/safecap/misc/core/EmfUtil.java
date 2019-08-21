package uk.ac.ncl.safecap.misc.core;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import safecap.Project;
import uk.ac.ncl.safecap.common.resources.SafecapModelRegistry;

public class EmfUtil {

	public static IResource getPlatformResource(Project project) {
		if (project == null) {
			return null;
		}
		final Resource eResource = project.eResource();
		final URI eUri = eResource.getURI();
		if (eUri.isPlatformResource()) {
			final String platformString = eUri.toPlatformString(true);
			return ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
		}
		return null;
	}

	public static IResource getPlatformResource(Resource resource) {
		final URI eUri = resource.getURI();
		if (eUri.isPlatformResource()) {
			final String platformString = eUri.toPlatformString(true);
			return ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
		}
		return null;
	}

	public static void saveProject(Project project) {
		try {
			if (project.eResource() == null) { // ok, some trouble here. Try
												// locate
				MessageDialog.openError(null, "Saving failed", "Cannot save schema changes as the primary editor has been closed.");
				return;
			}

			project.eResource().save(Collections.EMPTY_MAP);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		// Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		// Map<String, Object> m = reg.getExtensionToFactoryMap();
		// m.put(SafecapConstants.SAFECAP_FILE_EXTENSION, new
		// XMIResourceFactoryImpl());
		//
		// // Obtain a new resource set
		// ResourceSet resSet = new ResourceSetImpl();
		//
		// org.eclipse.emf.common.util.URI loc = project.eResource().getURI();
		//
		// // Create a resource
		// //org.eclipse.emf.common.util.URI loc =
		// org.eclipse.emf.common.util.URI.createPlatformResourceURI(log_file.getFullPath().toString(),
		// true);
		// Resource resource = resSet.getResource(loc, true);
		// resource.getContents().set(0, project);
		//
		// // Now save the content.
		// try {
		// resource.save(Collections.EMPTY_MAP);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static String getElementDescriptor(final EObject element) {
		return element.eResource().getURIFragment(element);
	}

	public static EObject getObjectFromDescriptor(final Project project, final String descriptor) {
		if (descriptor != null && project != null && project.eResource() != null) {
			final EObject object = project.eResource().getEObject(descriptor);
			return object;
		} else {
			return null;
		}
	}

	public static void setFeature(final EObject object, final EStructuralFeature feature, final Object value) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(object);
		if (domain == null) {
			object.eSet(feature, value);
		} else {
			final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "setFeatureCommand", null) {

				@Override
				protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info)
						throws ExecutionException {

					object.eSet(feature, value);

					return CommandResult.newOKCommandResult();
				}
			};

			try {
				command.execute(null, null);
			} catch (final ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public static Project fromFile(String pathString) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IPath location = Path.fromPortableString(pathString);
		final IFile xfile = (IFile) workspace.getRoot().findMember(location);

		if (xfile == null) {
			return null;
		} else {
			return fromFile(xfile);
		}
	}

	public static Project fromFile(IFile file) {

		safecap.SafecapPackage.eINSTANCE.eClass();
		safecap.model.ModelPackage.eINSTANCE.eClass();
		safecap.schema.SchemaPackage.eINSTANCE.eClass();
		safecap.trackside.TracksidePackage.eINSTANCE.eClass();

		if (!SafecapConstants.SAFECAP_FILE_EXTENSION.equals(file.getFileExtension())) {
			return null;
		}

		final Resource resource = SafecapModelRegistry.getResource(file);
		if (resource.getContents().isEmpty()) {
			return null;
		}
		final Object contents = resource.getContents().get(0);
		if (contents instanceof Project) {
			// System.out.println("Loaded project " + contents);
			return (Project) contents;
		}
		return null;

	}

	public static Resource resourceFromFile(IFile file) {

		safecap.SafecapPackage.eINSTANCE.eClass();
		safecap.model.ModelPackage.eINSTANCE.eClass();
		safecap.schema.SchemaPackage.eINSTANCE.eClass();
		safecap.trackside.TracksidePackage.eINSTANCE.eClass();

		if (!SafecapConstants.SAFECAP_FILE_EXTENSION.equals(file.getFileExtension())) {
			return null;
		}

		final Resource resource = SafecapModelRegistry.getResource(file);
		if (resource.getContents().isEmpty()) {
			return null;
		}

		return resource;
	}

	public static Project fromActiveEditor(IFile file) {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			final IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				for (final IEditorReference ref : page.getEditorReferences()) {
					final IEditorPart editor = ref.getEditor(false);
					if (editor instanceof DiagramDocumentEditor) {
						final DiagramDocumentEditor diag_editor = (DiagramDocumentEditor) editor;
						final org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) diag_editor
								.getDiagramDocument().getContent();
						final Project prj = getProject(view.getElement());
						final URI uri = prj.eResource().getURI();
						final String name = uri.lastSegment();
						if (file.getName().equals(name)) {
							ref.getEditor(true);
							return prj;
						}
					}
				}
			}
		}

		return null;
	}

	public static Project fromActiveEditor(String file) {

		// Resource res = SafecapModelRegistry.getResource();
		// List<EObject> contents = res.getContents();
		// for (EObject root: contents)
		// if(root instanceof Project)
		// return (Project) root;

		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			final IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				for (final IEditorReference ref : page.getEditorReferences()) {
					final IEditorPart editor = ref.getEditor(false);
					if (editor instanceof DiagramDocumentEditor) {
						final DiagramDocumentEditor diag_editor = (DiagramDocumentEditor) editor;
						final org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) diag_editor
								.getDiagramDocument().getContent();
						final Project prj = getProject(view.getElement());
						final URI uri = prj.eResource().getURI();
						final String name = uri.lastSegment();
						if (file.equals(name)) {
							ref.getEditor(true);
							return prj;
						}
					}
				}
			}
		}

		return null;
	}

	public static Project getProject(EObject obj) {
		try {
			safecap.SafecapPackage.eINSTANCE.eClass();
			safecap.model.ModelPackage.eINSTANCE.eClass();
			safecap.schema.SchemaPackage.eINSTANCE.eClass();
			safecap.trackside.TracksidePackage.eINSTANCE.eClass();

			EObject c = obj;

			while (c.eContainer() != null) {
				c = c.eContainer();
			}

			if (c instanceof Project) {
				return (Project) c;
			} else {
				return null;
			}
		} catch (final Throwable e) {
			return null;
		}
	}

	public static boolean hasErrors(final Project scenario) {
		final URI uri = scenario.eResource().getURI();

		final IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(uri.toPlatformString(false)));

		return hasErrors(resource);
	}

	public static boolean hasErrors(final IResource resource) {
		IMarker[] problems = null;
		final int depth = IResource.DEPTH_INFINITE;
		try {
			problems = resource.findMarkers(IMarker.PROBLEM, true, depth);
			if (problems != null && problems.length > 0) {
				for (final IMarker m : problems) {
					final int severityAttribute = m.getAttribute(IMarker.SEVERITY, -1);
					if (severityAttribute >= IMarker.SEVERITY_ERROR) {
						return true;
					}
				}
			}
			return false;
		} catch (final CoreException e) {
		}

		return false;
	}
}
