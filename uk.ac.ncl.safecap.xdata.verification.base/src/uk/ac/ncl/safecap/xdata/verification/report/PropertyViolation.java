package uk.ac.ncl.safecap.xdata.verification.report;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class PropertyViolation implements Serializable {
	private static final long serialVersionUID = -488403965444992808L;
	private transient Collection<Object> elements;
	private final String index;
	private final String location;
	private String justification;

	public PropertyViolation(Collection<Object> elements, String index, String location) {
		this.elements = new HashSet<>(elements);
		this.index = index;
		this.location = location;
	}

	public Collection<Object> getElements() {
		return elements;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getIndex() {
		return index;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return index + ":" + elements.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (index == null ? 0 : index.hashCode());
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
		final PropertyViolation other = (PropertyViolation) obj;
		if (index == null) {
			if (other.index != null) {
				return false;
			}
		} else if (!index.equals(other.index)) {
			return false;
		}
		return true;
	}

}
