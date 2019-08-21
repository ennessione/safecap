package uk.ac.ncl.safecap.xdata.verification.transitions;

public class TransitionContainerTransformInfo {
	public String name;
	public int hits;
	public long time;
	public int goals;

	public TransitionContainerTransformInfo(String name) {
		this.name = name;
		hits = 0;
		time = 0;
		goals = 0;
	}

}