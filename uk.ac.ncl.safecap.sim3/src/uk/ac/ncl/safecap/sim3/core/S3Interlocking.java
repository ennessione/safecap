package uk.ac.ncl.safecap.sim3.core;

import java.util.Set;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public interface S3Interlocking extends ISafetyProfile {
	void start();

	void step(Set<ITrain> activeTrains);

	void end();

	boolean isCompleted();

	Object getState();
}
