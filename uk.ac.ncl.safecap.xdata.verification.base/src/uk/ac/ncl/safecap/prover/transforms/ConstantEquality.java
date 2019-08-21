package uk.ac.ncl.safecap.prover.transforms;

import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.IGoalContainer;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class ConstantEquality extends EqualityRewriter {
	public static String NAME = "eql const";

	protected ConstantEquality(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected boolean doHypothesesRewrite() {
		return true;
	}

	@Override
	protected Subst getSubstHyp(IGoalContainer po, ProofGoal goal) {
		final Set<String> goalFree = goal.getFormula().getFreeIdentifiers();

		for (final ProofHypothesis t : goal) {
			final Subst subst = findSubst(goal, goalFree, t);
			if (subst != null) {
				return subst;
			}
		}

		return null;
	}

	@Override
	protected Subst findSubst(ProofGoal goal, Set<String> goalFree, ProofHypothesis t) {
		final CLExpression h = t.getFormula();
		if (!t.isExclude() && h.getTag() == alphabet.OP_EQL) {
			final CLBinaryExpression h_eql = (CLBinaryExpression) h;
			CLIdentifier h_eql_id;
			CLExpression h_eql_expr;
			if (isValidRewriteExpressionLeft(goal, h_eql)) {
				h_eql_id = (CLIdentifier) h_eql.getLeft();
				h_eql_expr = h_eql.getRight();
			} else if (isValidRewriteExpressionRight(goal, h_eql)) {
				h_eql_id = (CLIdentifier) h_eql.getRight();
				h_eql_expr = h_eql.getLeft();
			} else {
				return null;
			}
			if (CLUtils.isConstant(h_eql_expr, goal.getTypingContext()) && h_eql_expr instanceof CLIdentifier
					&& (goalFree.contains(h_eql_id.getName()) || hasMatchingHyp(goal, h_eql_id.getName(), t))) {
				return new Subst(t, h_eql, h_eql_id.getName(), h_eql_expr);
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
