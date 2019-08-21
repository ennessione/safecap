package uk.ac.ncl.safecap.misc.core;

import java.util.ArrayList;
import java.util.List;

import safecap.Project;
import safecap.schema.Node;
import safecap.trackside.Equipment;
import safecap.trackside.NodeWire;

public class NodeWireUtil {
	public static List<Node> getConnectedNodes(Project root, Equipment source) {
		final List<Node> r = new ArrayList<>();

		for (final NodeWire nw : root.getNodewires()) {
			if (nw.getFrom() == source && !r.contains(nw.getTo())) {
				r.add(nw.getTo());
			}
		}

		return r;
	}
}
