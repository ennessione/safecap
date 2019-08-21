package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

public final class SpeedBound {
	private final double bound;
	private final String reason;
	private double arrival_time;

	public static SpeedBound UNBOUNDED = new SpeedBound(Double.POSITIVE_INFINITY, "unbounded");
	public static SpeedBound FULL_STOP = new SpeedBound(0, "full stop");

	public SpeedBound(double bound, String reason) {
		this.bound = bound;
		this.reason = reason;
	}

	public double getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(double arrival_time) {
		this.arrival_time = arrival_time;
	}

	public double getBound() {
		return bound;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public String toString() {
		return "[" + getBound() + "] => " + getReason();
	}
}
