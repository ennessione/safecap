package uk.ac.ncl.safecap.xdata.verification.core;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public interface IVerificationProgressMonitor extends IProgressMonitor {
	void checkedProperty(ICondition property, VerificationResult result);

	void report(String message);
}
