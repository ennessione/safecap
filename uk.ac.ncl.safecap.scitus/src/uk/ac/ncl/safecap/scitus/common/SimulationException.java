package uk.ac.ncl.safecap.scitus.common;

public abstract class SimulationException extends Throwable {
	private static final long serialVersionUID = 593183692393682500L;
	protected String information;

	public SimulationException(String kind, String information) {
		super(kind);
		this.information = information;
	}

	public String getInformation() {
		return information;
	}

	@Override
	public String toString() {
		return super.getMessage() + "::" + getInformation();
	}
}
