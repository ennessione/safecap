package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;

public class ConditionConjecture implements ICondition {
	private final Conjecture conjecture;

	public ConditionConjecture(Conjecture conjecture) {
		this.conjecture = conjecture;
	}

	@Override
	public CLExpression getHypothesis() {
		return null;
	}

	@Override
	public CLExpression getGoal() {
		return conjecture.getParsed().content();
	}

	@Override
	public boolean isConjecture() {
		return true;
	}

	@Override
	public void setResult(VerificationResult result) {
		conjecture.setVResult(result);
		if (result != null) {
			conjecture.setValid(result.isValid());
		} else {
			conjecture.isValid().clear();
		}
	}

	@Override
	public SDAContext getContext() {
		return SDAUtils.getDataContext((RootCatalog) conjecture.root());
	}

	@Override
	public String getName() {
		return conjecture.getId().content();
	}

	@Override
	public RootCatalog getRoot() {
		return (RootCatalog) conjecture.root();
	}

	@Override
	public VerificationResult getResult() {
		return conjecture.getVResult().content();
	}

	@Override
	public ManagedProofObligation getManagedProofObligation() {
		return null;
	}

	@Override
	public TypingContext getTypingContext() {
		return getContext().getRootRuntimeContext().getRootContext();
	}

	@Override
	public String getShortName() {
		return conjecture.getKey().content();
	}	
}
