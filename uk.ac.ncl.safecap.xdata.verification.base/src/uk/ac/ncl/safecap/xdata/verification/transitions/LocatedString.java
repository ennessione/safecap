package uk.ac.ncl.safecap.xdata.verification.transitions;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class LocatedString extends LocatedElement {
	private String value;

	public LocatedString() {

	}

	private LocatedString(String value, SourceLocation source) {
		super(source);
		this.value = value;
	}

	private LocatedString(String value) {
		this.value = value;
	}

	public static LocatedString make(CLExpression expression) {
		if (expression.getLocation() != null) {
			return new LocatedString(expression.asString(), expression.getLocation());
		} else {
			return new LocatedString(expression.asString());
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * Locates a nested located element matching a given key
	 * 
	 * @param key
	 * @return
	 */
	public LocatedElement resolveKey(String key) {
		if (key.equals(getKey())) {
			return this;
		} else if (getParts() != null) {
			for (final LocatedElement le : getParts()) {
				if (key.equals(le.getKey())) {
					return le;
				}
			}
		}
		return null;
	}
}
