package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class ImplicationDisjunction extends GoalTransform {
	public static String NAME = "impl disj elim";

	protected ImplicationDisjunction(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLBinaryExpression bexpr = (CLBinaryExpression) goal.getFormula();
		final ImplicationDisjunctionConf conf = getConf(goal);

		for (final ProofHypothesis hyp : goal) {
			if (hyp.getFormula() == conf.source) {
				transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(), conf.target, getName(), false, goal.getGoalContainer()));
			}
		}

		return Collections.singletonList(makeGoal(goal, bexpr));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getConf(goal);
	}

	private ImplicationDisjunctionConf getConf(ProofGoal goal) {
		if (goal.getFormula().getTag() == alphabet.OP_IMPLIES) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) goal.getFormula();
			final CLExpression cond = CLUtils.negate(bexpr.getLeft());
			for (final ProofHypothesis hyp : goal) {
				if (hyp.getFormula().getTag() == alphabet.OP_OR) {
					final CLMultiExpression b = (CLMultiExpression) hyp.getFormula();
					if (b.getParts().size() == 2) {
						final CLExpression l = b.getParts().byIndex(0);
						final CLExpression r = b.getParts().byIndex(1);
						if (l.equals(cond)) {
							return new ImplicationDisjunctionConf(b, r);
						}
						if (r.equals(cond)) {
							return new ImplicationDisjunctionConf(b, l);
						}
					}
				}
			}
		}

		return null;
	}

	class ImplicationDisjunctionConf {
		CLMultiExpression source;
		CLExpression target;

		public ImplicationDisjunctionConf(CLMultiExpression source, CLExpression target) {
			this.source = source;
			this.target = target;
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

}
