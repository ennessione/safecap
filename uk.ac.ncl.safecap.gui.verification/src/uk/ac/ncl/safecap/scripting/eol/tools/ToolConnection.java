package uk.ac.ncl.safecap.scripting.eol.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.ac.ncl.safecap.scripting.verification.IVerificationStatus;

public abstract class ToolConnection implements Runnable {
	private InputStream stderr;
	private InputStream stdout;
	private BufferedReader bout;
	private BufferedReader berr;

	private final String tool_path;
	protected StringBuffer log;
	protected boolean success = false;

	protected IVerificationStatus status;

	protected ToolConnection(String tool_path, IVerificationStatus status) throws IOException {
		this.tool_path = tool_path;
		this.status = status;

		if (tool_path == null) {
			throw new IOException("Tool path  " + tool_path + " is not valid");
		}

		log = new StringBuffer();
	}

	/**
	 * Command line arguments like path to the script file
	 * 
	 * @return
	 * @throws IOException
	 */
	public abstract String getToolArguments() throws IOException;

	@Override
	public void run() {
		try {
			String s = readErrLine();
			while (s != null && (status == null || !status.isTerminated())) {
				s = readErrLine();
				if (status != null) {
					status.progress(1);
				}
			}

			berr.close();
			stderr.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isSuccessful() {
		return success;
	}

	public String getReport() {
		return log.toString();
	}

	public void process() throws IOException {
		final Process process = mkProcess();
		stderr = process.getErrorStream();
		stdout = process.getInputStream();
		bout = new BufferedReader(new InputStreamReader(stdout));
		berr = new BufferedReader(new InputStreamReader(stderr));

		final Thread thread = new Thread(this);
		thread.start();

		try {
			String s = readLine();
			while (s != null && (status == null || !status.isTerminated())) {
				processLine(s);
				s = readLine();

			}
			bout.close();
			stdout.close();

			finalReport();

		} catch (final IOException e) {

			e.printStackTrace();
			log.append("\n***\n");
			log.append("EXCEPTION:" + e.getMessage() + "\n");
			log.append("***\n");
		}
	}

	public abstract void finalReport();

	public abstract void processLine(String line);

	public String getFullPath() throws IOException {
		return tool_path + " " + getToolArguments();
	}

	private Process mkProcess() throws IOException {
		final String path = getFullPath();
		final Process process = Runtime.getRuntime().exec(path);
		return process;
	}

	private String readLine() throws IOException {
		final String line = bout.readLine();
		return line;
	}

	private String readErrLine() throws IOException {
		final String line = berr.readLine();
		return line;
	}

}
