package uk.ac.ncl.prime.sim.lang;

public class CLNonExecutableException extends CLExecutionException implements ICLLocatableException {
	private static final long serialVersionUID = 5959749696433248492L;
	private final CLElement element;

	public CLNonExecutableException(CLElement element) {
		super("Non-executable construct");
		this.element = element;
	}

	public CLNonExecutableException(CLElement element, String message) {
		super("Non-executable construct: " + message);
		this.element = element;
	}

	@Override
	public CLElement getElement() {
		return element;
	}

}
