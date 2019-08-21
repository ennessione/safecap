package uk.ac.ncl.safecap.xdata.verification.transitions;

public class TransitionContainerInvariantInfo {
	public String name;
	public int generated;
	public int proved;
	public int goals;
	public int goals_closed;
	public int goals_stuck;
	public int time_correct;
	public int time_failed;

	public TransitionContainerInvariantInfo(String name) {
		this.name = name;
		generated = 0;
		proved = 0;
		time_correct = 0;
		time_failed = 0;
	}

}