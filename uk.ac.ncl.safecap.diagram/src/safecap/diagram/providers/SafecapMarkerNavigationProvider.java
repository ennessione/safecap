package safecap.diagram.providers;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.emf.ui.providers.marker.AbstractModelMarkerNavigationProvider;

import safecap.diagram.part.SafecapDiagramEditorPlugin;
import safecap.diagram.part.SafecapDiagramEditorUtil;

/**
 * @generated
 */
public class SafecapMarkerNavigationProvider extends AbstractModelMarkerNavigationProvider {

	/**
	 * @generated
	 */
	public static final String MARKER_TYPE = SafecapDiagramEditorPlugin.ID + ".diagnostic"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	@Override
	protected void doGotoMarker(IMarker marker) {
		final String elementId = marker.getAttribute(org.eclipse.gmf.runtime.common.core.resources.IMarker.ELEMENT_ID, null);
		if (elementId == null || !(getEditor() instanceof DiagramEditor)) {
			return;
		}
		final DiagramEditor editor = (DiagramEditor) getEditor();
		final Map editPartRegistry = editor.getDiagramGraphicalViewer().getEditPartRegistry();
		final EObject targetView = editor.getDiagram().eResource().getEObject(elementId);
		if (targetView == null) {
			return;
		}
		final EditPart targetEditPart = (EditPart) editPartRegistry.get(targetView);
		if (targetEditPart != null) {
			SafecapDiagramEditorUtil.selectElementsInDiagram(editor, Arrays.asList(new EditPart[] { targetEditPart }));
		}
	}

	/**
	 * @generated
	 */
	public static void deleteMarkers(IResource resource) {
		try {
			resource.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ZERO);
		} catch (final CoreException e) {
			SafecapDiagramEditorPlugin.getInstance().logError("Failed to delete validation markers", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public static IMarker addMarker(IFile file, String elementId, String location, String message, int statusSeverity) {
		IMarker marker = null;
		try {
			marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.LOCATION, location);
			marker.setAttribute(org.eclipse.gmf.runtime.common.core.resources.IMarker.ELEMENT_ID, elementId);
			int markerSeverity = IMarker.SEVERITY_INFO;
			if (statusSeverity == IStatus.WARNING) {
				markerSeverity = IMarker.SEVERITY_WARNING;
			} else if (statusSeverity == IStatus.ERROR || statusSeverity == IStatus.CANCEL) {
				markerSeverity = IMarker.SEVERITY_ERROR;
			}
			marker.setAttribute(IMarker.SEVERITY, markerSeverity);
		} catch (final CoreException e) {
			SafecapDiagramEditorPlugin.getInstance().logError("Failed to create validation marker", e); //$NON-NLS-1$
		}
		return marker;
	}
}
