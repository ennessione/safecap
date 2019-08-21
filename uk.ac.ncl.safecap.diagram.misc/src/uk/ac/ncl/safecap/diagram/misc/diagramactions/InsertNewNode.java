package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;

import safecap.Project;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.visual.LineEq;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackConstants;
import uk.ac.ncl.safecap.misc.core.EmfUtil;

public class InsertNewNode {
	private final IGraphicalEditPart projectPart;
	private final Segment segment;
	private final Project root;
	private final Point location;

	private static final int SNAP_TOLERANCE = TrackConstants.TRACK_WIDTH * 2;

	private safecap.schema.Node newNode;
	private safecap.schema.Node a, b;
	private final Map<Segment, IGraphicalEditPart> map;

	public InsertNewNode(IGraphicalEditPart segmentPart, Segment seg, Point location) {
		final DiagramRootEditPart dpart = (DiagramRootEditPart) segmentPart.getParent();
		projectPart = (IGraphicalEditPart) dpart.getContents();
		segment = seg;
		root = EmfUtil.getProject(seg);
		this.location = location;
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
					final IGraphicalEditPart newNode_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, newNode);
					final IGraphicalEditPart a_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, a);
					final IGraphicalEditPart b_part = (IGraphicalEditPart) projectPart.findEditPart(projectPart, b);
					final Bounds ac = (Bounds) ((Node) a_part.getModel()).getLayoutConstraint();
					final Bounds bc = (Bounds) ((Node) b_part.getModel()).getLayoutConstraint();

					final Bounds newc = (Bounds) ((Node) newNode_part.getModel()).getLayoutConstraint();

					final int x = location.x - TrackConstants.NODE_WIDTH / 2;
					int y = location.y - TrackConstants.NODE_HEIGHT / 2;
					final LineEq eq = new LineEq(ac.getX(), ac.getY(), bc.getX(), bc.getY());
					final double yp = eq.y(location.x);
					if (Math.abs(location.y - yp) <= TrackConstants.TRACK_WIDTH * 2) {
						y = (int) yp;
					}

					newc.setX(x);
					newc.setY(y);
					newNode_part.getViewer().select(newNode_part);
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

				a = segment.getFrom();
				b = segment.getTo();
				newNode = safecap.schema.SchemaFactory.eINSTANCE.createNode();
				root.getNodes().add(newNode);

				// re-route old segment
				final List<Segment> affected = getMatchingSegments();
				affected.add(segment);

				for (final Segment s : affected) {
					final safecap.schema.Node old_to = s.getTo();
					s.setTo(newNode);

					final Segment newSegment = safecap.schema.SchemaFactory.eINSTANCE.createSegment();
					newSegment.setFrom(newNode);
					newSegment.setTo(old_to);
					newSegment.setLength(s.getLength() / 2);
					s.setLength(s.getLength() / 2);
					root.getSegments().add(newSegment);
				}

				return CommandResult.newOKCommandResult();
			}
		};

		return command;
	}

	private List<Segment> getMatchingSegments() {
		final List<Segment> result = new ArrayList<>();

		for (final Segment seg : root.getSegments()) {
			if (seg != segment && segmentIntersects(seg)) {
				result.add(seg);
			}
		}

		return result;
	}

	private boolean segmentIntersects(Segment segment) {
		final IGraphicalEditPart seg_part = map.get(segment);
		if (seg_part == null) {
			return false;
		}

		final IFigure figure = seg_part.getFigure();
		if (figure == null) {
			return false;
		}

		final PolylineConnection conn = (PolylineConnection) figure;
		final PointList points = conn.getPoints();
		return points.polylineContainsPoint(location.x, location.y, SNAP_TOLERANCE);
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
