package uk.ac.ncl.safecap.xdata.provers.prob;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public interface IProBTool {
	IProBTool getInstance(String model) throws IOException;

	VerificationResult.RESULT check(IProgressMonitor monitor) throws Exception;
}
