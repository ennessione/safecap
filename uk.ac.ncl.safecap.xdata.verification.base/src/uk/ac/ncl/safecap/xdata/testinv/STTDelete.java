package uk.ac.ncl.safecap.xdata.testinv;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;

public class STTDelete implements IStressTestingTransform {
	private final CLExpression template;
	private final String name;

	public STTDelete(CLExpression template, String name) {
		this.template = template;
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
		if (hyp.getFormula().matches(template, base.getTypingContext())) {
			final ManagedProofObligation transformed = new ManagedProofObligation(base, hyp, name + ":" + hyp.getCanonicalName());
			return transformed;
		}
		return null;
	}

	@Override
	public ProofHypothesis getApplicable(ManagedProofObligation base) {
		for (final ProofHypothesis hyp : base.getHypothesis()) {
			if (hyp.getFormula().matches(template, base.getTypingContext())) {
				return hyp;
			}
		}
		return null;
	}

}
