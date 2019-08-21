package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Section;
import safecap.schema.Segment;
import safecap.trackside.LeftSignal;
import safecap.trackside.Signal;
import safecap.trackside.Wire;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;

public class CreateNewSignalOnSegment {
	private final IGraphicalEditPart projectPart;
	private final Project root;
	private final List<Signal> addedSignals;
	private final List<Segment> signalLocations;
	private final Map<Segment, IGraphicalEditPart> map;

	public CreateNewSignalOnSegment(IGraphicalEditPart projectPart, Project prj) {
		this.projectPart = projectPart;
		root = prj;
		map = buildSegmentMap();
		addedSignals = new ArrayList<>();
		signalLocations = new ArrayList<>();
	}

	public void run() {
		final CompositeCommand compc = new CompositeCommand("CreateNewSignalOnSegment");
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

		return new AbstractTransactionalCommand(domain, "position_signal", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				try {
					for (int i = 0; i < addedSignals.size(); i++) {
						final Signal signal = addedSignals.get(i);
						final Segment segment = signalLocations.get(i);
						final IGraphicalEditPart newNode_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, signal);
						final Bounds newc = (Bounds) ((Node) newNode_part.getModel()).getLayoutConstraint();

						final Point mid_point = mid_point(segment);
						newc.setX(mid_point.x);
						newc.setY(signal instanceof LeftSignal ? mid_point.y - 25 : mid_point.y + 25);
					}
				} catch (final Throwable e) {
					e.printStackTrace();
				}

				return CommandResult.newOKCommandResult();
			}
		};
	}

	private AbstractTransactionalCommand getCreateCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "create_signals", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				int index = 0;
				for (final Ambit ambit : root.getAmbits()) {
					if (ambit instanceof Section) {
						final Set<safecap.schema.Node> rightBoundary = AmbitUtil.getAmbitRightBoundaryNodes(ambit);
						for (final Segment s : ambit.getSegments()) {
							if (rightBoundary.contains(s.getTo())) {
								addSignal(s, false, index++);
							}
						}

						final Set<safecap.schema.Node> leftBoundary = AmbitUtil.getAmbitLeftBoundaryNodes(ambit);
						for (final Segment s : ambit.getSegments()) {
							if (leftBoundary.contains(s.getFrom())) {
								addSignal(s, true, index++);
							}
						}
					}
				}

				return CommandResult.newOKCommandResult();
			}

			private Signal addSignal(Segment where, boolean right, int index) {
				Signal newSignal;
				if (right) {
					newSignal = safecap.trackside.TracksideFactory.eINSTANCE.createRightSignal();
				} else {
					newSignal = safecap.trackside.TracksideFactory.eINSTANCE.createLeftSignal();
				}

				newSignal.setLabel("S" + index);

				root.getEquipment().add(newSignal);

				addedSignals.add(newSignal);
				signalLocations.add(where);

				final Wire w = safecap.trackside.TracksideFactory.eINSTANCE.createWire();
				w.setFrom(newSignal);
				w.setTo(where);
				w.setOffset((int) (where.getLength() * 0.1));
				root.getWires().add(w);
				return newSignal;
			}
		};

		return command;
	}

	private Point mid_point(Segment s) {
		final IGraphicalEditPart seg_part = map.get(s);
		if (seg_part == null) {
			return null;
		}

		final IFigure figure = seg_part.getFigure();
		if (figure == null) {
			return null;
		}

		final PolylineConnection conn = (PolylineConnection) figure;
		final PointList points = conn.getPoints();
		double mid_y = points.getPoint(0).y;
		double mid_x = points.getPoint(0).x;
		for (int i = 1; i < points.size(); i++) {
			final Point p = points.getPoint(i);
			mid_x += p.x;
			mid_y += p.y;
		}

		return new Point((int) (mid_x / points.size()), (int) (mid_y / points.size()));
	}

	private Map<Segment, IGraphicalEditPart> buildSegmentMap() {
		final Map<Segment, IGraphicalEditPart> result = new HashMap<>(root.getSegments().size());

		for (final Object _ep : projectPart.getChildren()) {
			if (_ep instanceof NodeEditPart) {
				final NodeEditPart node = (NodeEditPart) _ep;
				for (final Object _se : node.getSourceConnections()) {
					if (_se instanceof ConnectionEditPart) {
						final ConnectionEditPart conn = (ConnectionEditPart) _se;
						final Edge se = (Edge) conn.getModel();
						if (se.getElement() instanceof Segment) {
							result.put((Segment) se.getElement(), (IGraphicalEditPart) _se);
						}
					}
				}
				for (final Object _te : node.getTargetConnections()) {
					if (_te instanceof ConnectionEditPart) {
						final ConnectionEditPart conn = (ConnectionEditPart) _te;
						final Edge te = (Edge) conn.getModel();
						if (te.getElement() instanceof Segment) {
							result.put((Segment) te.getElement(), (IGraphicalEditPart) _te);
						}
					}
				}
			}
		}

		return result;
	}
}
