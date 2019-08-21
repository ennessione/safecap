package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLFunAppExpression;
import uk.ac.ncl.prime.sim.lang.CLGenericRewriter;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class OverrideElimination extends OverrideCases {
	public static String NAME = "ovr elim";

	protected OverrideElimination(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		try {
			final CasesDescriptor cd = getCasesDescriptor(goal);
			if (cd != null) {
				final CLExpression nonePred = makeNonePredicate(cd);
				if (hasPredicateInHypotheses(goal, nonePred)) {
					final OverrideRewriter rewriter = new OverrideRewriter(cd);
					final CLExpression newGoal = rewriter.rewrite(goal.getFormula());
					return Collections.singletonList(makeGoal(goal, newGoal));
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}

		return Collections.singletonList(goal);
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		final CasesDescriptor cd = getCasesDescriptor(goal);
		if (cd != null) {
			final CLExpression nonePred = makeNonePredicate(cd);
			if (hasPredicateInHypotheses(goal, nonePred)) {
				return Boolean.TRUE;
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

	class OverrideRewriter extends CLGenericRewriter {
		private final CasesDescriptor cd;

		public OverrideRewriter(CasesDescriptor cd) {
			this.cd = cd;
		}

		@Override
		public CLExpression rewriter(CLFunAppExpression original, CLExpression left, CLCollection<CLExpression> args)
				throws CLException, CLExecutionException, InvalidSetOpException {
			if (original == cd.funapp) {
				// CLFunAppExpression expr = original;
				final CLMultiExpression mleft = (CLMultiExpression) original.getLeft();
				return new CLFunAppExpression(mleft.getParts().byIndex(0), args);
			}
			return super.rewriter(original, left, args);
		}
	}
}
