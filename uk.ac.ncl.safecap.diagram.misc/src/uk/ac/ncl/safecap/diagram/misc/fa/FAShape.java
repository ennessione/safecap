package uk.ac.ncl.safecap.diagram.misc.fa;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

public class FAShape extends FAFigure {
	private final PointList shape;

	public FAShape(PointList shape, boolean handler, boolean scalable) {
		super(handler);
		this.shape = shape;
		super.setScalable(scalable);
	}

	public FAShape(Rectangle r, boolean handler, boolean scalable) {
		super(handler);
		shape = PointListUtilities.createPointsFromRect(r);
		super.setScalable(scalable);
	}

	public PointList getShape() {
		return shape;
	}

	@Override
	public void rotate(Point mid, double angle) {
		FAUtils.rotate(shape, mid, angle);
	}

	@Override
	public void draw(Graphics g) {
		g.fillPolygon(shape);
	}

	@Override
	public Rectangle getBounds() {
		return shape.getBounds();
	}

	@Override
	public boolean isSensitivePoint(int x, int y) {
		return shape.polygonContainsPoint(x, y);
	}

	@Override
	public void scale(Point c, double sf) {

		if (super.isScalable()) {
			shape.performTranslate(-c.x, -c.y);
			shape.performScale(sf);
			shape.performTranslate(c.x, c.y);
		} else {
			final Rectangle q = getBounds();
			final Point r = q.getCenter();
			final int dx = (int) (r.x - sf * r.x + sf * c.x - c.x);
			final int dy = (int) (r.y - sf * r.y + sf * c.y - c.y);
			shape.performTranslate(-dx, -dy);
		}
	}
}
