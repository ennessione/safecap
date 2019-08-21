package uk.ac.ncl.safecap.xdata.verification.errorinjection;

public class EIRecord {
	private final String property;
	private final String function;
	private final String kind;
	private final int result;

	public EIRecord(String property, String function, String kind, boolean result) {
		this.property = property;
		this.function = function;
		this.kind = kind;
		this.result = result ? 1 : 0;
	}

	public String getFunction() {
		return function;
	}

	public String getProperty() {
		return property;
	}

	public int getResult() {
		return result;
	}

	public String getKind() {
		return kind;
	}

}
