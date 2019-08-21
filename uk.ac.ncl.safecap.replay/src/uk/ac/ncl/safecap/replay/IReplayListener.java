package uk.ac.ncl.safecap.replay;

import uk.ac.ncl.safecap.capacity.experiment.SimulationExperiment;

public interface IReplayListener {
	void onReplayPositionChanged(double time, SimulationExperiment experiment);
}
