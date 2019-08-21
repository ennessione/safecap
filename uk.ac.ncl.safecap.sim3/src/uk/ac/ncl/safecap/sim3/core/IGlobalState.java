package uk.ac.ncl.safecap.sim3.core;

import java.util.Set;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public interface IGlobalState {
	void refreshGlobalState(Set<ITrain> movedTrains);
}
