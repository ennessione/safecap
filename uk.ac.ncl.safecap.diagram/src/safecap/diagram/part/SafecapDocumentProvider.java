package safecap.diagram.part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.AbstractDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.internal.EditorStatusCodes;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.internal.util.DiagramIOUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import safecap.diagram.edit.parts.ProjectEditPart;
import uk.ac.ncl.safecap.common.resources.SafecapModelRegistry;
import uk.ac.ncl.safecap.diagram.misc.actions.PointCrossoverDetection;
import uk.ac.ncl.safecap.diagram.misc.actions.RouteSync;
import uk.ac.ncl.safecap.diagram.misc.postprocess.AmbitLabelling;
import uk.ac.ncl.safecap.diagram.misc.postprocess.BuildRoutes;
import uk.ac.ncl.safecap.diagram.misc.postprocess.JunctionPoints;
import uk.ac.ncl.safecap.diagram.misc.postprocess.MarkGenericEquipment;
import uk.ac.ncl.safecap.diagram.misc.postprocess.ModelCleanup;
import uk.ac.ncl.safecap.diagram.misc.postprocess.PointRoleDetection;
import uk.ac.ncl.safecap.diagram.misc.postprocess.RefreshNodeKindAndRole;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SegmentAmbitLink;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SubRouteSubOverlapDetection;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.ProjectTransient;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

/**
 * @generated
 */
public class SafecapDocumentProvider extends AbstractDocumentProvider implements IDiagramDocumentProvider {

	private IFile _file;

	/**
	 * @generated
	 */
	@Override
	protected ElementInfo createElementInfo(Object element) throws CoreException {
		if (false == element instanceof FileEditorInput && false == element instanceof URIEditorInput) {
			throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0,
					NLS.bind(Messages.SafecapDocumentProvider_IncorrectInputError,
							new Object[] { element, "org.eclipse.ui.part.FileEditorInput", "org.eclipse.emf.common.ui.URIEditorInput" }), //$NON-NLS-1$ //$NON-NLS-2$
					null));
		}
		final IEditorInput editorInput = (IEditorInput) element;
		final IDiagramDocument document = (IDiagramDocument) createDocument(editorInput);

		final ResourceSetInfo info = new ResourceSetInfo(document, editorInput);
		info.setModificationStamp(computeModificationStamp(info));
		info.fStatus = null;
		return info;
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		if (false == element instanceof FileEditorInput && false == element instanceof URIEditorInput) {
			throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0,
					NLS.bind(Messages.SafecapDocumentProvider_IncorrectInputError,
							new Object[] { element, "org.eclipse.ui.part.FileEditorInput", "org.eclipse.emf.common.ui.URIEditorInput" }), //$NON-NLS-1$ //$NON-NLS-2$
					null));
		}
		_file = ((FileEditorInput) element).getFile();
		final IDocument document = createEmptyDocument();
		setDocumentContent(document, (IEditorInput) element);
		setupDocument(element, document);
		return document;
	}

	/**
	 * Sets up the given document as it would be provided for the given element. The
	 * content of the document is not changed. This default implementation is empty.
	 * Subclasses may reimplement.
	 * 
	 * @param element  the blue-print element
	 * @param document the document to set up
	 * @generated
	 */
	protected void setupDocument(Object element, IDocument document) {
		// for subclasses
	}

	/**
	 * @generated
	 */
	private long computeModificationStamp(ResourceSetInfo info) {
		int result = 0;
		for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
			final Resource nextResource = it.next();
			final IFile file = WorkspaceSynchronizer.getFile(nextResource);
			if (file != null) {
				if (file.getLocation() != null) {
					result += file.getLocation().toFile().lastModified();
				} else {
					result += file.getModificationStamp();
				}
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	@Override
	protected IDocument createEmptyDocument() {
		final DiagramDocument document = new DiagramDocument();
		document.setEditingDomain(createEditingDomain());
		return document;
	}

	/**
	 * @generated NOT
	 */
	private TransactionalEditingDomain createEditingDomain() {
		// TransactionalEditingDomain editingDomain =
		// DiagramEditingDomainFactory
		// .getInstance().createEditingDomain();
		// editingDomain.setID("uk.ac.ncl.safecap.diagram.EditingDomain"); //$NON-NLS-1$
		final TransactionalEditingDomain editingDomain = uk.ac.ncl.safecap.common.resources.SafecapModelRegistry.getDomain(_file);

		final NotificationFilter diagramResourceModifiedFilter = NotificationFilter.createNotifierFilter(editingDomain.getResourceSet())
				.and(NotificationFilter.createEventTypeFilter(Notification.ADD))
				.and(NotificationFilter.createFeatureFilter(ResourceSet.class, ResourceSet.RESOURCE_SET__RESOURCES));
		editingDomain.getResourceSet().eAdapters().add(new Adapter() {

			private Notifier myTarger;

			@Override
			public Notifier getTarget() {
				return myTarger;
			}

			@Override
			public boolean isAdapterForType(Object type) {
				return false;
			}

			@Override
			public void notifyChanged(Notification notification) {
				if (diagramResourceModifiedFilter.matches(notification)) {
					final Object value = notification.getNewValue();
					if (value instanceof Resource) {
						((Resource) value).setTrackingModification(true);
					}
				}
			}

			@Override
			public void setTarget(Notifier newTarget) {
				myTarger = newTarget;
			}

		});

		return editingDomain;
	}

	/**
	 * @generated NOT
	 */
	protected void setDocumentContent(IDocument document, IEditorInput element) throws CoreException {
		final IDiagramDocument diagramDocument = (IDiagramDocument) document;
		final TransactionalEditingDomain domain = diagramDocument.getEditingDomain();
		if (element instanceof FileEditorInput) {

			final FileEditorInput fileInput = (FileEditorInput) element;
			final Resource resource = SafecapModelRegistry.getResource(fileInput.getFile());
			Diagram diagram = null;
			final int i = 3;
			final Iterator rootContents = resource.getContents().iterator();
			while (rootContents.hasNext()) {
				final EObject rootElement = (EObject) rootContents.next();
				if (rootElement instanceof Diagram) {
					diagram = (Diagram) rootElement;
					break;
				}
			}

			if (diagram == null) {
				final IStorage storage = ((FileEditorInput) element).getStorage();
				diagram = DiagramIOUtil.load(domain, storage, true, getProgressMonitor());
			}

			if (diagram == null) {
				throw new RuntimeException("Resource contains no diagram");
			}
			document.setContent(diagram);
		} else if (element instanceof URIEditorInput) {
			final URI uri = ((URIEditorInput) element).getURI();
			Resource resource = null;
			try {
				resource = domain.getResourceSet().getResource(uri.trimFragment(), false);
				if (resource == null) {
					resource = domain.getResourceSet().createResource(uri.trimFragment());
				}
				if (!resource.isLoaded()) {
					try {
						final Map options = new HashMap(GMFResourceFactory.getDefaultLoadOptions());
						// @see 171060
						// options.put(org.eclipse.emf.ecore.xmi.XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
						// Boolean.TRUE);
						resource.load(options);
					} catch (final IOException e) {
						resource.unload();
						throw e;
					}
				}
				if (uri.fragment() != null) {
					final EObject rootElement = resource.getEObject(uri.fragment());
					if (rootElement instanceof Diagram) {
						document.setContent(rootElement);
						return;
					}
				} else {
					for (final Iterator it = resource.getContents().iterator(); it.hasNext();) {
						final Object rootElement = it.next();
						if (rootElement instanceof Diagram) {
							document.setContent(rootElement);
							return;
						}
					}
				}
				throw new RuntimeException(Messages.SafecapDocumentProvider_NoDiagramInResourceError);
			} catch (final Exception e) {
				CoreException thrownExcp = null;
				if (e instanceof CoreException) {
					thrownExcp = (CoreException) e;
				} else {
					final String msg = e.getLocalizedMessage();
					thrownExcp = new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0,
							msg != null ? msg : Messages.SafecapDocumentProvider_DiagramLoadingError, e));
				}
				throw thrownExcp;
			}
		} else {
			throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0,
					NLS.bind(Messages.SafecapDocumentProvider_IncorrectInputError,
							new Object[] { element, "org.eclipse.ui.part.FileEditorInput", "org.eclipse.emf.common.ui.URIEditorInput" }), //$NON-NLS-1$ //$NON-NLS-2$
					null));
		}
	}

	/**
	 * @generated
	 */
	@Override
	public long getModificationStamp(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			return computeModificationStamp(info);
		}
		return super.getModificationStamp(element);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isDeleted(Object element) {
		final IDiagramDocument document = getDiagramDocument(element);
		if (document != null) {
			final Resource diagramResource = document.getDiagram().eResource();
			if (diagramResource != null) {
				final IFile file = WorkspaceSynchronizer.getFile(diagramResource);
				return file == null || file.getLocation() == null || !file.getLocation().toFile().exists();
			}
		}
		return super.isDeleted(element);
	}

	/**
	 * @generated
	 */
	public ResourceSetInfo getResourceSetInfo(Object editorInput) {
		return (ResourceSetInfo) super.getElementInfo(editorInput);
	}

	/**
	 * @generated
	 */
	@Override
	protected void disposeElementInfo(Object element, ElementInfo info) {
		if (info instanceof ResourceSetInfo) {
			final ResourceSetInfo resourceSetInfo = (ResourceSetInfo) info;
			resourceSetInfo.dispose();
		}
		super.disposeElementInfo(element, info);
	}

	/**
	 * @generated
	 */
	@Override
	protected void doValidateState(Object element, Object computationContext) throws CoreException {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			final LinkedList<IFile> files2Validate = new LinkedList<>();
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				final IFile file = WorkspaceSynchronizer.getFile(nextResource);
				if (file != null && file.isReadOnly()) {
					files2Validate.add(file);
				}
			}
			ResourcesPlugin.getWorkspace().validateEdit(files2Validate.toArray(new IFile[files2Validate.size()]), computationContext);
		}

		super.doValidateState(element, computationContext);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isReadOnly(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			if (info.isUpdateCache()) {
				try {
					updateCache(element);
				} catch (final CoreException ex) {
					SafecapDiagramEditorPlugin.getInstance().logError(Messages.SafecapDocumentProvider_isModifiable, ex);
					// Error message to log was initially taken from
					// org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.internal.l10n.EditorMessages.StorageDocumentProvider_isModifiable
				}
			}
			return info.isReadOnly();
		}
		return super.isReadOnly(element);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isModifiable(Object element) {
		if (!isStateValidated(element)) {
			if (element instanceof FileEditorInput || element instanceof URIEditorInput) {
				return true;
			}
		}
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			if (info.isUpdateCache()) {
				try {
					updateCache(element);
				} catch (final CoreException ex) {
					SafecapDiagramEditorPlugin.getInstance().logError(Messages.SafecapDocumentProvider_isModifiable, ex);
					// Error message to log was initially taken from
					// org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.internal.l10n.EditorMessages.StorageDocumentProvider_isModifiable
				}
			}
			return info.isModifiable();
		}
		return super.isModifiable(element);
	}

	/**
	 * @generated
	 */
	protected void updateCache(Object element) throws CoreException {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				final IFile file = WorkspaceSynchronizer.getFile(nextResource);
				if (file != null && file.isReadOnly()) {
					info.setReadOnly(true);
					info.setModifiable(false);
					return;
				}
			}
			info.setReadOnly(false);
			info.setModifiable(true);
			return;
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void doUpdateStateCache(Object element) throws CoreException {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			info.setUpdateCache(true);
		}
		super.doUpdateStateCache(element);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isSynchronized(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			return info.isSynchronized();
		}
		return super.isSynchronized(element);
	}

	/**
	 * @generated
	 */
	@Override
	protected ISchedulingRule getResetRule(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			final LinkedList<ISchedulingRule> rules = new LinkedList<>();
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				final IFile file = WorkspaceSynchronizer.getFile(nextResource);
				if (file != null) {
					rules.add(ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(file));
				}
			}
			return new MultiRule(rules.toArray(new ISchedulingRule[rules.size()]));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	protected ISchedulingRule getSaveRule(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			final LinkedList<ISchedulingRule> rules = new LinkedList<>();
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				final IFile file = WorkspaceSynchronizer.getFile(nextResource);
				if (file != null) {
					rules.add(computeSchedulingRule(file));
				}
			}
			return new MultiRule(rules.toArray(new ISchedulingRule[rules.size()]));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	protected ISchedulingRule getSynchronizeRule(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			final LinkedList<ISchedulingRule> rules = new LinkedList<>();
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				final IFile file = WorkspaceSynchronizer.getFile(nextResource);
				if (file != null) {
					rules.add(ResourcesPlugin.getWorkspace().getRuleFactory().refreshRule(file));
				}
			}
			return new MultiRule(rules.toArray(new ISchedulingRule[rules.size()]));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	protected ISchedulingRule getValidateStateRule(Object element) {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			final LinkedList<ISchedulingRule> files = new LinkedList<>();
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				final IFile file = WorkspaceSynchronizer.getFile(nextResource);
				if (file != null) {
					files.add(file);
				}
			}
			return ResourcesPlugin.getWorkspace().getRuleFactory().validateEditRule(files.toArray(new IFile[files.size()]));
		}
		return null;
	}

	/**
	 * @generated
	 */
	private ISchedulingRule computeSchedulingRule(IResource toCreateOrModify) {
		if (toCreateOrModify.exists()) {
			return ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(toCreateOrModify);
		}

		IResource parent = toCreateOrModify;
		do {
			/*
			 * XXX This is a workaround for
			 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=67601
			 * IResourceRuleFactory.createRule should iterate the hierarchy itself.
			 */
			toCreateOrModify = parent;
			parent = toCreateOrModify.getParent();
		} while (parent != null && !parent.exists());

		return ResourcesPlugin.getWorkspace().getRuleFactory().createRule(toCreateOrModify);
	}

	/**
	 * @generated
	 */
	@Override
	protected void doSynchronize(Object element, IProgressMonitor monitor) throws CoreException {
		final ResourceSetInfo info = getResourceSetInfo(element);
		if (info != null) {
			for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				final Resource nextResource = it.next();
				handleElementChanged(info, nextResource, monitor);
			}
			return;
		}
		super.doSynchronize(element, monitor);
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {

		final org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) document.getContent();

		// the project being saved
		final safecap.Project root = EmfUtil.getProject(view.getElement());

		ProjectEditPart project = null;
		final IEditorInput input = (IEditorInput) element;
		final IWorkbench wb = PlatformUI.getWorkbench();
		final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		final IWorkbenchPage page = win.getActivePage();
		final IEditorPart editor = page.findEditor(input);
		if (editor != null && editor instanceof SafecapDiagramEditor) {
			final SafecapDiagramEditor sc_editor = (SafecapDiagramEditor) editor;
			project = (ProjectEditPart) sc_editor.getDiagramEditPart();
		}

		final long timer0 = System.currentTimeMillis();

		ProjectTransient.resetProjectLevel(root);
		final boolean customBuild = ExtensionUtil.getBool(root, SafecapConstants.EXT_LDL_GEN, "customBuild");
		final boolean forcePointDetection = ExtensionUtil.getFlag(root, "forcePointDetection");
		final boolean importedRoutes = ExtensionUtil.getBool(root, "uk.ac.ncl.safecap.importroutes", "importroutes");
		// System.out.println("[SAVE-0]: " + (System.currentTimeMillis() - timer0));

		if (!customBuild) {

			// System.out.println("0: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));
			// do post-processing
			// if (!EmfUtil.hasErrors(root)) {
			// AutonameSegments.refresh(root);
			RefreshNodeKindAndRole.refresh(root, monitor);
			// System.out.println("[SAVE-1]: " + (System.currentTimeMillis() - timer0));
			// System.out.println("1: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));
			// RefreshSegmentRole.refresh(root, monitor);
			SegmentAmbitLink.refresh(root, monitor);
			// System.out.println("[SAVE-2]: " + (System.currentTimeMillis() - timer0));
			// System.out.println("2: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));
			AmbitLabelling.refresh(root, monitor);
			// System.out.println("[SAVE-3]: " + (System.currentTimeMillis() - timer0));
			// System.out.println("3: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));

			if (project != null && (forcePointDetection || PointCrossoverDetection.getToggleState())) {
				PointRoleDetection.refresh(project, root, monitor);
				// System.out.println("[SAVE-4]: " + (System.currentTimeMillis() - timer0));
			}

			// System.out.println("4: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));
			JunctionPoints.refresh(root, monitor);
			// System.out.println("[SAVE-5]: " + (System.currentTimeMillis() - timer0));
			// System.out.println("5: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));

			if (RouteSync.getToggleState() && !importedRoutes) {
				BuildRoutes.doSignalRoutes(root, monitor);
				// System.out.println("[SAVE-6]: " + (System.currentTimeMillis() - timer0));
				// System.out.println("6: has TPLH ambit: " + AmbitUtil.getByLabel(root,
				// "TPLH"));
			}

			// TrapPointDetection.refresh(project, root, monitor);
			// System.out.println("7: has TPLH ambit: " + AmbitUtil.getByLabel(root,
			// "TPLH"));

			ModelCleanup.refresh(root, monitor);
			// System.out.println("[SAVE-7]: " + (System.currentTimeMillis() - timer0));
			// System.out.println("has TPLH ambit: " + AmbitUtil.getByLabel(root, "TPLH"));
			// SubRouteSubOverlapDetection.refresh(project, root, monitor);
			// System.out.println("has TPLH ambit: " + AmbitUtil.getByLabel(root, "TPLH"));
		} else {
			RefreshNodeKindAndRole.refresh(root, monitor);
			AmbitLabelling.refresh(root, monitor);
			JunctionPoints.refresh(root, monitor);
		}
		SubRouteSubOverlapDetection.refresh(project, root, monitor);
		// System.out.println("[SAVE-8]: " + (System.currentTimeMillis() - timer0));
		MarkGenericEquipment.refresh(root, monitor);
		// System.out.println("[SAVE-9]: " + (System.currentTimeMillis() - timer0));

		// validation must be run after post-processing
		ValidateAction.runValidation(view);
		// System.out.println("[SAVE-10]: " + (System.currentTimeMillis() - timer0));

		final ResourceSetInfo info = getResourceSetInfo(element);

		// // update navigator
		// IWorkspaceRoot workspaceRoot =
		// org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot();
		// for(Resource r: info.getResourceSet().getResources()) {
		// URI uri = r.getURI();
		// IFile file = workspaceRoot.getFile(new
		// Path(uri.toPlatformString(true)));
		// if (file != null) file.refreshLocal(IResource.DEPTH_INFINITE,
		// monitor);
		// }

		if (info != null) {
			if (!overwrite && !info.isSynchronized()) {
				throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, IResourceStatus.OUT_OF_SYNC_LOCAL,
						Messages.SafecapDocumentProvider_UnsynchronizedFileSaveError, null));
			}
			info.stopResourceListening();
			fireElementStateChanging(element);
			try {
				monitor.beginTask(Messages.SafecapDocumentProvider_SaveDiagramTask, info.getResourceSet().getResources().size() + 1); // "Saving
																																		// diagram"
				for (final Iterator<Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					final Resource nextResource = it.next();
					// if (nextResource.getContents().size() == 1) { // missing
					// // safecap
					// // project?
					// if (!(nextResource.getContents().get(0) instanceof
					// Project)) {
					// nextResource.getContents().add(0, root);
					// }
					// }
					monitor.setTaskName(NLS.bind(Messages.SafecapDocumentProvider_SaveNextResourceTask, nextResource.getURI()));
					if (nextResource.isLoaded() && !info.getEditingDomain().isReadOnly(nextResource)) {
						try {
							nextResource.save(SafecapDiagramEditorUtil.getSaveOptions());
						} catch (final IOException e) {
							fireElementStateChangeFailed(element);
							throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID,
									EditorStatusCodes.RESOURCE_FAILURE, e.getLocalizedMessage(), null));
						}
					}
					monitor.worked(1);
				}
				monitor.done();
				info.setModificationStamp(computeModificationStamp(info));
			} catch (final RuntimeException x) {
				fireElementStateChangeFailed(element);
				throw x;
			} finally {
				info.startResourceListening();
			}
		} else {
			URI newResoruceURI;
			List<IFile> affectedFiles = null;
			if (element instanceof FileEditorInput) {
				final IFile newFile = ((FileEditorInput) element).getFile();
				affectedFiles = Collections.singletonList(newFile);
				newResoruceURI = URI.createPlatformResourceURI(newFile.getFullPath().toString(), true);
			} else if (element instanceof URIEditorInput) {
				newResoruceURI = ((URIEditorInput) element).getURI();
			} else {
				fireElementStateChangeFailed(element);
				throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0,
						NLS.bind(Messages.SafecapDocumentProvider_IncorrectInputError,
								new Object[] { element, "org.eclipse.ui.part.FileEditorInput", //$NON-NLS-1$
										"org.eclipse.emf.common.ui.URIEditorInput" }), //$NON-NLS-1$
						null));
			}
			if (false == document instanceof IDiagramDocument) {
				fireElementStateChangeFailed(element);
				throw new CoreException(
						new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0,
								"Incorrect document used: " + document //$NON-NLS-1$
										+ " instead of org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument", //$NON-NLS-1$
								null));
			}
			final IDiagramDocument diagramDocument = (IDiagramDocument) document;
			final Resource newResource = diagramDocument.getEditingDomain().getResourceSet().createResource(newResoruceURI);
			final Diagram diagramCopy = EcoreUtil.copy(diagramDocument.getDiagram());
			try {
				new AbstractTransactionalCommand(diagramDocument.getEditingDomain(),
						NLS.bind(Messages.SafecapDocumentProvider_SaveAsOperation, diagramCopy.getName()), affectedFiles) {
					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						// newResource.getContents().add(root);
						newResource.getContents().add(diagramCopy);
						return CommandResult.newOKCommandResult();
					}
				}.execute(monitor, null);
				newResource.save(SafecapDiagramEditorUtil.getSaveOptions());
			} catch (final ExecutionException e) {
				fireElementStateChangeFailed(element);
				throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0, e.getLocalizedMessage(), null));
			} catch (final IOException e) {
				fireElementStateChangeFailed(element);
				throw new CoreException(new Status(IStatus.ERROR, SafecapDiagramEditorPlugin.ID, 0, e.getLocalizedMessage(), null));
			}
			newResource.unload();
		}

		System.out.println("[SAVE-11]: " + (System.currentTimeMillis() - timer0));

	}

	/**
	 * @generated
	 */
	protected void handleElementChanged(ResourceSetInfo info, Resource changedResource, IProgressMonitor monitor) {
		final IFile file = WorkspaceSynchronizer.getFile(changedResource);
		if (file != null) {
			try {
				file.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (final CoreException ex) {
				SafecapDiagramEditorPlugin.getInstance().logError(Messages.SafecapDocumentProvider_handleElementContentChanged, ex);
				// Error message to log was initially taken from
				// org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.internal.l10n.EditorMessages.FileDocumentProvider_handleElementContentChanged
			}
		}
		changedResource.unload();

		fireElementContentAboutToBeReplaced(info.getEditorInput());
		removeUnchangedElementListeners(info.getEditorInput(), info);
		info.fStatus = null;
		try {
			setDocumentContent(info.fDocument, info.getEditorInput());
		} catch (final CoreException e) {
			info.fStatus = e.getStatus();
		}
		if (!info.fCanBeSaved) {
			info.setModificationStamp(computeModificationStamp(info));
		}
		addUnchangedElementListeners(info.getEditorInput(), info);
		fireElementContentReplaced(info.getEditorInput());
	}

	/**
	 * @generated
	 */
	protected void handleElementMoved(IEditorInput input, URI uri) {
		if (input instanceof FileEditorInput) {
			final IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(URI.decode(uri.path())).removeFirstSegments(1));
			fireElementMoved(input, newFile == null ? null : new FileEditorInput(newFile));
			return;
		}
		// TODO: append suffix to the URI! (use diagram as a parameter)
		fireElementMoved(input, new URIEditorInput(uri));
	}

	/**
	 * @generated
	 */
	@Override
	public IEditorInput createInputWithEditingDomain(IEditorInput editorInput, TransactionalEditingDomain domain) {
		return editorInput;
	}

	/**
	 * @generated
	 */
	@Override
	public IDiagramDocument getDiagramDocument(Object element) {
		final IDocument doc = getDocument(element);
		if (doc instanceof IDiagramDocument) {
			return (IDiagramDocument) doc;
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		return null;
	}

	/**
	 * @generated
	 */
	protected class ResourceSetInfo extends ElementInfo {

		/**
		 * @generated
		 */
		private long myModificationStamp = IResource.NULL_STAMP;

		/**
		 * @generated
		 */
		private WorkspaceSynchronizer mySynchronizer;

		/**
		 * @generated
		 */
		private final LinkedList<Resource> myUnSynchronizedResources = new LinkedList<>();

		/**
		 * @generated
		 */
		private final IDiagramDocument myDocument;

		/**
		 * @generated
		 */
		private final IEditorInput myEditorInput;

		/**
		 * @generated
		 */
		private boolean myUpdateCache = true;

		/**
		 * @generated
		 */
		private boolean myModifiable = false;

		/**
		 * @generated
		 */
		private boolean myReadOnly = true;

		/**
		 * @generated
		 */
		private final ResourceSetModificationListener myResourceSetListener;

		/**
		 * @generated
		 */
		public ResourceSetInfo(IDiagramDocument document, IEditorInput editorInput) {
			super(document);
			myDocument = document;
			myEditorInput = editorInput;
			startResourceListening();
			myResourceSetListener = new ResourceSetModificationListener(this);
			getResourceSet().eAdapters().add(myResourceSetListener);
		}

		/**
		 * @generated
		 */
		public long getModificationStamp() {
			return myModificationStamp;
		}

		/**
		 * @generated
		 */
		public void setModificationStamp(long modificationStamp) {
			myModificationStamp = modificationStamp;
		}

		/**
		 * @generated
		 */
		public TransactionalEditingDomain getEditingDomain() {
			return myDocument.getEditingDomain();
		}

		/**
		 * @generated
		 */
		public ResourceSet getResourceSet() {
			return getEditingDomain().getResourceSet();
		}

		/**
		 * @generated
		 */
		public Iterator<Resource> getLoadedResourcesIterator() {
			return new ArrayList<>(getResourceSet().getResources()).iterator();
		}

		/**
		 * @generated
		 */
		public IEditorInput getEditorInput() {
			return myEditorInput;
		}

		/**
		 * @generated NOT
		 */
		public void dispose() {
			stopResourceListening();
			getResourceSet().eAdapters().remove(myResourceSetListener);
			// for (Iterator<Resource> it = getLoadedResourcesIterator(); it
			// .hasNext();) {
			// Resource resource = it.next();
			// resource.unload();
			// }
			// getEditingDomain().dispose();
		}

		/**
		 * @generated
		 */
		public boolean isSynchronized() {
			return myUnSynchronizedResources.size() == 0;
		}

		/**
		 * @generated
		 */
		public void setUnSynchronized(Resource resource) {
			myUnSynchronizedResources.add(resource);
		}

		/**
		 * @generated
		 */
		public void setSynchronized(Resource resource) {
			myUnSynchronizedResources.remove(resource);
		}

		/**
		 * @generated
		 */
		public final void stopResourceListening() {
			mySynchronizer.dispose();
			mySynchronizer = null;
		}

		/**
		 * @generated
		 */
		public final void startResourceListening() {
			mySynchronizer = new WorkspaceSynchronizer(getEditingDomain(), new SynchronizerDelegate());
		}

		/**
		 * @generated
		 */
		public boolean isUpdateCache() {
			return myUpdateCache;
		}

		/**
		 * @generated
		 */
		public void setUpdateCache(boolean update) {
			myUpdateCache = update;
		}

		/**
		 * @generated
		 */
		public boolean isModifiable() {
			return myModifiable;
		}

		/**
		 * @generated
		 */
		public void setModifiable(boolean modifiable) {
			myModifiable = modifiable;
		}

		/**
		 * @generated
		 */
		public boolean isReadOnly() {
			return myReadOnly;
		}

		/**
		 * @generated
		 */
		public void setReadOnly(boolean readOnly) {
			myReadOnly = readOnly;
		}

		/**
		 * @generated
		 */
		private class SynchronizerDelegate implements WorkspaceSynchronizer.Delegate {

			/**
			 * @generated
			 */
			@Override
			public void dispose() {
			}

			/**
			 * @generated
			 */
			@Override
			public boolean handleResourceChanged(final Resource resource) {
				synchronized (ResourceSetInfo.this) {
					if (ResourceSetInfo.this.fCanBeSaved) {
						setUnSynchronized(resource);
						return true;
					}
				}
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						handleElementChanged(ResourceSetInfo.this, resource, null);
					}
				});
				return true;
			}

			/**
			 * @generated
			 */
			@Override
			public boolean handleResourceDeleted(Resource resource) {
				synchronized (ResourceSetInfo.this) {
					if (ResourceSetInfo.this.fCanBeSaved) {
						setUnSynchronized(resource);
						return true;
					}
				}
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						fireElementDeleted(getEditorInput());
					}
				});
				return true;
			}

			/**
			 * @generated
			 */
			@Override
			public boolean handleResourceMoved(Resource resource, final URI newURI) {
				synchronized (ResourceSetInfo.this) {
					if (ResourceSetInfo.this.fCanBeSaved) {
						setUnSynchronized(resource);
						return true;
					}
				}
				if (myDocument.getDiagram().eResource() == resource) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							handleElementMoved(getEditorInput(), newURI);
						}
					});
				} else {
					handleResourceDeleted(resource);
				}
				return true;
			}

		}

	}

	/**
	 * @generated
	 */
	private class ResourceSetModificationListener extends EContentAdapter {

		/**
		 * @generated
		 */
		private final NotificationFilter myModifiedFilter;

		/**
		 * @generated
		 */
		private final ResourceSetInfo myInfo;

		/**
		 * @generated
		 */
		public ResourceSetModificationListener(ResourceSetInfo info) {
			myInfo = info;
			myModifiedFilter = NotificationFilter.createEventTypeFilter(Notification.SET)
					.or(NotificationFilter.createEventTypeFilter(Notification.UNSET))
					.and(NotificationFilter.createFeatureFilter(Resource.class, Resource.RESOURCE__IS_MODIFIED));
		}

		/**
		 * @generated
		 */
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getNotifier() instanceof ResourceSet) {
				super.notifyChanged(notification);
			}
			if (!notification.isTouch() && myModifiedFilter.matches(notification)) {
				if (notification.getNotifier() instanceof Resource) {
					final Resource resource = (Resource) notification.getNotifier();
					if (resource.isLoaded()) {
						boolean modified = false;
						for (final Iterator /* <org.eclipse.emf.ecore.resource.Resource> */it = myInfo.getLoadedResourcesIterator(); it
								.hasNext() && !modified;) {
							final Resource nextResource = (Resource) it.next();
							if (nextResource.isLoaded()) {
								modified = nextResource.isModified();
							}
						}
						boolean dirtyStateChanged = false;
						synchronized (myInfo) {
							if (modified != myInfo.fCanBeSaved) {
								myInfo.fCanBeSaved = modified;
								dirtyStateChanged = true;
							}
							if (!resource.isModified()) {
								myInfo.setSynchronized(resource);
							}
						}
						if (dirtyStateChanged) {
							fireElementDirtyStateChanged(myInfo.getEditorInput(), modified);

							if (!modified) {
								myInfo.setModificationStamp(computeModificationStamp(myInfo));
							}
						}
					}
				}
			}
		}

	}

}
