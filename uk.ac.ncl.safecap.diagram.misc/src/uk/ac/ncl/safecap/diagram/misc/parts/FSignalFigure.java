package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Path;

import safecap.Orientation;
import safecap.trackside.LeftSignal;
import safecap.trackside.RightSignal;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SignalUtil;

public abstract class FSignalFigure extends Shape {
	protected Signal signal;
	protected static final int LEG = 8;
	protected static final int RAD = 8;
	public static final int WIDTH = LEG + RAD * 2 + 1;
	public static final int HEIGHT = RAD * 2 + 2;

	protected ZoomManager zoomManager = null;

	public FSignalFigure() {
	}

	public Signal getSignal() {
		return signal;
	}

	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
	}

	/**
	 * @see Shape#fillShape(Graphics)
	 */
	@Override
	protected void fillShape(Graphics graphics) {

	}

	@Override
	public boolean isVisible() {
		return ExtensionUtil.isSignalsVisible(signal);
	}

	@Override
	public IFigure getToolTip() {
		if (ZoomLevels.isCoarse(zoomManager)) {
			return null;
		}

		final StringBuffer sb = new StringBuffer();

		sb.append("Signal: " + (signal.getLabel() != null && signal.getLabel().length() > 0 ? signal.getLabel() : "(no label)") + "\n");

		for (final String key : ExtensionUtil.getKeys(signal, SafecapConstants.EXT_LDL_GEN)) {
			sb.append(key + " : " + ExtensionUtil.getString(signal, SafecapConstants.EXT_LDL_GEN, key) + "\n");
		}

		sb.append("Routes: " + SignalUtil.getRoutesProtectedBy(signal) + "\n");
		if (signal.getOrigin() != null) {
			sb.append("\nProvenance: " + signal.getOrigin());
		}

		final Label l = new Label();
		l.setFont(TrackFonts.FONT_SMALLER);
		l.setOpaque(true);
		l.setBorder(null);
		l.setBackgroundColor(ColorConstants.white);
		l.setForegroundColor(ColorConstants.black);
		l.setText(sb.toString());

		return l;

	}

	protected Color getBackground(Color def) {
		if (signal.getStyle() != null && signal.getStyle().getBackground() != null) {
			return (Color) signal.getStyle().getBackground();
		} else {
			return def;
		}

	}

	/**
	 * @see Shape#outlineShape(Graphics)
	 */
	@Override
	protected void outlineShape(Graphics g) {
		// if (ZoomLevels.isOutline(zoomManager))
		// return;

		final Color originalColor = g.getForegroundColor();

		if (signal.getStyle() != null && signal.getStyle().getBackground() != null) {

			final Rectangle r = getBounds();
			int x = r.x;
			int y = r.y + r.height / 2;

			g.setBackgroundColor((Color) signal.getStyle().getBackground());

			// int centerX = r.x + RAD, centerY = r.y + RAD;
			if (signal instanceof LeftSignal) {
				x = r.x;
				y = r.y + r.height / 2;
				g.fillOval(x + LEG, y - RAD, RAD * 2 + 1, RAD * 2);
			} else if (signal instanceof RightSignal) {
				x = r.x + 1;
				y = r.y + r.height / 2 - 1;
				g.fillOval(x, y - RAD, RAD * 2 + 1, RAD * 2);
			}
		}

		g.setForegroundColor(originalColor);
	}

	protected void drawControlled(Graphics g, int xc, int yc) {
		g.setBackgroundColor(getBackground(ColorConstants.lightGray));
		g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.setForegroundColor(ColorConstants.white);
		// g.setFont(TrackFonts.FONT_BOLD);
		// String text = "" + aspects;
		// Dimension ext = FigureUtilities.getStringExtents(text, g.getFont());
		// g.drawString(text, xc - ext.width / 2, yc - ext.height / 2 - 1);
	}

	protected void drawAuto(Graphics g, int xc, int yc) {
		g.setBackgroundColor(getBackground(ColorConstants.lightBlue));
		g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.setForegroundColor(ColorConstants.white);
		g.setFont(TrackFonts.FONT_SMALL);
		final Dimension ext = org.eclipse.draw2d.FigureUtilities.getStringExtents("A", g.getFont());
		g.drawString("A", xc - ext.width / 2, yc - ext.height / 2 - 2);
	}

	protected void drawSemiAuto(Graphics g, int xc, int yc) {
		g.setBackgroundColor(getBackground(ColorConstants.gray));
		// g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.fillArc(xc - RAD, yc - RAD, RAD * 2, RAD * 2, 90, 180);
		g.setBackgroundColor(ColorConstants.lightBlue);
		g.fillArc(xc - RAD, yc - RAD, RAD * 2, RAD * 2, 270, 180);
		g.setForegroundColor(ColorConstants.white);
		g.setFont(TrackFonts.FONT_SMALL);
		final Dimension ext = org.eclipse.draw2d.FigureUtilities.getStringExtents("SA", g.getFont());
		g.drawString("SA", xc - ext.width / 2, yc - ext.height / 2 - 2);
	}

	protected static void drawBannerRepeater(Graphics g, int xc, int yc) {
		g.setBackgroundColor(ColorConstants.black);
		g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(xc - RAD + 2, yc - RAD / 2 + RAD / 4, RAD * 2 - 4, RAD / 2);
	}

	protected static void drawDistant(Graphics g, int xc, int yc) {
		g.setBackgroundColor(ColorConstants.black);
		g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.setForegroundColor(ColorConstants.white);
		g.setFont(TrackFonts.FONT_SMALL);
		final Dimension ext = org.eclipse.draw2d.FigureUtilities.getStringExtents("D", g.getFont());
		g.drawString("D", xc - ext.width / 2, yc - ext.height / 2 - 2);
	}

	protected static void drawShunt(Graphics g, int xc, int yc) {
		g.setBackgroundColor(ColorConstants.black);
		g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.setForegroundColor(ColorConstants.white);
		g.setFont(TrackFonts.FONT_SMALL);
		final Dimension ext = org.eclipse.draw2d.FigureUtilities.getStringExtents("S", g.getFont());
		g.drawString("S", xc - ext.width / 2, yc - ext.height / 2 - 2);
	}

	protected static void drawPresetShunt(Graphics g, int xc, int yc) {
		g.setBackgroundColor(ColorConstants.darkGray);
		g.fillOval(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		g.setForegroundColor(ColorConstants.white);
		g.setFont(TrackFonts.FONT_SMALL);
		final Dimension ext = org.eclipse.draw2d.FigureUtilities.getStringExtents("pS", g.getFont());
		g.drawString("pS", xc - ext.width / 2, yc - ext.height / 2 - 2);
	}

	protected static void drawBlockMarker(Graphics g, int xc, int yc, Orientation o) {
		g.setLineStyle(SWT.LINE_SOLID);
		drawBlockMarkerGeneric(g, xc, yc, o);
	}

	protected static void drawVirtualBlockMarker(Graphics g, int xc, int yc, Orientation o) {
		g.setLineStyle(SWT.LINE_DASH);
		drawBlockMarkerGeneric(g, xc, yc, o);
	}

	private static void drawBlockMarkerGeneric(Graphics g, int xc, int yc, Orientation o) {
		g.setForegroundColor(ColorConstants.black);
		final int third = RAD * 2 / 3;
		g.drawRectangle(xc - RAD, yc - RAD, RAD * 2, RAD * 2);
		final Path path = new Path(null);
		path.moveTo(xc - RAD, yc);

		if (o == Orientation.LEFT) {
			path.lineTo(xc, yc + RAD);
			path.lineTo(xc + RAD, yc);
			path.lineTo(xc + RAD - third, yc);
			path.lineTo(xc + RAD - third, yc - RAD);
			path.lineTo(xc + RAD - third * 2, yc - RAD);
			path.lineTo(xc + RAD - third * 2, yc);
			path.lineTo(xc - RAD, yc);
		} else {
			path.lineTo(xc, yc - RAD);
			path.lineTo(xc + RAD, yc);
			path.lineTo(xc + RAD - third, yc);
			path.lineTo(xc + RAD - third, yc + RAD);
			path.lineTo(xc + RAD - third * 2, yc + RAD);
			path.lineTo(xc + RAD - third * 2, yc);
			path.lineTo(xc - RAD, yc);
		}
		g.drawPath(path);
	}

	protected void drawSignalMainPart(Graphics g, int x, int y, Orientation o) {
		switch (signal.getType()) {
		case CONTROLLED:
			drawControlled(g, x + LEG + RAD, y);
			break;
		case AUTOMATIC:
			drawAuto(g, x + LEG + RAD, y);
			break;
		case BANNER_REPEATER:
			drawBannerRepeater(g, x + LEG + RAD, y);
			break;
		case DISTANT:
			drawDistant(g, x + LEG + RAD, y);
			break;
		case SHUNT:
			drawShunt(g, x + LEG + RAD, y);
			break;
		case PRESET_SHUNT:
			drawShunt(g, x + LEG + RAD, y);
			break;
		case SEMI_AUTOMATIC:
			drawSemiAuto(g, x + LEG + RAD, y);
			break;
		case BLOCK_MARKER:
			drawBlockMarker(g, x + LEG + RAD, y, o);
			break;
		case VIRTUAL_BLOCK_MARKER:
			drawVirtualBlockMarker(g, x + LEG + RAD, y, o);
			break;
		default:
			break;
		}
	}

	protected static Point rotate(Point p, int degrees) {
		final double rad = Math.toRadians(degrees);
		final double x = p.x;
		final double y = p.y;
		final int nx = (int) (x * Math.cos(rad) + y * Math.sin(rad));
		final int ny = (int) (-x * Math.sin(rad) + y * Math.cos(rad));

		return new Point(nx, ny);
	}

}