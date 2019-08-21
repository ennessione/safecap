package uk.ac.ncl.prime.sim.lang;

import uk.ac.ncl.prime.sim.parser.SourceLocation;

public abstract class CLElement extends CLBase {
	private SourceLocation location;
	private final int tag;

	public CLElement(int tag) {
		this.tag = tag;
	}

	protected CLElement(int tag, SourceLocation location) {
		this.tag = tag;
		this.location = location;
	}

	/**
	 * Syntactic element tag
	 */
	final public int getTag() {
		return tag;
	}

	/**
	 * Element position in the source text
	 */
	public final SourceLocation getLocation() {
		return location;
	}

	/**
	 * Sets element source position. It is not advisable to use this method
	 */
	public final void setLocation(SourceLocation location) {
		this.location = location;
	}

	@Override
	public String toString() {
		if (this instanceof CLExpression) {
			final CLExpression st = (CLExpression) this;
			return st.asString();
		} else {
			return super.toString();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (location == null ? 0 : location.hashCode());
		result = prime * result + tag;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CLElement other = (CLElement) obj;
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (tag != other.tag) {
			return false;
		}
		return true;
	}

}
