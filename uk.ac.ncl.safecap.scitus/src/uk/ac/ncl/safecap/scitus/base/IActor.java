package uk.ac.ncl.safecap.scitus.base;

import uk.ac.ncl.safecap.scitus.common.SimulationException;

public interface IActor {
	double eventHorizon() throws SimulationException;

	Progression progress(double horizon) throws SimulationException;
}
