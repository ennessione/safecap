package uk.ac.ncl.safecap.xdata.verification.transitions;

public class LocatedBase implements Comparable<LocatedBase> {
	protected int start;
	protected int end;

	public LocatedBase() {
	}

	public LocatedBase(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public int compareTo(LocatedBase o) {
		return start - o.start;
	}

}
