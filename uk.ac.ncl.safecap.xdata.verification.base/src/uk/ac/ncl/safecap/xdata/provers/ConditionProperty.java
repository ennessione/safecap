package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;

public class ConditionProperty implements ICondition {
	private final VerificationProperty property;

	public ConditionProperty(VerificationProperty property) {
		this.property = property;
	}

	@Override
	public CLExpression getHypothesis() {
		return property.getHypotheses().getParsed().content();
	}

	@Override
	public CLExpression getGoal() {
		return property.getParsed().content();
	}

	@Override
	public boolean isConjecture() {
		return false;
	}

	@Override
	public void setResult(VerificationResult result) {
		property.setVResult(result);
		if (result != null) {
			property.setValid(result.isValid());
		} else {
			property.isValid().clear();
		}
	}

	@Override
	public SDAContext getContext() {
		return SDAUtils.getDataContext((RootCatalog) property.root());
	}

	@Override
	public String getName() {
		return property.getId().content();
	}

	@Override
	public RootCatalog getRoot() {
		return (RootCatalog) property.root();
	}

	@Override
	public VerificationResult getResult() {
		return property.getVResult().content();
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
		return getName();
	}
}
