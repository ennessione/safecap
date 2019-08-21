package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.zest.core.widgets.GraphNode;

public class RouteNode extends ElementNode {

	public RouteNode(EObject element) {
		super(element);
	}

	@Override
	public void setVisual(GraphNode node) {
		node.setBackgroundColor(ColorConstants.white);
		node.setForegroundColor(ColorConstants.black);
	}

}
