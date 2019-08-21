package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.xdata.provers.prob.ProBPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class MultiProver implements IPropertyVerifier {
	public static IPropertyVerifier PROB_AND_NATIVE = new MultiProver(ProBPropertyVerifier.INSTANCE, NativeEvaluationVerifier.INSTANCE);

	private final IPropertyVerifier[] base;

	public MultiProver(IPropertyVerifier... base) {
		this.base = base;
	}

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		final VerificationResult[] result = new VerificationResult[base.length];
		for (int i = 0; i < base.length; i++) {
			result[i] = base[i].verify(property, monitor, false);
		}
		return new CombinedVerificationResult(result);
	}

}
