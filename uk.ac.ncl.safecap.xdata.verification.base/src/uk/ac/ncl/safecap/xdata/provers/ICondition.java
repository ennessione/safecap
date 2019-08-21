package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public interface ICondition {
	RootCatalog getRoot();

	String getShortName();	
	
	String getName();

	SDAContext getContext();

	CLExpression getHypothesis();

	CLExpression getGoal();

	boolean isConjecture();

	void setResult(VerificationResult result);

	VerificationResult getResult();

	ManagedProofObligation getManagedProofObligation();

	TypingContext getTypingContext();

}
