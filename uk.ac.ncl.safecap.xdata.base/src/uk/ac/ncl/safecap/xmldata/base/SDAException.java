package uk.ac.ncl.safecap.xmldata.base;

public abstract class SDAException extends Exception {
	private static final long serialVersionUID = 8022335289693301055L;

	public SDAException(String message) {
		super(message);
	}
}
