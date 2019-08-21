package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;

public class FRedLeftSignalFigure extends FLeftSignalFigure {

	public FRedLeftSignalFigure() {
	}

	@Override
	protected void outlineShape(Graphics g) {
		final Rectangle r = getBounds();
		final int x = r.x;
		final int y = r.y + r.height / 2;
		if (ZoomLevels.isOutline(zoomManager)) {
			g.setBackgroundColor(ColorConstants.red);
			g.fillOval(x + LEG, y - RAD, RAD * 2, RAD * 2);
			return;
		}

		g.drawLine(x, y, x + LEG, y);
		g.drawOval(x + LEG, y - RAD, RAD * 2, RAD * 2);
		g.drawLine(x + LEG + RAD - 1, y - RAD, x + LEG + RAD - 1, y + RAD);
		g.drawLine(x + LEG + RAD + 1, y - RAD, x + LEG + RAD + 1, y + RAD);
	}

}