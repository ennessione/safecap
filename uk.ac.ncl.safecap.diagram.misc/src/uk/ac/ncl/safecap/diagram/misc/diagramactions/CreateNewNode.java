package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;

import safecap.Project;
import safecap.schema.Segment;

public class CreateNewNode {
	private final IGraphicalEditPart projectPart;
	private final Project root;
	private final Point location;
	private final safecap.schema.Node previous;
	private safecap.schema.Node newNode;

	public CreateNewNode(IGraphicalEditPart projectPart, Project prj, Point location, safecap.schema.Node previous) {
		this.projectPart = projectPart;
		root = prj;
		this.location = location;
		this.previous = previous;
	}

	public safecap.schema.Node getNewNode() {
		return newNode;
	}

	public void run() {
		final CompositeCommand compc = new CompositeCommand("CreateNewNode");
		compc.add(getCreateCommand());
		compc.add(getConfigureCommand());
		try {
			OperationHistoryFactory.getOperationHistory().execute(compc, null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getConfigureCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "position_node", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				try {
					final IGraphicalEditPart newNode_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, newNode);
					final Bounds newc = (Bounds) ((Node) newNode_part.getModel()).getLayoutConstraint();
					final Point pos = MyGridUtils.putOnMajor(projectPart, location);
					newc.setX(pos.x);
					newc.setY(pos.y);
					// newc.setX(location.x - TrackConstants.NODE_WIDTH/2);
					// newc.setY(location.y - TrackConstants.NODE_HEIGHT/2);

					// Node x =
					// org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createNode();

				} catch (final Throwable e) {
					e.printStackTrace();
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private AbstractTransactionalCommand getCreateCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "create_node", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				newNode = safecap.schema.SchemaFactory.eINSTANCE.createNode();
				root.getNodes().add(newNode);

				if (previous != null) {
					final Segment segment = safecap.schema.SchemaFactory.eINSTANCE.createSegment();
					segment.setFrom(previous);
					segment.setTo(newNode);
					root.getSegments().add(segment);
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}
}
