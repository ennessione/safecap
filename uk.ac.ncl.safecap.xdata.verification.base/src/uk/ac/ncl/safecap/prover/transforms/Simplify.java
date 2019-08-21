package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class Simplify extends GoalTransform {
	public static String NAME = "simp";

	protected Simplify(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final SimplifyConfig config = (SimplifyConfig) data;

		if (config.newGoal != null) {
			return Collections.singletonList(makeGoal(goal, config.newGoal));
		} else if (config.hyp != null) {
			transformHyp(goal, config.hyp,
					new Transforms(config.hyp.getHypothesis(), config.newHyp, getName(), false, goal.getGoalContainer()));
			return Collections.singletonList(goal);
		} else {
			return Collections.singletonList(goal);
		}
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		final CLExpression newExpr = goal.getFormula().simplify(goal.getTypingContext());
		if (newExpr != goal.getFormula()) {
			return new SimplifyConfig(newExpr, null, null);
		}

		for (final ProofHypothesis hyp : goal) {
			if (!hyp.isExclude()) {
				final CLExpression simplified = hyp.getFormula().simplify(goal.getTypingContext());
				if (simplified != hyp.getFormula()) {
					return new SimplifyConfig(null, hyp, simplified);
				}
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private class SimplifyConfig {
		CLExpression newGoal;
		ProofHypothesis hyp;
		CLExpression newHyp;

		public SimplifyConfig(CLExpression newGoal, ProofHypothesis hyp, CLExpression newHyp) {
			this.newGoal = newGoal;
			this.hyp = hyp;
			this.newHyp = newHyp;
		}

	}

}
