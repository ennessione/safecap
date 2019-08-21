package uk.ac.ncl.safecap.diagram.misc.copypaste;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class MyEditorClipboardSupportGlobalActionHandler extends DiagramGlobalActionHandler {

	@Override
	public ICommand getCommand(IGlobalActionContext cntxt) {
		final IWorkbenchPart part = cntxt.getActivePart();
		if (!(part instanceof IDiagramWorkbenchPart)) {
			return null;
		}
		final IDiagramWorkbenchPart diagramPart = (IDiagramWorkbenchPart) part;
		org.eclipse.gmf.runtime.common.core.command.ICommand command = null;
		final String actionId = cntxt.getActionId();
		if (actionId.equals(GlobalActionId.DELETE)) {
			super.getCommand(cntxt);
		} else if (actionId.equals(GlobalActionId.COPY)) {
			command = getCopyCommand(cntxt, diagramPart, false);
		} else if (actionId.equals(GlobalActionId.CUT)) {
			// command = getCutCommand(cntxt, diagramPart);
			return null;
		} else if (actionId.equals(GlobalActionId.OPEN)) {
			super.getCommand(cntxt);
		} else if (actionId.equals(GlobalActionId.PASTE)) {
			command = getPasteCommand(cntxt, diagramPart);
		} else if (actionId.equals(GlobalActionId.SAVE)) {
			super.getCommand(cntxt);
		} else if (actionId.equals(GlobalActionId.PROPERTIES)) {
			super.getCommand(cntxt);
		}
		return command;
	}

	@Override
	protected boolean canPaste(IGlobalActionContext cntxt) {

		return MyEditorCopyCommand.getCopied().size() > 0;

		/* Check if the clipboard has data for the drawing surface */
//		return ClipboardManager.getInstance().doesClipboardHaveData(
//			ClipboardCommand.DRAWING_SURFACE,
//			ClipboardContentsHelper.getInstance())
//			|| (ClipboardManager.getInstance().doesClipboardHaveData(
//				ClipboardManager.COMMON_FORMAT, ClipboardContentsHelper
//					.getInstance()));
	}

	@Override
	protected ICommand getCopyCommand(IGlobalActionContext cntxt, IDiagramWorkbenchPart diagramPart, final boolean isUndoable) {
		final List toCopyElements = getSelectedElements(cntxt.getSelection());
		final List toCopyEditParts = getSelectedEditParts(cntxt.getSelection());
		final MyEditorCopyCommand copyCmd = new MyEditorCopyCommand("Copy", toCopyElements, toCopyEditParts);
		return copyCmd;
	}

	private ICommand getPasteCommand(IGlobalActionContext cntxt, IDiagramWorkbenchPart diagramPart) {
		return new MyEditorPasteCommand("Paste", (IGraphicalEditPart) ((StructuredSelection) cntxt.getSelection()).getFirstElement());
	}

	// These are 2 utilitary methods:
	protected List getSelectedElements(ISelection selection) {
		final List results = new LinkedList();
		if (selection == null || selection.isEmpty()) {
			return results;
		}
		final Iterator iterator = ((IStructuredSelection) selection).iterator();
		while (iterator.hasNext()) {
			final Object selectedElement = iterator.next();
			final EObject element = ((EditPart) selectedElement).getAdapter(EObject.class);
			results.add(element);
		}
		return results;
	}

	private List getSelectedEditParts(ISelection selection)

	{
		final List results = new LinkedList();
		final Iterator iterator = ((IStructuredSelection) selection).iterator();
		while (iterator.hasNext()) {
			final Object selectedElement = iterator.next();
			results.add(selectedElement);
		}
		return results;
	}

}
