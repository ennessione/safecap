package uk.ac.ncl.safecap.xmldata;

public class DataConfigurationException extends Exception {
	private static final long serialVersionUID = -3860341949712374239L;

	private TypeCastingAbort reason;

	public DataConfigurationException(String message) {
		super(message);
	}

	public DataConfigurationException(TypeCastingAbort reason) {
		super(reason.toString());
		this.reason = reason;
	}

	public TypeCastingAbort getReason() {
		return reason;
	}
}
