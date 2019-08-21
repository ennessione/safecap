package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class AssymetricDoubleProver implements IPropertyVerifier {
	private final IPropertyVerifier base;

	public AssymetricDoubleProver(IPropertyVerifier base) {
		this.base = base;
	}

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		final VerificationResult a = base.verify(property, monitor, negated);
		final VerificationResult b = base.verify(property, monitor, !negated);
		return new CombinedVerificationResult(a, b);
	}

}
