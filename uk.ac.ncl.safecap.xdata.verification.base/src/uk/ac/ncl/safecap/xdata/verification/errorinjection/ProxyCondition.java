package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public class ProxyCondition implements ICondition {
	private ICondition origin;
	private CLExpression goal;
	
	public ProxyCondition(ICondition origin, CLExpression goal) {
		super();
		this.origin = origin;
		this.goal = goal;
	}

	@Override
	public RootCatalog getRoot() {
		return origin.getRoot();
	}

	@Override
	public String getShortName() {
		return origin.getShortName();
	}

	@Override
	public String getName() {
		return origin.getName();
	}

	@Override
	public SDAContext getContext() {
		return origin.getContext();
	}

	@Override
	public CLExpression getHypothesis() {
		return origin.getHypothesis();
	}

	@Override
	public CLExpression getGoal() {
		return goal;
	}

	@Override
	public boolean isConjecture() {
		return origin.isConjecture();
	}

	@Override
	public void setResult(VerificationResult result) {
		origin.setResult(result);
	}

	@Override
	public VerificationResult getResult() {
		return origin.getResult();
	}

	@Override
	public ManagedProofObligation getManagedProofObligation() {
		return origin.getManagedProofObligation();
	}

	@Override
	public TypingContext getTypingContext() {
		return origin.getTypingContext();
	}

}
