package uk.ac.ncl.safecap.gui.trainconfig;

public class TrainType {
	private String name = "";
	private int speed = 40, length = 200;
	private float acceleration = 0.5f, deceleration = 0.4f;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(float deceleration) {
		this.deceleration = deceleration;
	}
}
