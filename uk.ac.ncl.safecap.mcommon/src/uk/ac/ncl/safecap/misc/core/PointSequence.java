package uk.ac.ncl.safecap.misc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import safecap.model.Line;

public class PointSequence implements Serializable {
	private static final long serialVersionUID = -5634406977752605322L;
	private final List<Line> lineSeq;
	private int state = 0;

	public PointSequence(List<Line> lineSeq) {
		this.lineSeq = lineSeq;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public PointSequence copy() {
		final List<Line> c = new ArrayList<>(lineSeq.size());

		for (final Line l : lineSeq) {
			c.add(l);
		}

		final PointSequence ps = new PointSequence(c);
		ps.setState(state);
		return ps;
	}

	public Line getCurrent(Line expected) {
		if (state < lineSeq.size()) {
			return lineSeq.get(state);
		} else {
			return expected;
		}
	}

	public void advance() {
		state = state + 1;
	}

	public List<Line> getLineSeq() {
		return lineSeq;
	}

	@Override
	public String toString() {
		return state + ":" + lineSeq.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PointSequence) {
			final PointSequence ps = (PointSequence) obj;
			return lineSeq.equals(ps.lineSeq);
		}

		return false;
	}

}
