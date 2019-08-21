package uk.ac.ncl.safecap.xdata.verification.core;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;

public class WrappingProgressMonitor implements IVerificationProgressMonitor {
	private final IProgressMonitor base;
	private int processed;
	private int total;
	private long startTime;

	public WrappingProgressMonitor(IProgressMonitor base) {
		super();
		this.base = base;
	}

	@Override
	public void beginTask(String name, int totalWork) {
		base.beginTask(name, totalWork);
		total = totalWork;
		startTime = System.currentTimeMillis();
	}

	@Override
	public void done() {
		base.done();
	}

	@Override
	public void internalWorked(double work) {
		base.internalWorked(work);
	}

	@Override
	public boolean isCanceled() {
		return base.isCanceled();
	}

	@Override
	public void setCanceled(boolean value) {
		base.setCanceled(value);
	}

	@Override
	public void setTaskName(String name) {
		base.setTaskName(name);
	}

	@Override
	public void subTask(String name) {
		base.subTask(name);
	}

	@Override
	public void worked(int work) {
		processed += work;
		final long runTime = System.currentTimeMillis() - startTime;
		final double timePerUnit = (double) runTime / (double) processed;
		final double timeToWork = timePerUnit * (total - processed);

		final String running = SDAUtils.formatTime(runTime);
		final String left = SDAUtils.formatTime(timeToWork);

		base.worked(work);
		base.setTaskName("Completed " + processed + " out of " + total + "; Elapsed: " + running + ";  Remaining: " + left);
	}

	@Override
	public void checkedProperty(ICondition property, VerificationResult result) {
		base.subTask(property.getName() + ": " + result.toString());

	}

	@Override
	public void report(String message) {
	}

}
