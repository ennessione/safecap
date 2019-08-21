package uk.ac.ncl.safecap.xdata.provers;

import java.util.Map;

public abstract class ReportingTool<T> extends ToolConnection<T> {
	private final String[] args;
	private final Map<String, T> status_map;

	protected ReportingTool(String tool_path, String[] args, T initial_status, Map<String, T> status_map) {
		super(tool_path, initial_status);
		this.args = args;
		this.status_map = status_map;
	}

	@Override
	public String[] getToolArguments() {
		return args;
	}

	public T statusReport(String line) {
		for (final String pattern : status_map.keySet()) {
			if (line.contains(pattern)) {
				return status_map.get(pattern);
			}
		}

		return null;
	}

	@Override
	public void processLine(String line) {
		final T st = statusReport(line);
		if (st != null) {
			status = st;
		}

		parseReport(line);
	}

	public abstract void parseReport(String line);

	public void log(String message) {
		log.append(message);
		log.append("\n");
	}

}
