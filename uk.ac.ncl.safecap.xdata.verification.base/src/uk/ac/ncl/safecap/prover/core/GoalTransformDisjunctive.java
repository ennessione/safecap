package uk.ac.ncl.safecap.prover.core;

import java.util.List;

public abstract class GoalTransformDisjunctive extends GoalTransform {

	protected GoalTransformDisjunctive(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	public int apply(ProofGoal goal, Object data) {
		goal.getGoalContainer().nextStage();
		final List<ProofGoal> result = doTransform(goal, data);
		goal.getGoalContainer().replaceDisjunctive(goal, result);
		return result.size();
	}

}
