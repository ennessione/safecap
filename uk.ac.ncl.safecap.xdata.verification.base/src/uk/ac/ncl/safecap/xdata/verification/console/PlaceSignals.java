package uk.ac.ncl.safecap.xdata.verification.console;

public class PlaceSignals extends SchemaBaseCommand {
	public static PlaceSignals INSTANCE = new PlaceSignals();

	private PlaceSignals() {
	}

	@Override
	public String getName() {
		return "placesignals";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		final CreateNewSignalOnSegment cmd = new CreateNewSignalOnSegment(console.getGraphicalEditPart(), console.getProject());
		cmd.run();
	}

}
