package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.List;

import safecap.Orientation;
import safecap.Project;
import safecap.model.Ambit;
import safecap.model.Junction;
import safecap.model.Line;
import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.Segment;

public class LineUtil {
	public static Line getByLabel(Project root, String label) {
		if (label == null) {
			return null;
		}

		for (final Line l : root.getLines()) {
			if (label.equals(l.getLabel())) {
				return l;
			}
		}

		return null;
	}

	public static Line getByLabelPrefix(Project root, String prefix) {
		if (prefix == null || prefix.length() == 0) {
			return null;
		}

		for (final Line l : root.getLines()) {
			if (prefix.startsWith(l.getLabel()) || l.getLabel().startsWith(prefix)) {
				return l;
			}
		}

		return null;
	}

	public static String getUniqueName(Project root, String base) {
		int index = 1;
		String candidate = base;

		while (getByLabel(root, candidate) != null) {
			candidate = base + "_" + index++;
		}

		return candidate;
	}

	public static int getLength(Line line) {
		int len = 0;

		for (final Route r : line.getRoutes()) {
			len += RouteUtil.getLength(r);
		}

		return len;

	}

	public static boolean containsSegment(Line line, Segment segment) {
		for (final Route route : line.getRoutes()) {
			if (route.getSegments().contains(segment)) {
				return true;
			}
		}
		return false;
	}

	public static List<Segment> getSegments(Line line) {
		final List<Segment> segments = new ArrayList<>();
		for (final Route route : line.getRoutes()) {
			segments.addAll(route.getSegments());
		}
		return segments;
	}

	public static Node getStartNode(Line line) {
		if (line.getOrientation() == Orientation.LEFT) {
			return line.getRoutes().get(0).getSegments().get(0).getFrom();
		} else {
			return line.getRoutes().get(0).getSegments().get(0).getTo();
		}
	}

	public static Node getEndNode(Line line) {
		final Route lroute = line.getRoutes().get(line.getRoutes().size() - 1);
		final Segment lseg = lroute.getSegments().get(lroute.getSegments().size() - 1);
		if (line.getOrientation() == Orientation.LEFT) {
			return lseg.getTo();
		} else {
			return lseg.getFrom();
		}
	}

	public static List<Point> getAllPoints(Line line) {
		final List<Point> points = new ArrayList<>();
		for (final Route route : line.getRoutes()) {
			for (final Ambit ambit : route.getAmbits()) {
				if (ambit instanceof Junction) {
					final Junction j = (Junction) ambit;
					points.addAll(j.getPoints());
				}
			}
		}
		return points;
	}
}
