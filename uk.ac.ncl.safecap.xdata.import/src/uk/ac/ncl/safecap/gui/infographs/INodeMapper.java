package uk.ac.ncl.safecap.gui.infographs;

import org.eclipse.zest.core.widgets.GraphNode;

public interface INodeMapper {
	GraphNode getNodeFor(Object object);
}
