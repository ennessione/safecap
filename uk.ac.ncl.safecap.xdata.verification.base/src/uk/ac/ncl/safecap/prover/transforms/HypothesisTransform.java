package uk.ac.ncl.safecap.prover.transforms;

import java.util.List;

import uk.ac.ncl.safecap.prover.core.IHypothesisTtransform;
import uk.ac.ncl.safecap.prover.core.IProofBranch;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.prover.core.TacticPackage;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public abstract class HypothesisTransform implements IHypothesisTtransform {
	private final TacticPackage tacticPackage;

	protected HypothesisTransform(TacticPackage tacticPackage) {
		this.tacticPackage = tacticPackage;
	}

	public SDAContext getSDAContext() {
		return tacticPackage.getContext();
	}

	// public void fireChange(IProofBranch branch) {
	// tacticPackage.fireChange(branch);
	// }

	@Override
	public void apply(IProofBranch branch, ProofHypothesis hypothesis) {

	}

	protected abstract List<ProofHypothesis> doTransform(IProofBranch branch, ProofHypothesis hypothesis);

	public boolean isRepeatable() {
		return true;
	}
}
