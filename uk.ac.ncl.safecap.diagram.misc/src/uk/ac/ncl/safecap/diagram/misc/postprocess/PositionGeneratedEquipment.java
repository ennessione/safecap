package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;

import safecap.Project;
import safecap.schema.Node;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.LeftSide;
import safecap.trackside.LeftSignal;
import safecap.trackside.LeftSpeedLimit;
import safecap.trackside.RightSignal;
import safecap.trackside.RightSpeedLimit;
import safecap.trackside.SpeedLimit;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.misc.core.EquipmentUtil;

public class PositionGeneratedEquipment {

	public PositionGeneratedEquipment() {
	}

	public static void refresh(IGraphicalEditPart projectPart, Project root, String origin) {
		final PositionGeneratedEquipment cmd = new PositionGeneratedEquipment();
		cmd.run(projectPart, root, origin);
	}

	public void run(IGraphicalEditPart projectPart, Project root, String origin) {
		final CompositeCommand compc = new CompositeCommand("PositionGeneratedEquipment");
		compc.add(getPositionGeneratedEquipmentCommand(projectPart, root, origin));
		try {
			OperationHistoryFactory.getOperationHistory().execute(compc, null, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static AbstractTransactionalCommand getPositionGeneratedEquipmentCommand(final IGraphicalEditPart projectPart,
			final Project root, final String origin) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "PositionGeneratedEquipment", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				final List<Equipment> equip = EquipmentUtil.getEquipmentByOrigin(root, origin);
				for (final Equipment e : equip) {
					if (e instanceof LeftSide) {
						position(e, true);
					} else {
						position(e, false);
					}
				}

				return CommandResult.newOKCommandResult();
			}

			private double estimateRelative(Segment s, int x) {
				final Node n1 = s.getFrom();
				IGraphicalEditPart n_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, n1);
				Bounds n_bounds = (Bounds) ((org.eclipse.gmf.runtime.notation.Node) n_part.getModel()).getLayoutConstraint();
				final int x1 = n_bounds.getX();

				final Node n2 = s.getTo();
				n_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, n2);
				n_bounds = (Bounds) ((org.eclipse.gmf.runtime.notation.Node) n_part.getModel()).getLayoutConstraint();
				final int x2 = n_bounds.getX();

				int start = x1;
				int end = x2;
				if (x1 > x2) {
					start = x2;
					end = x1;
				}

				final int t = x - x1;
				final int l = end - start;

				if (t <= 0) {
					return 0d;
				}

				if (t >= l) {
					return 1.0d;
				}

				return (double) t / (double) l;
			}

			private void position(Equipment e, boolean above) {
				final Wire w = EquipmentUtil.getWire(e);
				if (w == null) {
					return;
				}

				final Segment s = w.getTo();
				final Node n = above ? s.getTo() : s.getFrom();
				final IGraphicalEditPart n_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, n);
				if (n_part == null) {
					return;
				}

				final Bounds n_bounds = (Bounds) ((org.eclipse.gmf.runtime.notation.Node) n_part.getModel()).getLayoutConstraint();
				int x = n_bounds.getX();
				int y = n_bounds.getY();

				// wild guess
				x += above ? -30 : 15;
				y += above ? -50 : 50;

				if (e instanceof SpeedLimit) {
					x += above ? -40 : 40;
				}

				final IGraphicalEditPart e_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, e);
				final Bounds e_bounds = (Bounds) ((org.eclipse.gmf.runtime.notation.Node) e_part.getModel()).getLayoutConstraint();
				e_bounds.setX(x);
				e_bounds.setY(y);

				final List<?> src = e_part.getSourceConnections();
				if (src != null && src.size() > 0 && src.get(0) instanceof ConnectionEditPart) {
					final ConnectionEditPart conn = (ConnectionEditPart) src.get(0);
					final Edge te = (Edge) conn.getModel();
					if (te.getTargetAnchor() instanceof IdentityAnchor) {

						if (e instanceof LeftSpeedLimit) {
							x += 10;
						} else if (e instanceof LeftSignal) {
							x += 5;
						} else if (e instanceof RightSignal) {
							x += 15;
						} else if (e instanceof RightSpeedLimit) {
							x += 15;
						}

						final IdentityAnchor ia = (IdentityAnchor) te.getTargetAnchor();

//							Point t = new Point(x, y);
//
//							//conn.getConnectionFigure().translateToRelative(t);
//							PrecisionPoint pt = BaseSlidableAnchor.getAnchorRelativeLocation(t, conn.getConnectionFigure().getBounds());
//							
						final double pos = estimateRelative(s, x);

						ia.setId("(" + pos + "," + 0.5 + ")");

//							IdentityAnchor ia = (IdentityAnchor) te.getTargetAnchor();
//							if (above)
//								ia.setId("[" + x  + "," + y + "]");
//							else
//								ia.setId("[" + x  + "," + y + "]");
					}
				}
			}
		};

		return command;
	}

}
