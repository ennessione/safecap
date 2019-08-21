package uk.ac.ncl.safecap.xdata.verification.core;

import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public interface IPropertyVerifier {

	VerificationResult verify(ICondition condition, IVerificationProgressMonitor monitor, boolean negated);
}
