package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class IntersectionToPredicate extends GoalTransform {
	public static String NAME = "inter elim";

	protected IntersectionToPredicate(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final IntersectionToPredicateConfig conf = getConfig(goal);
		final List<CLExpression> conj = new ArrayList<>();

		for (final CLExpression val : conf.multi.getParts()) {
			final CLExpression ein = new CLBinaryExpression(alphabet.OP_NOTIN, val, conf.base);
			conj.add(ein);
		}

		final CLExpression newGoal = CLUtils.buildConjunct(conj, null);
		return Collections.singletonList(makeGoal(goal, newGoal));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getConfig(goal);
	}

	public IntersectionToPredicateConfig getConfig(ProofGoal goal) {
		// x /\ {A, B, C} == {}
		if (goal.getFormula().getTag() == alphabet.OP_EQL) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) goal.getFormula();
			if (bexpr.getLeft().getTag() == alphabet.OP_INTER && bexpr.getRight().getTag() == alphabet.OP_EMPTYSET) {
				return getConf2((CLMultiExpression) bexpr.getLeft());
			} else if (bexpr.getRight().getTag() == alphabet.OP_INTER && bexpr.getLeft().getTag() == alphabet.OP_EMPTYSET) {
				return getConf2((CLMultiExpression) bexpr.getRight());
			}
		}

		return null;
	}

	private IntersectionToPredicateConfig getConf2(CLMultiExpression multi) {
		if (multi.getParts().size() == 2) {
			if (multi.getParts().byIndex(0).getTag() == alphabet.SETC) {
				return new IntersectionToPredicateConfig(multi.getParts().byIndex(1), (CLMultiExpression) multi.getParts().byIndex(0));
			} else if (multi.getParts().byIndex(1).getTag() == alphabet.SETC) {
				return new IntersectionToPredicateConfig(multi.getParts().byIndex(0), (CLMultiExpression) multi.getParts().byIndex(1));
			}
		}

		return null;
	}

	class IntersectionToPredicateConfig {
		CLExpression base;
		CLMultiExpression multi;

		public IntersectionToPredicateConfig(CLExpression base, CLMultiExpression multi) {
			this.base = base;
			this.multi = multi;
		}

	}

	@Override
	public String getName() {
		return NAME;
	}

}
