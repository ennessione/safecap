package uk.ac.ncl.safecap.misc.core;

import safecap.trackside.Signal;

public class Overlap {
	private final Signal signal;
	private final String name;
	private int length;
	private int kind = -1;

	public Overlap(Signal signal, String name) {
		this.signal = signal;
		this.name = name;
		final Integer len = ExtensionUtil.getInt(signal, SafecapConstants.EXT_OVERLAP_LENGTH, name);
		if (len != null) {
			length = len;
		}

		final Integer _kind = ExtensionUtil.getInt(signal, SafecapConstants.EXT_OVERLAP_KIND, name);
		if (_kind != null) {
			kind = _kind;
		}
	}

	public boolean isByLength() {
		return kind == -1;
	}

	public boolean isReduced() {
		return kind == 0;
	}

	public boolean isFull() {
		return kind == 1;
	}

	public int getKind() {
		return kind;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Signal getSignal() {
		return signal;
	}

	public String getName() {
		return name;
	}

	public String describe() {
		if (kind != -1) {
			return "From " + signal.getLabel() + ", " + (kind == 1 ? "full" : "reduced");
		} else {
			return "From " + signal.getLabel() + ", " + length + "m";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + (name == null ? 0 : name.hashCode());
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
		final Overlap other = (Overlap) obj;
		if (length != other.length) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
