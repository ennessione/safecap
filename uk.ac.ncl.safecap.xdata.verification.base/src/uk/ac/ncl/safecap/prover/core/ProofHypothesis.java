package uk.ac.ncl.safecap.prover.core;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public class ProofHypothesis implements ICLFormulaProvider {
	private final IProofBranch parent;
	private Transforms hyp;
	private final String canonicalName;

	public ProofHypothesis(IProofBranch parent, Transforms hyp, String id) {
		super();
		this.parent = parent;
		this.hyp = hyp;
		canonicalName = id;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	@Override
	public CLExpression getFormula() {
		return hyp.getFormula();
	}

	public Transforms getOrigin() {
		return hyp.getOrigin();
	}

	public boolean isExclude() {
		return hyp.isExclude();
	}

	public IProofBranch getProofBranch() {
		return parent;
	}

	public Transforms getHypothesis() {
		return hyp;
	}

	public void revert(int stage) {
		Transforms c = hyp;

		while (c.getStage() > stage && c.getOrigin() != null) {
			c = c.getOrigin();
		}

		hyp = c;
	}

	@Override
	public String toString() {
		return hyp.getTransform() + "::" + getFormula().toString();
	}

}
