package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.NodeRole;
import safecap.schema.PointRole;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;

public class SegmentUtil {

	public static List<Segment> normaliseSegments(Collection<Segment> segments) {
		final List<Segment> result = new ArrayList<>(segments.size());

		for (final Segment a : segments) {
			placeLeft(a, result);
		}

		return result;
	}

	private static void placeLeft(Segment a, List<Segment> result) {
		final Collection<Segment> c = SegmentUtil.getRightSegments(a);
		for (int i = 0; i < result.size(); i++) {
			final Segment z = result.get(i);
			if (c.size() == 1 && c.contains(z)) {
				result.add(i, a);
				return;
			}
		}
		result.add(a);
	}

	public static Segment getByNodePair(Project root, Node from, Node to) {
		for (final Segment s : root.getSegments()) {
			if (s.getFrom() == from && s.getTo() == to || s.getFrom() == to && s.getTo() == from) {
				return s;
			}
		}

		return null;
	}

	public static Collection<SegmentPath> buildPaths(Collection<Segment> segments, Orientation orientation, ISegmentWalkerFilter filter) {
		final Collection<SegmentPath> result = new HashSet<>();
		for (final Segment s : segments) {
			result.addAll(buildPaths(s, orientation, filter));
		}

		return result;
	}

	public static Collection<SegmentPath> buildPaths(Segment segment, Orientation orientation, ISegmentWalkerFilter filter) {
		if (orientation == Orientation.LEFT) {
			return buildPathsLeft(segment, filter);
		} else if (orientation == Orientation.RIGHT) {
			return buildPathsRight(segment, filter);
		} else {
			final Collection<SegmentPath> result = new HashSet<>();
			result.addAll(buildPathsLeft(segment, filter));
			result.addAll(buildPathsRight(segment, filter));
			return result;
		}
	}

	/**
	 * Normal overlap filter that stop at a signal
	 */
	public static class OverlapFilter1 implements ISegmentWalkerFilter2 {
		public static final OverlapFilter1 INSTANCE = new OverlapFilter1();

		@Override
		public boolean accept(SegmentPath path, Segment current) {
			return path.getPath().isEmpty() || SignalUtil.getSignalOn(path.getLast(), path.getOrientation().opposite()) == null;
		}
	}

	/**
	 * Alternative overlap filter that stop at a second signal provided that start
	 * is one hop from the first signal
	 */
	public static class OverlapFilter2 implements ISegmentWalkerFilter2 {
		public static final OverlapFilter2 INSTANCE = new OverlapFilter2();

		@Override
		public boolean accept(SegmentPath path, Segment current) {
			return path.size() < 2 || SignalUtil.getSignalOn(path.getLast(), path.getOrientation().opposite()) == null;
		}
	}

	public static Collection<SegmentPath> buildPaths(Segment segment, Orientation orientation, ISegmentWalkerFilter2 filter) {
		final SegmentPath initial = new SegmentPath(orientation, segment);
		initial.getPath().add(segment);
		return extendPath(initial, orientation, filter);
	}

	private static Collection<SegmentPath> extendPath(SegmentPath path, Orientation orientation, ISegmentWalkerFilter2 filter) {
		final Segment segment = path.getLast();
		final List<Segment> out = orientation == Orientation.LEFT ? SegmentUtil.getRightSegments(segment)
				: SegmentUtil.getLeftSegments(segment);
		if (out.isEmpty()) {
			return Collections.singleton(path);
		}

		final Collection<SegmentPath> result = new HashSet<>();
		for (final Segment s : out) {
			if (filter.accept(path, s)) {
				result.addAll(extendPath(new SegmentPath(path, s), orientation, filter));
			}
		}

		if (result.isEmpty()) {
			return Collections.singleton(path);
		}

		return result;
	}

	private static Collection<SegmentPath> buildPathsLeft(Segment segment, ISegmentWalkerFilter filter) {
		if (!filter.accept(null, segment)) {
			return Collections.emptySet();
		}

		final List<Segment> out = SegmentUtil.getRightSegments(segment);
		if (out.isEmpty()) {
			final SegmentPath sp = new SegmentPath(Orientation.LEFT, segment);
			sp.getPath().add(segment);
			return Collections.singleton(sp);
		}

		final Collection<SegmentPath> result = new HashSet<>();

		for (final Segment s : out) {
			if (filter.accept(segment, s)) {
				final Collection<SegmentPath> pp = buildPathsLeft(s, filter);
				for (final SegmentPath sp : pp) {
					sp.prepend(segment);
				}

				result.addAll(pp);
			}
		}

		if (result.isEmpty()) {
			final SegmentPath sp = new SegmentPath(Orientation.LEFT, segment);
			sp.getPath().add(segment);
			return Collections.singleton(sp);
		}

		return result;
	}

	private static Collection<SegmentPath> buildPathsRight(Segment segment, ISegmentWalkerFilter filter) {
		if (!filter.accept(null, segment)) {
			return Collections.emptySet();
		}

		final List<Segment> out = SegmentUtil.getLeftSegments(segment);
		if (out.isEmpty()) {
			final SegmentPath sp = new SegmentPath(Orientation.RIGHT, segment);
			sp.getPath().add(segment);
			return Collections.singletonList(sp);
		}

		final Collection<SegmentPath> result = new ArrayList<>();

		for (final Segment s : out) {
			if (filter.accept(segment, s)) {
				final Collection<SegmentPath> pp = buildPathsRight(s, filter);
				for (final SegmentPath sp : pp) {
					sp.prepend(segment);
				}

				result.addAll(pp);
			}
		}

		if (result.isEmpty()) {
			final SegmentPath sp = new SegmentPath(Orientation.RIGHT, segment);
			sp.getPath().add(segment);
			return Collections.singletonList(sp);
		}
		return result;
	}

	public static List<Segment> getRelated(Segment segment) {
		final List<Segment> result = new ArrayList<>();

		collectRelated(segment, result);

		return result;
	}

	public static void collectRelated(Segment segment, List<Segment> list) {
		final Node left = segment.getFrom();
		final Node right = segment.getTo();

		list.add(segment);

		if (NodeUtil.isJunctionNode(left) || left.getKind() == NodeKind.SILENT) {
			for (final Segment s : NodeUtil.getIncomingSegments(left)) {
				if (!list.contains(s)) {
					collectRelated(s, list);
				}
			}
		}

		if (NodeUtil.isJunctionNode(right) || right.getKind() == NodeKind.SILENT) {
			for (final Segment s : NodeUtil.getOutgoingSegments(right)) {
				if (!list.contains(s)) {
					collectRelated(s, list);
				}
			}
		}

	}

	public static List<Route> getRoutesContaining(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		final List<Route> result = new ArrayList<>();

		for (final Route r : root.getRoutes()) {
			if (r.getSegments().contains(segment)) {
				result.add(r);
			}
		}

		return result;
	}

	public static List<Route> getRoutesContaining(Segment segment, Orientation orientation) {
		final Project root = EmfUtil.getProject(segment);
		final List<Route> result = new ArrayList<>();

		for (final Route r : root.getRoutes()) {
			if (r.getSegments().contains(segment) && r.getOrientation().equals(orientation)) {
				result.add(r);
			}
		}

		return result;
	}

	public static List<Route> getSegmentRoutes(Segment segment) {
		final Ambit ambit = getSegmentAmbit(segment);
		if (ambit == null) {
			return new ArrayList<>();
		}

		return AmbitUtil.getAmbitRoutes(ambit);
	}

	public static List<Ambit> getSegmentAmbits(List<Segment> segments) {
		final List<Ambit> result = new ArrayList<>();
		for (final Segment s : segments) {
			final Ambit a = getSegmentAmbit(s);
			if (a != null && !result.contains(a)) {
				result.add(a);
			}
		}
		return result;
	}

	public static Ambit getSegmentAmbit(Segment segment) {
		final Project root = EmfUtil.getProject(segment);
		if (root == null) {
			return null;
		}

		for (final Ambit ambit : root.getAmbits()) {
			if (ambit.getSegments().contains(segment)) {
				return ambit;
			}
		}

		return null;
	}

	public static int getSegmentRole(Segment segment) {
		int role = segment.getRole() & ~SegmentRole.JUNCTION_VALUE;

		if (isJunctionPart(segment)) {
			role |= SegmentRole.JUNCTION_VALUE;
		}

		return role;
	}

	public static boolean needsLabel(Segment segment) {
		if (segment == null) {
			return false;
		}

		if (segment.getFrom() == null || segment.getTo() == null) {
			return false;
		}

		if (segment.getFrom().getKind() == NodeKind.CROSSOVER || segment.getFrom().getKind() == NodeKind.SWITCHED_CROSSOVER) {
			return false;
		}

		if (segment.getFrom().getKind() == NodeKind.POINT
				&& (segment.getFrom().getRoles() & NodeRole.LEFT_POINT_VALUE) == NodeRole.LEFT_POINT_VALUE) {
			return false;
		}

		if (segment.getTo().getKind() == NodeKind.POINT && (segment.getTo().getRoles() & NodeRole.LEFT_POINT_VALUE) == 0) {
			return false;
		}

		if (segment.getTo().getKind() == NodeKind.CROSSOVER || segment.getFrom().getKind() == NodeKind.SWITCHED_CROSSOVER) {
			final List<Segment> seg = NodeUtil.getIncomingSegments(segment.getTo());
			int min = Integer.MAX_VALUE;
			for (final Segment s : seg) {
				if (s.hashCode() < min) {
					min = s.hashCode();
				}
			}

			if (segment.hashCode() != min) {
				return false;
			}
		}

		return true;
	}

	public static String autonameSegment(Segment segment) {
		// if (!needsLabel())

		final List<Segment> left = getLeftSegments(segment);
		final List<Segment> right = getRightSegments(segment);

		final Project root = EmfUtil.getProject(segment);

		if (left.size() > 0) {
			final String l = left.get(0).getLabel();
			if (l != null && l.length() > 0) {
				return nextUnique(root, l);
			}
		}

		if (right.size() > 0) {
			final String l = right.get(0).getLabel();
			if (l != null && l.length() > 0) {
				return prevUnique(root, l);
			}
		}

		return "";
	}

	private static Pattern numberp;
	static {
		numberp = Pattern.compile("([^0-9]+)([0-9]+).*");
	}

	private static String nextUnique(Project root, String label) {
		if (label.length() == 2) {
			final char[] chr = label.toCharArray();
			if (Character.isLetter(chr[0])) {
				if (Character.isLetter(chr[1]) && Character.isLowerCase(chr[1]) && chr[1] < 'z'
						|| Character.isUpperCase(chr[1]) && chr[1] < 'Z') {
					final String base = String.valueOf(chr[0]) + String.valueOf((char) (chr[1] + 1));
					if (getByLabel(root, base) == null) {
						return base;
					} else {
						return getUniqueName(root, label);
					}
				} else if (Character.isDigit(chr[1])) {
					final int number = chr[1] - '0';
					final String base = String.valueOf(chr[0]) + (number + 1);
					return getUniqueName(root, base);
				}
			}
		}

		final Matcher m = numberp.matcher(label);
		if (m.matches()) {
			final String base = m.group(1);
			int i = Integer.parseUnsignedInt(m.group(2));
			while (getByLabel(root, base + i) != null) {
				i++;
			}
			return base + i;
		}

		return getUniqueName(root, label);
	}

	private static String prevUnique(Project root, String label) {
		if (label.length() == 2) {
			final char[] chr = label.toCharArray();
			if (Character.isLetter(chr[0])) {
				if (Character.isLetter(chr[1]) && Character.isLowerCase(chr[1]) && chr[1] > 'a'
						|| Character.isUpperCase(chr[1]) && chr[1] > 'A') {
					final String base = String.valueOf(chr[0]) + String.valueOf((char) (chr[1] - 1));
					if (getByLabel(root, base) == null) {
						return base;
					} else {
						return getUniqueName(root, label);
					}
				} else if (Character.isDigit(chr[1]) && chr[1] > '0') {
					final int number = chr[1] - '0';
					final String base = String.valueOf(chr[0]) + +(number - 1);
					return getUniqueName(root, base);
				}
			}
		}

		final Matcher m = numberp.matcher(label);
		if (m.matches()) {
			final String base = m.group(1);
			long i = Long.parseUnsignedLong(m.group(2)) + 1;
			while (getByLabel(root, base + i) != null) {
				i++;
			}
			return base + i;
		}

		return getUniqueName(root, label);
	}

	public static String getUniqueName(Project root, String base) {
		int index = 1;
		String candidate = base;

		while (getByLabel(root, candidate) != null) {
			candidate = base + index++;
		}

		return candidate;
	}

	public static String getUniqueName(Project root) {
		for (char a = 'A'; a <= 'Z'; a++) {
			final String candidate = "" + a + a;
			if (getByLabel(root, candidate) == null) {
				return candidate;
			}
		}

		for (char a = 'A'; a <= 'Z'; a++) {
			for (char b = 'A'; b <= 'Z'; b++) {
				final String candidate = "" + a + b;
				if (getByLabel(root, candidate) == null) {
					return candidate;
				}
			}
		}

		return getUniqueName(root, "XX");
	}

	public static Segment getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Segment s : root.getSegments()) {
			if (label.equals(s.getLabel())) {
				return s;
			}
		}

		return null;
	}

	private static boolean matchingRolesLeftToRight(Segment from, Segment to) {
		if (from.getTo().getKind() == NodeKind.POINT) {
			if (from.getPointrole() == PointRole.NONE || to.getPointrole() == PointRole.NONE) {
				return true;
			} else {
				return false;
			}
		}

		return isDiamondCrossingCompatible(from, to);
	}

	private static boolean matchingRolesRightToLeft(Segment from, Segment to) {
		if (from.getFrom().getKind() == NodeKind.POINT) {
			if (from.getPointrole() == PointRole.NONE || to.getPointrole() == PointRole.NONE) {
				return true;
			} else {
				return false;
			}
		}

		return isDiamondCrossingCompatible(from, to);
	}

	private static boolean isDiamondCrossingCompatible(Segment from, Segment to) {
		if (SegmentUtil.isJunctionPart(from) && SegmentUtil.isJunctionPart(to)) {
			if (getSegmentAmbit(from) == getSegmentAmbit(to) && shareCrossoverNode(from, to)) {
				final boolean result = (from.getRole() & (SegmentRole.CROSS_A_VALUE | SegmentRole.CROSS_B_VALUE)) == (to.getRole()
						& (SegmentRole.CROSS_A_VALUE | SegmentRole.CROSS_B_VALUE));
				return result;
			}
		}

		return true;
	}

	private static boolean shareCrossoverNode(Segment from, Segment to) {
		final Node a = from.getFrom();
		final Node b = from.getTo();
		final Node c = to.getFrom();
		final Node d = to.getTo();

		if (a == c || a == d) {
			if (a.getKind() == NodeKind.CROSSOVER || a.getKind() == NodeKind.SWITCHED_CROSSOVER) {
				return true;
			}
		}

		if (b == c || b == d) {
			if (b.getKind() == NodeKind.CROSSOVER || b.getKind() == NodeKind.SWITCHED_CROSSOVER) {
				return true;
			}
		}

		return false;
	}

	public static List<Segment> getLeftSegments(Segment segment) {
		final List<Segment> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(segment);
		final Node left = segment.getFrom();

		if (root != null) {
			for (final Segment s : root.getSegments()) {
				if (s.getTo() == left && matchingRolesRightToLeft(segment, s)) {
					result.add(s);
				}
			}
		}

		return result;
	}

	/*
	 * Get segments after this one (to the right) (from) --(this)-- (to) --
	 * (another) -- (x)
	 */
	public static List<Segment> getRightSegments(Segment segment) {
		final List<Segment> result = new ArrayList<>();
		final Project root = EmfUtil.getProject(segment);
		final Node right = segment.getTo();
		for (final Segment s : root.getSegments()) {
			if (s.getFrom() == right) {
				if (matchingRolesLeftToRight(segment, s)) {
					result.add(s);
				}
			}

		}

		return result;
	}

	public static boolean isJunctionPart(Segment segment) {
		if (segment.getFrom() == null || segment.getTo() == null) {
			return false;
		}

		return segment.getFrom().getKind() == NodeKind.POINT || segment.getFrom().getKind() == NodeKind.CROSSOVER
				|| segment.getFrom().getKind() == NodeKind.SWITCHED_CROSSOVER || segment.getTo().getKind() == NodeKind.POINT
				|| segment.getTo().getKind() == NodeKind.CROSSOVER || segment.getTo().getKind() == NodeKind.SWITCHED_CROSSOVER;
	}

	public static boolean isLeftOpen(Segment segment) {
		if (segment.getFrom().getKind() == NodeKind.SILENT) {
			return true;
		}

		return false;
	}

	public static boolean isRightOpen(Segment segment) {
		if (segment.getTo().getKind() == NodeKind.SILENT) {
			return true;
		}

		return false;
	}

	public static boolean isOpen(Segment segment) {
		if (segment.getTo().getKind() == NodeKind.SILENT || segment.getFrom().getKind() == NodeKind.SILENT) {
			return true;
		}

		return false;
	}
}
