package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class SplitUnionInGoal extends GoalTransform {
	public static String NAME = "split union";

	protected SplitUnionInGoal(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLBinaryExpression binary = (CLBinaryExpression) goal.getFormula();
		final CLMultiExpression unionParts = (CLMultiExpression) binary.getLeft();
		final CLExpression set = binary.getRight();

		final List<ProofGoal> result = new ArrayList<>();
		int i = 0;
		for (final CLExpression e : unionParts.getParts()) {
			result.add(makeGoal(goal, new CLBinaryExpression(alphabet.OP_SUBSETEQ, e, set), "union:" + i++));
		}

		return result;
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		if (goal.getFormula().getTag() == alphabet.OP_SUBSETEQ) {
			final CLBinaryExpression binary = (CLBinaryExpression) goal.getFormula();
			if (binary.getLeft().getTag() == alphabet.OP_UNION) {
				return Boolean.TRUE;
			}

		}

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
