package uk.ac.ncl.safecap.scitus.types;

import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

public abstract class UConstraint {
	private final UIConstraintCommand command;

	public UConstraint(UIConstraintCommand command) {
		this.command = command;
	}

	public UIConstraintCommand getCommand() {
		return command;
	}

	public abstract boolean evaluateGuard(SystemState state);
}
