package uk.ac.ncl.safecap.xdata.testinv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofHypothesis;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;

/**
 * Global stress testing for specific transform and hypothesis
 *
 */
public class GlobalStressTesting {
	private static final int PICK_TRIALS = 125;
	private static final boolean DEBUG = true;
	private final Random random;
	private final RootCatalog root;
	private final List<IStressTestingTransform> transforms;
	private final List<CLExpression> used;
	private final List<ManagedProofObligation> allPOs;
	private final Map<String, Integer> arbiter;

	public GlobalStressTesting(RootCatalog root, List<IStressTestingTransform> transforms) {
		this.transforms = transforms;
		random = new Random();
		this.root = root;
		arbiter = new HashMap<>();
		used = new ArrayList<>();
		allPOs = VisitorUtils.getAllManagedProofObligation(root);
	}

	private CLExpression pickHypothesis(IStressTestingTransform transform) {
		int trials = PICK_TRIALS;
		if (allPOs.isEmpty()) {
			return null;
		}
		ManagedProofObligation po = allPOs.get(random.nextInt(allPOs.size()));
		ProofHypothesis h = transform.getApplicable(po);

		while (h == null && trials > 0) {
			po = allPOs.get(random.nextInt(allPOs.size()));
			h = transform.getApplicable(po);
			if (h != null && used.contains(h.getFormula())) {
				h = null;
			}
			trials--;
		}

		if (h != null) {
			used.add(h.getFormula());
			return h.getFormula();
		}

		return null;
	}

	public void test(int iterations) {
		int valid = 0;
		int invalid = 0;
		while (iterations-- > 0 && transforms.size() > 0) {
			if (DEBUG) {
				System.out.println("[GST] " + iterations);
			}
			final IStressTestingTransform transform = transforms.get(random.nextInt(transforms.size()));
			final CLExpression base = pickHypothesis(transform);
			if (base == null) {
				if (DEBUG) {
					System.out.println("[GST] no solutions " + transform.getName());
				}
				transforms.remove(transform);
				continue;
			}

			if (DEBUG) {
				System.out.println("[GST] picked " + transform.getName() + " and " + base);
			}
			final GlobalStressTestingIteration gsti = new GlobalStressTestingIteration(root, allPOs, transform, base);
			final String property = gsti.test();
			final boolean result = property == null;
			if (result) {
				valid++;
			} else {
				increment(arbiter, property);
				invalid++;
			}
			if (DEBUG) {
				System.out.println("[GST] " + result + " for " + transform.getName() + " and " + base + " @ " + base.getLocation());
			}

		}
		if (DEBUG) {
			System.out.println("[GST] all done");
			System.out.println("\tvalid: " + valid);
			System.out.println("\tinvalid: " + invalid);
			System.out.println("\tdecided by: " + arbiter);
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
