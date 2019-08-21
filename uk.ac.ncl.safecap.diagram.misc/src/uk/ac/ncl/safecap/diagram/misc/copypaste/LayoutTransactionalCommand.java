package uk.ac.ncl.safecap.diagram.misc.copypaste;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Tool;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.DiagramUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;

public class LayoutTransactionalCommand extends AbstractTransactionalCommand {

	private final IGraphicalEditPart targetPart;
	private final List<ObjectCopy> elemToPaste;
	private final MyEditorPasteCommand pasteCmd;

	public LayoutTransactionalCommand(TransactionalEditingDomain domain, List<ObjectCopy> elemToPaste, IGraphicalEditPart targetPart,
			MyEditorPasteCommand pasteCmd) {

		super(domain, PasteTransactionalCommand.class.getName(), getWorkspaceFiles(elemToPaste));
		this.targetPart = targetPart;
		this.elemToPaste = elemToPaste;
		this.pasteCmd = pasteCmd;
	}

	@Override
	public void addContext(IUndoContext context) {
		super.addContext(context);
		pasteCmd.addContext(context);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		int x_shift = 200;
		int y_shift = 200;

		final Point point = mouseLocation();
		if (point != null && elemToPaste.size() > 0) {
			final Point topl = getLeftTopCorner();
			x_shift = point.x - topl.x;
			y_shift = point.y - topl.y;
		}

		final List<EditPart> new_parts = new ArrayList<>(elemToPaste.size());

		for (final ObjectCopy obj : elemToPaste) {
			if (obj.getSourcePart() instanceof IGraphicalEditPart) {
				final IGraphicalEditPart src_part = (IGraphicalEditPart) obj.getSourcePart();
				if (src_part.getModel() instanceof Node) {
					final Node src_node = (Node) src_part.getModel();
					final IGraphicalEditPart eoEditPart = (IGraphicalEditPart) targetPart.findEditPart(targetPart, obj.getCopy());

					if (eoEditPart != null && eoEditPart.getModel() instanceof Node) {
						final Bounds newb = (Bounds) ((Node) eoEditPart.getModel()).getLayoutConstraint();
						final Bounds oldb = (Bounds) src_node.getLayoutConstraint();
						newb.setX(oldb.getX() + x_shift);
						newb.setY(oldb.getY() + y_shift);
						new_parts.add(eoEditPart);
					}
				}
			}
		}

		// select pasted elements
		if (targetPart.getModel() instanceof Diagram) {
			final Diagram diagram = (Diagram) targetPart.getModel();
			final IDiagramWorkbenchPart part = DiagramUtil.getOpenedDiagramEditor(diagram, null);
			part.getDiagramGraphicalViewer().deselectAll();

			for (final EditPart ep : new_parts) {
				part.getDiagramGraphicalViewer().appendSelection(ep);
			}
		}

		return CommandResult.newOKCommandResult();
	}

	private Point getLeftTopCorner() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;

		for (final ObjectCopy obj : elemToPaste) {
			if (obj.getSourcePart() instanceof IGraphicalEditPart) {
				final IGraphicalEditPart src_part = (IGraphicalEditPart) obj.getSourcePart();
				if (src_part.getModel() instanceof Node) {
					final Node n = (Node) src_part.getModel();
					final Bounds b = (Bounds) n.getLayoutConstraint();
					if (b.getX() < x) {
						x = b.getX();
					}
					if (b.getY() < y) {
						y = b.getY();
					}
				}
			}
		}

		return new Point(x, y);
	}

	private Point mouseLocation() {
		try {
			final Tool tool = targetPart.getViewer().getEditDomain().getActiveTool();
			final AbstractTool aTool = (AbstractTool) tool;
			final Method m = AbstractTool.class.getDeclaredMethod("getLocation");
			m.setAccessible(true);
			final Point toolLocation = ((org.eclipse.draw2d.geometry.Point) m.invoke(aTool)).getCopy();
			return toolLocation;
		} catch (final Throwable e) {
			return null;
		}
	}
}
