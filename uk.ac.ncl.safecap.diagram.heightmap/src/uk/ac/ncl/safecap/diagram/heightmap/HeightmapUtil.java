package uk.ac.ncl.safecap.diagram.heightmap;

import java.util.ArrayList;
import java.util.List;

import safecap.schema.Node;
import safecap.schema.NodeKind;
import safecap.schema.Segment;

public class HeightmapUtil {
	public static Segment findLeftMost(List<Segment> segments) {
		for (final Segment seg : segments) {
			final Node node = seg.getFrom();
			if (node.getKind().equals(NodeKind.BOUNDARY)) {
				return seg;
			}
		}
//        throw new IllegalArgumentException("The given list does not contain a boundary node");
		return null;
	}

	public static Segment findNext(List<Segment> segments, Segment segment) {
		if (segment.getTo().getKind().equals(NodeKind.BOUNDARY)) {
			return null;
		}
		for (final Segment seg : segments) {
			if (seg.getFrom().equals(segment.getTo())) {
				return seg;
			}
		}
		return null;
	}

	public static List<Segment> arrangeSegments(List<Segment> segments) {
		Segment cur = findLeftMost(segments);
		final List<Segment> arranged = new ArrayList<>();
		while (cur != null) {
			arranged.add(cur);
			cur = findNext(segments, cur);
		}

		return arranged;

//        if (arranged.size() == segments.size())
//            return arranged;
//        else
//            throw new IllegalArgumentException("The arranged segments list is of different size: segments are obrupt");
	}
}
