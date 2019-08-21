package safecap.diagram.part;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

/**
 * @generated
 */
public class SafecapDiagramUpdateCommand implements IHandler {

	/**
	 * @generated
	 */
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

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
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() != 1) {
				return null;
			}
			if (structuredSelection.getFirstElement() instanceof EditPart
					&& ((EditPart) structuredSelection.getFirstElement()).getModel() instanceof View) {
				final EObject modelElement = ((View) ((EditPart) structuredSelection.getFirstElement()).getModel()).getElement();
				final List editPolicies = CanonicalEditPolicy.getRegisteredEditPolicies(modelElement);
				for (final Iterator it = editPolicies.iterator(); it.hasNext();) {
					final CanonicalEditPolicy nextEditPolicy = (CanonicalEditPolicy) it.next();
					nextEditPolicy.refresh();
				}

			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isHandled() {
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
