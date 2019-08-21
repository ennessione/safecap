package uk.ac.ncl.safecap.diagram.misc.fa;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;

public class FAUtils {
	public static void rotate(PointList pl, Point mid, double angle) {
		final Transform x = new Transform();
		x.setRotation(angle);

		for (int i = 0; i < pl.size(); i++) {
			Point p = pl.getPoint(i);

			p = p.getTranslated(-mid.x, -mid.y);
			p = x.getTransformed(p);
			p = p.getTranslated(mid.x, mid.y);

			pl.setPoint(p, i);
		}
	}

	public static void fit(PointList pl, Rectangle b) {
		final Rectangle rr = pl.getBounds();

		if (b.width < rr.width || b.height < rr.height) {
//			int x_shift = (rr.width - b.width) / 2;
//			int y_shift = (rr.height - b.height) / 2;
			final Point c = rr.getCenter();

			final double sh = (double) b.width / (double) rr.width;
			final double sv = (double) b.height / (double) rr.height;
			pl.performTranslate(-c.x, -c.y);
			pl.performScale(Math.min(sh, sv));
			pl.performTranslate(c.x, c.y);
		}

	}

}
