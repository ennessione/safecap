package uk.ac.ncl.safecap.xdata.testinv;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLRewriteRule;
import uk.ac.ncl.prime.sim.lang.CLRewriteRule.RewriteRuleState;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;

public class STTRewrite implements IStressTestingTransform {
	private final CLRewriteRule rule;
	private final String name;

	public STTRewrite(CLRewriteRule rule, String name) {
		this.rule = rule;
		this.name = name;
	}

	public STTRewrite(CLExpression template, CLExpression pattern, String name) {
		rule = new CLRewriteRule(name, template, pattern);
		this.name = name;
	}

	@Override
	public ManagedProofObligation transform(ManagedProofObligation base) {
		for (final ProofHypothesis hyp : base.getHypothesis()) {
			final ManagedProofObligation po = transform(base, hyp);
			if (po != null) {
				return po;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ManagedProofObligation transform(ManagedProofObligation base, ProofHypothesis hyp) {
		final RewriteRuleState state = rule.isApplicable(hyp.getFormula(), null, base.getTypingContext());
		if (state != null) {
			final CLExpression t = state.apply();
			final ManagedProofObligation transformed = new ManagedProofObligation(base, hyp, t, name + ":" + hyp.getCanonicalName());
			return transformed;
		}

		assert false;
		return null;
	}

	@Override
	public ProofHypothesis getApplicable(ManagedProofObligation base) {
		for (final ProofHypothesis hyp : base.getHypothesis()) {
			final RewriteRuleState state = rule.isApplicable(hyp.getFormula(), null, base.getTypingContext());
			if (state != null) {
				return hyp;
			}
		}
		return null;
	}

}
