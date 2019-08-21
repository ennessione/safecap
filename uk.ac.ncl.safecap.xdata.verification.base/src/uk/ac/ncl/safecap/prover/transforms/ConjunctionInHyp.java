package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class ConjunctionInHyp extends GoalTransform {
	public static String NAME = "hyp conj";

	protected ConjunctionInHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final ProofHypothesis hyp = (ProofHypothesis) data;
		final CLMultiExpression multi = (CLMultiExpression) hyp.getFormula();

		transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(), multi.getParts().byIndex(0), getName(), false, goal.getGoalContainer()),
				hyp.getCanonicalName() + ":" + 0);

		for (int i = 1; i < multi.getParts().size(); i++) {
			goal.addHypothesis(multi.getParts().byIndex(i), getName(), goal.getGoalContainer(), hyp.getCanonicalName() + ":" + i);
		}

		return Collections.singletonList(makeGoal(goal, goal.getGoal().getFormula()));
	}

	private ProofHypothesis getMatchingHypothesis(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			if (!hyp.isExclude() && hyp.getFormula().getTag() == alphabet.OP_AND) {
				return hyp;
			}
		}
		return null;
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getMatchingHypothesis(goal);
	}

	@Override
	public String getName() {
		return NAME;
	}

}
