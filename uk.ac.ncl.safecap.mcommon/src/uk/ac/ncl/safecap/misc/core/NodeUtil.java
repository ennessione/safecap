package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Point;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.NodeRole;
import safecap.schema.Segment;

public class NodeUtil {

	public static Collection<SegmentPath> buildPaths(Collection<Node> nodes, Orientation orientation, ISegmentWalkerFilter filter) {
		final Collection<SegmentPath> result = new HashSet<>();
		for (final Node n : nodes) {
			result.addAll(buildPaths(n, orientation, filter));
		}

		return result;
	}

	public static Collection<SegmentPath> buildPaths(Node node, Orientation orientation, ISegmentWalkerFilter filter) {
		if (orientation == Orientation.LEFT) {
			return SegmentUtil.buildPaths(getOutgoingSegments(node), Orientation.LEFT, filter);
		} else if (orientation == Orientation.RIGHT) {
			return SegmentUtil.buildPaths(getIncomingSegments(node), Orientation.RIGHT, filter);
		} else {
			final Collection<SegmentPath> result = new HashSet<>();
			result.addAll(SegmentUtil.buildPaths(getOutgoingSegments(node), Orientation.LEFT, filter));
			result.addAll(SegmentUtil.buildPaths(getIncomingSegments(node), Orientation.RIGHT, filter));
			return result;
		}
	}

	public static List<Node> getSourceNodes(Project project) {
		final List<Node> result = new ArrayList<>(10);

		for (final Node node : project.getNodes()) {
			if (isSource(node)) {
				result.add(node);
			}
		}

		return result;
	}

	public static final boolean isSource(Node node) {
		return node != null && (node.getRoles() & NodeRole.SOURCE_VALUE) == NodeRole.SOURCE_VALUE;
	}

	public static final boolean isSink(Node node) {
		return node != null && (node.getRoles() & NodeRole.SINK_VALUE) == NodeRole.SINK_VALUE;
	}

	public static final boolean isOnlySource(Node node) {
		return !isSink(node) && isSource(node);
	}

	public static final boolean isOnlySink(Node node) {
		return isSink(node) && !isSource(node);
	}

	public static final boolean isLeftOverlap(Node node) {
		return (node.getRoles() & NodeRole.LEFT_OVERLAP_VALUE) == NodeRole.LEFT_OVERLAP_VALUE;
	}

	public static final boolean isRightOverlap(Node node) {
		return (node.getRoles() & NodeRole.RIGHT_OVERLAP_VALUE) == NodeRole.RIGHT_OVERLAP_VALUE;

	}

	public static final boolean isJunctionNode(Node node) {
		return node.getKind() == NodeKind.POINT || node.getKind() == NodeKind.CROSSOVER || node.getKind() == NodeKind.SWITCHED_CROSSOVER;
	}

	public static final boolean isPointNode(Node node) {
		return node.getKind() == NodeKind.POINT;
	}

	public static final boolean isEdgeNode(Node node) {
		return node.getKind() == NodeKind.BOUNDARY || node.getKind() == NodeKind.TERMINAL;
	}

	public static final void setLeftOverlap(Node node) {
		if (!isPointNode(node)) {
			final int role = node.getRoles() | NodeRole.LEFT_OVERLAP_VALUE;
			node.setRoles(role);
		}
	}

	public static final void setRightOverlap(Node node) {
		if (!isPointNode(node)) {
			final int role = node.getRoles() | NodeRole.RIGHT_OVERLAP_VALUE;
			node.setRoles(role);
		}
	}

	public static NodeDescriptor getNodeDescr(Node node) {
		NodeKind kind = NodeKind.INVALID;
		int role = node.getRoles() & (NodeRole.LEFT_OVERLAP_VALUE | NodeRole.RIGHT_OVERLAP_VALUE);
		final NodeKind current = node.getKind();

		final List<Segment> in = getIncomingSegments(node);
		final List<Segment> out = getOutgoingSegments(node);

		if (in.size() == 1 && out.size() == 1) {
			if (current == NodeKind.SILENT) {
				kind = NodeKind.SILENT;
			} else {
				kind = NodeKind.NORMAL;
			}
		} else if (in.size() == 2 && out.size() == 2) {
			if (current == NodeKind.SWITCHED_CROSSOVER) {
				kind = NodeKind.SWITCHED_CROSSOVER;
			} else {
				kind = NodeKind.CROSSOVER;
			}
		} else if (in.size() == 1 && out.size() == 2) {
			kind = NodeKind.POINT;
			role |= NodeRole.LEFT_POINT_VALUE;
		} else if (in.size() == 2 && out.size() == 1) {
			kind = NodeKind.POINT;
		} else if (in.size() == 0 && out.size() == 1 || in.size() == 1 && out.size() == 0) {
			if (current == NodeKind.TERMINAL) {
				kind = NodeKind.TERMINAL;
			} else {
				kind = NodeKind.BOUNDARY;
			}
		}

		// preserve/compute sink/source for boundary nodes
		if (kind == NodeKind.BOUNDARY) {
			int flag = node.getRoles() & (NodeRole.SOURCE_VALUE | NodeRole.SINK_VALUE);
			if (flag == 0) {
				flag = NodeRole.SOURCE_VALUE | NodeRole.SINK_VALUE;
			}
			role |= flag;
		}

		// compute overlap roles
		// if (kind == NodeKind.NORMAL || kind == NodeKind.TERMINAL || kind ==
		// NodeKind.BOUNDARY) {
		// Project project = EmfUtil.getProject(node);
		// if (project.isOverlaps()) {
		// if (in.size() == 1) {
		// Segment ins = in.get(0);
		// Node from = ins.getFrom();
		// List<Segment> in_from = getIncomingSegments(from);
		// if (in_from.size() == 1) {
		// Segment s = in_from.get(0);
		// Signal signal = SignalUtil.getLeftSignalOn(s);
		// if (signal != null) {
		// role |= NodeRole.LEFT_OVERLAP_VALUE;
		// }
		// }
		// }
		//
		// if (out.size() == 1) {
		// Segment outs = out.get(0);
		// Node from = outs.getTo();
		// List<Segment> out_to = getOutgoingSegments(from);
		// if (out_to.size() == 1) {
		// Segment s = out_to.get(0);
		// Signal signal = SignalUtil.getRightSignalOn(s);
		// if (signal != null) {
		// role |= NodeRole.RIGHT_OVERLAP_VALUE;
		// }
		// }
		// }
		//
		// }
		// }

		return new NodeDescriptor(kind, role);
	}

	public static List<Node> getNodesOfKind(Project root, NodeKind kind) {
		final List<Node> result = new ArrayList<>();

		for (final Node n : root.getNodes()) {
			if (n.getKind() == kind) {
				result.add(n);
			}
		}

		return result;
	}

	public static List<Node> getNodesOfKind(Project root, NodeKind[] kinds) {
		final List<Node> result = new ArrayList<>();

		for (final Node n : root.getNodes()) {
			for (final NodeKind kind : kinds) {
				if (n.getKind() == kind) {
					result.add(n);
				}
			}
		}

		return result;
	}

	public static List<Segment> getIncomingSegments(Node node) {
		final List<Segment> result = new ArrayList<>();

		final Project root = EmfUtil.getProject(node);

		for (final Segment seg : root.getSegments()) {
			if (seg.getTo() == node) {
				result.add(seg);
			}
		}

		return result;
	}

	public static List<Segment> getSegments(Node node) {
		final List<Segment> result = new ArrayList<>();

		final Project root = EmfUtil.getProject(node);

		for (final Segment seg : root.getSegments()) {
			if (seg.getTo() == node || seg.getFrom() == node) {
				result.add(seg);
			}
		}

		return result;
	}

	public static List<Ambit> getLeftBoundaryAmbits(Node node) {
		final List<Ambit> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(node);

		for (final Ambit ambit : root.getAmbits()) {
			if (AmbitUtil.getAmbitLeftBoundaryNodes(ambit).contains(node)) {
				result.add(ambit);
			}
		}

		return result;
	}

	public static List<Ambit> getRightBoundaryAmbits(Node node) {
		final List<Ambit> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(node);

		for (final Ambit ambit : root.getAmbits()) {
			if (AmbitUtil.getAmbitRightBoundaryNodes(ambit).contains(node)) {
				result.add(ambit);
			}
		}

		return result;
	}

	public static List<Segment> getOutgoingSegments(Node node) {
		final List<Segment> result = new ArrayList<>();

		final Project root = EmfUtil.getProject(node);

		for (final Segment seg : root.getSegments()) {
			if (seg.getFrom() == node) {
				result.add(seg);
			}
		}

		return result;
	}

	public static Node getByLabel(Project root, String string) {
		if (string == null) {
			return null;
		}

		for (final Node node : root.getNodes()) {
			if (string.equals(node.getLabel())) {
				return node;
			}
		}

		return null;
	}

	public static boolean isTrapPointEnd(Node node) {
		if (isEdgeNode(node)) {
			for (final Segment s : NodeUtil.getSegments(node)) {
				if (s.getFrom() == node && isPointNode(s.getTo())) {
					final Point point = PointUtil.getPointFor(s.getTo());
					return point != null && PointUtil.isTrap(point);
				} else if (s.getTo() == node && isPointNode(s.getFrom())) {
					final Point point = PointUtil.getPointFor(s.getFrom());
					return point != null && PointUtil.isTrap(point);
				}
			}
		}

		return false;
	}

}
