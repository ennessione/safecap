package uk.ac.ncl.safecap.gui.verificationreplay;

public class TraceStep {
	private String command;
	private String[] arguments;

	public TraceStep(String raw_command) {
		final int b1 = raw_command.indexOf('(');
		final int b2 = raw_command.indexOf(')');

		if (b2 > b1 && b1 > 0) {
			command = raw_command.substring(0, b1);
			final String args = raw_command.substring(b1 + 1, b2);
			arguments = args.split(",");
			for (int i = 0; i < arguments.length; i++) {
				arguments[i] = arguments[i].trim();
			}
		} else {
			command = raw_command;
			arguments = new String[0];
		}
	}

	public boolean isValid() {
		return command != null;
	}

	public String getCommand() {
		return command;
	}

	public String[] getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return command + arguments;
	}
}
