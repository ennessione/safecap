package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class ContradictionInHyp extends GoalTransform {
	public static String NAME = "contrinhyp";

	protected ContradictionInHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		ProofGoal g = makeGoal(goal, CLAtomicExpression.TRUE, data.toString());
		g.getGoal().setProperty("configuration", data);
		return Collections.singletonList(g);
	}

	@Override
	public Object isApplicable(ProofGoal goal) {

		ContradictionInHypConfiguration c1 = directContradiction(goal);
		if (c1 != null)
			return c1;
		ContradictionInHypConfiguration c2 = twoPredicateContradiction(goal);
		if (c2 != null)
			return c2;
		ContradictionInHypConfiguration c3 = equalityContradiction(goal);
		if (c3 != null)
			return c3;
		
		return null;
	}

	private ContradictionInHypConfiguration directContradiction(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			if (!hyp.isExclude()) {
				final CLExpression simplified = hyp.getFormula().simplify(goal.getTypingContext());
				if (simplified.getTag() == alphabet.OP_FALSE) {
					return new ContradictionInHypConfiguration("direct", hyp.getFormula());
				}
			}
		}
		return null;
	}

	private ContradictionInHypConfiguration twoPredicateContradiction(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			final CLExpression negated = CLUtils.negate(hyp.getFormula());
			final CLExpression matching = findPredicateInHypotheses(goal, negated);
			if (matching != null) {
				return new ContradictionInHypConfiguration("pair", hyp.getFormula(), matching);
			}
		}
		return null;
	}

	private ContradictionInHypConfiguration equalityContradiction(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			if (hyp.getFormula().getTag() == alphabet.OP_EQL) {
				final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
				final CLExpression matching1 = contrTest(goal, hyp, binary.getLeft(), binary.getRight());
				if (matching1 != null)
					return new ContradictionInHypConfiguration("eql", hyp.getFormula(), matching1);
				final CLExpression matching2 = contrTest(goal, hyp, binary.getRight(), binary.getLeft());
				if (matching2 != null)
					return new ContradictionInHypConfiguration("eql", hyp.getFormula(), matching2);
			}
		}

		return null;
	}

	private CLExpression contrTest(ProofGoal goal, ProofHypothesis hyp, CLExpression left, CLExpression right) {
		try {
			if (CLUtils.isConstant(left, goal.getTypingContext())) {
				final CLBinaryExpression other = findPredicate(goal, hyp, right);
				if (other == null) {
					return null;
				}
				CLBinaryExpression test;
				if (other.getLeft().equals(right)) {
					test = new CLBinaryExpression(alphabet.OP_EQL, left, other.getRight());
				} else {
					test = new CLBinaryExpression(alphabet.OP_EQL, left, other.getLeft());
				}

				try {
					final Object val = test.getValue(getSDAContext().getRootRuntimeContext());
					if (val != null && val.equals(Boolean.FALSE)) {
						return other;
					}
				} catch (final Throwable e) {
					return null;
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	private CLBinaryExpression findPredicate(ProofGoal goal, ProofHypothesis ohyp, CLExpression expression) {
		for (final ProofHypothesis hyp : goal) {
			if (hyp != ohyp && hyp.getFormula().getTag() == alphabet.OP_EQL) {
				final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
				if (binary.getLeft().equals(expression) || binary.getRight().equals(expression)) {
					return binary;
				}
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
