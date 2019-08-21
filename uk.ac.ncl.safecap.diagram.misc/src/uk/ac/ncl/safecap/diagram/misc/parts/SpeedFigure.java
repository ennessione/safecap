package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.graphics.Color;

import safecap.trackside.SpeedLimit;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;

public class SpeedFigure extends Shape {
	private SpeedLimit speedLimit;
	protected ZoomManager zoomManager = null;

	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final int LEFT_ANCHOR_X = 8;
	public static final int LEFT_ANCHOR_Y = 14;
	public static final int RIGHT_ANCHOR_X = 8;
	public static final int RIGHT_ANCHOR_Y = 1;
	public static final int DEFAULT_LIMIT = 70;
	public static final Color DEFAULT_COLOR = ColorConstants.gray;

	public SpeedLimit getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(SpeedLimit speedLimit) {
		this.speedLimit = speedLimit;
	}

	@Override
	public boolean isVisible() {
		return ExtensionUtil.isSpeedLimitsVisible(speedLimit);
	}

	private String getLimit() {
		if (speedLimit != null && speedLimit.getLimit() > 0 && speedLimit.getLimit() < 999) {
			return "" + speedLimit.getLimit();
		} else {
			return "?";
		}
	}

//	private Color getColor()
//	{
//		if (speedLimit != null && speedLimit.getLine().size() == 1) {
//			Line line = speedLimit.getLine().get(0);
//			if (line != null && line.getColor() != null)
//				return new Color(null, (RGB) line.getColor());
//		}
//
//		return DEFAULT_COLOR;
//	}	

	@Override
	public IFigure getToolTip() {
		if (ZoomLevels.isCoarse(zoomManager)) {
			return null;
		}

		final StringBuffer sb = new StringBuffer();

		sb.append("Speed limit: " + getLimit() + "\n");
		sb.append("Lines: " + speedLimit.getLine());
		if (speedLimit.getOrigin() != null) {
			sb.append("\nProvenance: " + speedLimit.getOrigin());
		}

		final Label l = new Label();
		l.setFont(TrackFonts.FONT_SMALL);
		l.setOpaque(true);
		l.setBorder(null);
		l.setBackgroundColor(ColorConstants.white);
		l.setForegroundColor(ColorConstants.black);
		l.setText(sb.toString());

		return l;

	}

	/**
	 * @see Shape#outlineShape(Graphics)
	 */
	@Override
	protected void outlineShape(Graphics g) {
		if (ZoomLevels.isOutline(zoomManager)) {
			return;
		}

		Rectangle r = getBounds();

		if (zoomManager.getZoom() > 1.1) {
			g.setLineWidth(2);
			g.setBackgroundColor(ColorConstants.white);
			r = r.getCopy().shrink(2, 2);
			g.fillOval(r);
			g.setForegroundColor(ColorConstants.red);
			g.drawOval(r);
			// g.drawRectangle(r);
			g.setFont(TrackFonts.FONT_TINY);

			String label = getLimit();
			try {
				final double speed_d = Double.parseDouble(label);
				if (speed_d == Math.floor(speed_d)) {
					label = new Integer((int) speed_d).toString();
				}
			} catch (final NumberFormatException e) {
				// ignore
			}

			final Dimension ext = FigureUtilities.getStringExtents(label, font);

			final int x = r.x + (r.width - ext.width) / 2 + 3;
			final int y = r.y + (r.height - ext.height) / 2 + 5;

			g.setForegroundColor(ColorConstants.black);
			g.drawString(label, x, y);
		} else {
			Color c = ColorConstants.red;
			final String label = getLimit();
			final double speed_d = Double.parseDouble(label);
			if (speed_d < 10) {
				c = ColorConstants.red;
			} else if (speed_d < 20) {
				c = ColorConstants.orange;
			} else if (speed_d < 40) {
				c = ColorConstants.yellow;
			} else {
				c = ColorConstants.green;
			}

			g.setBackgroundColor(c);
			r = r.getCopy().shrink(2, 2);
			g.fillOval(r);
		}
	}

	@Override
	protected void fillShape(Graphics g) {
		final Rectangle r = getBounds();
		if (ZoomLevels.isOutline(zoomManager)) {
			g.setBackgroundColor(ColorConstants.orange);
			g.fillOval(r.getCopy().shrink(7, 7));
		} else {
			g.fillOval(r.getCopy().shrink(1, 1));
		}
	}
}