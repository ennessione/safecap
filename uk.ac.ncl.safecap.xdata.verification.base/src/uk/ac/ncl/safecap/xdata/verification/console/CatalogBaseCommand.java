package uk.ac.ncl.safecap.xdata.verification.console;

public abstract class CatalogBaseCommand extends BaseCommand {

	@Override
	public boolean handle(ISafeCapConsole console, String command, String[] arguments) {
		if (console.getRootCatalog() != null) {
			return super.handle(console, command, arguments);
		}
		return false;
	}

}
