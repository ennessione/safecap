package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public class LineBasedAggregatingSafetyProfile<T extends ISafetyProfile> extends AggregatingSafetyProfile<T> {
	private final TrainLine line;

	public LineBasedAggregatingSafetyProfile(TrainLine line) {
		this.line = line;
	}

	@Override
	public double getSafeSpeedAt(ITrain train, double position, int kind, boolean probe) {
		if (train.getLine().getSchemaLine() == line.getSchemaLine()) {
			return super.getSafeSpeedAt(train, position, kind, probe);
		} else {
			return Double.MAX_VALUE;
		}
	}

}
