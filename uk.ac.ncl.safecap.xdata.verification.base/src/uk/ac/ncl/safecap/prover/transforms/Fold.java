package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;
import uk.ac.ncl.safecap.xdata.provers.ConstantFoldingTopDown;

public class Fold extends GoalTransform {
	public static String NAME = "fold";
	private ConstantFoldingTopDown folding = null;

	protected Fold(TacticPackage tacticPackage) {
		super(tacticPackage);
		folding = new ConstantFoldingTopDown(getSDAContext().getRootRuntimeContext());
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {

		if (data instanceof FoldResultGoal) {
			final FoldResultGoal frg = (FoldResultGoal) data;
			return Collections.singletonList(makeGoal(goal, frg.result));
		} else if (data instanceof FoldResultHyp) {
			final FoldResultHyp frh = (FoldResultHyp) data;
			final ProofHypothesis hyp = frh.hyp;
			transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(), frh.result, getName() + "/hyp", goal.getGoalContainer()));
			return Collections.singletonList(goal);
		} else {
			return Collections.singletonList(goal);
		}

	}

	@Override
	public Object isApplicable(ProofGoal goal) { // TODO: return complete object to avoid double computation
		if (folding == null) {
			return null;
		}

		CLExpression newExpr = folding.doFolding(goal.getFormula());
		if (newExpr != goal.getFormula()) {
			return new FoldResultGoal(newExpr);
		}

		for (final ProofHypothesis hyp : goal) {
			newExpr = folding.doFolding(hyp.getFormula());
			if (newExpr != hyp.getFormula()) {
				return new FoldResultHyp(hyp, newExpr);
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

	@Override
	public int maxRepeatSteps() {
		return 1;
	}

	static private class FoldResultGoal {
		CLExpression result;

		public FoldResultGoal(CLExpression result) {
			super();
			this.result = result;
		}
	}

	static private class FoldResultHyp {
		ProofHypothesis hyp;
		CLExpression result;

		public FoldResultHyp(ProofHypothesis hyp, CLExpression result) {
			this.hyp = hyp;
			this.result = result;
		}
	}

}
