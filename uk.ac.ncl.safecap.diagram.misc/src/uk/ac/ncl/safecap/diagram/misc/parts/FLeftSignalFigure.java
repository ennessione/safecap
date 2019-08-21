package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import safecap.Orientation;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;

public class FLeftSignalFigure extends FSignalFigure {
	public static final int ANCHOR_X = 0;
	public static final int ANCHOR_Y = RAD + 1;

	public FLeftSignalFigure() {
	}

	@Override
	protected void outlineShape(Graphics g) {
		// super.outlineShape(g);
		final Rectangle r = getBounds();
		final int x = r.x;
		final int y = r.y + r.height / 2;

		if (ZoomLevels.isOutline(zoomManager)) {
			g.setBackgroundColor(ColorConstants.black);
			g.fillOval(x + LEG, y - RAD, RAD * 2, RAD * 2);
			return;
		}

		g.drawLine(x, y, x + LEG, y);

		drawSignalMainPart(g, x, y, Orientation.LEFT);

		g.setForegroundColor(ColorConstants.black);
	}

}