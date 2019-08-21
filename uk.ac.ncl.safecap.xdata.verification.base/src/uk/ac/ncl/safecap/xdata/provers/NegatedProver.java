package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.xdata.provers.prob.ProBPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class NegatedProver implements IPropertyVerifier {
	public static IPropertyVerifier PROB_NEGATED = new NegatedProver(ProBPropertyVerifier.INSTANCE);

	private final IPropertyVerifier base;

	public NegatedProver(IPropertyVerifier base) {
		this.base = base;
	}

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		return new NegatedVerificationResult(base.verify(property, monitor, !negated));
	}

}
