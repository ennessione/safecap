package uk.ac.ncl.safecap.xdata.testinv;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;

public interface IStressTestingTransform {
	ManagedProofObligation transform(ManagedProofObligation base);

	ManagedProofObligation transform(ManagedProofObligation base, ProofHypothesis hyp);

	ProofHypothesis getApplicable(ManagedProofObligation base);

	String getName();
}
