package uk.ac.ncl.safecap.capacity.blockdiagram;

import java.util.List;

import safecap.Project;
import safecap.model.Ambit;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public interface IBlockDiagramdataProvider {

	Project getSafecapProject();

	List<TrainLine> getLine();

	List<S1TrainActor> getTrains();

	TimeInterval getAmbitBlockingTime(S1TrainActor train, Ambit ambit);

	double getTrainPosition(S1TrainActor train, double t);

	double getTimeDelta(S1TrainActor train);
}
