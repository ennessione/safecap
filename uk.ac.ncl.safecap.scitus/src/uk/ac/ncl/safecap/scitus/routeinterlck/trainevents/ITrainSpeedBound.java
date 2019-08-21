package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public interface ITrainSpeedBound {

	/**
	 * Determine the top permissible speed at a given distance from the train head
	 * 
	 * @param train
	 * @param distance distance from train head
	 * @return
	 */
	SpeedBound permissibleSpeedAt(S1TrainActor train, double distance);
}
