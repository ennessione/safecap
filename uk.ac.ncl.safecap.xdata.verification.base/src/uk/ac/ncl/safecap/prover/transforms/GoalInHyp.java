package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class GoalInHyp extends GoalTransform {
	public static String NAME = "hypingoal";

	protected GoalInHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		return Collections.singletonList(makeGoal(goal, CLAtomicExpression.TRUE));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return hasPredicateInHypotheses(goal, goal.getFormula()) ? Boolean.TRUE : null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
