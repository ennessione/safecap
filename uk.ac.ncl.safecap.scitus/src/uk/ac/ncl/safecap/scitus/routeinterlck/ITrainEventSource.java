package uk.ac.ncl.safecap.scitus.routeinterlck;

import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;

public interface ITrainEventSource {
	TrainEvent getTrainEventFor(S1TrainActor train);
}
