package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class SplitConjunct extends GoalTransform {
	public static String NAME = "split conj";

	protected SplitConjunct(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLMultiExpression multi = (CLMultiExpression) goal.getFormula();
		final List<ProofGoal> result = new ArrayList<>();

		int i = 0;
		for (final CLExpression e : multi.getParts()) {
			result.add(makeGoal(goal, e, "conj:" + i++ + " - " + e));
		}

		return result;
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return goal.getFormula().getTag() == alphabet.OP_AND ? Boolean.TRUE : null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
