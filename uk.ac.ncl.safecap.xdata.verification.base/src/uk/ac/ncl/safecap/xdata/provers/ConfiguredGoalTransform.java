package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.prover.core.GoalTransform;

public class ConfiguredGoalTransform {
	private final GoalTransform transforms;
	private final Object configuration;

	public ConfiguredGoalTransform(GoalTransform transforms, Object configuration) {
		this.transforms = transforms;
		this.configuration = configuration;
	}

	public GoalTransform getTransforms() {
		return transforms;
	}

	public Object getConfiguration() {
		return configuration;
	}

}
