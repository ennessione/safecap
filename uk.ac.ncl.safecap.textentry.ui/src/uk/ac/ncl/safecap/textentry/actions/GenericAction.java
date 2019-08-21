package uk.ac.ncl.safecap.textentry.actions;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import uk.ac.ncl.safecap.textentry.core.TEBuilder;
import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.core.TEPlugin;
import uk.ac.ncl.safecap.textentry.core.TERegistry;

public class GenericAction extends AbstractHandler implements IHandler {
	public static final String ACTION_PARAM = "uk.ac.ncl.safecap.textentry.ui.command.generic.action";
	public static final String COMMAND_ID = "uk.ac.ncl.safecap.textentry.ui.command.generic";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final String label = event.getParameter(ACTION_PARAM);
		if (label == null) {
			return null;
		}

		final String[] parts = label.split("///");
		if (parts.length != 2) {
			return null;
		}

		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final Object element = selection.getFirstElement();

		final TERegistry registry = TEPlugin.getTERegistry();
		final List<ITEAction> list = registry.getActionInfo(parts[0]);
		if (list == null) {
			return null;
		}

		for (final ITEAction action : list) {
			if (action.getLabel().equals(parts[1])) {
				return execute(element, action);
			}
		}

		return null;
	}

	private Object execute(Object element, ITEAction action) {
		try {
			if (element instanceof IFile) {
				final IFile file = (IFile) element;
				final TEPart part = TEBuilder.build(file.getContents());
				if (part != null) {
					action.execute(part);
				}
			}
		} catch (final Throwable e) {
			MessageDialog.openError(null, "Action error", "Action failed: " + e.getMessage());
		}

		return null;
	}

}
