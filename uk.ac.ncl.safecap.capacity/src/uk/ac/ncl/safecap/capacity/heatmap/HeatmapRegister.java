package uk.ac.ncl.safecap.capacity.heatmap;

import java.util.HashMap;
import java.util.Map;

import safecap.Project;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;

public class HeatmapRegister {
	private static Map<Project, Heatmap> register;

	static {
		register = new HashMap<>(25);
	}

	public static void addHeatmap(Project editor, Heatmap value) {
		register.put(editor, value);
	}

	public static void addHeatmap(Project editor, SimulationExperiment experiment) {
		final Heatmap map = new Heatmap();
		map.setHeat(experiment);
		register.put(editor, map);
	}

	public static Heatmap getHeatmap(Project editor) {
		return register.get(editor);
	}

}
