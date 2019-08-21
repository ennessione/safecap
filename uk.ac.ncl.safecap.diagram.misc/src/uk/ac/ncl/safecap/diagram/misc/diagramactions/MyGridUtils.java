package uk.ac.ncl.safecap.diagram.misc.diagramactions;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;

public class MyGridUtils {
	public static int getXMajor(GraphicalEditPart container) {
		final Dimension spacing = (Dimension) container.getViewer().getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
		return spacing.width;
	}

	public static int getYMajor(GraphicalEditPart container) {
		final Dimension spacing = (Dimension) container.getViewer().getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
		return spacing.height;
	}

	public static Point putOnMajor(GraphicalEditPart container, Point location) {
		if (container.getParent() instanceof DiagramRootEditPart) {
			final DiagramRootEditPart root = (DiagramRootEditPart) container.getParent();
			final double zoom = root.getZoomManager().getZoom();
			final Dimension spacing = (Dimension) container.getViewer().getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
			if (spacing != null && spacing.width > 0 && spacing.height > 0) {
				final int x = (int) (location.x / zoom);
				final int y = (int) (location.y / zoom);
				final int w = (int) (spacing.width / zoom);
				final int h = (int) (spacing.height / zoom);

				final int sx = x / w * w;
				final int sy = y / h * h;

				return new Point(sx, sy);
			}
		}
		return location;
	}

	public static Point putOnMinor(GraphicalEditPart container, Point location) {
		if (container.getParent() instanceof DiagramRootEditPart) {
			final DiagramRootEditPart root = (DiagramRootEditPart) container.getParent();
			final double zoom = root.getZoomManager().getZoom();
			final Dimension spacing = (Dimension) container.getViewer().getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
			if (spacing != null && spacing.width > 0 && spacing.height > 0) {
				final int x = (int) (location.x / zoom);
				final int y = (int) (location.y / zoom);
				final int w = (int) (spacing.width / 3.0 / zoom);
				final int h = (int) (spacing.height / 3.0 / zoom);

				final int sx = x / w * w;
				final int sy = y / h * h;

				return new Point(sx, sy);
			}
		}
		return location;
	}
}
