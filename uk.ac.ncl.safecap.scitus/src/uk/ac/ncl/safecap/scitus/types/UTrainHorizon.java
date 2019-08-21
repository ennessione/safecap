package uk.ac.ncl.safecap.scitus.types;

public class UTrainHorizon {
	public static final int MODE_UNDEFINED = 0;
	public static final int MODE_ACCELERATION_MAX = 1;
	public static final int MODE_CRUISE_MAX = 2;
	public static final int MODE_DECELERATION_MAX = 3;

	private double time_horizon;
	private int mode;

	public UTrainHorizon() {
	}

	public double getTimeHorizon() {
		return time_horizon;
	}

	public void set(int mode, double time_horizon) {
		this.mode = mode;
		this.time_horizon = time_horizon;
		if (Double.isNaN(time_horizon) || time_horizon < 0 || time_horizon == 0.0) {
			System.out.println("Invalid horizon");
		}
	}

	public int getMode() {
		return mode;
	}

	public void setTimeHorizon(double time_horizon) {
		this.time_horizon = time_horizon;
		if (Double.isNaN(time_horizon)) {
			System.out.println("Invalid horizon");
		}
	}

	@Override
	public String toString() {
		switch (getMode()) {
		case MODE_ACCELERATION_MAX:
			return "acceleration:" + time_horizon;
		case UTrainHorizon.MODE_CRUISE_MAX:
			return "cruise:" + time_horizon;
		case UTrainHorizon.MODE_DECELERATION_MAX:
			return "deceleration:" + time_horizon;
		default:
			return "?";
		}
	}
}
