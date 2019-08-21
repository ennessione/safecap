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

public class SplitSetCMemInHyp extends GoalTransform {
	public static String NAME = "split setc mem hyp";

	protected SplitSetCMemInHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final ProofHypothesis hyp = (ProofHypothesis) data;

		final int tag = hyp.getFormula().getTag() == alphabet.OP_IN ? alphabet.OP_EQL : alphabet.OP_NEQ;

		final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
		final CLMultiExpression unionParts = (CLMultiExpression) binary.getRight();
		final CLExpression element = binary.getLeft();

		final Collection<CLExpression> parts = new ArrayList<>();

		assert unionParts.getParts().size() > 0;

		for (final CLExpression set : unionParts.getParts()) {
			parts.add(new CLBinaryExpression(tag, element, set).simplify(goal.getTypingContext()));
		}

		super.transformHyp(goal, hyp,
				new Transforms(hyp.getHypothesis(), CLUtils.buildDisjunct(parts, null), getName(), goal.getGoalContainer()));

		return Collections.singletonList(makeGoal(goal, goal.getGoal().getFormula()));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getApplicableHyp(goal);
	}

	private ProofHypothesis getApplicableHyp(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			if (applies(hyp)) {
				return hyp;
			}
		}

		return null;
	}

	private boolean applies(ProofHypothesis hyp) {
		if (hyp.getFormula().getTag() == alphabet.OP_IN || hyp.getFormula().getTag() == alphabet.OP_NOTIN) {
			final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
			if (binary.getRight().getTag() == alphabet.SETC) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
