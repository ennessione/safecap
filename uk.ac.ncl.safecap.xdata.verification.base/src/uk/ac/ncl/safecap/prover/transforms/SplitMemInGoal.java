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
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class SplitMemInGoal extends GoalTransform {
	public static String NAME = "split mem";

	protected SplitMemInGoal(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLBinaryExpression binary = (CLBinaryExpression) goal.getFormula();
		final CLMultiExpression unionParts = (CLMultiExpression) binary.getRight();
		final CLExpression element = binary.getLeft();

		final Config config = (Config) data;
		final int tag = config.tag;
		final int opr = config.operator;

		final Collection<CLExpression> parts = new ArrayList<>();
		for (final CLExpression set : unionParts.getParts()) {
			parts.add(new CLBinaryExpression(opr, element, set).simplify(goal.getTypingContext()));
		}

		if (tag == alphabet.OP_UNION) {
			return Collections.singletonList(
					makeGoal(goal, opr == alphabet.OP_IN ? CLUtils.buildDisjunct(parts, null) : CLUtils.buildConjunct(parts, null)));
		} else if (tag == alphabet.OP_INTER) {
			return Collections.singletonList(
					makeGoal(goal, opr == alphabet.OP_IN ? CLUtils.buildConjunct(parts, null) : CLUtils.buildDisjunct(parts, null)));
		} else {
			assert false;
			return Collections.singletonList(goal);
		}
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		if (goal.getFormula().getTag() == alphabet.OP_IN || goal.getFormula().getTag() == alphabet.OP_NOTIN) {
			final CLBinaryExpression binary = (CLBinaryExpression) goal.getFormula();
			if (binary.getRight().getTag() == alphabet.OP_UNION || binary.getRight().getTag() == alphabet.OP_INTER) {
				return new Config(binary.getRight().getTag(), goal.getFormula().getTag());
			}
		}
		return null;
	}

	static class Config {
		private final int tag;
		private final int operator;

		private Config(int tag, int operator) {
			super();
			this.tag = tag;
			this.operator = operator;
		}

		public int getTag() {
			return tag;
		}

		public int getOperator() {
			return operator;
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

}
