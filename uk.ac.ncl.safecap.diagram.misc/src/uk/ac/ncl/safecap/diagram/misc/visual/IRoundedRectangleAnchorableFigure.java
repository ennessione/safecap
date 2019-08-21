package uk.ac.ncl.safecap.diagram.misc.visual;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * An interface for figures which have a rounded rectangle shape and need to
 * provide a connection anchor.
 * 
 * @author mri
 */
public interface IRoundedRectangleAnchorableFigure extends IFigure {

	/**
	 * Returns the rounded rectangles corner dimensions.
	 * 
	 * @return the corner dimensions
	 */
	Dimension getCornerDimensions();

	/**
	 * Returns the rounded rectangles bounds.
	 * 
	 * @return the bounds
	 */
	Rectangle getRoundedRectangleBounds();
}
