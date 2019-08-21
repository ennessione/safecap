package uk.ac.ncl.safecap.mcommon.og.rail;

import safecap.model.Ambit;
import uk.ac.ncl.safecap.mcommon.og.OrderGraph;

public class AmbitBlock extends RailBlock {
	private Ambit ambit;

	public AmbitBlock(Ambit ambit) {
		super(ambit.getLabel());
	}

	public Ambit getAmbit() {
		return ambit;
	}

	@Override
	public OrderGraph getSubgraph() {
		return null;
	}

	@Override
	public void setSubgraph(OrderGraph subgraph) {
		throw new UnsupportedOperationException("AmbitBlock.setSubgraph(OrderGraph subgraph)");
	}
}
