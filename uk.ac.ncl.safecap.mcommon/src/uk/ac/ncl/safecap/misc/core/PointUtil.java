package uk.ac.ncl.safecap.misc.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import safecap.Project;
import safecap.derived.DerivedFactory;
import safecap.derived.MergedPoint;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.PointRole;
import safecap.schema.Segment;

public class PointUtil {

	public static Segment getAnySegment(Point point) {
		final Project root = EmfUtil.getProject(point);
		final Node node = point.getNode();

		if (point.getNode() != null) {
			for (final Segment seg : root.getSegments()) {
				if (seg.getTo() == node || seg.getFrom() == node) {
					return seg;
				}
			}
		}

		return null;
	}

	public static Segment getNormalBranch(Point point, Collection<Segment> segments) {
		if (point.getNode() != null) {
			for (final Segment s : segments) {
				if (s.getPointrole() == PointRole.NORMAL) {
					if (s.getFrom() == point.getNode() || s.getTo() == point.getNode()) {
						return s;
					}
				}
			}
		}

		return null;
	}

	public static Segment getReverseBranch(Point point, Collection<Segment> segments) {
		if (point.getNode() != null) {
			for (final Segment s : segments) {
				if (s.getPointrole() == PointRole.REVERSE) {
					if (s.getFrom() == point.getNode() || s.getTo() == point.getNode()) {
						return s;
					}
				}
			}
		}

		return null;
	}

	public static Point getPointFor(Node node) {
		final Project project = EmfUtil.getProject(node);

		for (final Ambit ambit : project.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {
					if (point.getNode() == node) {
						return point;
					}
				}
			}
		}

		return null;
	}

	public static Collection<Point> getAllSiblings(Project root, Point point) {
		final MergedPoint mp = getMergedByPoint(root, point);
		if (mp == null) {
			return Collections.singleton(point);
		} else {
			return mp.getPoints();
		}
	}

	public static MergedPoint getMergedByLabel(Project root, String label) {
		for (final MergedPoint mp : root.getMergedpoints()) {
			if (label.equals(mp.getLabel())) {
				return mp;
			}
		}

		return null;
	}

	public static MergedPoint getMergedByPoint(Project root, Point point) {
		for (final MergedPoint mp : root.getMergedpoints()) {
			if (mp.getPoints().contains(point)) {
				return mp;
			}
		}

		return null;
	}

	public static Point getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final MergedPoint mp : root.getMergedpoints()) {
			if (label.equals(mp.getLabel())) {
				return mp;
			}
		}

		for (final Ambit ambit : root.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {
					if (label.equals(point.getNode().getLabel())) {
						return point;
					}
				}
			}
		}

		return null;
	}

	public static void renamePoints(Project root, String patternString, String template) {
		final Pattern pattern = Pattern.compile(patternString);

		for (final Ambit ambit : root.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {
					final Matcher matcher = pattern.matcher(point.getNode().getLabel());
					if (matcher.matches()) {
						String newPointName = template;
						for (int i = 1; i < matcher.groupCount() + 1; i++) {
							final String value = matcher.group(i);
							newPointName = newPointName.replaceAll("\\$" + i, value);
						}
						point.getNode().setLabel(newPointName);
					}
				}
			}
		}
	}

	public static void buildMergedPoints(Project project, String part1, String delimeter, String part2) {
		// project.getMergedpoints().clear();

		final Collection<Point> processed = new HashSet<>();
		final String pstring = "(" + part1 + ")" + delimeter + part2;
		final Pattern pattern = Pattern.compile(pstring);

		for (final Ambit ambit : project.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {
					if (!processed.contains(point)) {
						final Matcher matcher = pattern.matcher(point.getNode().getLabel());
						if (matcher.matches()) {
							processed.add(point);
							final String name = matcher.group(1);
							buildPointGroup(project, name, processed, delimeter, part2);
						}
					}
				}
			}
		}
	}

	private static void buildPointGroup(Project project, String name, Collection<Point> processed, String delimeter, String part2) {
		Pattern pattern = Pattern.compile(name, Pattern.LITERAL);
		final String pstring = pattern + delimeter + "(" + part2 + ")";
		pattern = Pattern.compile(pstring);

		final MergedPoint mpoints = DerivedFactory.eINSTANCE.createMergedPoint();
		mpoints.setLabel(name);

		for (final Ambit ambit : project.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {
					final Matcher matcher = pattern.matcher(point.getNode().getLabel());
					if (matcher.matches()) {
						final String code = matcher.group(1);
						mpoints.getCodes().add(code);
						mpoints.getPoints().add(point);
						processed.add(point);
						// System.out.println("Add merged point " + name);
					}
				}
			}
		}

		if (mpoints.getPoints().size() > 0) {
			project.getMergedpoints().add(mpoints);
		}
	}

	public static boolean isTrap(Point point) {
		return ExtensionUtil.getBool(point.getNode(), SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP);
	}

	public static boolean isMerged(Point point) {
		return point instanceof MergedPoint;
	}

	public static String getPointState(Segment from, Segment to) {
		Node node;
		if (from.getTo() == to.getFrom() && to.getFrom().getKind() == NodeKind.POINT) {
			node = from.getTo();
		} else if (from.getFrom() == to.getTo() && to.getTo().getKind() == NodeKind.POINT) {
			node = from.getFrom();
		} else {
			return null;
		}

		if (from.getPointrole() == PointRole.NONE) {
			if (to.getPointrole() == PointRole.NORMAL) {
				return node.getLabel() + "N";
			} else if (to.getPointrole() == PointRole.REVERSE) {
				return node.getLabel() + "R";
			}
		} else if (to.getPointrole() == PointRole.NONE) {
			if (from.getPointrole() == PointRole.NORMAL) {
				return node.getLabel() + "N";
			} else if (from.getPointrole() == PointRole.REVERSE) {
				return node.getLabel() + "R";
			}
		}

		return null;
	}

	public static safecap.Orientation getPointOrientation(Point point) {
		final List<Segment> incoming = NodeUtil.getIncomingSegments(point.getNode());
		final List<Segment> outgoing = NodeUtil.getOutgoingSegments(point.getNode());
		if (incoming.size() == 2 && outgoing.size() == 1) {
			return safecap.Orientation.LEFT;
		} else if (incoming.size() == 1 && outgoing.size() == 2) {
			return safecap.Orientation.RIGHT;
		} else {
			return safecap.Orientation.ANY;
		}
	}

}
