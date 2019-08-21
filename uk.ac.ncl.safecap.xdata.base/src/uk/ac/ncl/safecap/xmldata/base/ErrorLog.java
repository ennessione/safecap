package uk.ac.ncl.safecap.xmldata.base;

import java.util.ArrayList;
import java.util.Collection;

public class ErrorLog {
	private final Collection<ErrorLogEntry> entries;
	private int errors, warnings, infos;

	public ErrorLog() {
		entries = new ArrayList<>();
	}

	public void log(ErrorLogEntry entry) {
		entries.add(entry);
		switch (entry.getSeverity()) {
		case CRITICAL:
			errors++;
			break;
		case WARNING:
			warnings++;
			break;
		case INFO:
			infos++;
			break;
		}
	}

	public void clear() {
		entries.clear();
		errors = 0;
		warnings = 0;
		infos = 0;
	}

	public int getErrors() {
		return errors;
	}

	public int getWarnings() {
		return warnings;
	}

	public int getInfos() {
		return infos;
	}

	public Collection<ErrorLogEntry> getEntries() {
		return entries;
	}
}
