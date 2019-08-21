package uk.ac.ncl.safecap.sim3.core;

public abstract class BaseFixedPositionSafetyProfile extends BaseSafetyProfile implements Comparable<BaseFixedPositionSafetyProfile> {
	private final double position;

	public BaseFixedPositionSafetyProfile(double position) {
		super();
		this.position = position;
	}

	public double getPosition() {
		return position;
	}

	@Override
	public int compareTo(BaseFixedPositionSafetyProfile o) {
		return (int) Math.signum(position - o.position);
	}

}
