package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import safecap.Orientation;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;

public class FRightSignalFigure extends FSignalFigure {
	public static final int ANCHOR_X = LEG + RAD * 2 + 1;
	public static final int ANCHOR_Y = RAD;
	public static final int HEIGHT = RAD * 2 + 2;

	public FRightSignalFigure() {
	}

	@Override
	protected void outlineShape(Graphics g) {
		final Rectangle r = getBounds();

		final int x = r.x + 1;
		final int y = r.y + r.height / 2 - 1;
		if (ZoomLevels.isOutline(zoomManager)) {
			g.setBackgroundColor(ColorConstants.black);
			g.fillOval(x, y - RAD, RAD * 2, RAD * 2);
			return;
		}

		g.drawLine(x + RAD * 2, y, x + RAD * 2 + LEG, y);

		drawSignalMainPart(g, x - RAD + 1, y, Orientation.RIGHT);

		g.setForegroundColor(ColorConstants.black);
	}

}