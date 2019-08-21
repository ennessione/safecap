package uk.ac.ncl.safecap.prover.transforms.trace;

public abstract class GenerativeBase extends RuleTrace {
	private final String sourceId;

	public GenerativeBase(GoalProvenance parent, String rule, String sourceId) {
		super(parent, rule);
		this.sourceId = sourceId;
	}

	public String getSourceId() {
		return sourceId;
	}

	@Override
	public String toString() {
		return getRule() + " on " + sourceId;
	}
}
