package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;

public class FRedRightSignalFigure extends FSignalFigure {
	public static final int ANCHOR_X = LEG + RAD * 2 + 1;
	public static final int ANCHOR_Y = RAD;

	public FRedRightSignalFigure() {
	}

	@Override
	protected void outlineShape(Graphics g) {
		final Rectangle r = getBounds();
		final int x = r.x + 1;
		final int y = r.y + r.height / 2;

		if (ZoomLevels.isOutline(zoomManager)) {
			g.setBackgroundColor(ColorConstants.red);
			g.fillOval(x, y - RAD, RAD * 2, RAD * 2);
			return;
		}

		g.drawLine(x + RAD * 2, y, x + LEG + RAD * 2, y);
		g.drawOval(x, y - RAD, RAD * 2, RAD * 2);
		g.drawLine(x + RAD - 1, y - RAD, x + RAD - 1, y + RAD);
		g.drawLine(x + RAD + 1, y - RAD, x + RAD + 1, y + RAD);
	}

}