package uk.ac.ncl.safecap.scitus.types;

public class UTrainState {
	private double position;
	private double acceleration;
	private double velocity;

	public UTrainState() {

	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
		if (Double.isNaN(velocity)) {
			System.out.println("Invalid velocity");
		}
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
		if (Double.isNaN(velocity)) {
			System.out.println("Invalid velocity");
		}
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
		if (Double.isNaN(velocity) || velocity < 0) {
			System.out.println("Invalid velocity");
		}
	}

}
