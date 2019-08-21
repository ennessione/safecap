package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import safecap.model.Ambit;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.routeinterlck.ITrainEventSource;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

/**
 * Train head reaches ambit boundary
 * 
 * @author alex
 *
 */
public class HeadToAmbitBoundary implements ITrainEventSource {
	private final SystemState state;

	public HeadToAmbitBoundary(SystemState state) {
		this.state = state;
	}

	@Override
	public TrainEvent getTrainEventFor(S1TrainActor train) {

		final Ambit headAmbit = train.getLine().getSegmentAmbit(train.getHeadSegment());
		if (headAmbit == null) { // possible when train is about to finish
			return TrainEvent.UNLIMITED;
		}

		final double headAmbitPos = train.getLine().getAmbitOffset(headAmbit) + train.getLine().getAmbitLength(headAmbit);

		final double distance_to_ambit_end = headAmbitPos - train.getHead();
		if (distance_to_ambit_end == 0) { // right on ambit boundary already
			final Ambit next_ambit = train.getLine().getNextAmbit(headAmbit);
			if (next_ambit == null) {
				return TrainEvent.UNLIMITED;
			}

			final double nextAmbitPos = train.getLine().getAmbitOffset(next_ambit) + train.getLine().getAmbitLength(next_ambit);
			final double distance_to_next_ambit_end = nextAmbitPos - train.getHead();

			// test if we can go to the boundary of the next mabit
			if (!state.canMove(train, distance_to_next_ambit_end)) {
				return TrainEvent.UNLIMITED;
			}

			final SpeedBound speed = state.getSpeedManager().permissibleSpeedAt(train, distance_to_next_ambit_end);

			if (speed == null) {
				return TrainEvent.UNLIMITED;
			}

			final TrainEvent event = new TrainEvent(distance_to_next_ambit_end, speed);
			event.setCause(next_ambit);
			event.setDistanceOracle("HeadToAmbitBoundary/2");

			return event;
		} else if (distance_to_ambit_end < 0) {
			return TrainEvent.UNLIMITED;
		}

		// test if we can go to the boundary of the next mabit
		if (!state.canMove(train, distance_to_ambit_end)) {
			return TrainEvent.UNLIMITED;
		}

		// double speed = Util.permissibleSpeedAtAmbitEnd(state, headAmbit, train);
		final SpeedBound speed = state.getSpeedManager().permissibleSpeedAt(train, distance_to_ambit_end);

		if (speed == null) {
			return TrainEvent.UNLIMITED;
		}

		final TrainEvent event = new TrainEvent(distance_to_ambit_end, speed);
		event.setCause(headAmbit);
		event.setDistanceOracle("HeadToAmbitBoundary");

		return event;
	}

}
