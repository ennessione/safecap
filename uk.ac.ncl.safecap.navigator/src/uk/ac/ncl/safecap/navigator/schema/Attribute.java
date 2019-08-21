package uk.ac.ncl.safecap.navigator.schema;

public class Attribute {
	private final String attribute;
	private final Object value;

	public Attribute(String attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	public String getAttribute() {
		return attribute;
	}

	public Object getValue() {
		return value;
	}
}
