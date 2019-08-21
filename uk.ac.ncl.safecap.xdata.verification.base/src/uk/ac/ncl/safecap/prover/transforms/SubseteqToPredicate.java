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

public class SubseteqToPredicate extends GoalTransform {
	public static String NAME = "subseteq elim";

	protected SubseteqToPredicate(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final SubseteqToPredicateConfig conf = getConfig(goal);
		final List<CLExpression> conj = new ArrayList<>();

		for (final CLExpression val : conf.multi.getParts()) {
			final CLExpression ein = new CLBinaryExpression(alphabet.OP_IN, val, conf.set);
			conj.add(ein);
		}

		final CLExpression newGoal = CLUtils.buildConjunct(conj, null);
		return Collections.singletonList(makeGoal(goal, newGoal));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getConfig(goal);
	}

	public SubseteqToPredicateConfig getConfig(ProofGoal goal) {
		if (goal.getFormula().getTag() == alphabet.OP_SUBSETEQ) {
			final CLBinaryExpression bexpr = (CLBinaryExpression) goal.getFormula();
			if (bexpr.getLeft().getTag() == alphabet.SETC) {
				return new SubseteqToPredicateConfig(bexpr.getRight(), (CLMultiExpression) bexpr.getLeft());
			}
		}

		return null;
	}

	class SubseteqToPredicateConfig {
		CLExpression set;
		CLMultiExpression multi;

		public SubseteqToPredicateConfig(CLExpression set, CLMultiExpression multi) {
			this.set = set;
			this.multi = multi;
		}

	}

	@Override
	public String getName() {
		return NAME;
	}

}
