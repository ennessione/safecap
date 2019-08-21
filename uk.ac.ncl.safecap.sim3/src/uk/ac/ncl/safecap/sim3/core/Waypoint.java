package uk.ac.ncl.safecap.sim3.core;

public class Waypoint {
	private final double position;
	private final double speed;
	private final Object source;

	public Waypoint(double position, double speed, Object source) {
		this.position = position;
		this.speed = speed;
		this.source = source;
	}

	public double getPosition() {
		return position;
	}

	public double getSpeed() {
		return speed;
	}

	public Object getSource() {
		return source;
	}

	@Override
	public String toString() {
		return speed + "@" + position;
	}
}
