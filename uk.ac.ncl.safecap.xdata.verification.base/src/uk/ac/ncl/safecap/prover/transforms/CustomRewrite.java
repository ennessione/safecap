package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.prime.sim.lang.CLRewriteRule.RewriteRuleState;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ICLFormulaProvider;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class CustomRewrite extends GoalTransform {
	private final CLRewriteRule rule;

	public CustomRewrite(TacticPackage tacticPackage, CLRewriteRule rule) {
		super(tacticPackage);
		this.rule = rule;
	}

	@Override
	public String getName() {
		return rule.getName();
	}

	@Override
	public Object isApplicable(ProofGoal source) {
		final Object goal = appliesTo(source);
		if (goal != null) {
			return goal;
		}
		for (final ProofHypothesis t : source) {
			final Object hyp = appliesTo(t);
			if (hyp != null) {
				return hyp;
			}
		}
		return null;
	}

	private Object appliesTo(ICLFormulaProvider source) {
		return rule.isApplicable(source.getFormula(), source, getSDAContext().getRootRuntimeContext().getRootContext());
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final RewriteRuleState state = (RewriteRuleState) data;
		if (state.getUserData() == goal) {
			return Collections.singletonList(makeGoal(goal, state.apply(), state.getName()));
		} else if (state.getUserData() instanceof ProofHypothesis) {
			final ProofHypothesis hyp = (ProofHypothesis) state.getUserData();
			transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(), state.apply(), getName(), true, goal.getGoalContainer()));
			return Collections.singletonList(makeGoal(goal, goal.getGoal().getFormula()));
		}

		return null;
	}

}
