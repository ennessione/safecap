package uk.ac.ncl.safecap.scitus.base;

import uk.ac.ncl.safecap.scitus.common.SimulationException;

public abstract class TrainDriver {
	protected S1TrainActor train;

	public void register(S1TrainActor train) {
		this.train = train;
	}

	public abstract double immediateAcceleration(double targetSpeed, double distance);

	public abstract double travelTime(double targetSpeed, double distance) throws SimulationException;

	public abstract Progression drive(double time) throws SimulationException;

}
