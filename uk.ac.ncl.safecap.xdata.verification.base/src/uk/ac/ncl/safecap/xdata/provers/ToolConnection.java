package uk.ac.ncl.safecap.xdata.provers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

public abstract class ToolConnection<T> implements Runnable {
	private InputStream stderr;
	private InputStream stdout;
	private BufferedWriter stdin;
	private BufferedReader bout;
	private BufferedReader berr;
	private Process process;

	private final String tool_path;
	protected StringBuffer log;
	protected T status;
	protected boolean hasErrors = false;

	protected ToolConnection(String tool_path, T initial_status) {
		this.tool_path = tool_path;
		this.status = initial_status;

		log = new StringBuffer();
	}

	public String getToolPath() {
		return tool_path;
	}

	/**
	 * Command line arguments like path to the script file
	 * 
	 * @return
	 * @throws IOException
	 */
	public abstract String[] getToolArguments();

	@Override
	public void run() {
		hasErrors = false;
		try {
			String s = readErrLine();
			while (s != null) {
				s = readErrLine();
				if (Properties.DEBUG) {
					System.err.println("~~~ error line: " + s);
				}
			}

			berr.close();
			stderr.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public T getStatus() {
		return status;
	}

	public String getReport() {
		return log.toString();
	}

	public void stop() {
		try {
			if (process != null) {
				// System.out.println("Killing process " + getFullPath());
				stdout.close();
				stderr.close();
				stdin.close();
				process.destroy();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void process(IProgressMonitor monitor) throws IOException {
		process = mkProcess();
		stderr = process.getErrorStream();
		stdout = process.getInputStream();
		stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		bout = new BufferedReader(new InputStreamReader(stdout));
		berr = new BufferedReader(new InputStreamReader(stderr));

		// stdin.write(":help\n");

		final Thread thread = new Thread(this);
		thread.start();

		if (monitor != null) {
			CancellationListener.start(this, monitor);
		}

		try {
			String s = readLine();
			while (s != null && (monitor == null || !monitor.isCanceled())) {
				processLine(s);
				s = readLine();
			}
			try {
				berr.close();
				bout.close();
				stdout.close();
				stdin.close();
			} catch (final Throwable e) {
				// ignore
			}
			finalReport();

			if (Properties.DEBUG) {
				System.out.println("### Finished " + getFullPath());
			}

		} catch (final IOException e) {

			e.printStackTrace();
			log.append("\n***\n");
			log.append("EXCEPTION:" + e.getMessage() + "\n");
			log.append("***\n");
		}
	}

	@SuppressWarnings("rawtypes")
	static class CancellationListener implements Runnable {

		private final ToolConnection tool;
		private final IProgressMonitor monitor;

		public CancellationListener(ToolConnection tool, IProgressMonitor monitor) {
			this.tool = tool;
			this.monitor = monitor;
		}

		public static void start(ToolConnection tool, IProgressMonitor monitor) {
			new Thread(new CancellationListener(tool, monitor)).start();
			;
		}

		@Override
		public void run() {
			try {
				while (!monitor.isCanceled()) {
					Thread.sleep(300);
				}
			} catch (final Throwable e) {
			} finally {
				// System.out.println("isCancelled:" + monitor.isCanceled());
				tool.stop();
			}
		}
	}

	public abstract void finalReport();

	public abstract void processLine(String line);

	public BufferedWriter getStdin() {
		return stdin;
	}

	public String getFullPath() {
		final StringBuilder sb = new StringBuilder();
		for (final String s : getToolArguments()) {
			sb.append(s);
			sb.append(" ");
		}
		return tool_path + " " + sb.toString();
	}

	private Process mkProcess() throws IOException {
		if (Properties.DEBUG) {
			System.out.println("### Executing " + getFullPath());
		}

		final List<String> cmd = new ArrayList<>();
		cmd.add(tool_path);
		for (final String s : getToolArguments()) {
			cmd.add(s);
		}

		// Process process = Runtime.getRuntime().exec(cmd.toArray(new
		// String[cmd.size()]));
		final Process process = Runtime.getRuntime().exec(getFullPath());
		return process;
	}

	private String readLine() throws IOException {
		final String line = bout.readLine();
		return line;
	}

	private String readErrLine() throws IOException {
		try {
			final String line = berr.readLine();
			if (line != null) {
				processLine(line);
				if (Properties.DEBUG) {
					System.err.println(line);
				}
				hasErrors = true;
			}
			return line;
		} catch (final Throwable e) {
			return null;
		}
	}

}
