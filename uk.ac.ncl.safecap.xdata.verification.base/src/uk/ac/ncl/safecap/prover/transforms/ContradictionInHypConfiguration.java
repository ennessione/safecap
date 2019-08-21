package uk.ac.ncl.safecap.prover.transforms;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public class ContradictionInHypConfiguration {
	private String name;
	private CLExpression[] hypothesis;
	
	public ContradictionInHypConfiguration(String name, CLExpression ... hypothesis) {
		super();
		this.name = name;
		this.hypothesis = hypothesis;
	}

	public String getName() {
		return name;
	}

	public CLExpression[] getHypothesis() {
		return hypothesis;
	}
}
