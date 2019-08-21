package uk.ac.ncl.safecap.xdata.verification.console;

public abstract class SchemaBaseCommand extends BaseCommand {

	@Override
	public boolean handle(ISafeCapConsole console, String command, String[] arguments) {
		if (console.getProject() != null) {
			return super.handle(console, command, arguments);
		}
		return false;
	}

}
