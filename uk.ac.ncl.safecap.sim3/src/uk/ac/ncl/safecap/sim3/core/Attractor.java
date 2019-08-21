package uk.ac.ncl.safecap.sim3.core;

public class Attractor {
	private final double initialPosition;
	private double zeroLockSpeed;
	private boolean zeroLock = false;

	public Attractor(double initialPosition) {
		this.initialPosition = initialPosition;
	}

	public double getInitialPosition() {
		return initialPosition;
	}

	public boolean isZeroLock() {
		return zeroLock;
	}

	public void setZeroLock(double zeroLockSpeed) {
		zeroLock = true;
		this.zeroLockSpeed = zeroLockSpeed;
	}

	public double getZeroLockSpeed() {
		return zeroLockSpeed;
	}
}
