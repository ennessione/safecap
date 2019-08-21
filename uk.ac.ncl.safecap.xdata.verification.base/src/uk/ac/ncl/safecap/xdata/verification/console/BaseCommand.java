package uk.ac.ncl.safecap.xdata.verification.console;

public abstract class BaseCommand implements ISafeCapConsoleCommand {

	public abstract String getName();

	public abstract int getArguments();

	public abstract void execute(ISafeCapConsole console, String[] arguments);

	@Override
	public boolean handle(ISafeCapConsole console, String command, String[] arguments) {
		if (command.equals(getName()) && (getArguments() == -1 || getArguments() == arguments.length)) {
			execute(console, arguments);
			return true;
		}
		return false;
	}

}
