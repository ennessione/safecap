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

public class FCBridge extends FANode {

	public FCBridge(OrientableCommentary node) {
		super(node);
	}

	@Override
	protected void createFigure(Rectangle bounds) {
		final Point c = bounds.getCenter();

		final Rectangle upperRail = new Rectangle(bounds.x, bounds.y, bounds.width, 10);

		final Rectangle lowerRail = new Rectangle(bounds.x, bounds.y + bounds.height - 10, bounds.width, 10);

		final Rectangle middle = new Rectangle(bounds.x + 30, bounds.y + 10, bounds.width - 60, bounds.height - 20);

		final FAShape upperRailS = new FAShape(upperRail, false, true);
		final FAShape lowerRailS = new FAShape(lowerRail, false, true);
		final FAShape middleS = new FAShape(middle, false, true);

		final FAShape handler1 = new FAShape(new Rectangle(bounds.x, bounds.y, 10, 10), true, false);
		final FAShape handler2 = new FAShape(new Rectangle(bounds.x + bounds.width - 10, bounds.y + bounds.height - 10, 10, 10), true,
				false);

		final Labeled labeled = (Labeled) getNode();
		facontainer.add(upperRailS);
		facontainer.add(lowerRailS);
		facontainer.add(middleS);
		facontainer.add(handler1);
		facontainer.add(handler2);

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