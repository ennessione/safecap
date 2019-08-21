package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransformDisjunctive;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class SplitDisjunctionInGoal extends GoalTransformDisjunctive {
	public static String NAME = "split goal disj";

	protected SplitDisjunctionInGoal(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLMultiExpression me = (CLMultiExpression) goal.getFormula();
		final List<ProofGoal> goals = new ArrayList<>(me.getParts().size());
		for (final CLExpression ce : me.getParts().getParts()) {
			goals.add(makeGoal(goal, ce));
		}

		return goals;
	}

	@Override
	public Object isApplicable(ProofGoal source) {
		return source.getFormula().getTag() == alphabet.OP_OR ? Boolean.TRUE : null;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
