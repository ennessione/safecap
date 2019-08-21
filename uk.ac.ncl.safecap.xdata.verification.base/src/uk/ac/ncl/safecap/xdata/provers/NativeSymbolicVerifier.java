package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.prover.core.TransformStats;
import uk.ac.ncl.safecap.prover.transforms.TransformFactory;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult.RESULT;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;

public class NativeSymbolicVerifier implements IPropertyVerifier {
	public static final NativeSymbolicVerifier INSTANCE = new NativeSymbolicVerifier();

	private static final String NAME = "symb";
	protected static VerificationResult FAILED;
	private static final int MAX_CYCLES = 60000;
	private static final int MAX_MILSECONS = 80000;
	private static final int MAX_SUBGOALS = 8000;

	static {
		FAILED = new VerificationResult(VerificationResult.RESULT.FAILED);
		FAILED.setProver(NAME);
	}

	public boolean verify(RootCatalog root, ManagedProofObligation po) {
		final long time = System.currentTimeMillis();
		try {

			final TransformFactory factory = new TransformFactory(root, po);

			int attempts = MAX_CYCLES;
			int totalGoals = 1;
			ProofGoal goal;
			while ((goal = po.getAnyOpenGoal()) != null && attempts > 0 && totalGoals < MAX_SUBGOALS
					&& System.currentTimeMillis() - time < MAX_MILSECONS) {
				int newGoals;

				newGoals = prove(factory, goal);

				attempts += (newGoals - 1) * 100;
				totalGoals += newGoals - 1;
				attempts--;

				if (po.hasStuckGoals()) {
					break;
				}
			}

			po.purge();

			final long runtime = System.currentTimeMillis() - time;
			if (totalGoals >= MAX_SUBGOALS || runtime > MAX_MILSECONS) {
				return false;
			}

			return po.getStatus() == RESULT.VALID;
		} catch (final Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		final long time = System.currentTimeMillis();
		try {
			final ManagedProofObligation po = property.getManagedProofObligation();
			if (po == null) {
				return FAILED;
			}

			final TransformFactory factory = new TransformFactory(property.getRoot(), po);

			TransformStats stats = null;
			if (ProversConstants.PROVER_COLLECT_STATS) {
				stats = new TransformStats(factory);
				po.setStats(stats);
			}

			final String path = getPath(property);
			int attempts = MAX_CYCLES;
			int totalGoals = 1;
			ProofGoal goal;
			while ((goal = po.getAnyOpenGoal()) != null && attempts > 0 && totalGoals < MAX_SUBGOALS
					&& System.currentTimeMillis() - time < MAX_MILSECONS) {
				int newGoals;

				if (ProversConstants.PROVER_COLLECT_STATS) {
					newGoals = prove(factory, goal, stats);
				} else {
					newGoals = prove(factory, goal);
				}

				attempts += (newGoals - 1) * 100;
				totalGoals += newGoals - 1;
				attempts--;

				if (po.hasStuckGoals()) {
					break;
				}
			}

			if (ProversConstants.PROVER_PURGE_HISTORY) {
				po.purge();
			}

			final long runtime = System.currentTimeMillis() - time;
			if (totalGoals >= MAX_SUBGOALS || runtime >= MAX_MILSECONS) {

				System.out
						.println("*** NativeSymbolicVerifier: " + path + "/" + po.getName() + " - " + totalGoals + " in " + runtime + "ms");
				final VerificationResult result = new VerificationResult(VerificationResult.RESULT.FAILED);
				result.setTime(System.currentTimeMillis() - time);
				result.setProver(NAME + "/Limit exceeded");

				return result;
			}

			final VerificationResult result = new VerificationResult(po.getStatus());
			result.setTime(System.currentTimeMillis() - time);
			result.setProver(NAME);
			po.setResult(result);
			return result;
		} catch (final Throwable e) {
			e.printStackTrace();
			return FAILED;
		}
	}

	private String getPath(ICondition property) {
		if (property instanceof ConditionManagedPO) {
			final ConditionManagedPO prop = (ConditionManagedPO) property;
			if (prop.getElement() != null) {
				return prop.getElement().getPath();
			}
		}
		return "?";
	}

	private int prove(TransformFactory factory, ProofGoal goal) {
		final ConfiguredGoalTransform transform = factory.getAutoTransformsFor(goal);
		if (transform != null) {
			final int goals = transform.getTransforms().apply(goal, transform.getConfiguration());
			return goals;
		} else {
			goal.setStuck();
			return 0;
		}
	}

	private int prove(TransformFactory factory, ProofGoal goal, TransformStats stats) {
		final long time = System.currentTimeMillis();
		final ConfiguredGoalTransform transform = factory.getAutoTransformsFor(goal);

		if (transform != null) {
			final int goals = transform.getTransforms().apply(goal, transform.getConfiguration());
			final long runtime = System.currentTimeMillis() - time;
			stats.add(transform.getTransforms(), goals - 1, runtime);
			if (runtime > 500) {
				System.out.println("Slow: " + transform.getTransforms().getName() + " in " + runtime + "ms for "
						+ goal.getGoalContainer().getManagedProofObligation().getName());
			}
			return goals;
		} else {
			goal.setStuck();
			return 0;
		}

	}
}
