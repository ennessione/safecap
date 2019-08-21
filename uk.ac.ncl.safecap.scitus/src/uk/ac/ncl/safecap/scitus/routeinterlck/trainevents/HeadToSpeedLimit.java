package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.EquipmentPosition;
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
public class HeadToSpeedLimit implements ITrainEventSource {
	private final SystemState state;

	public HeadToSpeedLimit(SystemState state) {
		this.state = state;
	}

	@Override
	public TrainEvent getTrainEventFor(S1TrainActor train) {

		EquipmentPosition limit = train.getLine().getNextSpeedLimit(train.getHead());

		if (limit.getEquipment() == null) {
			return TrainEvent.UNLIMITED;
		}

		// attempt to get next speed limit
		if (limit.distance == 0) {
			limit = train.getLine().getNextSpeedLimit(train.getHead() + Delta.DELTA);
			if (limit.getEquipment() == null) {
				return TrainEvent.UNLIMITED;
			}

		}

		// check if the train can move to the limit sign
		if (!state.canMove(train, limit.distance)) {
			return TrainEvent.UNLIMITED;
		}

		// double speed = ((SpeedLimit) limit.getEquipment()).getLimit();
		final SpeedBound speed = state.getSpeedManager().permissibleSpeedAt(train, limit.distance);
		if (speed == null) {
			return TrainEvent.UNLIMITED;
		}

		final TrainEvent event = new TrainEvent(limit.distance, speed);
		event.setCause(limit.getEquipment());
		event.setDistanceOracle("HeadToSpeedLimit");

		return event;
	}

}
