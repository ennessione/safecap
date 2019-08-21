package uk.ac.ncl.safecap.misc.core;

public class Delta {
	public final static double DELTA = 0.0000001;

	public final static boolean Eql(double a, double b) {
		return Math.abs(a - b) < DELTA;
	}

	public final static boolean Eql(double a, double b, double delta) {
		return Math.abs(a - b) < delta;
	}

	public final static boolean isZero(double a) {
		return Math.abs(a - 0) < DELTA;
	}

	public final static boolean isLess(double a, double b) {
		return a < b && Math.abs(a - b) > DELTA;
	}

	public final static boolean isLess(double a, double b, double delta) {
		return a < b && Math.abs(a - b) > delta;
	}

	public final static double roundToDelta(double a) {
		final double rd = Math.rint(a);
		if (Eql(a, rd)) {
			return rd;
		} else {
			return a;
		}

	}

	public final static double round2(double v) {
		final int t = (int) (v * 100d);
		return t / 100d;
	}

	public final static double round3(double v) {
		final int t = (int) (v * 1000d);
		return t / 1000d;
	}
}
