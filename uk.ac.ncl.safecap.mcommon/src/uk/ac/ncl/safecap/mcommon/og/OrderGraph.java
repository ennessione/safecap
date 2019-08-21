package uk.ac.ncl.safecap.mcommon.og;

import java.util.Collection;
import java.util.HashSet;

public class OrderGraph {
	private final Collection<OGNode> nodes;
	private final Collection<OGEdge> edges;

	public OrderGraph() {
		nodes = new HashSet<>();
		edges = new HashSet<>();
	}

	public void addNode(OGNode node) {
		nodes.add(node);
	}

	public void addNode(String node) {
		nodes.add(new OGNode(node));
	}

	public void addEdge(OGEdge edge) {
		edges.add(edge);
		nodes.add(edge.getFrom());
		nodes.add(edge.getTo());
	}

	public void addEdge(String label, String from, String to) {
		final OGNode nfrom = new OGNode(from);
		final OGNode nto = new OGNode(to);
		edges.add(new OGEdge(label, nfrom, nto));
	}

	public Collection<OGNode> getNodes() {
		return nodes;
	}

	public Collection<OGEdge> getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"nodes\": [");

		boolean first = true;
		for (final OGNode node : nodes) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(node.toString());
			first = false;
		}

		sb.append("]");

		if (!edges.isEmpty()) {
			sb.append(", \"edges\": [");
			first = true;
			for (final OGEdge edge : edges) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(edge.toString());
				first = false;
			}
			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}
