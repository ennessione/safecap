package uk.ac.ncl.safecap.diagram.misc.fa;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;

public class FALabel extends FAFigure {
	private final String text;
	private final Font font;
	private final int x, y;

	public FALabel(String text, int x, int y, Font font) {
		super(false);
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = font;
	}

	@Override
	public void rotate(Point mid, double angle) {
		// TODO Auto-generated method stub
	}

	@Override
	public Rectangle getBounds() {
		final Dimension ext = FigureUtilities.getStringExtents(text, font);
		return new Rectangle(x, y, ext.width, ext.height);
	}

	@Override
	public void draw(Graphics g) {
		final Font _f = g.getFont();
		g.setFont(font);
		final Dimension ext = FigureUtilities.getStringExtents(text, font);
		g.drawText(text, x - ext.width / 2, y - ext.height / 2);
		g.setFont(_f);
	}

	@Override
	public boolean isSensitivePoint(int x, int y) {
		return false;
	}

	@Override
	public void scale(Point center, double sf) {
		// do nothing
	}

}
