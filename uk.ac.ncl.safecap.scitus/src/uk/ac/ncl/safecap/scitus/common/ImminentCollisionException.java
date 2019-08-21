package uk.ac.ncl.safecap.scitus.common;

public class ImminentCollisionException extends SimulationException {
	public ImminentCollisionException(String information) {
		super("ImminentCollision", information);
	}

	private static final long serialVersionUID = 2201136352005086814L;
}
