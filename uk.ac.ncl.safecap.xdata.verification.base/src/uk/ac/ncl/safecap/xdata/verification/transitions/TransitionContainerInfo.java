package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;

public class TransitionContainerInfo {
	private final TransitionContainer root;
	public Map<String, TransitionContainerInvariantInfo> map;
	public Map<String, TransitionContainerTransformInfo> stats;

	public TransitionContainerInfo(TransitionContainer root) {
		map = new HashMap<>();
		stats = new HashMap<>();
		this.root = root;
	}

	public void compute() {
		for (final TransitionCluster tc : root.getClusters()) {
			compute(tc);
		}
	}

	private void compute(TransitionCluster tc) {
		if (tc.getPos() != null) {
			for (final ManagedProofObligation po : tc.getPos()) {
				compute(po);
			}
		}
	}

	private void compute(ManagedProofObligation po) {
		final String[] parts = po.getName().split("/");
		final TransitionContainerInvariantInfo info = getInvariantProofInfo(parts[1]);
		info.generated++;
		info.proved += po.isValid() ? 1 : 0;
		info.goals += po.getGoals().size();

		if (po.getResult() != null) {
			if (po.isValid()) {
				info.time_correct += po.getResult().getTime();
			} else {
				info.time_failed += po.getResult().getTime();
			}
		}

		for (final ProofGoal goal : po.getGoals()) {
			compute(info, goal);
		}

		if (po.getStats() != null) {
			final String[] transforms = po.getStats().getFactory().getAutoTransformNames();
			for (int i = 0; i < transforms.length; i++) {
				final int hits = po.getStats().getHits()[i];
				final long time = po.getStats().getMsecs()[i];
				final int goals = po.getStats().getGoals()[i];
				final TransitionContainerTransformInfo record = getTransformStats(transforms[i]);
				if (record != null) {
					record.hits += hits;
					record.time += time;
					record.goals += goals;
				}
			}
		}
	}

	private void compute(TransitionContainerInvariantInfo info, ProofGoal goal) {
		if (goal.isClosed()) {
			info.goals_closed++;
		} else if (goal.isStuck()) {
			info.goals_stuck++;
		}

	}

	private TransitionContainerInvariantInfo getInvariantProofInfo(String name) {
		if (!map.containsKey(name)) {
			final TransitionContainerInvariantInfo info = new TransitionContainerInvariantInfo(name);
			map.put(name, info);
			return info;
		} else {
			return map.get(name);
		}
	}

	private TransitionContainerTransformInfo getTransformStats(String name) {
		if (!stats.containsKey(name)) {
			final TransitionContainerTransformInfo info = new TransitionContainerTransformInfo(name);
			stats.put(name, info);
			return info;
		} else {
			return stats.get(name);
		}
	}
}