package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.Collection;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;

public interface ITransitionPOs {
	Collection<ManagedProofObligation> getPos();
}
