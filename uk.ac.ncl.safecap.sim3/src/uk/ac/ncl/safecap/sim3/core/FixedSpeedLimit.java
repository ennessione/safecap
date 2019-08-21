package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public class FixedSpeedLimit extends BaseFixedPositionSafetyProfile {
	private final Speedpoint speedpoint;
	private double limitPosition;

	public FixedSpeedLimit(double position, double speed) {
		super(position);
		speedpoint = new Speedpoint(position, speed);
		limitPosition = position;
	}

	@Override
	public Speedpoint getSpeedpoint(ITrain train, double position, int kind) {
		if (train.getHead() > limitPosition) {
			return null;
		} else {
			return speedpoint;
		}
	}

	public void setLimitPosition(double limitPosition) {
		this.limitPosition = limitPosition;
	}

	@Override
	public String toString() {
		return "" + speedpoint.getSpeed() + " at (" + speedpoint.getPosition() + ", " + limitPosition + ")";
	}

	@Override
	public int getKind() {
		return ISafetyProfile.LINE;
	}

}
