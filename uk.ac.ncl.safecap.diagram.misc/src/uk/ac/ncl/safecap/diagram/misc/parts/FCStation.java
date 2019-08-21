package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import safecap.Labeled;
import safecap.commentary.OrientableCommentary;
import uk.ac.ncl.safecap.diagram.misc.fa.FALabel;
import uk.ac.ncl.safecap.diagram.misc.fa.FANode;
import uk.ac.ncl.safecap.diagram.misc.fa.FAShape;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;

public class FCStation extends FANode {

	public FCStation(OrientableCommentary node) {
		super(node);
	}

	@Override
	protected void createFigure(Rectangle bounds) {
		final Point c = bounds.getCenter();
		final FAShape platform = new FAShape(bounds, false, true);
		final FAShape handler = new FAShape(new Rectangle(bounds.x, bounds.y, 10, 10), true, false);
		final Labeled labeled = (Labeled) getNode();
		facontainer.add(platform);
		facontainer.add(handler);

		if (labeled.getLabel() != null) {
			final FALabel label = new FALabel(labeled.getLabel(), c.x, c.y, TrackFonts.FONT_BOLD);
			facontainer.add(label);
		}
	}

	@Override
	protected void setContext(Graphics g) {
		g.setForegroundColor(ColorConstants.black);
		g.setBackgroundColor(ColorConstants.gray);
		g.setAlpha(80);
	}

	@Override
	protected void setHoverContext(Graphics g) {
		g.setForegroundColor(ColorConstants.black);
		g.setBackgroundColor(ColorConstants.blue);
		g.setAlpha(80);
		g.setFillRule(SWT.FILL_WINDING);
	}

}