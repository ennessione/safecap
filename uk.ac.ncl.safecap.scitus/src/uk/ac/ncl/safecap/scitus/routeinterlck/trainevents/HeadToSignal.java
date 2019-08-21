package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import safecap.trackside.Signal;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.routeinterlck.ITrainEventSource;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

/**
 * Train head reaches permanent speed restriction sign
 * 
 * @author alex
 *
 */
public class HeadToSignal implements ITrainEventSource {
	private final SystemState state;

	public HeadToSignal(SystemState state) {
		this.state = state;
	}

	@Override
	public TrainEvent getTrainEventFor(S1TrainActor train) {

		final Signal signal = train.getLine().getNextSignal(train.getHead());

		if (signal == null) {
			return TrainEvent.UNLIMITED;
		}

		final double distance = train.getLine().getSignalPosition(signal) - train.getHead();

		// check if the train can move to the limit sign
		if (!state.canMove(train, distance)) {
			return TrainEvent.UNLIMITED;
		}

		final SpeedBound speed = state.getSpeedManager().permissibleSpeedAt(train, distance);
		if (speed == null) {
			return TrainEvent.UNLIMITED;
		}

		final TrainEvent event = new TrainEvent(distance, speed);
		event.setCause(signal);
		event.setDistanceOracle("HeadToSignal");

		return event;
	}

}
