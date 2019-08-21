package uk.ac.ncl.safecap.prover.core;

import java.util.Collection;

public interface ITransformProvider {
	Collection<GoalTransform> getTransforms(TacticPackage tacticPackage);
}
