package uk.ac.ncl.safecap.prover.core;

public interface IHypothesisTtransform {
	String getName();

	boolean isApplicable(IProofBranch branch, ProofHypothesis source);

	void apply(IProofBranch branch, ProofHypothesis source);
}
