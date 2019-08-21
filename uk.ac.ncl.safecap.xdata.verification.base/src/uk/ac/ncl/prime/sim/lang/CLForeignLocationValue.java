package uk.ac.ncl.prime.sim.lang;

import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class CLForeignLocationValue {
	private Object value;
	private SourceLocation location;

	public CLForeignLocationValue(Object value, SourceLocation location) {
		super();
		this.value = value;
		this.location = location;
	}

	public Object getValue() {
		return value;
	}

	public SourceLocation getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CLForeignLocationValue other = (CLForeignLocationValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	
	
}
