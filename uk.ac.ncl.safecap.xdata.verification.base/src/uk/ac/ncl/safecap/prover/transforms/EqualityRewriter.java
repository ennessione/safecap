package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.IGoalContainer;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public abstract class EqualityRewriter extends GoalTransform {

	protected EqualityRewriter(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final IGoalContainer po = goal.getGoalContainer();
		Subst subst;

		if (data != null) {
			subst = (Subst) data;
		} else {
			subst = getSubstHyp(po, goal);
		}

		if (doHypothesesRewrite()) {
			rewriteHypothesis(goal, Collections.singletonMap(subst.name, subst.expression), subst.hyp);
		}

		final CLExpression newGoal = goal.getFormula().rewrite(subst.name, subst.expression);

		// find and exclude the hypothesis providing the equality
		goal.modifyHypothesis();
		for (final ProofHypothesis hyp : goal) {
			if (hyp.getFormula() == subst.source) {
				transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(), hyp.getFormula(), getName(), true, goal.getGoalContainer()));
			}
		}

		return Collections.singletonList(makeGoal(goal, newGoal));
	}

	protected boolean doHypothesesRewrite() {
		return false;
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		final IGoalContainer po = goal.getGoalContainer();
		return getSubstHyp(po, goal);
	}

	protected Subst getSubstHyp(IGoalContainer po, ProofGoal goal) {
		final Set<String> goalFree = goal.getFormula().getFreeIdentifiers();

		if (!goalFree.isEmpty()) {
			for (final ProofHypothesis t : goal) {
				final Subst subst = findSubst(goal, goalFree, t);
				if (subst != null) {
					return subst;
				}
			}
		}

		return null;
	}

	protected boolean hasMatchingHyp(ProofGoal goal, String name, ProofHypothesis t) {
		if (!doHypothesesRewrite()) {
			return false;
		}

		for (final ProofHypothesis hyp : goal) {
			if (hyp != t && hyp.getFormula().getFreeIdentifiers().contains(name)) {
				return true;
			}
		}
		return false;
	}

	protected abstract Subst findSubst(ProofGoal goal, Set<String> goalPrimed, ProofHypothesis t);

	protected boolean isValidRewriteExpressionLeft(ProofGoal goal, CLBinaryExpression expr) {
		return isValidRewriteSource(goal, expr.getLeft()) && !isValidRewriteSource(goal, expr.getRight());
	}

	protected boolean isValidRewriteExpressionRight(ProofGoal goal, CLBinaryExpression expr) {
		return isValidRewriteSource(goal, expr.getRight()) && !isValidRewriteSource(goal, expr.getLeft());
	}

	protected boolean isValidRewriteSource(ProofGoal goal, CLExpression expr) {
		return expr instanceof CLIdentifier && !goal.getTypingContext().getSymbolClass(((CLIdentifier) expr).getName()).isConstant();
	}

	class Subst {
		ProofHypothesis hyp;
		CLBinaryExpression source;
		String name;
		CLExpression expression;

		protected Subst(ProofHypothesis hyp, CLBinaryExpression source, String name, CLExpression expression) {
			this.hyp = hyp;
			this.source = source;
			this.name = name;
			this.expression = expression;
		}

	}
}
