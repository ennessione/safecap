package uk.ac.ncl.safecap.diagram.misc.fa;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomManager;

import safecap.commentary.OrientableCommentary;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;

public abstract class FANode extends Shape implements MouseMotionListener {
	protected static Dimension minsize = new Dimension(20, 20);

	protected FAContainer facontainer;

	private final OrientableCommentary node;
	private boolean hover = false;
	private ZoomManager zoomManager = null;

	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	public FANode(OrientableCommentary node) {
		this.node = node;
		super.setMinimumSize(minsize);
		super.addMouseMotionListener(this);

		facontainer = new FAContainer();
	}

	public OrientableCommentary getNode() {
		return node;
	}

	protected abstract void setContext(Graphics g);

	protected abstract void setHoverContext(Graphics g);

	@Override
	protected void fillShape(Graphics g) {
		if (ZoomLevels.isOutline(zoomManager)) {
			return;
		}

		if (!hover) {
			setContext(g);
		} else {
			setHoverContext(g);
		}

		facontainer.getChildren().clear();
		createFigure(getBounds());
		facontainer.rotate(Math.toRadians(node.getAngle()), getBounds());
		facontainer.draw(g);
	}

	protected abstract void createFigure(Rectangle bounds);

	@Override
	protected void outlineShape(Graphics g) {
		// do nothing
	}

	@Override
	public IFigure findFigureAt(int x, int y, TreeSearch ss) {
		if (containsPoint(x, y)) {
			return this;
		} else {
			return null;
		}
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return facontainer.isSensitivePoint(x, y);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		hover = true;
		super.repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		hover = false;
		super.repaint();
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// do nothing
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// do nothing
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// do nothing
	}
}