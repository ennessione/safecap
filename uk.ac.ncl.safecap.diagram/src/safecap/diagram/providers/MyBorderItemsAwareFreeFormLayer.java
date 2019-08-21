package safecap.diagram.providers;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemsAwareFreeFormLayer;

public class MyBorderItemsAwareFreeFormLayer extends BorderItemsAwareFreeFormLayer {

	@Override
	public Rectangle getFreeformExtent() {
		final Rectangle r = new Rectangle(super.getFreeformExtent());
		return r.expand(400, 400);
	}
}
