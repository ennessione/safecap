package uk.ac.ncl.safecap.prover.transforms;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.core.GoalTransform;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.prover.core.Transforms;

public class DisjunctionInHyp extends GoalTransform {
	public static String NAME = "case hyp disj";

	protected DisjunctionInHyp(TacticPackage tacticPackage) {
		super(tacticPackage);
	}

	@Override
	protected List<ProofGoal> doTransform(ProofGoal goal, Object data) {
		// long start = System.currentTimeMillis();
		final ProofHypothesis hyp = (ProofHypothesis) data;

		final boolean local = goal.getHypothesis().contains(hyp);
		final boolean global = goal.getGoalContainer().getHypothesis().contains(hyp);

		assert local || global;

		final CLMultiExpression multi = (CLMultiExpression) hyp.getFormula();
		final Transforms original = hyp.getHypothesis();
		final List<ProofGoal> goals = new ArrayList<>(multi.getParts().size());

		// long end1 = System.currentTimeMillis();

		transformHyp(goal, hyp, new Transforms(hyp.getHypothesis(), CLAtomicExpression.TRUE, getName(), true, goal.getGoalContainer()));

		// long end2 = System.currentTimeMillis();

		int i = 0;
		// make copies of the goal with disjunct parts
		for (final CLExpression term : multi.getParts().getParts()) {
			final String newHypId = hyp.getCanonicalName() + ":" + i;
			final ProofGoal termGoal = makeGoal(goal, goal.getGoal().getFormula(),
					goal.getGoal().getStage() + " " + newHypId + " - " + term);
			termGoal.addHypothesis(term, original, getName(), goal.getGoalContainer(), newHypId);
			goals.add(termGoal);
			i++;
		}

		assert goals.size() > 1;

		// long end = System.currentTimeMillis();
//		if (end - start > 50) {
//			System.out.println("Times " + (end1 - start) + ", " + (end2 - end1) + ", " + (end - end2));
//			System.out.println("Slow?");
//		}

		return goals;
	}

	@Override
	public Object isApplicable(ProofGoal goal) {
		if (!isSimpleGoal(goal))
			return getMatchingHypothesis(goal);
		else
			return null;
	}

	private static final CLExpression[] SIMPLE_GOAL_TEMPLATES = new CLExpression[] {
			False(),
			in(tem("a", true), var("$$b")),
			notin(tem("a", true), var("$$b"))
	};
	
	private boolean isSimpleGoal(ProofGoal goal) {
//		if (!(goal.getFormula() instanceof CLBinaryExpression))
//			return false;
//		for(CLExpression e: SIMPLE_GOAL_TEMPLATES)
//			if (goal.getFormula().matches(e, goal.getTypingContext()))
//				return true;
//		
		return false;
	}
	
	private ProofHypothesis getMatchingHypothesis(ProofGoal goal) {
		for (final ProofHypothesis hyp : goal) {
			if (!hyp.isExclude() && hyp.getFormula().getTag() == alphabet.OP_OR && CLUtils.isRelevant(goal.getFormula().getFreeIdentifiers(), hyp.getFormula())) {
				return hyp;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
