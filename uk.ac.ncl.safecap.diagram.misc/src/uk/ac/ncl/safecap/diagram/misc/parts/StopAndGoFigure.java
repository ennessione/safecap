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
import org.eclipse.swt.graphics.Font;

import safecap.trackside.RightStopAndGo;
import safecap.trackside.StopAndGo;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;

public class StopAndGoFigure extends Shape {
	private StopAndGo stopandgo;
	protected ZoomManager zoomManager = null;

	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final int LEFT_ANCHOR_X = 8;
	public static final int LEFT_ANCHOR_Y = 16;
	public static final int RIGHT_ANCHOR_X = 8;
	public static final int RIGHT_ANCHOR_Y = 0;
	public static final int DEFAUL_DELAY = 60;
	public static final Color DEFAULT_COLOR = ColorConstants.gray;
	public static final Font font = TrackFonts.FONT_SMALL_BOLD;

	public StopAndGo getStopAndGo() {
		return stopandgo;
	}

	public void setStopAndGo(StopAndGo speedLimit) {
		stopandgo = speedLimit;
	}

	@Override
	public boolean isVisible() {
		return ExtensionUtil.isPlatformsVisible(stopandgo);
	}

	private String getDelay() {
		if (stopandgo != null && stopandgo.getDelay() > 0 && stopandgo.getDelay() < 999) {
			return "" + stopandgo.getDelay();
		} else {
			return "";
		}
	}

	@Override
	public IFigure getToolTip() {
		if (ZoomLevels.isCoarse(zoomManager)) {
			return null;
		}

		final StringBuffer sb = new StringBuffer();

		sb.append("Delay: " + getDelay() + "\n");
		sb.append("Lines: " + stopandgo.getLine());
		if (stopandgo.getOrigin() != null) {
			sb.append("\nProvenance: " + stopandgo.getOrigin());
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

//	private Color getColor()
//	{
//		if (stopandgo != null && stopandgo.getLine().size() == 1) {
//			Line line = stopandgo.getLine().get(0);
//			if (line != null && line.getColor() != null)
//				return new Color(null, (RGB) line.getColor());
//		}
//
//		return DEFAULT_COLOR;
//	}	

	private boolean isLeft() {
		if (stopandgo instanceof RightStopAndGo) {
			return false;
		} else {
			return true;
		}
	}

	private int[] getFigShape(Rectangle r) {
		if (stopandgo instanceof RightStopAndGo) {
			return new int[] { r.x, r.y, r.x + r.width, r.y, r.x + r.width / 2, r.y + r.height - 1, };
		} else {
			return new int[] { r.x + r.width / 2, r.y, r.x + r.width, r.y + r.height - 1, r.x, r.y + r.height - 1, };
		}
	}

	/**
	 * @see Shape#fillShape(Graphics)
	 */
	@Override
	protected void fillShape(Graphics graphics) {
		final Rectangle r = getBounds();
		if (ZoomLevels.isOutline(zoomManager)) {
			graphics.setBackgroundColor(ColorConstants.lightBlue);
			graphics.fillPolygon(getFigShape(r));
		} else {
			graphics.setForegroundColor(ColorConstants.white);
			graphics.fillPolygon(getFigShape(r));
		}
	}

	/**
	 * @see Shape#outlineShape(Graphics)
	 */
	@Override
	protected void outlineShape(Graphics g) {
		if (ZoomLevels.isOutline(zoomManager)) {
			return;
		}

		final Rectangle r = getBounds();
		g.setForegroundColor(ColorConstants.black);
		g.setLineWidth(2);
		g.drawPolygon(getFigShape(r));

//		g.setLineWidth(5);
//		g.setForegroundColor(getColor());
//		g.drawRectangle(r);

		if (ZoomLevels.isNormal(zoomManager)) {
			final String label = getDelay();

			final Dimension ext = FigureUtilities.getStringExtents(label, font);

			int x;
			int y;
			if (isLeft()) {
				x = r.x + (r.width - ext.width) / 2 - 1;
				y = r.y + (r.height - ext.height) / 2 + 5;
			} else {
				x = r.x + (r.width - ext.width) / 2 - 1;
				y = r.y + (r.height - ext.height) / 2 - 8;
			}

			g.setFont(TrackFonts.FONT_SMALL_BOLD);
			g.setForegroundColor(ColorConstants.white);
			g.drawString(label, x - 1, y);
			g.drawString(label, x + 1, y);
			g.drawString(label, x, y - 1);
			g.drawString(label, x, y + 1);

			g.setForegroundColor(ColorConstants.black);
			g.drawString(label, x, y);
		}
	}
}