package uk.ac.ncl.safecap.prover.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.VerificationProperty;
import uk.ac.ncl.safecap.xdata.verification.rules.IProverFragments;

public class ManagedProofObligation extends BaseConjunctiveContainer implements IProofBranch {
	private String name;
	private List<ProofHypothesis> hypothesis;
	private TypingContext typingContext;
	private int stage;
	private VerificationResult result;
	private TransformStats stats;
	private IProverFragments proverFragments;
	private RootCatalog catalog;

	public ManagedProofObligation(TypingContext context, VerificationProperty prop) {
		name = prop.getId().content();
		catalog = (RootCatalog) prop.root();
		typingContext = context;
		final CLExpression hyp = prop.getHypotheses().getParsed().content();
		hypothesis = new ArrayList<>();
		int index = 0;
		if (hyp instanceof CLMultiExpression) {
			final CLMultiExpression multi = (CLMultiExpression) hyp;
			for (final CLExpression e : multi.getParts()) {
				hypothesis.add(new ProofHypothesis(this, new Transforms(e.simplify(context)), "HYP" + index++));
			}
		} else {
			hypothesis.add(new ProofHypothesis(this, new Transforms(hyp.simplify(context)), "HYP" + index++));
		}
		final CLExpression z = prop.getParsed().content().simplify(context);
		addGoal(new ProofGoal(this, new Transforms(z), "G"));
	}

	public RootCatalog getCatalog() {
		return catalog;
	}

	public ManagedProofObligation(TypingContext context, String name, Collection<CLExpression> hyp, CLExpression goal) {
		this.name = name;
		typingContext = context;
		hypothesis = new ArrayList<>();
		int index = 0;
		for (final CLExpression e : hyp) {
			hypothesis.add(new ProofHypothesis(this, new Transforms(e.simplify(context)), "HYP" + index++));
		}
		addGoal(new ProofGoal(this, new Transforms(goal), "G"));
	}

	public ManagedProofObligation(ManagedProofObligation base) {
		init(base, "");

		for (final ProofHypothesis h : base.hypothesis) {
			hypothesis.add(new ProofHypothesis(this, new Transforms(h.getFormula()), h.getCanonicalName()));
		}
	}	
	
	public ManagedProofObligation(ManagedProofObligation base, ProofHypothesis hyp, String suffix) {
		init(base, suffix);

		for (final ProofHypothesis h : base.hypothesis) {
			if (h != hyp) {
				hypothesis.add(new ProofHypothesis(this, new Transforms(h.getFormula()), h.getCanonicalName()));
			}
		}
	}

	public ManagedProofObligation(ManagedProofObligation base, ProofHypothesis hyp, CLExpression t, String suffix) {
		init(base, suffix);

		for (final ProofHypothesis h : base.hypothesis) {
			if (h != hyp) {
				hypothesis.add(new ProofHypothesis(this, new Transforms(h.getFormula()), h.getCanonicalName()));
			} else {
				hypothesis.add(new ProofHypothesis(this, new Transforms(t), h.getCanonicalName()));
			}
		}
	}

	private void init(ManagedProofObligation base, String suffix) {
		assert base.stage == 0;

		name = base.name + suffix;
		typingContext = base.typingContext;
		hypothesis = new ArrayList<>();
		proverFragments = base.proverFragments;
		stage = 0;
		addGoal(new ProofGoal(this, new Transforms(base.goals.iterator().next().getGoal().getFormula()), "G"));
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

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public void nextStage() {
		stage++;
	}

	public boolean isValid() {
		return getStatus() == VerificationResult.RESULT.VALID;
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

	@Override
	public Iterator<ProofHypothesis> iterator() {
		return hypothesis.iterator();
	}

	public void revert(int stage) {

	}

	public void setProverFragments(IProverFragments proverFragments) {
		this.proverFragments = proverFragments;
	}

	public IProverFragments getProverFragments() {
		return proverFragments;
	}

	@Override
	public ManagedProofObligation getManagedProofObligation() {
		return this;
	}

}
