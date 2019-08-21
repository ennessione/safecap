package uk.ac.ncl.safecap.scitus.common;

public class SimulationRuntimeException extends SimulationException {
	public SimulationRuntimeException(String information) {
		super("RuntimeException", information);
	}

	private static final long serialVersionUID = 2201136352005086814L;
}
