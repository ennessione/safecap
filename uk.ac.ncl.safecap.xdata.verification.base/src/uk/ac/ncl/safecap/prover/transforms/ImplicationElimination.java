package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class ImplicationElimination extends GoalTransform {
	public static String NAME = "impl elim";

	protected ImplicationElimination(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLBinaryExpression impl = (CLBinaryExpression) goal.getFormula();

		final ProofGoal newGoal = makeGoal(goal, impl.getRight());
		newGoal.addHypothesis(impl.getLeft(), NAME, goal.getGoalContainer(), "DER" + goal.getGoal().getStage());
		return Collections.singletonList(newGoal);
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return goal.getFormula().getTag() == alphabet.OP_IMPLIES ? Boolean.TRUE : null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
