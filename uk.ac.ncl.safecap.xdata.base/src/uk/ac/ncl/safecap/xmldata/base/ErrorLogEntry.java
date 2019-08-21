package uk.ac.ncl.safecap.xmldata.base;

import java.util.Date;

public class ErrorLogEntry {
	public static enum SEVERITY {
		CRITICAL, WARNING, INFO
	}

	private final SEVERITY severity;
	private final Date timestamp;
	private final String description;
	private Throwable exception;
	private StackTraceElement[] stackTrace;

	public ErrorLogEntry(SEVERITY severity, String descripton) {
		this.severity = severity;
		timestamp = new Date();
		description = descripton;
	}

	public ErrorLogEntry(SEVERITY severity, Throwable exception) {
		this.severity = severity;
		timestamp = new Date();
		description = exception.getMessage();
		this.exception = exception;
		stackTrace = exception.getStackTrace();
	}

	public String getDescription() {
		return description;
	}

	public Throwable getException() {
		return exception;
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public SEVERITY getSeverity() {
		return severity;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getDescripton() {
		return description;
	}

	@Override
	public String toString() {
		return "[" + severity.name() + ", " + timestamp + "] " + ": " + description;

	}

}
