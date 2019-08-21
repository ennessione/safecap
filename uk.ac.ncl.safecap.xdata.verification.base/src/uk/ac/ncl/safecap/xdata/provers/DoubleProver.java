package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.xdata.provers.prob.ProBPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class DoubleProver implements IPropertyVerifier {
	public static IPropertyVerifier PROB_DOUBLE = new DoubleProver(ProBPropertyVerifier.INSTANCE);
	public static IPropertyVerifier NATIVE_DOUBLE = new DoubleProver(NativeEvaluationVerifier.INSTANCE);
	public static IPropertyVerifier PROB_AND_NATIVE_DOUBLE = new DoubleProver(MultiProver.PROB_AND_NATIVE);
	public static IPropertyVerifier PROB_DOUBLE_AND_NATIVE_DOUBLE = new MultiProver(PROB_DOUBLE, NATIVE_DOUBLE);

	private final IPropertyVerifier base;

	public DoubleProver(IPropertyVerifier base) {
		this.base = base;
	}

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		final VerificationResult a = base.verify(property, monitor, negated);
		final VerificationResult b = base.verify(property, monitor, !negated);
		return new DoubleNegatedVerificationResult(a, b);
	}

}
