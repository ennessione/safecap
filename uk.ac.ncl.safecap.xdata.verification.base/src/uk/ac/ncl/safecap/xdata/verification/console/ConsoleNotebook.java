package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class ConsoleNotebook {
	private transient SDARuntimeExecutionContext context;
	private final List<ConsoleNotebookEntry> entries;

	public ConsoleNotebook() {
		context = new SlottableSDARuntimeExecutionContext();
		entries = new ArrayList<>();
	}

	public ConsoleNotebookEntry addEntry(String input) {
		final ConsoleNotebookEntry entry = new ConsoleNotebookEntry(input);
		entries.add(entry);
		return entry;
	}

	public SDARuntimeExecutionContext getContext() {
		return context;
	}

	public List<ConsoleNotebookEntry> getEntries() {
		return entries;
	}
}
