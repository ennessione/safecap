package uk.ac.ncl.safecap.capacity.heatmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import safecap.SafecapFactory;
import safecap.Style;
import safecap.model.Ambit;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.diagram.misc.highlights.HLMap;
import uk.ac.ncl.safecap.diagram.misc.highlights.Highlight;

public class HeatmapHighlighter {
	public static List<Style> heatmapStyles = new ArrayList<>();

	static {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final Color[] colors = { new Color(display, 90, 90, 200), new Color(display, 90, 90, 150),
				new Color(display, 90, 90, 120), new Color(display, 90, 90, 100), new Color(display, 120, 90, 90),

				new Color(display, 140, 90, 90), new Color(display, 160, 90, 90), new Color(display, 180, 90, 90),
				new Color(display, 200, 90, 90), new Color(display, 220, 90, 90), new Color(display, 250, 90, 90) };
		for (int i = 0; i < colors.length; i++) {
			final Style style = SafecapFactory.eINSTANCE.createStyle();
			style.setForeground(colors[i]);
			heatmapStyles.add(style);
		}
	}

	public static HLMap generate(Map<Ambit, Integer> heatmap) {
		final HLMap map = new HLMap();
		final int steps = heatmapStyles.size();
		int min = 100000, max = 0;
		for (final Integer val : heatmap.values()) {
			final int val1 = val.intValue();
			if (val1 < min) {
				min = val1;
			}
			if (val1 > max) {
				max = val1;
			}
		}
		final float heatPerStep = (max - min) / (float) steps;
		for (final Ambit ambit : heatmap.keySet()) {
			final float heat = heatmap.get(ambit);
			final float step = (heat - min) / heatPerStep;
			int stepInt = (int) Math.floor(step);
			if (stepInt >= steps) {
				stepInt = steps - 1;
			}
			final List<Segment> segments = ambit.getSegments();
			for (final Segment segment : segments) {
				map.add(segment, heatmapStyles.get(stepInt));
			}
		}

		return map;
	}

	public static void highlightHeatmap(Map<Ambit, Integer> heatmap) {
		final HLMap map = generate(heatmap);
		Highlight.highlight(map);
	}
}
