package uk.ac.ncl.safecap.scitus.base;

public interface ITrainReleaseCondition {
	boolean isConditionCleared(S1TrainActor actor);
}
