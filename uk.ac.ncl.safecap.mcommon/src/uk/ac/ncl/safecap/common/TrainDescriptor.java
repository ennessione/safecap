package uk.ac.ncl.safecap.common;

import org.eclipse.swt.graphics.RGB;

import uk.ac.ncl.safecap.misc.core.Delta;

public class TrainDescriptor {
	protected String trainClass; // train type, e.g., freight, suburban, express
	protected String trainName; // unique train name
	protected double maxSpeed; // maximum speed, in m/s
	protected double maxAcceleration; // maximum acceleration in m/(s^2)
	protected double maxDeceleration; // maximum deceleration (braking) in
										// m/(s^2)
	protected double length; // total length, in m

	protected RGB rgb;
	protected int lineWidth = 1;
	protected boolean dashed = false;

	public TrainDescriptor(String trainClass, String trainName, double maxSpeed, double maxAcceleration, double maxDecelaration,
			double length, RGB rgb) {
		this.trainClass = trainClass;
		this.trainName = trainName;
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		maxDeceleration = maxDecelaration;
		this.length = length;
		this.rgb = rgb;
	}

	protected TrainDescriptor(String trainName, RGB rgb) {
		this.trainName = trainName;
		this.rgb = rgb;
	}

	public RGB getRgb() {
		return rgb;
	}

	public double effectiveAcceleration(double currentSpeed, double targetSpeed, double distance) {
		final double a = (targetSpeed * targetSpeed - currentSpeed * currentSpeed) / (2 * distance);
		return a;
	}

	public double getMaximumEntrySpeed(double region_length) {
		final double v = Math.sqrt(2 * maxDeceleration * region_length);
		return v;
	}

	public double getMaximumEntrySpeed(double region_length, double targetSpeed) {
		final double v = Math.sqrt(2 * maxDeceleration * region_length + targetSpeed * targetSpeed);
		if (Double.isInfinite(v)) {
			return getMaxSpeed() * 2;
		} else {
			return v;
		}
	}

	/**
	 * Returns the safe braking distance for the current train (1.a)
	 * 
	 * @param currentSpeed the current speed, m/s
	 * @return the braking distance, meters
	 */
	public double getSafeBrakingDistance(double currentSpeed) {
		return currentSpeed * currentSpeed / (2 * maxDeceleration);
	}

	/**
	 * Returns the safe braking distance to a given target speed (1.b)
	 * 
	 * @param currentSpeed the current speed, m/s
	 * @param targetSpeed  the target speed (must be lower than currentSpeed), m/s
	 * @return the braking distance, meters
	 */
	public double getSafeBrakingDistance(double currentSpeed, double targetSpeed) {
		assert targetSpeed <= currentSpeed;
		return (currentSpeed * currentSpeed - targetSpeed * targetSpeed) / (2 * maxDeceleration);
	}

	/**
	 * Returns the minimum length of track a train has to travel to accelerate to a
	 * given target speed (1.c)
	 * 
	 * @param currentSpeed the current speed, m/s
	 * @param targetSpeed  the target speed (must be higher than currentSpeed), m/s
	 * @return the acceleration distance, meters
	 */
	public double getMinimumAccelerationDistance(double currentSpeed, double targetSpeed) {
		assert currentSpeed < targetSpeed;
		return (targetSpeed * targetSpeed - currentSpeed * currentSpeed) / (2 * maxAcceleration);
	}

	/**
	 * Returns the minimum time it takes to slow down from a current speed to a
	 * lower target speed (2)
	 * 
	 * @param currentSpeed the current speed, m/s
	 * @param targetSpeed  the target speed (must be lower than currentSpeed), m/s
	 * @return deceleration time, in seconds
	 */
	public double bestDecelerationTime(double currentSpeed, double targetSpeed) {
		assert targetSpeed <= currentSpeed;
		return (currentSpeed - targetSpeed) / maxDeceleration;
	}

	/**
	 * Assuming that a train is accelerating, determine the optimal target speed T
	 * considering the safe travel distance S: (3) S - BD(V, T) = (T^2 - V^2)/(2a) T
	 * = +- sqrt(S/(1/(2a) + 1/(2d)) + V^2))
	 * 
	 * @param safeDistance the overall movement authority, meters
	 * @param currentSpeed the current speed, m/s
	 * @return the target speed, m/s
	 */
	public double optimalTargetSpeed(double safeDistance, double currentSpeed, double maxTargetSpeed) {
		final double a = maxAcceleration;
		final double d = maxDeceleration;

		final double lt = Math.sqrt(2 * a * safeDistance + currentSpeed * currentSpeed);

		if (Delta.isLess(lt, maxTargetSpeed)) {
			return lt;
		}

		final double q1 = Math.sqrt(2d * a * d * safeDistance + a * maxTargetSpeed * maxTargetSpeed + d * currentSpeed * currentSpeed);
		final double q2 = Math.sqrt(a + d);
		final double t = q1 / q2;

		if (t > maxSpeed) {
			return maxSpeed;
		} else {
			return t;
		}

	}

	/**
	 * Returns the minimum time it takes to accelerate from a current speed to a
	 * higher target speed (4)
	 * 
	 * @param currentSpeed the current speed, m/s
	 * @param targetSpeed  the target speed (must be higher than currentSpeed), m/s
	 * @return acceleration time, in seconds
	 */
	public double bestAccelerationTime(double currentSpeed, double targetSpeed) {
		assert Delta.isLess(currentSpeed, targetSpeed);
		return (targetSpeed - currentSpeed) / maxAcceleration;
	}

	/**
	 * Given current speed and overall safe distance determine the time the train
	 * may travel at the current speed before it has to start braking (5)
	 * 
	 * @param safeDistance the overall movement authority, meters
	 * @param currentSpeed the current speed, m/s
	 * @return steady travel time, in seconds
	 */
	public double steadyTravelTime(double safeDistance, double currentSpeed, double targetSpeed) {
		if (targetSpeed < currentSpeed) {
			final double s = safeDistance - getSafeBrakingDistance(currentSpeed, targetSpeed);
			return s / currentSpeed;
		} else {
			final double s = safeDistance;
			return s / currentSpeed;
		}
	}

	public double getSafeAccelerationTime(double currentSpeed, double distance) {
		final double t = (Math.sqrt(2 * getMaxAcceleration() * distance + currentSpeed * currentSpeed) - currentSpeed)
				/ getMaxAcceleration();

		return Math.abs(t);
	}

	public String getTrainClass() {
		return trainClass;
	}

	public String getTrainName() {
		return trainName;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public double getMaxDeceleration() {
		return maxDeceleration;
	}

	public double getLength() {
		return length;
	}

	@Override
	public String toString() {
		return trainClass + "." + trainName;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public boolean isDashed() {
		return dashed;
	}

	public void setDashed(boolean dashed) {
		this.dashed = dashed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (trainClass == null ? 0 : trainClass.hashCode());
		result = prime * result + (trainName == null ? 0 : trainName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TrainDescriptor other = (TrainDescriptor) obj;
		if (trainClass == null) {
			if (other.trainClass != null) {
				return false;
			}
		} else if (!trainClass.equals(other.trainClass)) {
			return false;
		}
		if (trainName == null) {
			if (other.trainName != null) {
				return false;
			}
		} else if (!trainName.equals(other.trainName)) {
			return false;
		}
		return true;
	}
}
