package uk.ac.ncl.safecap.prover.core;

public interface IGoalTtransform {
	String getName();

	Object isApplicable(ProofGoal source);

	int apply(ProofGoal source, Object data);
}
