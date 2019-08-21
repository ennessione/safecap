package uk.ac.ncl.safecap.diagram.misc.visual;

import org.eclipse.draw2d.geometry.Point;

public class LineEq {
	public double k;
	public double b;

	public LineEq(double x0, double y0, double x1, double y1) {
		k = (y1 - y0) / (x1 - x0);
		b = y0 - k * x0;
	}

	public LineEq(Point from, Point to) {
		this(from.x, from.y, to.x, to.y);
	}

	public double y(double x) {
		return k * x + b;
	}

	public boolean containsPoint(double x, double y, double tolerance) {
		return Math.abs(k * x + b - y) <= tolerance;
	}

}
