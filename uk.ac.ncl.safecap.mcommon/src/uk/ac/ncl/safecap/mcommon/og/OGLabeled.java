package uk.ac.ncl.safecap.mcommon.og;

public class OGLabeled {
	private final String label;

	public OGLabeled(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return "\"" + label + "\"";
	}

	@Override
	public String toString() {
		return "\"" + label + "\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (label == null ? 0 : label.hashCode());
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
		final OGLabeled other = (OGLabeled) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

}
