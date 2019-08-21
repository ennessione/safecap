package uk.ac.ncl.safecap.prover.transforms;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class SetMinusPairToEqlHyp extends GoalTransform {
	public static String NAME = "setminus mem eql hyp";

	protected SetMinusPairToEqlHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		SetMinusPairToEqlHypConf conf;
		if (data != null) {
			conf = (SetMinusPairToEqlHypConf) data;
		} else {
			conf = getApplicableHyp(goal);
		}

		transformHyp(goal, conf.mem,
				new Transforms(conf.mem.getHypothesis(), conf.mem.getFormula(), getName(), true, goal.getGoalContainer()));

		final CLBinaryExpression eql = new CLBinaryExpression(alphabet.OP_EQL, conf.left, conf.right);
		transformHyp(goal, conf.setminus, new Transforms(conf.setminus.getHypothesis(), eql, getName(), false, goal.getGoalContainer()));

		return Collections.singletonList(makeGoal(goal, goal.getGoal().getFormula()));
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		return getApplicableHyp(goal);
	}

	private SetMinusPairToEqlHypConf getApplicableHyp(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			final SetMinusPairToEqlHypConf c = getApplicableHyp(goal, hyp);
			if (c != null) {
				return c;
			}
		}

		return null;
	}

	private SetMinusPairToEqlHypConf getApplicableHyp(ProofGoal goal, ProofHypothesis hyp) {
		if (hyp.getFormula().getTag() == alphabet.OP_NOTIN) {
			final CLBinaryExpression binary = (CLBinaryExpression) hyp.getFormula();
			if (binary.getRight().getTag() == alphabet.OP_SETMINUS) {
				final CLMultiExpression multi = (CLMultiExpression) binary.getRight();
				if (multi.getParts().size() == 2 && multi.getParts().byIndex(1).getTag() == alphabet.SETC) {
					final CLMultiExpression set = (CLMultiExpression) multi.getParts().byIndex(1);
					if (set.getParts().size() == 1) {
						final CLBinaryExpression matching = new CLBinaryExpression(alphabet.OP_IN, binary.getLeft(),
								multi.getParts().byIndex(0));
						final ProofHypothesis matched = findHypotheses(goal, matching);
						if (matched != null) {
							return new SetMinusPairToEqlHypConf(matched, hyp, binary.getLeft(), set.getParts().byIndex(0));
						}
					}
				}
			}
		}
		return null;
	}

	class SetMinusPairToEqlHypConf {
		ProofHypothesis mem;
		ProofHypothesis setminus;
		CLExpression left;
		CLExpression right;

		public SetMinusPairToEqlHypConf(ProofHypothesis mem, ProofHypothesis setminus, CLExpression left, CLExpression right) {
			this.mem = mem;
			this.setminus = setminus;
			this.left = left;
			this.right = right;
		}

	}

	@Override
	public String getName() {
		return NAME;
	}

}
