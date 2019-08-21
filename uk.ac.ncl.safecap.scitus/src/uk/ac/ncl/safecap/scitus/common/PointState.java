package uk.ac.ncl.safecap.scitus.common;

public enum PointState {
	NORMAL, REVERSE;

	@Override
	public String toString() {
		switch (this) {
		case NORMAL:
			return "N";
		case REVERSE:
			return "R";
		}
		return null;
	}

	public PointState next() {
		switch (this) {
		case NORMAL:
			return REVERSE;
		case REVERSE:
			return NORMAL;
		}
		return null;
	}
}
