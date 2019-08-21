package uk.ac.ncl.safecap.diagram.misc.highlights;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;

import safecap.SafecapFactory;
import safecap.Style;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackColours;

public class HLMap {
	public static Style originalBase = SafecapFactory.eINSTANCE.createStyle();
	public static Style originalJunction = SafecapFactory.eINSTANCE.createStyle();
	public static Style dflt = SafecapFactory.eINSTANCE.createStyle();
	public static Style path = SafecapFactory.eINSTANCE.createStyle();
	public static Style reservation = SafecapFactory.eINSTANCE.createStyle();
	public static Style overlap = SafecapFactory.eINSTANCE.createStyle();

	static {
		originalBase.setForeground(TrackColours.SegmentBase);
		originalJunction.setForeground(TrackColours.SegmentJunction);
		dflt.setForeground(ColorConstants.lightGray);
		path.setForeground(ColorConstants.lightBlue);
		reservation.setForeground(ColorConstants.darkGreen);
		overlap.setForeground(ColorConstants.green);
	}

	private final Map<Segment, Style> map;

	public HLMap() {
		map = new HashMap<>();
	}

	public void reset() {
		map.clear();
	}

	public void reset(Segment segment) {
		map.remove(segment);
	}

	public void add(Segment segment, Style style) {
		map.put(segment, style);
	}

	public void add(List<Segment> segment, Style style) {
		for (final Segment seg : segment) {
			map.put(seg, style);
		}
	}

	public Map<Segment, Style> getMap() {
		return map;
	}
}
