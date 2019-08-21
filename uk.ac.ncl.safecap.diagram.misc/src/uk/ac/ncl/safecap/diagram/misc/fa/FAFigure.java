package uk.ac.ncl.safecap.diagram.misc.fa;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public abstract class FAFigure {
	private boolean handler;
	private boolean scalable = false;

	public FAFigure(boolean handler) {
		this.handler = handler;
	}

	public abstract void rotate(Point mid, double angle);

	public abstract Rectangle getBounds();

	public boolean isScalable() {
		return scalable;
	}

	public void setScalable(boolean scalable) {
		this.scalable = scalable;
	}

	public boolean isHandler() {
		return handler;
	}

	public void setHandler(boolean handler) {
		this.handler = handler;
	}

	public abstract void draw(Graphics g);

	public abstract boolean isSensitivePoint(int x, int y);

	public abstract void scale(Point center, double sf);
}
