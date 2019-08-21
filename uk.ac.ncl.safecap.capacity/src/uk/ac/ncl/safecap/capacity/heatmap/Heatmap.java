package uk.ac.ncl.safecap.capacity.heatmap;

import java.util.HashMap;
import java.util.Map;

import safecap.model.Ambit;
import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public class Heatmap {
	public Map<Ambit, Integer> data;

	public Heatmap() {
		data = new HashMap<>(100);
	}

	public Heatmap(Map<Ambit, Integer> data) {
		this.data = data;
	}

	public void setHeat(Ambit ambit, int heat) {
		data.put(ambit, heat);
	}

	public int getHeat(Ambit ambit) {
		if (data.containsKey(ambit)) {
			return data.get(ambit);
		} else {
			return 0;
		}
	}

	public void addHeat(Ambit ambit, int increment) {
		setHeat(ambit, getHeat(ambit) + increment);
	}

	public void setHeat(SimulationExperiment experiment) {
		for (final ITrain train : experiment.getTrains()) {
			final TrainLine line = train.getLine();
			for (final Ambit ambit : train.getLine().getAmbitPath()) {
				final double start = line.getAmbitOffset(ambit);
				final double end = start + line.getAmbitLength(ambit);
				final double time = experiment.getIntegralTime(start, end);
				final double length = line.getAmbitLength(ambit);
				final double heat = time / length;
				// double heat = experiment.getIntegralTime(start, end);
				addHeat(ambit, (int) (heat * 10000));
				// System.out.println("Ambit " + ambit + " occupied for " + time
				// + "; heat is " + (int) (heat * 10000));
			}
		}
	}

}
