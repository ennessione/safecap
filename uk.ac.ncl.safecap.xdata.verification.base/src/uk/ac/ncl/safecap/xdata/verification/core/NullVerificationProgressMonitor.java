package uk.ac.ncl.safecap.xdata.verification.core;

import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public class NullVerificationProgressMonitor implements IVerificationProgressMonitor {
	public static final NullVerificationProgressMonitor INSTANCE = new NullVerificationProgressMonitor();

	private NullVerificationProgressMonitor() {
	}

	@Override
	public void beginTask(String name, int totalWork) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done() {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalWorked(double work) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTaskName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subTask(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void worked(int work) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkedProperty(ICondition property, VerificationResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void report(String message) {
		// TODO Auto-generated method stub

	}

}
