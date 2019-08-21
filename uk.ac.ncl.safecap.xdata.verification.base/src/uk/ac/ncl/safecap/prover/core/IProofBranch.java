package uk.ac.ncl.safecap.prover.core;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.typing.TypingContext;

public interface IProofBranch extends Iterable<ProofHypothesis> {
	TypingContext getTypingContext();

	List<ProofHypothesis> getHypothesis();

	String getName();
}
