package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class CaseFromOverride extends OverrideCases {
	public static String NAME = "case ovr";

	protected CaseFromOverride(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CasesDescriptor cd = getCasesDescriptor(goal);
		if (cd != null) {
			final List<ProofGoal> goals = new ArrayList<>();
			// case 1: none of the value
			final CLExpression nonePred = makeNonePredicate(cd);
			final ProofGoal noneCase = makeGoal(goal, goal.getGoal().getFormula(), "ovr:*");
			noneCase.addHypothesis(nonePred, NAME, goal.getGoalContainer(), "DER" + goal.getGoal().getStage());
			goals.add(noneCase);
			int i = 0;
			for (final CLExpression val : cd.values) {
				final CLExpression eql = new CLBinaryExpression(alphabet.OP_EQL, cd.variable, val);
				final ProofGoal valCase = makeGoal(goal, goal.getGoal().getFormula(), "ovr:" + i++ + " - " + val);
				valCase.addHypothesis(eql, NAME, goal.getGoalContainer(), "DER" + goal.getGoal().getStage());
				goals.add(valCase);
			}

			return goals;
		}

		return Collections.singletonList(goal);
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getCasesDescriptor(goal);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isRepeatable() {
		return false;
	}

}
