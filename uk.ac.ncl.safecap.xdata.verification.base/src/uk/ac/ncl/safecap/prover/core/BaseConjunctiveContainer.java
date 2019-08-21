package uk.ac.ncl.safecap.prover.core;

import java.util.List;

import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult.RESULT;

public abstract class BaseConjunctiveContainer extends BaseGoalContainer {
	private Boolean closed = null;
	private Boolean stuck = null;
	
	@Override
	public TYPE getType() {
		return TYPE.CONJUNCTIVE;
	}

	@Override
	public ProofGoal getAnyOpenGoal() {
		if (Boolean.TRUE == closed || Boolean.TRUE == stuck) {
			return null;
		}
		
		for (final IGoalContainer x : children) {
			final ProofGoal g = x.getAnyOpenGoal();
			if (g != null) {
				return g;
			}
		}

		for (final ProofGoal goal : goals) {
			if (!goal.isClosed() && !goal.isStuck()) {
				return goal;
			}
		}

		return null;
	}

	@Override
	public boolean isClosed() {
		if (closed != null) {
			return closed;
		}
		
		for (final IGoalContainer x : children) {
			if (!x.isClosed()) {
				return false;
			}
		}

		for (final ProofGoal goal : goals) {
			if (!goal.isClosed()) {
				return false;
			}
		}

		closed = Boolean.TRUE;
		
		return true;
	}

	@Override
	public boolean hasStuckGoals() {
		if (stuck == Boolean.TRUE) {
			return false;
		}
		
		
		for (final IGoalContainer x : children) {
			if (x.hasStuckGoals()) {
				stuck = Boolean.TRUE;
				return true;
			}
		}

		for (final ProofGoal goal : goals) {
			if (goal.isStuck()) {
				stuck = Boolean.TRUE;
				return true;
			}
		}

		return false;
	}

	@Override
	public RESULT getStatus() {
		boolean allTrue = true;

		for (final IGoalContainer x : children) {
			if (x.isClosed()) {
				if (x.getStatus() != RESULT.VALID) {
					return VerificationResult.RESULT.INVALID;
				}
			} else {
				allTrue = false;
			}
		}

		for (final ProofGoal goal : goals) {
			if (goal.isClosed()) {
				if (!goal.getStatus().isValid()) {
					return VerificationResult.RESULT.INVALID;
				}
			} else {
				allTrue = false;
			}
		}

		return allTrue ? VerificationResult.RESULT.VALID : VerificationResult.RESULT.UNKNOWN;
	}

	@Override
	public void replaceDisjunctive(ProofGoal goal, List<ProofGoal> newGoals) {
		final DisjunctiveSubContainer sub = new DisjunctiveSubContainer(this);
		for (final ProofGoal g : newGoals) {
			final ConjunctiveSubContainer csub = new ConjunctiveSubContainer(sub);
			csub.addGoal(g);
			sub.addChild(csub);
		}

		addChild(sub);
		goals.remove(goal);
	}

	@Override
	public void replaceConjunctive(ProofGoal goal, List<ProofGoal> newGoals) {
		replace(goal, newGoals);
	}
}
