package safecap.diagram.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.part.FileEditorInput;

import safecap.diagram.edit.parts.ProjectEditPart;
import safecap.diagram.part.Messages;
import safecap.diagram.part.SafecapDiagramEditor;
import safecap.diagram.part.SafecapDiagramEditorPlugin;
import safecap.diagram.part.SafecapVisualIDRegistry;

/**
 * @generated
 */
public class SafecapNavigatorActionProvider extends CommonActionProvider {

	/**
	 * @generated
	 */
	private boolean myContribute;

	/**
	 * @generated
	 */
	private OpenDiagramAction myOpenDiagramAction;

	/**
	 * @generated
	 */
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);
		if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			myContribute = true;
			makeActions((ICommonViewerWorkbenchSite) aSite.getViewSite());
		} else {
			myContribute = false;
		}
	}

	/**
	 * @generated
	 */
	private void makeActions(ICommonViewerWorkbenchSite viewerSite) {
		myOpenDiagramAction = new OpenDiagramAction(viewerSite);
	}

	/**
	 * @generated
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (!myContribute) {
			return;
		}
		final IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		myOpenDiagramAction.selectionChanged(selection);
		if (myOpenDiagramAction.isEnabled()) {
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, myOpenDiagramAction);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
	}

	/**
	 * @generated
	 */
	private static class OpenDiagramAction extends Action {

		/**
		 * @generated
		 */
		private Diagram myDiagram;

		/**
		 * @generated
		 */
		private final ICommonViewerWorkbenchSite myViewerSite;

		/**
		 * @generated
		 */
		public OpenDiagramAction(ICommonViewerWorkbenchSite viewerSite) {
			super(Messages.NavigatorActionProvider_OpenDiagramActionName);
			myViewerSite = viewerSite;
		}

		/**
		 * @generated
		 */
		public final void selectionChanged(IStructuredSelection selection) {
			myDiagram = null;
			if (selection.size() == 1) {
				Object selectedElement = selection.getFirstElement();
				if (selectedElement instanceof SafecapNavigatorItem) {
					selectedElement = ((SafecapNavigatorItem) selectedElement).getView();
				} else if (selectedElement instanceof IAdaptable) {
					selectedElement = ((IAdaptable) selectedElement).getAdapter(View.class);
				}
				if (selectedElement instanceof Diagram) {
					final Diagram diagram = (Diagram) selectedElement;
					if (ProjectEditPart.MODEL_ID.equals(SafecapVisualIDRegistry.getModelID(diagram))) {
						myDiagram = diagram;
					}
				}
			}
			setEnabled(myDiagram != null);
		}

		/**
		 * @generated
		 */
		@Override
		public void run() {
			if (myDiagram == null || myDiagram.eResource() == null) {
				return;
			}

			final IEditorInput editorInput = getEditorInput(myDiagram);
			final IWorkbenchPage page = myViewerSite.getPage();
			try {
				page.openEditor(editorInput, SafecapDiagramEditor.ID);
			} catch (final PartInitException e) {
				SafecapDiagramEditorPlugin.getInstance().logError("Exception while openning diagram", e); //$NON-NLS-1$
			}
		}

		/**
		 * @generated
		 */
		private static IEditorInput getEditorInput(Diagram diagram) {
			final Resource diagramResource = diagram.eResource();
			for (final EObject nextEObject : diagramResource.getContents()) {
				if (nextEObject == diagram) {
					return new FileEditorInput(WorkspaceSynchronizer.getFile(diagramResource));
				}
				if (nextEObject instanceof Diagram) {
					break;
				}
			}
			final URI uri = EcoreUtil.getURI(diagram);
			final String editorName = uri.lastSegment() + '#' + diagram.eResource().getContents().indexOf(diagram);
			final IEditorInput editorInput = new URIEditorInput(uri, editorName);
			return editorInput;
		}

	}

}
