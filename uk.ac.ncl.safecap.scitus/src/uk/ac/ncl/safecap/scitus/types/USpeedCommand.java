package uk.ac.ncl.safecap.scitus.types;

public abstract class USpeedCommand implements UIConstraintCommand {
	public abstract double getLowerBound();

	public abstract double getHigherBound();
}
