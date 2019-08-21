package uk.ac.ncl.safecap.prover.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.prover.transforms.trace.GenerativeGoal;
import uk.ac.ncl.safecap.prover.transforms.trace.GoalProvenance;
import uk.ac.ncl.safecap.xdata.provers.ProversConstants;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public abstract class GoalTransform extends CLBase implements IGoalTtransform {
	private final TacticPackage tacticPackage;

	protected GoalTransform(TacticPackage tacticPackage) {
		this.tacticPackage = tacticPackage;
	}

	public SDAContext getSDAContext() {
		return tacticPackage.getContext();
	}

	protected GoalProvenance makeGoalProvenance(ProofGoal goal, String partid) {
		if (ProversConstants.PROVER_COLLECT_PROVENANCE) {
			return new GenerativeGoal(goal.getProvenance(), getName(), partid);
		} else {
			return null;
		}
	}

	protected ProofGoal makeGoal(ProofGoal old, CLExpression newGoal) {
		return new ProofGoal(old.getGoalContainer(), new Transforms(old.getGoal(), newGoal, getName(), old.getGoalContainer()), old,
				old.getProvenance());
	}

	protected ProofGoal makeGoal(ProofGoal old, CLExpression newGoal, GoalProvenance provenance) {
		return new ProofGoal(old.getGoalContainer(), new Transforms(old.getGoal(), newGoal, getName(), old.getGoalContainer()), old,
				provenance);
	}

	protected ProofGoal makeGoal(ProofGoal old, CLExpression newGoal, String suffix, GoalProvenance provenance) {
		return new ProofGoal(old.getGoalContainer(),
				new Transforms(old.getGoal(), newGoal, getName() + "/" + suffix, old.getGoalContainer()), old, provenance);
	}

	protected ProofGoal makeGoal(ProofGoal old, CLExpression newGoal, String partid) {
		final ProofGoal g = new ProofGoal(old.getGoalContainer(), new Transforms(old.getGoal(), newGoal, getName(), old.getGoalContainer()),
				old, makeGoalProvenance(old, partid));
		g.modifyHypothesis();
		return g;
	}

	protected ProofGoal makeGoal(ProofGoal old, CLExpression newGoal, String suffix, String partid) {
		return new ProofGoal(old.getGoalContainer(),
				new Transforms(old.getGoal(), newGoal, getName() + "/" + suffix, old.getGoalContainer()), old,
				makeGoalProvenance(old, partid));
	}

	@Override
	public int apply(ProofGoal goal, Object data) {
		goal.getGoalContainer().nextStage();
		final List<ProofGoal> result = doTransform(goal, data);
		goal.getGoalContainer().replaceConjunctive(goal, result);
		return result.size();
	}

	// rewrite hypothesis
	protected void rewriteHypothesis(ProofGoal goal, Map<String, CLExpression> map, ProofHypothesis exclude) {
		final Set<String> identifiers = map.keySet();

		for (final ProofHypothesis hyp : goal.getGoalContainer().getHypothesis()) {
			if (hyp != exclude && CLUtils.isRelevant(identifiers, hyp.getFormula())) {
				localiseHypothesis(goal, hyp);
			}
		}

		for (final ProofHypothesis hyp : goal.getHypothesis()) {
			if (hyp != exclude) {
				final CLExpression formula = hyp.getFormula().rewrite(map, goal.getTypingContext());
				transformHypLocal(goal, hyp, new Transforms(hyp.getHypothesis(), formula, getName(), goal.getGoalContainer()));
			}
		}
	}
	
	private static class LocalisationVisitor implements IGoalVisitor {
		private ProofGoal currentGoal;
		private ProofHypothesis hyp;
		private String name;
		ProofHypothesis result = null;
		
		public LocalisationVisitor(ProofGoal currentGoal, ProofHypothesis hyp, String name) {
			this.currentGoal = currentGoal;
			this.hyp = hyp;
			this.name = name;
		}

		@Override
		public boolean visit(ProofGoal goal) {
			if (!goal.isClosed()) {
				final ProofHypothesis newHyp = goal.addHypothesis(hyp.getFormula(), hyp.getHypothesis(), name + ":LOC/",
						currentGoal.getGoalContainer(), hyp.getCanonicalName());
				// newHyp.computeRank();
				if (goal == currentGoal) {
					result = newHyp;
				}
			}
			return true;
		}
		
	}

	private ProofHypothesis localiseHypothesis(ProofGoal currentGoal, ProofHypothesis hyp) {

		// check if already local
		if (currentGoal.getHypothesis().contains(hyp)) {
			return hyp;
		}

		currentGoal.modifyHypothesis();
		if (hyp.getProofBranch() instanceof ManagedProofObligation) {
			final ManagedProofObligation po = (ManagedProofObligation) hyp.getProofBranch();
			
			LocalisationVisitor lv = new LocalisationVisitor(currentGoal, hyp, getName());
			po.visitGoals(lv);
			
			ProofHypothesis result = lv.result;

			/*
			// make local versions
			for (final ProofGoal goal : po.getGoals()) {
				if (!goal.isClosed()) {
					final ProofHypothesis newHyp = goal.addHypothesis(hyp.getFormula(), hyp.getHypothesis(), getName() + ":LOC/",
							currentGoal.getGoalContainer(), hyp.getCanonicalName());
					// newHyp.computeRank();
					if (goal == currentGoal) {
						result = newHyp;
					}
				}
			}
			*/
			
			assert result != null;

			// suppress global hypothesis
			transformHypGlobal(po, hyp, new Transforms(hyp.getHypothesis(), CLAtomicExpression.TRUE, getName() + ":SUP/", true,
					currentGoal.getGoalContainer()));

			return result;
		}

		assert false;
		return null;
	}

//	private int getIndex(ManagedProofObligation po, ProofGoal goal) {
//		for (int i = 0; i < po.getGoals().size(); i++)
//			if (po.getGoals().get(i) == goal)
//				return i;
//		return -1;
//	}

	protected abstract List<ProofGoal> doTransform(ProofGoal goal, Object data);

	protected void transformHypLocal(IProofBranch po, int index, Transforms newHyp) {
		final String id = po.getHypothesis().get(index).getCanonicalName();
		po.getHypothesis().set(index, new ProofHypothesis(po, newHyp, id));
	}

	/*
	 * protected void transformHyp(ProofGoal goal, ProofHypothesis old, Transforms
	 * newHyp) { int goal_index = goal.getHypothesis().indexOf(old); if (goal_index
	 * >= 0) { goal.getHypothesis().set(goal_index, new ProofHypothesis(goal,
	 * newHyp)); return; }
	 * 
	 * ManagedProofObligation po = goal.getProofObligation(); int po_index =
	 * po.getHypothesis().indexOf(old); if (po_index >= 0) {
	 * po.getHypothesis().set(po_index, new ProofHypothesis(po, newHyp)); return; }
	 * assert false; }
	 */

	protected void transformHyp(ProofGoal goal, ProofHypothesis old, Transforms newHyp) {
		old = localiseHypothesis(goal, old);
		transformHypLocal(goal, old, newHyp);
	}

	protected void transformHyp(ProofGoal goal, ProofHypothesis old, Transforms newHyp, String id) {
		old = localiseHypothesis(goal, old);
		transformHypLocal(goal, old, newHyp, id);
	}

	private void transformHypLocal(ProofGoal goal, ProofHypothesis old, Transforms newHyp) {
		final int goal_index = goal.getHypothesis().indexOf(old);
		if (goal_index >= 0) {
			goal.getHypothesis().set(goal_index, new ProofHypothesis(goal, newHyp, old.getCanonicalName()));
			return;
		}

		assert false;
	}

	private void transformHypLocal(ProofGoal goal, ProofHypothesis old, Transforms newHyp, String id) {
		final int goal_index = goal.getHypothesis().indexOf(old);
		if (goal_index >= 0) {
			goal.getHypothesis().set(goal_index, new ProofHypothesis(goal, newHyp, id));
			return;
		}

		assert false;
	}

	private void transformHypGlobal(ManagedProofObligation po, ProofHypothesis old, Transforms newHyp) {
		final int goal_index = po.getHypothesis().indexOf(old);
		if (goal_index >= 0) {
			po.getHypothesis().set(goal_index, new ProofHypothesis(po, newHyp, old.getCanonicalName()));
			return;
		}

		assert false;
	}

	protected ProofHypothesis findHypotheses(ProofGoal goal, CLExpression expr) {
		for (final ProofHypothesis hyp : goal) {
			if (hyp.getFormula().equals(expr)) {
				return hyp;
			}
		}

		return null;
	}

	protected CLExpression findPredicateInHypotheses(ProofGoal goal, CLExpression predicate) {
		for (final ProofHypothesis hyp : goal) {
			CLExpression z = testPredicateInHypotheses2(hyp.getFormula(), predicate);
			if (z != null)
				return hyp.getFormula();
		}

		return null;
	}	
	
	

	protected boolean hasPredicateInHypotheses(ProofGoal goal, CLExpression predicate) {
		if (predicate.getTag() == alphabet.OP_AND) {
			final CLMultiExpression multi = (CLMultiExpression) predicate;
			for (final CLExpression p : multi.getParts()) {
				if (!hasPredicateInHypotheses(goal, p)) {
					return false;
				}
			}

			return true;
		}

		for (final ProofHypothesis hyp : goal) {
			if (testPredicateInHypotheses(hyp.getFormula(), predicate)) {
				return true;
			}
		}

		return false;
	}

	private boolean testPredicateInHypotheses(CLExpression formula, CLExpression predicate) {
		if (formula.getTag() == alphabet.OP_AND) {
			final CLMultiExpression multi = (CLMultiExpression) formula;
			for (final CLExpression p : multi.getParts()) {
				if (testPredicateInHypotheses(p, predicate)) {
					return true;
				}
			}

			return false;
		}

		return formula.equals(predicate);
	}	

	private CLExpression testPredicateInHypotheses2(CLExpression formula, CLExpression predicate) {
		if (formula.getTag() == alphabet.OP_AND) {
			final CLMultiExpression multi = (CLMultiExpression) formula;
			for (final CLExpression p : multi.getParts()) {
				CLExpression z = testPredicateInHypotheses2(p, predicate);
				if (z != null)
					return z;
			}

			return null;
		}

		if (formula.equals(predicate))
			return formula;
		else
			return null;
	}

	public boolean isRepeatable() {
		return true;
	}

	public int maxRepeatSteps() {
		return Integer.MAX_VALUE;
	}
}
