package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import java.util.HashMap;
import java.util.Map;

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
import safecap.schema.Segment;
import safecap.trackside.Signal;
import safecap.trackside.Wire;

public class CreateNewSignal {
	private final IGraphicalEditPart projectPart;
	private final Project root;
	private final Point location;
	private Signal newSignal;
	private final boolean right;
	private final Map<Segment, IGraphicalEditPart> map;

	public CreateNewSignal(IGraphicalEditPart projectPart, Project prj, Point location, boolean right) {
		this.projectPart = projectPart;
		root = prj;
		this.location = location;
		this.right = right;
		map = buildSegmentMap();
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
					final IGraphicalEditPart newNode_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, newSignal);
					final Bounds newc = (Bounds) ((Node) newNode_part.getModel()).getLayoutConstraint();
					final Point pos = MyGridUtils.putOnMinor(projectPart, location);
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

				if (right) {
					newSignal = safecap.trackside.TracksideFactory.eINSTANCE.createRightSignal();
				} else {
					newSignal = safecap.trackside.TracksideFactory.eINSTANCE.createLeftSignal();
				}
				root.getEquipment().add(newSignal);

				final Segment s = nearestSegment(location);
				if (s != null) {
					final Wire w = safecap.trackside.TracksideFactory.eINSTANCE.createWire();
					w.setFrom(newSignal);
					w.setTo(s);
					root.getWires().add(w);
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private double sqr(double x) {
		return x * x;
	}

	private double dist2(Point v, Point w) {
		return sqr(v.x - w.x) + sqr(v.y - w.y);
	}

	private double distToSegmentSquared(Point p, Point v, Point w) {
		final double l2 = dist2(v, w);
		if (l2 == 0) {
			return dist2(p, v);
		}
		double t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
		t = Math.max(0, Math.min(1, t));
		return dist2(p, new Point(v.x + t * (w.x - v.x), v.y + t * (w.y - v.y)));
	}

	private double distToSegment(Point p, Point v, Point w) {
		return Math.sqrt(distToSegmentSquared(p, v, w));
	}

	private Segment nearestSegment(Point p) {
		double min = Double.MAX_VALUE;
		Segment min_s = null;
		for (final Segment s : root.getSegments()) {
			final double d = distanceTo(p, s);
			if (d < min) {
				min = d;
				min_s = s;
			}
		}

		return min_s;
	}

	private double distanceTo(Point p, Segment s) {
		final IGraphicalEditPart seg_part = map.get(s);
		if (seg_part == null) {
			return Double.MAX_VALUE;
		}

		final IFigure figure = seg_part.getFigure();
		if (figure == null) {
			return Double.MAX_VALUE;
		}

		double min = Double.MAX_VALUE;
		final PolylineConnection conn = (PolylineConnection) figure;
		final PointList points = conn.getPoints();
		for (int i = 1; i < points.size(); i++) {
			final Point v = points.getPoint(i - 1);
			final Point w = points.getPoint(i);
			if (right && (v.y >= p.y || w.y >= p.y)) {
				return min;
			} else if (!right && (v.y <= p.y || w.y <= p.y)) {
				return min;
			}
			final double d = distToSegment(p, v, w);
			if (d < min) {
				min = d;
			}
		}
		return min;
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
