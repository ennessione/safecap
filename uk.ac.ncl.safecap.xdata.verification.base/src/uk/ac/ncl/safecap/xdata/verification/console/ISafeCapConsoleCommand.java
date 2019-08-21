package uk.ac.ncl.safecap.xdata.verification.console;

public interface ISafeCapConsoleCommand {
	boolean handle(ISafeCapConsole console, String command, String[] arguments);
}
