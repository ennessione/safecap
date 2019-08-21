package uk.ac.ncl.safecap.prover.core;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.typing.TypingContext;

public class ConjunctiveSubContainer extends BaseConjunctiveContainer {
	private final IGoalContainer parent;

	public ConjunctiveSubContainer(IGoalContainer parent) {
		this.parent = parent;
	}

	@Override
	public ManagedProofObligation getManagedProofObligation() {
		return parent.getManagedProofObligation();
	}

	@Override
	public int getStage() {
		return parent.getStage();
	}

	@Override
	public List<ProofHypothesis> getHypothesis() {
		return parent.getHypothesis();
	}

	@Override
	public TypingContext getTypingContext() {
		return parent.getTypingContext();
	}

	@Override
	public void nextStage() {
		parent.nextStage();
	}
}
