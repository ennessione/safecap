package uk.ac.ncl.safecap.mcommon.conf;

import safecap.schema.Node;
import safecap.trackside.Signal;

public class OverlapDeclaration {
	private final Signal signal;
	private final Node node;
	private final int isFull;

	public OverlapDeclaration(Signal signal, Node node, int isFull) {
		this.signal = signal;
		this.node = node;
		this.isFull = isFull;
	}

	public Signal getSignal() {
		return signal;
	}

	public Node getNode() {
		return node;
	}

	public int getIsFull() {
		return isFull;
	}
}
