package uk.ac.ncl.safecap.xdata.testinv;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.xdata.provers.NativeSymbolicVerifier;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;

/**
 * Global stress testing for specific transform and hypothesis
 *
 */
public class GlobalStressTestingIteration {
	private static final boolean DEBUG = true;
	private final RootCatalog root;
	private final List<ManagedProofObligation> base;
	private final IStressTestingTransform transform;
	private final List<ManagedProofObligation> allPOs;
	private final CLExpression baseHyp;
	private int valid;
	private int invalid;

	public GlobalStressTestingIteration(RootCatalog root, List<ManagedProofObligation> allPOs, IStressTestingTransform transform,
			CLExpression baseHyp) {
		super();
		this.transform = transform;
		this.root = root;
		this.baseHyp = baseHyp;
		this.allPOs = allPOs;
		base = new ArrayList<>();
		collectPOs();
		// filterPOs();
	}

	public int getValid() {
		return valid;
	}

	public int getInvalid() {
		return invalid;
	}

	public boolean isReady() {
		return base.size() > 0 && baseHyp != null;
	}

	private void filterPOs() {
		final List<ManagedProofObligation> toRemove = new ArrayList<>();
		for (final ManagedProofObligation po : base) {
			final ManagedProofObligation poc = new ManagedProofObligation(po);
			if (!NativeSymbolicVerifier.INSTANCE.verify(root, poc)) {
				toRemove.add(po);
			}
		}

		base.removeAll(toRemove);
		if (DEBUG) {
			System.out.println("[GST] removed " + toRemove.size() + " base proof obligations");
		}

	}

	private void collectPOs() {
		for (final ManagedProofObligation tpo : allPOs) {
			if (containsHyp(tpo)) {
				base.add(tpo);
			}
		}

		if (DEBUG) {
			System.out.println("[GST-base] found " + base.size() + " base proof obligations " + baseHyp);
		}

	}

	private boolean containsHyp(ManagedProofObligation tpo) {
		return matchingHyp(tpo) != null;
	}

	private ProofHypothesis matchingHyp(ManagedProofObligation tpo) {
		for (final ProofHypothesis hyp : tpo.getHypothesis()) {
			final CLExpression e = hyp.getFormula();
			if (e.equals(baseHyp) && baseHyp.getLocation().equals(e.getLocation())) {
				return hyp;
			}
		}
		return null;
	}

	public String test() {
		if (DEBUG) {
			System.out.println("[GST-base] starting test for " + baseHyp);
		}
		for (final ManagedProofObligation po : base) {
			final ProofHypothesis localHyp = matchingHyp(po);
			final ManagedProofObligation transformed = transform.transform(po, localHyp);
			if (transformed != null) {
				final boolean result = NativeSymbolicVerifier.INSTANCE.verify(root, transformed);
				if (!result) {
					final String[] parts = transformed.getName().split("/");
					return parts.length > 1 ? parts[1] : transformed.getName();
				}
			} else {
				if (DEBUG) {
					System.out.println("[GST-base] transform failed");
				}

			}
		}

		if (DEBUG) {
			System.out.println("[GST-base] all " + base.size() + " cases hold");
		}

		return null;

	}

}
