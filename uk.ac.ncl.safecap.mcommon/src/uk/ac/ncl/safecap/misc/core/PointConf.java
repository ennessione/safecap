package uk.ac.ncl.safecap.misc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import safecap.Project;
import safecap.derived.MergedPoint;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.PointRole;
import safecap.schema.Segment;

public class PointConf implements Serializable {

	private static final long serialVersionUID = -5210696734152651803L;
	private final List<Point> normal = new ArrayList<>();
	private final List<Point> reverse = new ArrayList<>();
	private final List<Point> facing = new ArrayList<>();
	private final List<Point> trailing = new ArrayList<>();
	private boolean expandMerged = false;

	public PointConf() {
	}

	public PointConf(List<Segment> segments) {
		Segment prev = null;
		for (final Segment segment : segments) {
			check(prev, segment);
			prev = segment;
		}
	}

	public PointConf(List<Segment> segments, boolean expandMerged) {
		this.expandMerged = true;

		Segment prev = null;
		for (final Segment segment : segments) {
			check(prev, segment);
			prev = segment;
		}
	}

	public PointConf(Route route) {
		this(route.getSegments());
	}

	public PointConf(Route route, boolean expandMerged) {
		this(route.getSegments(), expandMerged);

	}

	public PointConf(Project root, List<Node> nodes) {
		this();
		final List<Segment> segments = new ArrayList<>();
		for (int i = 1; i < nodes.size(); i++) {
			segments.add(SegmentUtil.getByNodePair(root, nodes.get(i - 1), nodes.get(i)));
		}

		Segment prev = null;
		for (final Segment segment : segments) {
			check(prev, segment);
			prev = segment;
		}
	}

	private void check(Segment prev, Segment segment) {

		if (segment != null && segment.getPointrole() != PointRole.NONE) {
			final Node left = segment.getFrom();
			final Node right = segment.getTo();

			Node point_node = null;
			if (left != null && left.getKind() == NodeKind.POINT) {
				point_node = left;
			} else if (right != null && right.getKind() == NodeKind.POINT) {
				point_node = right;
			}

			if (point_node != null) {
				final Point point = PointUtil.getPointFor(point_node);
				if (segment.getPointrole() == PointRole.NORMAL) {
					addPointNormal(prev, point);
				} else if (segment.getPointrole() == PointRole.REVERSE) {
					addPointReverse(prev, point);
				}
			}
		}
	}

	private void addPointReverse(Segment prev, Point point) {
		if (expandMerged) {
			final MergedPoint mp = PointUtil.getMergedByPoint(EmfUtil.getProject(point), point);
			if (mp != null) {
				for (final Point p : mp.getPoints()) {
					reverse.add(p);
					addPointType(prev, p);
				}
			} else {
				reverse.add(point);
				addPointType(prev, point);
			}
		} else {
			reverse.add(point);
			addPointType(prev, point);
		}
	}

	private void addPointType(Segment prev, Point point) {
		if (prev != null && prev.getPointrole() == PointRole.NONE
				&& (prev.getTo() == point.getNode() || prev.getFrom() == point.getNode())) {
			facing.add(point);
		} else {
			trailing.add(point);
		}
	}

	private void addPointNormal(Segment prev, Point point) {
		if (expandMerged) {
			final MergedPoint mp = PointUtil.getMergedByPoint(EmfUtil.getProject(point), point);
			if (mp != null) {
				for (final Point p : mp.getPoints()) {
					normal.add(p);
					addPointType(prev, p);
				}
			} else {
				normal.add(point);
				addPointType(prev, point);
			}
		} else {
			normal.add(point);
			addPointType(prev, point);
		}

	}

	public List<Point> getNormal() {
		return normal;
	}

	public List<Point> getReverse() {
		return reverse;
	}

	public List<Point> getFacing() {
		return facing;
	}

	public List<Point> getTrailing() {
		return trailing;
	}

}
