package uk.ac.ncl.safecap.xmldata.base;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.xmldata.base.ErrorLogEntry.SEVERITY;

public class ErrorLogStaged {
	private final Map<String, ErrorLog> stages;
	private final List<String> stageNames;
	private ErrorLog current;
	private long timer;

	public ErrorLogStaged() {
		stages = new HashMap<>();
		stageNames = new ArrayList<>();
	}

	public void start(String section) {
		current = new ErrorLog();
		stages.put(section, current);
		stageNames.add(section);
		timer = System.currentTimeMillis();
	}

	public void stop() {
		final DecimalFormat x = new DecimalFormat("##.##");
		logInfo("Finished in " + x.format((System.currentTimeMillis() - timer) / 1000.0) + " seconds");
		logInfo("Errors: " + current.getErrors() + "; warnings: " + current.getWarnings());
	}

	public List<String> getSections() {
		return stageNames;
	}

	public ErrorLog getCurrentSection() {
		return current;
	}

	public void log(ErrorLogEntry entry) {
		current.log(entry);
	}

	public void logError(String message) {
		log(new ErrorLogEntry(SEVERITY.CRITICAL, message));
	}

	public void logError(Throwable exception) {
		log(new ErrorLogEntry(SEVERITY.CRITICAL, exception));
	}

	public void logWarning(String message) {
		log(new ErrorLogEntry(SEVERITY.WARNING, message));
	}

	public void logWarning(Throwable exception) {
		log(new ErrorLogEntry(SEVERITY.WARNING, exception));
	}

	public void logInfo(String message) {
		log(new ErrorLogEntry(SEVERITY.INFO, message));
	}

	public Collection<ErrorLogEntry> getEntries(String stage) {
		return stages.get(stage).getEntries();
	}
}
