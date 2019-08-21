package uk.ac.ncl.safecap.diagram.misc.copypaste;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;

public class MyEditorCopyCommand extends AbstractCommand {
	private final List<EObject> elements;
	private final List<EditPart> editparts;

	private static List<ObjectCopy> copied = new ArrayList<>(1000);

	public MyEditorCopyCommand(String label, List toCopyElements, List toCopyEditParts) {
		super(label);
		elements = toCopyElements;
		editparts = toCopyEditParts;
	}

	public static List<ObjectCopy> getCopied() {
		return copied;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

		copied.clear();

		for (int i = 0; i < elements.size(); i++) {
			final ObjectCopy copy = new ObjectCopy(elements.get(i), editparts.get(i));
			copied.add(copy);
		}

		return CommandResult.newOKCommandResult();
	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

}
