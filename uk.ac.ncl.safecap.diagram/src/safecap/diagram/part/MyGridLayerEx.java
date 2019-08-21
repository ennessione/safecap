package safecap.diagram.part;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gmf.runtime.diagram.ui.internal.editparts.GridLayerEx;

import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;

public class MyGridLayerEx extends GridLayerEx implements ZoomListener {
	private double zoom = 1.0;

	public MyGridLayerEx() {
		super();
		gridX = 50;
		gridY = 50;
		setForegroundColor(ColorConstants.black);
	}

	@Override
	protected void paintFigure(Graphics g) {

		g.setForegroundColor(ColorConstants.black);
		g.setFont(TrackFonts.FONT_SMALL_BOLD);

		final double factor = 1.0D / zoom;
		final Point z3 = new Point(20, 20);
		translateToRelative(z3);
		z3.x = (int) (z3.x / factor);
		z3.y = (int) (z3.y / factor);
		final double z = Math.floor(zoom * 1000D / 500D) * 500D / 1000D;
		g.pushState();
		g.scale(factor);
		g.drawText("Scale " + zoom + "/" + z, z3);
		g.popState();

		if (zoom >= 0.5) {
			paintGridMajor(g);
		}
		// FigureUtilities.paintGridWithStyle(g, this, origin, gridX, gridY,
		// SWT.LINE_SOLID, null);
	}

	private void paintGridMajor(Graphics g) {
		final Rectangle clip = g.getClip(Rectangle.SINGLETON);
		g.setAlpha(128);
		g.setForegroundColor(ColorConstants.blue);
		g.setLineWidthFloat((float) (0.5 / zoom));

//		double z = (Math.floor((zoom * 1000D) / 500D) * 500D) / 1000D;
//
		final int _gridX = (int) (gridX / zoom);
		final int _gridY = (int) (gridY / zoom);

//		int _gridX = gridX;
//		int _gridY = gridY;

		// System.out.println("+++ Spacing " + gridX + " / " + _gridX);

		if (_gridX > 0) {
			final int shiftX = clip.x - clip.x % _gridX;
			for (int i = shiftX; i < clip.x + clip.width; i += _gridX) {
				g.drawLine(i, clip.y, i, clip.y + clip.height);
			}
		}

		if (_gridY > 0) {
			final int shiftY = clip.y - clip.y % _gridY;

			for (int i = shiftY; i < clip.y + clip.width; i += _gridY) {
				g.drawLine(clip.x, i, clip.x + clip.width, i);
			}
		}

	}

	@Override
	public void setSpacing(Dimension spacing) {
		// System.out.println("*** Spacing " + spacing);
		super.setSpacing(spacing);
	}

	@Override
	public void zoomChanged(double zoom) {
		this.zoom = zoom;
	}
}
