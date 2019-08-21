package safecap.diagram.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.part.FileEditorInput;

import safecap.diagram.part.SafecapDiagramEditorPlugin;

/**
 * @generated
 */
public class SafecapNavigatorLinkHelper implements ILinkHelper {

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

	/**
	 * @generated
	 */
	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		final IDiagramDocument document = SafecapDiagramEditorPlugin.getInstance().getDocumentProvider().getDiagramDocument(anInput);
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
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}
		if (false == aSelection.getFirstElement() instanceof SafecapAbstractNavigatorItem) {
			return;
		}

		final SafecapAbstractNavigatorItem abstractNavigatorItem = (SafecapAbstractNavigatorItem) aSelection.getFirstElement();
		View navigatorView = null;
		if (abstractNavigatorItem instanceof SafecapNavigatorItem) {
			navigatorView = ((SafecapNavigatorItem) abstractNavigatorItem).getView();
		} else if (abstractNavigatorItem instanceof SafecapNavigatorGroup) {
			final SafecapNavigatorGroup navigatorGroup = (SafecapNavigatorGroup) abstractNavigatorItem;
			if (navigatorGroup.getParent() instanceof SafecapNavigatorItem) {
				navigatorView = ((SafecapNavigatorItem) navigatorGroup.getParent()).getView();
			}
		}
		if (navigatorView == null) {
			return;
		}
		final IEditorInput editorInput = getEditorInput(navigatorView.getDiagram());
		final IEditorPart editor = aPage.findEditor(editorInput);
		if (editor == null) {
			return;
		}
		aPage.bringToTop(editor);
		if (editor instanceof DiagramEditor) {
			final DiagramEditor diagramEditor = (DiagramEditor) editor;
			final ResourceSet diagramEditorResourceSet = diagramEditor.getEditingDomain().getResourceSet();
			final EObject selectedView = diagramEditorResourceSet.getEObject(EcoreUtil.getURI(navigatorView), true);
			if (selectedView == null) {
				return;
			}
			final GraphicalViewer graphicalViewer = (GraphicalViewer) diagramEditor.getAdapter(GraphicalViewer.class);
			final EditPart selectedEditPart = (EditPart) graphicalViewer.getEditPartRegistry().get(selectedView);
			if (selectedEditPart != null) {
				graphicalViewer.select(selectedEditPart);
			}
		}
	}

}
