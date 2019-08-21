package uk.ac.ncl.safecap.prover.core;

import java.util.List;

import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult.RESULT;

public abstract class BaseDisjunctiveContainer extends BaseGoalContainer {
	private Boolean closed = null;
	private Boolean stuck = null;

	@Override
	public TYPE getType() {
		return TYPE.DISJUNCTIVE;
	}

	@Override
	public ProofGoal getAnyOpenGoal() {
		if (Boolean.TRUE == closed || Boolean.TRUE == stuck) {
			return null;
		}

		for (final IGoalContainer x : children) {
			if (!x.isClosed() && !x.hasStuckGoals()) {
				final ProofGoal g = x.getAnyOpenGoal();
				if (g != null) 
						return g;
			}
		}

		for (final ProofGoal g : goals) {
			if (g.isStuck()) {
				stuck = Boolean.TRUE;
				return null;
			}
			if (g.isClosed()) {
				closed = Boolean.TRUE;
				return null;
			}
			if (!g.isClosed() && !g.isStuck()) {
				return g;
			}
		}

		stuck = Boolean.TRUE;

		return null;
	}

	@Override
	public boolean isClosed() {
		if (closed != null) {
			return closed;
		}

		for (final IGoalContainer x : children) {
			if (x.isClosed()) {
				closed = Boolean.TRUE;
				return true;
			}
		}

		for (final ProofGoal goal : goals) {
			if (goal.isClosed()) {
				closed = Boolean.TRUE;
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasStuckGoals() {
		if (stuck == Boolean.TRUE) {
			return false;
		}

		for (final IGoalContainer x : children) {
			if (!x.hasStuckGoals()) {
				return false;
			}
		}

		for (final ProofGoal goal : goals) {
			if (!goal.isStuck()) {
				return false;
			}
		}

		stuck = Boolean.TRUE;

		return true;
	}

	@Override
	public RESULT getStatus() {
		boolean allFalse = true;

		for (final IGoalContainer x : children) {
			if (x.isClosed()) {
				if (x.getStatus() == VerificationResult.RESULT.VALID) {
					return VerificationResult.RESULT.VALID;
				}
			} else {
				allFalse = false;
			}
		}

		for (final ProofGoal goal : goals) {
			if (goal.isClosed()) {
				if (goal.getStatus().isValid()) {
					return VerificationResult.RESULT.VALID;
				}
			} else {
				allFalse = false;
			}
		}

		return allFalse ? VerificationResult.RESULT.INVALID : VerificationResult.RESULT.UNKNOWN;
	}

	@Override
	public void replaceConjunctive(ProofGoal goal, List<ProofGoal> newGoals) {
		final ConjunctiveSubContainer sub = new ConjunctiveSubContainer(this);
		for (final ProofGoal g : newGoals) {
			sub.addGoal(g);
		}

		addChild(sub);
		goals.remove(goal);
	}

	@Override
	public void replaceDisjunctive(ProofGoal goal, List<ProofGoal> newGoals) {
		replaceConjunctive(goal, newGoals);
	}
}
