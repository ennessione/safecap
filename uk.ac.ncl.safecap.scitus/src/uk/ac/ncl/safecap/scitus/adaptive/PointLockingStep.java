package uk.ac.ncl.safecap.scitus.adaptive;

import safecap.model.Line;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

/**
 * A record of locking a point for a given line
 */
public class PointLockingStep {
	private final S1TrainActor train;
	protected double start;
	protected double duration;

	public PointLockingStep(S1TrainActor train, double start, double duration) {
		this.train = train;
		this.duration = duration;
		this.start = start;
	}

	public Line getLine() {
		return train.getLine().getSchemaLine();
	}

	public S1TrainActor getTrain() {
		return train;
	}

	public double getDuration() {
		return duration;
	}

	public double getStart() {
		return start;
	}

	@Override
	public String toString() {
		return getLine().getLabel() + "@(" + Delta.round2(start) + " - " + Delta.round2(start + duration) + ")";
	}

}
