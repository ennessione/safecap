package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.stat.ITrainWithRun;

public interface IS3Train extends ITrainWithRun {
	void start(IWorld world);

	/**
	 * Performs train movement simulation. Returns true if the train has moved
	 * 
	 * @param profile
	 * @param delta
	 * @return
	 */
	boolean move(ISafetyProfile profile);
}
