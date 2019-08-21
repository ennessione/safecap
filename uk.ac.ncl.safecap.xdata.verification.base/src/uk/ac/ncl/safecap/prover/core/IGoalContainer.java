package uk.ac.ncl.safecap.prover.core;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult.RESULT;

public interface IGoalContainer {
	public enum TYPE {
		CONJUNCTIVE, DISJUNCTIVE
	};

	TYPE getType();

	List<IGoalContainer> getChildren();

	/**
	 * The goal attached directly to the current goal container
	 * @return
	 */
	List<ProofGoal> getGoals();

	
	/**
	 * Recursively visit all goal in the current container and below
	 * @param visitor
	 */
	boolean visitGoals(IGoalVisitor visitor);
	
	void addGoal(ProofGoal goal);

	void replaceConjunctive(ProofGoal goal, List<ProofGoal> newGoals);

	void replaceDisjunctive(ProofGoal goal, List<ProofGoal> newGoals);

	void addChild(IGoalContainer child);

	ProofGoal getAnyOpenGoal();

	ProofGoal getGoalByName(String name);

	boolean isClosed();

	boolean hasStuckGoals();

	void purge();

	long getTime();

	RESULT getStatus();

	ManagedProofObligation getManagedProofObligation();

	int getStage();

	void nextStage();

	List<ProofHypothesis> getHypothesis();

	TypingContext getTypingContext();

}
