package uk.ac.ncl.safecap.diagram.misc.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.SWT;

import safecap.schema.NodeKind;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackColours;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackConstants;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.NodeUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;

public class FNode extends Shape {
	private safecap.schema.Node node;
	private ZoomManager zoomManager = null;

	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	public FNode() {
		setBackgroundColor(TrackColours.NormalNode);
		setForegroundColor(TrackColours.NormalNode);
		setFill(true);
	}

	public void setNode(safecap.schema.Node node) {
		this.node = node;
	}

	public safecap.schema.Node getNode() {
		return node;
	}

	@Override
	protected void fillShape(Graphics g) {
		if (ZoomLevels.isOutline(zoomManager)) {
			if (node.getKind() == NodeKind.NORMAL) {
				final Rectangle l = getBounds();
				final int x = l.x + l.width / 2;
				g.setLineWidth(2);
				g.setForegroundColor(TrackColours.NormalNode);
				g.drawLine(x, l.y, x, l.y + l.height);
			}
			return;
		}

		// Rectangle l = getBounds();
		// int x = l.x + (l.width - TrackConstants.TRACK_WIDTH) / 2;
		// int y = l.y + (l.height - TrackConstants.TRACK_WIDTH) / 2;
		// Rectangle r = new Rectangle(x, y, TrackConstants.TRACK_WIDTH,
		// TrackConstants.TRACK_WIDTH);

		final Rectangle r_orig = getBounds();
		final Rectangle r = new Rectangle(r_orig);
		g.setLineWidth(1);
		r.setY(r.y + TrackConstants.OVERLAP_OFFSET);
		r.setHeight(r.height - TrackConstants.OVERLAP_OFFSET * 2);

		int min = r.width;
		if (r.height < min) {
			min = r.height;
		}

		if (node == null) {
			g.setBackgroundColor(TrackColours.NormalNode);
			g.fillRectangle(r);
		} else {
			switch (node.getKind().getValue()) {
			case NodeKind.SILENT_VALUE:
				g.setBackgroundColor(ColorConstants.black);
				g.fillOval(getBounds().getCopy().shrink(5, 5));
				// if (!NodeUtil.isLeftOverlap(node) && !NodeUtil.isRightOverlap(node)) {
				// g.setForegroundColor(ColorConstants.darkGray);
				// g.drawRectangle(getBounds().getCopy().shrink(1, 2));
				// }
				break;
			case NodeKind.NORMAL_VALUE:
				if (ZoomLevels.isNormal(zoomManager) && !NodeUtil.isLeftOverlap(node) && !NodeUtil.isRightOverlap(node)) {
					final Rectangle l = getBounds();
					final int x = l.x + l.width / 2;
					g.setLineWidth(3);
					g.setForegroundColor(TrackColours.NormalNode);
					g.drawLine(x, l.y, x, l.y + l.height);
				}
				break;
			case NodeKind.BOUNDARY_VALUE:
				g.setBackgroundColor(TrackColours.BoundaryNode);
				g.setForegroundColor(TrackColours.BoundaryNode);
				g.fillRectangle(r);
				g.drawRectangle(r);
				break;
			case NodeKind.TERMINAL_VALUE:
				g.setBackgroundColor(TrackColours.TerminalNode);
				g.fillRectangle(r);
				break;
			case NodeKind.CROSSOVER_VALUE:
				g.setBackgroundColor(TrackColours.NormalNode);
				g.fillOval(r);
				break;
			case NodeKind.SWITCHED_CROSSOVER_VALUE:
				g.setBackgroundColor(TrackColours.TerminalNode);
				g.fillOval(r);
				break;
			case NodeKind.POINT_VALUE:

				final boolean isTrap = ExtensionUtil.getBool(node, SafecapConstants.EXT_POINT, SafecapConstants.EXT_POINT_IS_TRAP);
				if (isTrap) {
					g.setBackgroundColor(ColorConstants.red);
					g.fillRectangle(r_orig.getShrinked(3, 3));
				} else {
					g.setBackgroundColor(ColorConstants.lightGray);
					g.fillOval(r_orig.getShrinked(3, 3));
				}
				break;
			case NodeKind.INVALID_VALUE:
				g.setBackgroundColor(TrackColours.InvalidNode);
				g.fillRectangle(r);
				break;
			default:
				g.setBackgroundColor(TrackColours.InvalidNode);
				g.fillRectangle(r);
			}
		}

		if (ZoomLevels.isNormal(zoomManager)) {

			drawOverlap(g, r_orig);

			if (node.getKind() == NodeKind.BOUNDARY) {
				final int beg_x = r.x;
				final int beg_y = r.y;
				final int mid_x = r.x + r.width / 2;
				final int mid_y = r.y + r.height / 2;
				final int far_x = r.x + r.width;
				final int far_y = r.y + r.height;
				g.pushState();
				g.setClip(r);
				g.setForegroundColor(ColorConstants.black);
				g.setLineWidth(2);
				g.setLineCap(SWT.CAP_SQUARE);

				if (NodeUtil.isSource(node) && !NodeUtil.isSink(node)) {
					g.drawLine(mid_x, beg_y, mid_x, far_y);
					g.drawLine(beg_x, mid_y, far_x, mid_y);
				} else if (!NodeUtil.isSource(node) && NodeUtil.isSink(node)) {
					g.drawLine(beg_x, mid_y, far_x, mid_y);
				}
				g.popState();
			}

		}
	}

	private void drawOverlap(Graphics g, Rectangle r_orig) {
		if (NodeUtil.isLeftOverlap(node) || NodeUtil.isRightOverlap(node)) {
			final int beg_x = r_orig.x;
			final int beg_y = r_orig.y + 2;
			final int mid_x = r_orig.x + r_orig.width / 2;
			final int far_x = r_orig.x + r_orig.width;
			final int far_y = r_orig.y + r_orig.height - 2;
			g.setForegroundColor(ColorConstants.darkGray);
			g.setLineWidth(1);

			if (NodeUtil.isLeftOverlap(node)) {
				final int[] points = new int[] { beg_x, beg_y, mid_x, beg_y, mid_x, far_y, beg_x, far_y };

				g.drawPolyline(points);
			}

			if (NodeUtil.isRightOverlap(node)) {
				final int[] points = new int[] { far_x, beg_y, mid_x, beg_y, mid_x, far_y, far_x, far_y };

				g.drawPolyline(points);
			}

		}
	}

	@Override
	protected void outlineShape(Graphics g) {

	}

}