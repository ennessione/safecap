package uk.ac.ncl.prime.sim.lang;

public class CLTerminationException extends Exception {
	private static final long serialVersionUID = -7144267396330413111L;

	private boolean stop = false;
	private final Object value;

	public CLTerminationException() {
		super();
		stop = true;
		value = null;
	}

	public CLTerminationException(Object value) {
		super();
		stop = false;
		this.value = value;
	}

	public boolean isStop() {
		return stop;
	}

	public Object getValue() {
		return value;
	}

}
