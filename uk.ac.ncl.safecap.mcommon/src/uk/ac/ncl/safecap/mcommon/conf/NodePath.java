package uk.ac.ncl.safecap.mcommon.conf;

import java.util.List;

import safecap.schema.Node;

public class NodePath {
	public List<Node> path;
	public int length;

	public NodePath(List<Node> path, int length) {
		this.path = path;
		this.length = length;
	}

	public String getPathString() {
		final StringBuilder sb = new StringBuilder();
		for (int j = 0; j < path.size(); j++) {
			if (j > 0) {
				sb.append(";");
			}
			sb.append(path.get(j).getLabel());
		}
		return sb.toString();
	}
}
