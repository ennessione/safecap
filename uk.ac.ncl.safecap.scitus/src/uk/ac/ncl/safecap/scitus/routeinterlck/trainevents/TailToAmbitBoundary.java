package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import safecap.model.Ambit;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.routeinterlck.ITrainEventSource;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

/**
 * XX: WQ1 Train head reaches ambit boundary
 * 
 * @author alex
 *
 */
public class TailToAmbitBoundary implements ITrainEventSource {
	private final SystemState state;

	public TailToAmbitBoundary(SystemState state) {
		this.state = state;
	}

	@Override
	public TrainEvent getTrainEventFor(S1TrainActor train) {
		final Ambit tailAmbit = train.getLine().getSegmentAmbit(train.getTailSegment());
//		Ambit headAmbit = train.getLine().getSegmentAmbit(train.getHeadSegment());
		assert tailAmbit != null;

//		double tail = train.getTail();
//		int zz = train.getLine().getSegmentIndex(train.getTail());

		final double tailAmbitPos = train.getLine().getAmbitOffset(tailAmbit) + train.getLine().getAmbitLength(tailAmbit);

		final double distance_to_ambit_end = tailAmbitPos - train.getTail();

		if (distance_to_ambit_end == 0) {
			final Ambit next_ambit = train.getLine().getNextAmbit(tailAmbit);
			if (next_ambit == null) {
				return TrainEvent.UNLIMITED;
			}

			final double nextAmbitPos = train.getLine().getAmbitOffset(next_ambit) + train.getLine().getAmbitLength(next_ambit);

			final double distance_to_next_ambit_end = nextAmbitPos - train.getTail();

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
			event.setDistanceOracle("TailToAmbitBoundary/2");
			event.setForHead(false);

			return event;
		} else if (distance_to_ambit_end < 0) {
			return TrainEvent.UNLIMITED;
		}

		// test if we can go to the boundary of the next ambit
		if (!state.canMove(train, distance_to_ambit_end)) {
			// state.getHistorian().sees("TailToAmbitBoundary failed: cannot move " + train
			// + " for " + distance_to_ambit_end + "m");
			return TrainEvent.UNLIMITED;
		}

		final SpeedBound speed = state.getSpeedManager().permissibleSpeedAt(train, distance_to_ambit_end);
		if (speed == null) {
			// state.getHistorian().sees("TailToAmbitBoundary failed: cannot get speed bound
			// for " + train + " at " + distance_to_ambit_end + "m");
			return TrainEvent.UNLIMITED;
		}

		final TrainEvent event = new TrainEvent(distance_to_ambit_end, speed);
		event.setCause(tailAmbit);
		event.setDistanceOracle("TailToAmbitBoundary");

		return event;
	}

}
