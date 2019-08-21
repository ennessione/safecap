package safecap.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;

import safecap.Project;
import safecap.diagram.edit.parts.NodeEditPart;
import safecap.diagram.navigator.SafecapNavigatorItem;
import safecap.diagram.providers.SafecapElementTypes;
import safecap.schema.Node;
import uk.ac.ncl.safecap.diagram.misc.diagramactions.CreateNewNode;
import uk.ac.ncl.safecap.diagram.misc.diagramactions.CreateNewSignal;
import uk.ac.ncl.safecap.diagram.misc.postprocess.AmbitLabelling;
import uk.ac.ncl.safecap.diagram.misc.postprocess.JunctionPoints;
import uk.ac.ncl.safecap.diagram.misc.postprocess.ModelCleanup;
import uk.ac.ncl.safecap.diagram.misc.postprocess.RefreshNodeKindAndRole;
import uk.ac.ncl.safecap.diagram.misc.postprocess.SegmentAmbitLink;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

/**
 * @generated
 */
public class SafecapDiagramEditor extends DiagramDocumentEditor implements IGotoMarker {

	/**
	 * @generated
	 */
	public static final String ID = "safecap.diagram.part.SafecapDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "safecap.diagram.ui.diagramContext"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public SafecapDiagramEditor() {
		super(true);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void setInput(IEditorInput input) {
		super.setInput(input);
		String name = input.getName();
		String version = null;
		int i = name.indexOf(".safecap");
		if (i > 0) {
			name = name.substring(0, i);
			i = name.indexOf("__");
			if (i > 0) {
				version = name.substring(i + 3);
				name = name.substring(0, i);
			}
		}

		if (version != null) {
			super.setPartName(name + " [" + version + "]");
		} else {
			super.setPartName(name);
		}

	}

	/**
	 * @generated NOT
	 */
	public Point getCursorPosition() {

		final Display display = Display.getDefault();
		Point point = getGraphicalViewer().getControl().toControl(display.getCursorLocation());
		final FigureCanvas figureCanvas = (FigureCanvas) getGraphicalViewer().getControl();
		final org.eclipse.draw2d.geometry.Point location = figureCanvas.getViewport().getViewLocation();
		point = new Point(point.x + location.x, point.y + location.y);
		return point;
	}

	public org.eclipse.draw2d.geometry.Point getCursorPosition2() {
		final Display display = Display.getDefault();
		final Point point = getGraphicalViewer().getControl().toControl(display.getCursorLocation());
		final FigureCanvas figureCanvas = (FigureCanvas) getGraphicalViewer().getControl();
		final org.eclipse.draw2d.geometry.Point location = figureCanvas.getViewport().getViewLocation();
		return new org.eclipse.draw2d.geometry.Point(point.x + location.x, point.y + location.y);
	}

	private SelectionSynchronizer synchronizer;

	@Override
	protected SelectionSynchronizer getSelectionSynchronizer() {
		if (synchronizer == null) {
			synchronizer = new CustomSelectionSynchronizer();
		}
		return synchronizer;
	}

	class CustomSelectionSynchronizer extends SelectionSynchronizer {

		@Override
		public void addViewer(EditPartViewer viewer) {
			super.addViewer(viewer);
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			final IStructuredSelection sel = event.getStructuredSelection();
			if (sel.size() == 1) {
				final Object element = sel.getFirstElement();
				if (element instanceof NodeEditPart) {
					final NodeEditPart nep = (NodeEditPart) element;
					final Object semantic = nep.resolveSemanticElement();

					if (semantic instanceof Node) {
						getGraphicalViewer().setProperty("editor.node", semantic);
					}
				}
			}
			super.selectionChanged(event);

		}
	}

	private Node getCurrentNode() {
		return (Node) SafecapDiagramEditor.this.getGraphicalViewer().getProperty("editor.node");
	}

	private void clearCurrentNode() {
		SafecapDiagramEditor.this.getGraphicalViewer().setProperty("editor.node", null);
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected KeyHandler getKeyHandler() {
		final KeyHandler keyhandler = super.getKeyHandler();
		keyhandler.put(KeyStroke.getPressed((char) 16, 'p', SWT.CTRL), new Action() {
			@Override
			public void run() {
				final List<IElementType> relationshipTypes = Collections.singletonList(SafecapElementTypes.Segment_4001);
				final Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
				tool.setEditDomain(SafecapDiagramEditor.this.getEditDomain());
				tool.setViewer(SafecapDiagramEditor.this.getGraphicalViewer());
				SafecapDiagramEditor.this.getGraphicalViewer().getEditDomain().setActiveTool(tool);
			}
		});

		/*
		 * keyhandler.put(KeyStroke.getReleased('s', 115, 0), new Action() {
		 * 
		 * @Override public void run() { List<IElementType> relationshipTypes =
		 * Collections.singletonList(SafecapElementTypes.Segment_4001); AbstractTool
		 * tool = new NoSelectionUnspecifiedTypeConnectionTool(relationshipTypes);
		 * tool.setEditDomain(SafecapDiagramEditor.this.getEditDomain());
		 * tool.setViewer(SafecapDiagramEditor.this.getGraphicalViewer());
		 * tool.setUnloadWhenFinished(false);
		 * SafecapDiagramEditor.this.getGraphicalViewer().getEditDomain().setActiveTool(
		 * tool); } });
		 */

		keyhandler.put(KeyStroke.getReleased('w', 119, 0), new Action() {
			@Override
			public void run() {
				final List<IElementType> relationshipTypes = Collections.singletonList(SafecapElementTypes.Wire_4002);
				final AbstractTool tool = new NoSelectionUnspecifiedTypeConnectionTool(relationshipTypes);
				tool.setEditDomain(SafecapDiagramEditor.this.getEditDomain());
				tool.setViewer(SafecapDiagramEditor.this.getGraphicalViewer());
				tool.setUnloadWhenFinished(false);
				SafecapDiagramEditor.this.getGraphicalViewer().getEditDomain().setActiveTool(tool);
			}
		});

		keyhandler.put(KeyStroke.getReleased('n', 110, 0), new Action() {
			@Override
			public void run() {
//				List<IElementType> relationshipTypes = Collections.singletonList(SafecapElementTypes.Node_2003);
//				AbstractTool tool = new NoSelectionUnspecifiedTypeCreationTool(relationshipTypes);
//
//				tool.setEditDomain(SafecapDiagramEditor.this.getEditDomain());
//				tool.setViewer(SafecapDiagramEditor.this.getGraphicalViewer());
//				tool.setUnloadWhenFinished(false);
//
//				SafecapDiagramEditor.this.getGraphicalViewer().getEditDomain().setActiveTool(tool);
//				

				final DiagramEditPart part = SafecapDiagramEditor.this.getDiagramEditPart();
				final CreateNewNode creator = new CreateNewNode(SafecapDiagramEditor.this.getDiagramEditPart(),
						(Project) SafecapDiagramEditor.this.getDiagram().getElement(), getCursorPosition2(), getCurrentNode());
				creator.run();
				SafecapDiagramEditor.this.getGraphicalViewer().setProperty("editor.node", creator.getNewNode());
			}
		});

		keyhandler.put(KeyStroke.getReleased('l', 108, 0), new Action() {
			@Override
			public void run() {
				final CreateNewSignal creator = new CreateNewSignal(SafecapDiagramEditor.this.getDiagramEditPart(),
						(Project) SafecapDiagramEditor.this.getDiagram().getElement(), getCursorPosition2(), false);
				creator.run();
				clearCurrentNode();
			}
		});

		keyhandler.put(KeyStroke.getReleased('r', 114, 0), new Action() {
			@Override
			public void run() {
				final CreateNewSignal creator = new CreateNewSignal(SafecapDiagramEditor.this.getDiagramEditPart(),
						(Project) SafecapDiagramEditor.this.getDiagram().getElement(), getCursorPosition2(), true);
				creator.run();
				clearCurrentNode();
			}
		});

		keyhandler.put(KeyStroke.getReleased('v', 118, 0), new Action() {
			@Override
			public void run() {
				final IProgressMonitor monitor = new NullProgressMonitor();
				final safecap.Project root = EmfUtil.getProject(SafecapDiagramEditor.this.getDiagram().getElement());
				RefreshNodeKindAndRole.refresh(root, monitor);
				SegmentAmbitLink.refresh(root, monitor);
				AmbitLabelling.refresh(root, monitor);
				JunctionPoints.refresh(root, monitor);
				ModelCleanup.refresh(root, monitor);
				ValidateAction.runValidation(SafecapDiagramEditor.this.getDiagram());
			}
		});

		/*
		 * keyhandler.put(KeyStroke.getReleased('x', 120, 0), new Action() {
		 * 
		 * @Override public void run() { IProgressMonitor monitor = new
		 * NullProgressMonitor(); final safecap.Project root =
		 * EmfUtil.getProject(SafecapDiagramEditor.this.getDiagram().getElement());
		 * ProjectEditPart project = (ProjectEditPart)
		 * SafecapDiagramEditor.this.getDiagram(); RefreshNodeKindAndRole.refresh(root,
		 * monitor); if (project != null && PointCrossoverDetection.getToggleState())
		 * PointRoleDetection.refresh(project, root, monitor);
		 * ValidateAction.runValidation(SafecapDiagramEditor.this.getDiagram()); } });
		 */
		return keyhandler;
	}

	/**
	 * @generated
	 */
	@Override
	protected String getContextID() {
		return CONTEXT_ID;
	}

	public void arrangeAll() {
		final ArrangeRequest arrangeRequest = new ArrangeRequest(ActionIds.ACTION_ARRANGE_ALL);

		final IGraphicalEditPart rootEP = (IGraphicalEditPart) getDiagramGraphicalViewer().getRootEditPart().getContents();

		final List l = new ArrayList();

		l.add(rootEP);

		arrangeRequest.setPartsToArrange(l);

		final Command cmd = rootEP.getCommand(arrangeRequest);
		getCommandStack().execute(cmd);
	}

	@Override
	protected void initializeGraphicalViewerContents() {
		super.initializeGraphicalViewerContents();
		//
		// IFile file = ((IFileEditorInput)getEditorInput()).getFile();
		// Project root = EmfUtil.fromFile(file);
		// View view = getDiagramEditPart().getDiagramView();
		//
		// EObject modelElement = view.getElement();
		// List editPolicies =
		// CanonicalEditPolicy.getRegisteredEditPolicies(modelElement);
		// for (Iterator it = editPolicies.iterator(); it.hasNext();) {
		// CanonicalEditPolicy nextEditPolicy = (CanonicalEditPolicy) it
		// .next();
		// nextEditPolicy.refresh();
		// }
		//
		// PositionGeneratedEquipment.refresh(getDiagramEditPart(), root,
		// "core.ROUTE_BUILDER");
		// for (Iterator it = editPolicies.iterator(); it.hasNext();) {
		// CanonicalEditPolicy nextEditPolicy = (CanonicalEditPolicy) it
		// .next();
		// nextEditPolicy.refresh();
		// }
	}

	/**
	 * @generated
	 */
	@Override
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		final PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new SafecapPaletteFactory().fillPalette(root);
		return root;
	}

	/**
	 * @generated
	 */
	@Override
	protected PreferencesHint getPreferencesHint() {
		return SafecapDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	@Override
	public String getContributorId() {
		return SafecapDiagramEditorPlugin.ID;
	}

	/**
	 * @generated
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				@Override
				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated
	 */
	@Override
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {
			return SafecapDiagramEditorPlugin.getInstance().getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		final IDocument document = getEditorInput() != null ? getDocumentProvider().getDocument(getEditorInput()) : null;
		if (document instanceof IDiagramDocument) {
			return ((IDiagramDocument) document).getEditingDomain();
		}
		return super.getEditingDomain();
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {
			final IDocumentProvider provider = SafecapDiagramEditorPlugin.getInstance().getDocumentProvider();
			setDocumentProvider(provider);
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * @generated
	 */
	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		final Shell shell = getSite().getShell();
		final IEditorInput input = getEditorInput();
		final SaveAsDialog dialog = new SaveAsDialog(shell);
		final IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input).getFile() : null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		final IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			final String message = NLS.bind(Messages.SafecapDiagramEditor_SavingDeletedFile, original.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		final IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		final IEditorMatchingStrategy matchingStrategy = getEditorDescriptor().getEditorMatchingStrategy();
		final IEditorReference[] editorRefs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog.openWarning(shell, Messages.SafecapDiagramEditor_SaveAsErrorTitle,
						Messages.SafecapDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor, newInput, getDocumentProvider().getDocument(getEditorInput()),
					true);
			success = true;
		} catch (final CoreException x) {
			final IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog.openError(shell, Messages.SafecapDiagramEditor_SaveErrorTitle, Messages.SafecapDiagramEditor_SaveErrorMessage,
						x.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		final IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		final Diagram diagram = document.getDiagram();
		if (diagram == null || diagram.eResource() == null) {
			return StructuredSelection.EMPTY;
		}
		final IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			final SafecapNavigatorItem item = new SafecapNavigatorItem(diagram, file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		final DiagramEditorContextMenuProvider provider = new DiagramEditorContextMenuProvider(this, getDiagramGraphicalViewer());
		getDiagramGraphicalViewer().setContextMenu(provider);
		getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU, provider, getDiagramGraphicalViewer());
	}

}
