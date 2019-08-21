package uk.ac.ncl.safecap.misc.core;

import safecap.schema.NodeKind;

public class NodeDescriptor {
	public NodeKind kind;
	public int role;

	public NodeDescriptor(NodeKind kind, int role) {
		this.kind = kind;
		this.role = role;
	}
}
