package uk.ac.ncl.safecap.prover.transforms.trace;

public abstract class GoalProvenance {
	private final GoalProvenance parent;

	public GoalProvenance(GoalProvenance parent) {
		this.parent = parent;
	}

	public GoalProvenance getParent() {
		return parent;
	}

}
