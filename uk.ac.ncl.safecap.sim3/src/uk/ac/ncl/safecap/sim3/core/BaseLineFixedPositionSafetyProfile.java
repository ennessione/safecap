package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public abstract class BaseLineFixedPositionSafetyProfile extends BaseFixedPositionSafetyProfile {
	private final TrainLine line;

	public BaseLineFixedPositionSafetyProfile(TrainLine line, double position) {
		super(position);
		this.line = line;
	}

	public TrainLine getLine() {
		return line;
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
