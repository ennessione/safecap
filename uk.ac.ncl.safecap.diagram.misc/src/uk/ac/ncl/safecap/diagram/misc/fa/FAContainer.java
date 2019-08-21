package uk.ac.ncl.safecap.diagram.misc.fa;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class FAContainer {
	private final List<FAFigure> children;
	private Point center;
	private Rectangle bounds;

	public FAContainer() {
		children = new ArrayList<>();
	}

	public List<FAFigure> getChildren() {
		return children;
	}

	public boolean isSensitivePoint(int x, int y) {
		for (final FAFigure fig : children) {
			if (fig.isHandler()) {
				final boolean b = fig.isSensitivePoint(x, y);
				if (b) {
					return true;
				}
			}
		}

		return false;
	}

	public void add(FAFigure child) {
		children.add(child);
	}

	public void draw(Graphics g) {
		for (final FAFigure fig : children) {
			fig.draw(g);
		}
	}

	public void rotate(double angle, Rectangle bounds) {
		if (angle != 0.0d) {

			refresh();
			for (final FAFigure fig : children) {
				fig.rotate(center, angle);
			}
			refresh();
			fit(bounds);
			refresh();
		}
	}

	public void refresh() {
		refreshBounds();
		refreshCenter();
	}

	public void fit(Rectangle b) {
		final Rectangle rr = getBounds();

		if (b.width < rr.width || b.height < rr.height) {
//			int x_shift = (rr.width - b.width) / 2;
//			int y_shift = (rr.height - b.height) / 2;

			final double sh = (double) b.width / (double) rr.width;
			final double sv = (double) b.height / (double) rr.height;
			final double sf = Math.min(sh, sv);

			for (final FAFigure fig : children) {
				fig.scale(center, sf);
			}

		}

	}

	private void refreshCenter() {
		center = bounds.getCenter();
	}

	private void refreshBounds() {
		Rectangle r = null;
		for (final FAFigure fig : children) {
			final Rectangle q = fig.getBounds();
			if (r != null) {
				r = r.getUnion(q);
			} else {
				r = q;
			}
		}

		bounds = r;
	}

	public Point getCenter() {
		return center;
	}

	public Rectangle getBounds() {
		return bounds;
	}

}
