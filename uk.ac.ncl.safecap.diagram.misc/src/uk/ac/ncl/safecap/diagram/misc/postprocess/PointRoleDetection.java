package uk.ac.ncl.safecap.diagram.misc.postprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;

import safecap.Project;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.NodeRole;
import safecap.schema.PointRole;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;
import uk.ac.ncl.safecap.diagram.misc.visual.LineEq;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class PointRoleDetection {
	private final Project root;
	private final Map<Segment, IGraphicalEditPart> map;
	private final IGraphicalEditPart projectPart;

	public PointRoleDetection(IGraphicalEditPart projectPart, Project root) {
		this.root = root;
		this.projectPart = projectPart;
		map = buildSegmentMap();
	}

	public static void refresh(IGraphicalEditPart projectPart, Project root, IProgressMonitor monitor) {
		final PointRoleDetection cmd = new PointRoleDetection(projectPart, root);
		cmd.run(monitor);
	}

	public void run(IProgressMonitor monitor) {
		// CompositeCommand compc = new CompositeCommand("RefreshSegmentRole");
		// compc.add(getRefreshNodeKindAndRoleCommand());
		try {
			// OperationHistoryFactory.getOperationHistory().execute(compc,null, null);
			getRefreshNodeKindAndRoleCommand().execute(monitor, null);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private AbstractTransactionalCommand getRefreshNodeKindAndRoleCommand() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(root);

		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "RefreshSegmentRole", null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

				for (final Segment s : root.getSegments()) {
					s.setPointrole(PointRole.NONE);
					s.setRole(SegmentRole.NONE_VALUE);
				}

				for (final Node node : root.getNodes()) {
					if (node.getKind() == NodeKind.POINT) {
						processPointNode(node);
					} else if (node.getKind() == NodeKind.CROSSOVER) {
						processCrossoverNode(node);
					} else if (node.getKind() == NodeKind.SWITCHED_CROSSOVER) {
						processCrossoverNode(node);
					}
				}

				for (final Segment s : root.getSegments()) {
					s.setRole(SegmentUtil.getSegmentRole(s));
				}

				return CommandResult.newOKCommandResult();
			}

		};

		return command;
	}

	private void processCrossoverNode(Node node) {
		final List<Segment> in = NodeUtil.getIncomingSegments(node);
		final List<Segment> out = NodeUtil.getOutgoingSegments(node);

		if (in.size() == 0 || out.size() == 0) {
			return;
		}

		assert in.size() == 2 && out.size() == 2;

		for (final Segment s : in) {
			s.setPointrole(PointRole.NONE);
		}

		for (final Segment s : out) {
			s.setPointrole(PointRole.NONE);
		}

		final PointList pl_in0 = getPoints(in.get(0));
		final PointList pl_out0 = getPoints(out.get(0));
		final PointList pl_in1 = getPoints(in.get(1));
		final PointList pl_out1 = getPoints(out.get(1));

		if (pl_in0 == null || pl_out0 == null || pl_in1 == null || pl_out1 == null) {
			return;
		}

		final double a1 = getAngle(pl_in0, pl_out0);
		final double a2 = getAngle(pl_in0, pl_out1);

		if (a1 < a2) { // out.get(0) is normal
			in.get(0).setRole(in.get(0).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_A_VALUE);
			out.get(0).setRole(out.get(0).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_A_VALUE);
			in.get(1).setRole(in.get(1).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_B_VALUE);
			out.get(1).setRole(out.get(1).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_B_VALUE);
		} else { // out.get(1) is normal
			in.get(0).setRole(in.get(0).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_A_VALUE);
			out.get(0).setRole(out.get(0).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_B_VALUE);
			in.get(1).setRole(in.get(1).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_B_VALUE);
			out.get(1).setRole(out.get(1).getRole() | SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_A_VALUE);
		}
	}

	private void processPointNode(Node node) {
		final List<Segment> in = NodeUtil.getIncomingSegments(node);
		final List<Segment> out = NodeUtil.getOutgoingSegments(node);

		if (in.size() == 0 || out.size() == 0) {
			return;
		}

		for (final Segment s : in) {
			final int r = s.getRole();
			s.setRole(r & (SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_A_VALUE | SegmentRole.CROSS_B_VALUE));
		}

		for (final Segment s : out) {
			final int r = s.getRole();
			s.setRole(r & (SegmentRole.JUNCTION_VALUE | SegmentRole.CROSS_A_VALUE | SegmentRole.CROSS_B_VALUE));
		}

		final PointList pl_in0 = getPoints(in.get(0));
		final PointList pl_out0 = getPoints(out.get(0));

		if (pl_in0 == null || pl_out0 == null) {
			return;
		}

		if ((node.getRoles() & NodeRole.LEFT_POINT_VALUE) == NodeRole.LEFT_POINT_VALUE) {
			assert in.size() == 1 && out.size() == 2;
			final PointList pl_out1 = getPoints(out.get(1));

			in.get(0).setPointrole(PointRole.NONE);

			final double a1 = getAngle(pl_in0, pl_out0);
			final double a2 = getAngle(pl_in0, pl_out1);

			if (a1 < a2) { // out.get(0) is normal
				out.get(0).setPointrole(PointRole.NORMAL);
				out.get(1).setPointrole(PointRole.REVERSE);
			} else { // out.get(1) is normal
				out.get(0).setPointrole(PointRole.REVERSE);
				out.get(1).setPointrole(PointRole.NORMAL);

			}
		} else {
			assert in.size() == 2 && out.size() == 1;
			final PointList pl_in1 = getPoints(in.get(1));

			out.get(0).setPointrole(PointRole.NONE);

			final double a1 = getAngle(pl_in0, pl_out0);
			final double a2 = getAngle(pl_in1, pl_out0);

			if (a1 < a2) { // in.get(0) is normal
				in.get(0).setPointrole(PointRole.NORMAL);
				in.get(1).setPointrole(PointRole.REVERSE);
			} else { // in.get(1) is normal
				in.get(0).setPointrole(PointRole.REVERSE);
				in.get(1).setPointrole(PointRole.NORMAL);
			}

		}
	}

	private double getAngle(PointList a, PointList b) {
		final LineEq line_a = new LineEq(a.getPoint(a.size() - 2), a.getPoint(a.size() - 1));
		final LineEq line_b = new LineEq(b.getPoint(0), b.getPoint(1));

		return Math.abs(line_a.k - line_b.k);
	}

	private PointList getPoints(Segment segment) {
		final IGraphicalEditPart seg_part = map.get(segment);
		if (seg_part == null) {
			return null;
		}

		final IFigure figure = seg_part.getFigure();
		if (figure == null) {
			return null;
		}

		final PolylineConnection conn = (PolylineConnection) figure;
		return conn.getPoints();
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
