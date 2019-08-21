package uk.ac.ncl.safecap.prover.core;

import java.util.Arrays;

import uk.ac.ncl.safecap.prover.transforms.TransformFactory;

public class TransformStats {
	private final TransformFactory factory;
	private final int[] hits;
	private final int[] goals;
	private final long[] msecs;

	public TransformStats(TransformFactory factory) {
		this.factory = factory;
		final int size = factory.getGoalTransforms().size();
		hits = new int[size];
		goals = new int[size];
		msecs = new long[size];
	}

	public void add(GoalTransform transform, int g, long time) {
		final int index = Arrays.binarySearch(factory.getAutoTransformNames(), transform.getName());
		if (index >= 0) {
			goals[index] += g;
			hits[index]++;
			msecs[index] += time;
		}
	}

	public TransformFactory getFactory() {
		return factory;
	}

	public int[] getHits() {
		return hits;
	}

	public int[] getGoals() {
		return goals;
	}

	public long[] getMsecs() {
		return msecs;
	}

}
