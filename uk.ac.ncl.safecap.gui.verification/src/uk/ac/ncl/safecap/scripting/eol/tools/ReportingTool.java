package uk.ac.ncl.safecap.scripting.eol.tools;

import java.io.IOException;

import uk.ac.ncl.safecap.scripting.verification.IVerificationStatus;

public abstract class ReportingTool extends ToolConnection {
	private final String args, success_pattern;

	protected ReportingTool(String tool_path, String args, String success, IVerificationStatus status) throws IOException {
		super(tool_path, status);
		this.args = args;
		success_pattern = success;
	}

	@Override
	public String getToolArguments() throws IOException {
		return args;
	}

	public boolean isSuccessReport(String line) {
		if (success_pattern == null) {
			return false;
		} else {
			return line.contains(success_pattern);
		}
	}

	@Override
	public void processLine(String line) {
		if (isSuccessReport(line)) {
			success = true;
		}

		parseReport(line);
	}

	public abstract void parseReport(String line);

	public void log(String message) {
		log.append(message);
		log.append("\n");
	}

	public void setStatus(String message) {
		if (status != null) {
			if (message != null) {
				status.setStatusMessage(message);
			}
			status.progress(1);
		}
	}
}
