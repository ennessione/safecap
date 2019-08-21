package uk.ac.ncl.safecap.capacity.heatmap;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import safecap.Project;
import safecap.model.Ambit;

public class RandomHeatmapGenerator {
	public static Map<Ambit, Integer> generate(Project project, int min, int max) {
		final Map<Ambit, Integer> map = new Hashtable<>();
		final Random rnd = new Random();
		final List<Ambit> ambits = project.getAmbits();
		for (final Ambit ambit : ambits) {
			final int rand = rnd.nextInt(max - min) + min;
			map.put(ambit, rand);
		}
		return map;
	}
}
