package uk.ac.ncl.safecap.prover.transforms.trace;

public class RuleTrace extends GoalProvenance {
	private final String rule;

	public RuleTrace(GoalProvenance parent, String rule) {
		super(parent);
		this.rule = rule;
	}

	public String getRule() {
		return rule;
	}

	@Override
	public String toString() {
		return rule;
	}
}
