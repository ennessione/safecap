package uk.ac.ncl.safecap.xdata.testinv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.provers.NativeSymbolicVerifier;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.ElementVisitor;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPOs;

public class InvariantStressTesting {
	private static final boolean DEBUG = true;
	private final RootCatalog root;
	private final Random random;
	private final String invariant;
	private final List<ManagedProofObligation> base;
	private final List<IStressTestingTransform> transforms;
	private final Map<String, Integer> valid;
	private final Map<String, Integer> invalid;

	public InvariantStressTesting(RootCatalog root, String invariant, List<IStressTestingTransform> transforms) {
		super();
		this.transforms = transforms;
		random = new Random();
		this.root = root;
		this.invariant = invariant;
		valid = new HashMap<>();
		invalid = new HashMap<>();
		base = new ArrayList<>();
		collectPOs();
		filterPOs();
	}

	public Map<String, Integer> getValid() {
		return valid;
	}

	public Map<String, Integer> getInvalid() {
		return invalid;
	}

	public Collection<String> getResultTransforms() {
		final Collection<String> r = new HashSet<>(valid.keySet());
		r.addAll(invalid.keySet());
		return r;
	}

	public int getResultValid(String transform) {
		if (valid.containsKey(transform)) {
			return valid.get(transform);
		} else {
			return 0;
		}
	}

	public int getResultInvalid(String transform) {
		if (invalid.containsKey(transform)) {
			return invalid.get(transform);
		} else {
			return 0;
		}
	}

	public boolean isReady() {
		return base.size() > 0 && transforms.size() > 0;
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
			System.out.println("[STT] removed " + toRemove.size() + " base proof obligations for " + invariant);
		}

	}

	private void collectPOs() {
		VisitorUtils.visitAllConditionsInImportedTransitions(root, new ElementVisitor<ITransitionPOs>() {

			@Override
			public Object visit(ITransitionPOs element, Object userData) {
				if (element.getPos() != null) {
					for (final ManagedProofObligation tpo : element.getPos()) {
						if (matches(tpo.getName(), invariant)) {
							base.add(tpo);
						}
					}
				}
				return null;
			}

		});

		if (DEBUG) {
			System.out.println("[STT] found " + base.size() + " base proof obligations for " + invariant);
		}

	}

	private boolean matches(String name, String invariant) {
		final String[] parts = name.split("/");
		if (parts.length == 3 && parts[0].equals("INV")) {
			final String inv = parts[1];
			return inv.equals(invariant);
		}
		return false;
	}

	public void test(int iterations) {
		if (DEBUG) {
			System.out.println("[STT] starting " + iterations + " iterations for " + invariant);
		}
		while (iterations-- > 0) {
			final ManagedProofObligation po = base.get(random.nextInt(base.size()));
			final IStressTestingTransform transform = transforms.get(random.nextInt(transforms.size()));
			final ManagedProofObligation transformed = transform.transform(po);
			if (transformed != null) {
				final boolean result = NativeSymbolicVerifier.INSTANCE.verify(root, transformed);
				increment(result ? valid : invalid, transform.getName());
			}
		}

		if (DEBUG) {
			System.out.println("[STT] results: ");
			for (final IStressTestingTransform tr : transforms) {
				if (valid.containsKey(tr.getName()) || invalid.containsKey(tr.getName())) {
					System.out.print("\t\t" + tr.getName() + ": ");

					if (valid.containsKey(tr.getName())) {
						System.out.print("valid " + valid.get(tr.getName()));
					} else {
						System.out.print("valid 0");
					}

					if (invalid.containsKey(tr.getName())) {
						System.out.println("; invalid " + invalid.get(tr.getName()));
					} else {
						System.out.println("; invalid 0");
					}
				}
			}
		}
	}

	private void increment(Map<String, Integer> map, String name) {
		if (map.containsKey(name)) {
			map.put(name, map.get(name) + 1);
		} else {
			map.put(name, 1);
		}

	}
}
