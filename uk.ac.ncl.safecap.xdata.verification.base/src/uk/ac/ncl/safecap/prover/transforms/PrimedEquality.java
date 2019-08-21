package uk.ac.ncl.safecap.prover.transforms;

import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class PrimedEquality extends EqualityRewriter {
	public static String NAME = "eql prime";

	protected PrimedEquality(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected Subst findSubst(ProofGoal goal, Set<String> goalFree, ProofHypothesis t) {
		final CLExpression h = t.getFormula();
		if (!t.isExclude() && h.getTag() == alphabet.OP_EQL) {
			final CLBinaryExpression h_eql = (CLBinaryExpression) h;
			if (isValidRewriteExpressionLeft(goal, h_eql)) {
				final CLIdentifier h_eql_id = (CLIdentifier) h_eql.getLeft();
				if (h_eql_id.isPrime() && goalFree.contains(h_eql_id.getName())) {
					return new Subst(t, h_eql, h_eql_id.getName(), h_eql.getRight());
				}
			} else if (isValidRewriteExpressionRight(goal, h_eql)) {
				final CLIdentifier h_eql_id = (CLIdentifier) h_eql.getRight();
				if (h_eql_id.isPrime() && goalFree.contains(h_eql_id.getName())) {
					return new Subst(t, h_eql, h_eql_id.getName(), h_eql.getLeft());
				}
			}
		}

		return null;
	}

	@Override
	protected boolean doHypothesesRewrite() {
		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
