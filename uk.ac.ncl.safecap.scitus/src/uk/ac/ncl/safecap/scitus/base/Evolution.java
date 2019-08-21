package uk.ac.ncl.safecap.scitus.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Evolution implements Serializable {
	private static final long serialVersionUID = 6037793003092163586L;
	protected double extent; // extent of the evolution, in time, in seconds
	protected List<Progression> progressions;

	public Evolution(double extent) {
		assert extent > 0 && !Double.isNaN(extent) && !Double.isInfinite(extent);
		this.extent = extent;
		progressions = new ArrayList<>(10);
	}

	public Evolution() {
		extent = 0d;
		progressions = new ArrayList<>(0);
	}

	public void addProgression(Progression progression) {
		progressions.add(progression);
	}

	public List<Progression> getProgressions() {
		return progressions;
	}

	public double getExtent() {
		return extent;
	}

	/* Auxiliary methods */
	public Progression getNProgression(int n) {
		return progressions.get(n);
	}

}
