package uk.ac.ncl.safecap.prover.transforms.trace;

public class Initial extends GoalProvenance {
	public static final Initial INSTANCE = new Initial();

	private Initial() {
		super(null);
	}

	@Override
	public String toString() {
		return "initial";
	}
}
