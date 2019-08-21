package uk.ac.ncl.safecap.diagram.misc.highlights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import safecap.Project;
import safecap.SafecapFactory;
import safecap.Style;
import safecap.VisualMarker;
import safecap.model.Ambit;
import safecap.model.Line;
import safecap.model.Route;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;

public class Highlight {

	public static void reset() {
		final HLMap map = new HLMap();
		highlight(map);
	}

	public static void reset(Project project) {
		final HLMap map = new HLMap();
		final List<Segment> segments = project.getSegments();
		for (final Segment segment : segments) {
			if (SegmentUtil.isJunctionPart(segment)) {
				map.add(segment, HLMap.originalJunction);
			} else {
				map.add(segment, HLMap.originalBase);
			}
		}
		highlight(map);
	}

	public static void highlight(HLMap map) {
		HighlightSegments.highlight(map.getMap());
	}

	public static void placeMarker(Segment s, String owner, String text, String offsetLabel, double position, Style style) {
		final VisualMarker marker = SafecapFactory.eINSTANCE.createVisualMarker();
		marker.setLabel(text);
		marker.setPosition(position);
		marker.setOwner(owner);
		marker.setOffsetLabel(offsetLabel);

		if (style != null) {
			marker.setStyle(style);
		}

		s.getMarkers().add(marker);
	}

	public static void placeMarker(Segment s, String owner, String text, double position) {
		placeMarker(s, owner, text, null, position, null);
	}

	public static void removeMarkers(Segment s, String owner) {
		final Collection<VisualMarker> vm = new ArrayList<>();
		for (final VisualMarker m : s.getMarkers()) {
			if (m.getOwner() == null || m.getOwner().equals(owner)) {
				vm.add(m);
			}
		}
		s.getMarkers().removeAll(vm);
	}

	public static void removeMarkers(Project root, String owner) {
		for (final Segment s : root.getSegments()) {
			removeMarkers(s, owner);
		}
	}

	public static void segment(Segment segment, Style style) {
		final HLMap map = new HLMap();
		map.add(segment, style);
		highlight(map);
	}

	public static void segment(Segment segment) {
		segment(segment, HLMap.dflt);
	}

	public static void segments(List<Segment> segments, Style style) {
		final HLMap map = new HLMap();
		map.add(segments, style);
		highlight(map);
	}

	public static void segments(List<Segment> segments) {
		segments(segments, HLMap.dflt);
	}

	public static void ambit(Ambit ambit, Style style) {
		segments(ambit.getSegments(), style);
	}

	public static void ambit(Ambit ambit) {
		segments(ambit.getSegments(), HLMap.dflt);
	}

	private static void routeResMap(Route route, HLMap map, boolean overlaps) {
		final List<Segment> reservation_segments = new ArrayList<>();

		for (final Ambit a : route.getAmbits()) {
			reservation_segments.addAll(a.getSegments());
		}

		reservation_segments.removeAll(route.getSegments());

		map.add(reservation_segments, HLMap.reservation);

	}

	private static void routePathMap(Route route, HLMap map, boolean overlaps) {
		final List<Segment> reservation_segments = new ArrayList<>();

		for (final Ambit a : route.getAmbits()) {
			reservation_segments.addAll(a.getSegments());
		}

		reservation_segments.removeAll(route.getSegments());

		map.add(route.getSegments(), HLMap.path);
	}

	public static void route(Route route) {
		final HLMap map = new HLMap();
		routeResMap(route, map, true);
		routePathMap(route, map, true);
		highlight(map);
	}

	public static void line(Line line) {
		final HLMap map = new HLMap();

		for (final Route r : line.getRoutes()) {
			routeResMap(r, map, false);
		}

		for (final Route r : line.getRoutes()) {
			routePathMap(r, map, false);
		}

		highlight(map);
	}

}
