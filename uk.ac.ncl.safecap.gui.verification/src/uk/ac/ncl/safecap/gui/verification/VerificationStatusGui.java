package uk.ac.ncl.safecap.gui.verification;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import uk.ac.ncl.safecap.scripting.verification.IVerificationStatus;

public class VerificationStatusGui implements IVerificationStatus {
	private final Label _status;
	private final IProgressMonitor _monitor;
	private final IVerificationTool _tool;
	private boolean _terminated = false;

	public VerificationStatusGui(IVerificationTool tool, Label status, IProgressMonitor monitor) {
		_status = status;
		_monitor = monitor;
		_tool = tool;
	}

	@Override
	public void begin() {
		_monitor.beginTask(_tool.getName(), IProgressMonitor.UNKNOWN);
	}

	@Override
	public void begin(int totalWork) {
		_monitor.beginTask(_tool.getName(), totalWork);
	}

	@Override
	public void progress(int work) {
		_monitor.worked(work);
	}

	@Override
	public void end() {
		_monitor.done();
		_terminated = true;
		setStatusMessage("Finished");
	}

	@Override
	public void setStatusMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				_status.setText("Status: " + message);
			}
		});
	}

	@Override
	public boolean isTerminated() {
		return _terminated;
	}

	public void terminate() {
		_terminated = true;
		setStatusMessage("Terminated");
	}

}
