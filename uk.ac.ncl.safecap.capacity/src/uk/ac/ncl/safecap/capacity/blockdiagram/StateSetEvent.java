package uk.ac.ncl.safecap.capacity.blockdiagram;

public class StateSetEvent implements Comparable<StateSetEvent> {
	public double time;
	public int state;
	public NamedBoxFigure figure;

	public StateSetEvent(double time, int state) {
		super();
		this.time = time;
		this.state = state;
	}

	@Override
	public int compareTo(StateSetEvent o) {
		if (time < o.time) {
			return -1;
		} else if (time > o.time) {
			return 1;
		} else {
			return 0;
		}
	}
}
