package uk.ac.ncl.safecap.prover.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public class ManagedProofObligationOld implements IProofBranch {
	private String name;
	private List<ProofHypothesis> hypothesis;
	private List<ProofGoal> goals;
	private TypingContext typingContext;
	private TacticState tacticState;
	private int stage;
	private VerificationResult result;
	private TransformStats stats;
	private IProverFragments proverFragments;

	public ManagedProofObligationOld(TypingContext context, VerificationProperty prop) {
		this.name = prop.getId().content();
		this.typingContext = context;
		this.tacticState = new TacticState();
		CLExpression hyp = prop.getHypotheses().getParsed().content();
		hypothesis = new ArrayList<ProofHypothesis>();
		int index = 0;
		if (hyp instanceof CLMultiExpression) {
			CLMultiExpression multi = (CLMultiExpression) hyp;
			for (CLExpression e : multi.getParts())
				hypothesis.add((new ProofHypothesis(this, new Transforms(e.simplify(context)), "HYP" + (index++))));
		} else {
			hypothesis.add(new ProofHypothesis(this, new Transforms(hyp.simplify(context)), "HYP" + (index++)));
		}
		goals = new ArrayList<ProofGoal>();
		CLExpression z = prop.getParsed().content().simplify(context);
		if (z.equals(CLAtomicExpression.TRUE)) {
			System.err.println("Empty goal");
		}
		goals.add(new ProofGoal(this, new Transforms(z), "G"));

//		for (ProofHypothesis ph : getHypothesis())
//			ph.computeRank();
	}

	public void purge() {
		for(ProofGoal pg: goals)
			pg.purge();
	}
	
	
	public TransformStats getStats() {
		return stats;
	}

	public void setStats(TransformStats stats) {
		this.stats = stats;
	}

	public VerificationResult getResult() {
		return result;
	}

	public void setResult(VerificationResult result) {
		this.result = result;
	}

	public int getStage() {
		return stage;
	}

	protected void nextStage() {
		stage++;
	}

	public boolean isClosed() {
		for (ProofGoal goal : goals)
			if (!goal.isClosed())
				return false;

		return true;
	}

	public ProofGoal getGoalByName(String name) {
		for (ProofGoal goal : goals)
			if (goal.getName().equals(name))
				return goal;

		return null;
	}

	public VerificationResult.RESULT getStatus() {
		boolean allTrue = true;
		for (ProofGoal goal : goals) {
			if (goal.isClosed()) {
				if (!goal.getStatus().isValid())
					return VerificationResult.RESULT.INVALID;
			} else {
				allTrue = false;
			}
		}

		return allTrue ? VerificationResult.RESULT.VALID : VerificationResult.RESULT.UNKNOWN;
	}

	public long getTime() {
		long time = 0;
		for (ProofGoal goal : goals)
			if (goal.isClosed() && goal.getStatus() != null)
				time += goal.getStatus().getTime();

		return time;
	}

	public boolean isValid() {
		return getStatus() == VerificationResult.RESULT.VALID;
	}

	public TacticState getTacticState() {
		return tacticState;
	}

	@Override
	public TypingContext getTypingContext() {
		return typingContext;
	}

	public void setTypingContext(TypingContext typingContext) {
		this.typingContext = typingContext;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<ProofHypothesis> getHypothesis() {
		return hypothesis;
	}

	public List<ProofGoal> getGoals() {
		return goals;
	}

	public ProofGoal getAnyOpenGoal() {
		for (ProofGoal goal : goals)
			if (!goal.isClosed() && !goal.isStuck())
				return goal;

		return null;
	}

	
	@Override
	public Iterator<ProofHypothesis> iterator() {
		return hypothesis.iterator();
	}

//	public void computeRank() {
//		for (ProofGoal goal : goals)
//			goal.computeRank();
//
//		for (ProofHypothesis ph : getHypothesis())
//			ph.computeRank();
//	}

	public void revert(int stage) {
		for (ProofHypothesis hyp : hypothesis)
			hyp.revert(stage);

		for (ProofGoal goal : goals)
			goal.revert(stage);

		// computeRank();
	}

	public void setProverFragments(IProverFragments proverFragments) {
		this.proverFragments = proverFragments;
	}

	public IProverFragments getProverFragments() {
		return proverFragments;
	}

	public boolean hasStuckGoals() {
		for (ProofGoal goal : goals)
			if (goal.isStuck())
				return true;
		return false;
	}

}
