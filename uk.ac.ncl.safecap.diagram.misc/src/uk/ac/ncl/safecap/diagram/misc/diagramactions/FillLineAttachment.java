package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import safecap.Project;
import safecap.model.Line;
import safecap.trackside.SpeedLimit;
import safecap.trackside.StopAndGo;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.PlatformUtil;
import uk.ac.ncl.safecap.misc.core.SpeedLimitUtil;

public class FillLineAttachment {

	private final List<EObject> objects;
	private final Project root;

	public FillLineAttachment(List<EObject> objects) {
		this.objects = objects;
		root = EmfUtil.getProject(objects.get(0));
	}

	public void run() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);
		final CompoundCommand compc = new CompoundCommand("FillLineAttachments");

		for (final EObject obj : objects) {
			if (obj instanceof SpeedLimit) {
				final SpeedLimit speedLimit = (SpeedLimit) obj;
				final List<Line> validLines = SpeedLimitUtil.getAllValidLines(speedLimit);
				if (!speedLimit.getLine().isEmpty()) {
					final RemoveCommand remCmd = new RemoveCommand(domain, speedLimit.getLine(), new ArrayList<>(speedLimit.getLine()));
					compc.append(remCmd);
				}
				if (!validLines.isEmpty()) {
					final AddCommand cmd = new AddCommand(domain, speedLimit.getLine(), validLines);
					compc.append(cmd);
				}
			} else if (obj instanceof StopAndGo) {
				final StopAndGo platform = (StopAndGo) obj;
				final List<Line> validLines = PlatformUtil.getAllValidLines(platform);
				if (!platform.getLine().isEmpty()) {
					final RemoveCommand remCmd = new RemoveCommand(domain, platform.getLine(), new ArrayList<>(platform.getLine()));
					compc.append(remCmd);
				}
				if (!validLines.isEmpty()) {
					final AddCommand cmd = new AddCommand(domain, platform.getLine(), validLines);
					compc.append(cmd);
				}
			}
		}

		try {
			domain.getCommandStack().execute(compc);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
