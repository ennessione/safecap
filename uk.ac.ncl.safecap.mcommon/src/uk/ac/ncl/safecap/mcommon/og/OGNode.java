package uk.ac.ncl.safecap.mcommon.og;

public class OGNode extends OGExpandable {

	public OGNode(String label) {
		super(label);
	}

	public OGNode(String label, OrderGraph subgraph) {
		super(label);
		setSubgraph(subgraph);
	}
}
