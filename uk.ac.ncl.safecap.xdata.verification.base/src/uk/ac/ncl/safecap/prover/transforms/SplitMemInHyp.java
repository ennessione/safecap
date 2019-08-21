package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.Collection;
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

public class SplitMemInHyp extends GoalTransform {
	public static String NAME = "split mem hyp x2";

	protected SplitMemInHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final Configuration config = (Configuration) data;
		final ProofHypothesis hyp = config.hyp;

		final int operator = hyp.getFormula().getTag();

		final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
		final CLMultiExpression unionParts = (CLMultiExpression) binary.getRight();
		final CLExpression element = binary.getLeft();

		// x /: A /\ B ==> not (x : A && x : B)
		// x : A /\ B ==> x : A && x : B

		// System.out.println(NAME + ": " + hyp.getFormula());

		final Collection<CLExpression> parts = new ArrayList<>();
		for (final CLExpression set : unionParts.getParts()) {
			parts.add(new CLBinaryExpression(operator, element, set).simplify(goal.getTypingContext()));
		}

		final boolean conjunction = operator == alphabet.OP_NOTIN || config.tag == alphabet.OP_INTER;

		super.transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(),
				conjunction ? CLUtils.buildConjunct(parts, null) : CLUtils.buildDisjunct(parts, null), getName(), goal.getGoalContainer()));

		return Collections.singletonList(makeGoal(goal, goal.getGoal().getFormula()));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getApplicableHyp(goal);
	}

	private Configuration getApplicableHyp(ProofGoal goal) {
		final Configuration hyp0 = getLocalApplicableHyp(goal);
		if (hyp0 != null) {
			return hyp0;
		}

		for (final ProofHypothesis hyp : goal) {
			final Configuration h = getApplicableHyp(hyp);
			if (h != null) {
				return h;
			}
		}

		return null;
	}

	private Configuration getApplicableHyp(ProofHypothesis hyp) {
		if (hyp.getFormula().getTag() == alphabet.OP_IN || hyp.getFormula().getTag() == alphabet.OP_NOTIN) {
			final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
			if (binary.getRight().getTag() == alphabet.OP_UNION
					|| binary.getRight().getTag() == alphabet.OP_INTER && hyp.getFormula().getTag() == alphabet.OP_IN) {
				return new Configuration(hyp, binary.getRight().getTag());
			}
		}
		return null;
	}

	private Configuration getLocalApplicableHyp(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal.getHypothesis()) {
			final Configuration h = getApplicableHyp(hyp);
			if (h != null) {
				return h;
			}
		}

		return null;
	}

	private static class Configuration {
		ProofHypothesis hyp;
		int tag;

		public Configuration(ProofHypothesis hyp, int tag) {
			super();
			this.hyp = hyp;
			this.tag = tag;
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

}
