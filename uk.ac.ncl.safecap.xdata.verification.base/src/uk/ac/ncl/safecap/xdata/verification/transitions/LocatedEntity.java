package uk.ac.ncl.safecap.xdata.verification.transitions;

public class LocatedEntity extends LocatedBase {
	private String source;

	public LocatedEntity() {

	}

	public LocatedEntity(int start, int end, String source) {
		super(start, end);
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return (source == null ? "?" : source) + ":(" + start + "-" + end + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + (source == null ? 0 : source.hashCode());
		result = prime * result + start;
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
		final LocatedEntity other = (LocatedEntity) obj;
		if (end != other.end) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (start != other.start) {
			return false;
		}
		return true;
	}

}
