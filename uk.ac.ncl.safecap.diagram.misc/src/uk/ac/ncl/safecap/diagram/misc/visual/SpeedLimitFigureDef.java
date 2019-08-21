package uk.ac.ncl.safecap.diagram.misc.visual;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class SpeedLimitFigureDef extends Shape {
	private RGB color;
	private int speed_limit;
	private boolean left = true;
	private static final int STEP = 10;

	/**
	 * Creates a RectangleFigure.
	 */
	public SpeedLimitFigureDef() {
	}

	public void setLeft() {
		left = true;
	}

	public void setRight() {
		left = false;
	}

	public void setColor(RGB color) {
		this.color = color;
	}

	public void setSpeedLimit(int speed_limit) {
		this.speed_limit = speed_limit;
	}

	/**
	 * @see Shape#fillShape(Graphics)
	 */
	@Override
	protected void fillShape(Graphics g) {

	}

	/**
	 * @see Shape#outlineShape(Graphics)
	 */
	@Override
	protected void outlineShape(Graphics g) {
		int x = getLocation().x;
		final int y = getLocation().y;

		g.setFont(TrackFonts.FONT_SMALL_BOLD);
		final String text = "" + speed_limit;

		final Dimension ext = FigureUtilities.getStringExtents(text, TrackFonts.FONT_SMALL_BOLD);

		int diam = ext.height + 4;
		if (ext.width > ext.height) {
			diam = ext.width + 4;
		}

		g.setForegroundColor(ColorConstants.black);
		g.setLineWidthFloat(2.0f);
		if (left) {
			g.drawLine(x, y + diam / 2, x + STEP + 1, y + diam / 2);
			x += STEP;
		} else {
			final int x_offset = 30 - diam - STEP;
			x += x_offset;
			g.drawLine(x + diam - 1, y + diam / 2, x + diam + STEP, y + diam / 2);
		}
		g.setLineWidthFloat(0.3f);

		g.setForegroundColor(ColorConstants.white);

		if (color != null) {
			final Color c = new Color(null, color);

			if (color.green * 3 + color.red + color.blue >= 700) {
				g.setForegroundColor(ColorConstants.black);
//				g.setBackgroundColor(ColorConstants.black);
//				g.fillOval(x, y, diam+2, diam+2);
//				x++;
//				y++;
			}

			g.setBackgroundColor(c);
			g.fillOval(x, y, diam, diam);

		} else {
			g.setBackgroundColor(ColorConstants.darkGray);
			g.fillOval(x, y, diam, diam);
		}

		final int y_offset = (diam - ext.height) / 2;

		g.drawString("" + speed_limit, x + 2, y + y_offset);
	}

}