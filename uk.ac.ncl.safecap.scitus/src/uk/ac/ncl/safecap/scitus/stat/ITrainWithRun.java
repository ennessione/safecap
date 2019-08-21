package uk.ac.ncl.safecap.scitus.stat;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public interface ITrainWithRun extends ITrain {
	SimHistoryRecord getTrainRun();
}
