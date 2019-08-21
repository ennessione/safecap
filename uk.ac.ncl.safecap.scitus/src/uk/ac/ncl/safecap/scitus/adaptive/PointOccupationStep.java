package uk.ac.ncl.safecap.scitus.adaptive;

import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public class PointOccupationStep extends PointLockingStep {
	private final double entrySpeed;
	private final double exitSpeed;
	private final double entryAcc;
	private final double exitAcc;

	public PointOccupationStep(S1TrainActor train, double start, double duration, double entrySpeed, double exitSpeed, double entryAcc,
			double exitAcc) {
		super(train, start, duration);
		this.entrySpeed = entrySpeed;
		this.exitSpeed = exitSpeed;
		this.entryAcc = entryAcc;
		this.exitAcc = exitAcc;
	}

	public double getEntrySpeed() {
		return entrySpeed;
	}

	public double getExitSpeed() {
		return exitSpeed;
	}

	public double getEntryAcc() {
		return entryAcc;
	}

	public double getExitAcc() {
		return exitAcc;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + Delta.round2(entrySpeed) + "/" + Delta.round2(entryAcc) + " - " + Delta.round2(exitSpeed) + "/"
				+ Delta.round2(entryAcc) + "]";
	}

}
