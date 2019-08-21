package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TacticPackage;

public class SetDifferenceMembershipInGoal extends GoalTransform {
	public static String NAME = "mem diff";

	protected SetDifferenceMembershipInGoal(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		final CLBinaryExpression binary = (CLBinaryExpression) goal.getFormula();
		final CLMultiExpression parts = (CLMultiExpression) binary.getRight();

		final int tag0 = binary.getTag();
		final int tag1 = tag0 == alphabet.OP_IN ? alphabet.OP_NOTIN : alphabet.OP_IN;

		final CLExpression part0 = new CLBinaryExpression(tag0, binary.getLeft(), parts.getParts().byIndex(0));
		final CLExpression part1 = new CLBinaryExpression(tag1, binary.getLeft(), parts.getParts().byIndex(1));
		final CLExpression whole = new CLMultiExpression(alphabet.OP_OR, part0, part1, null);

		return Collections.singletonList(makeGoal(goal, whole));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		if (goal.getFormula().getTag() == alphabet.OP_IN || goal.getFormula().getTag() == alphabet.OP_NOTIN) {
			final CLBinaryExpression binary = (CLBinaryExpression) goal.getFormula();
			if (binary.getRight().getTag() == alphabet.OP_SETMINUS) {
				final CLMultiExpression parts = (CLMultiExpression) binary.getRight();
				if (parts.getParts().size() == 2) {
					return Boolean.TRUE;
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
