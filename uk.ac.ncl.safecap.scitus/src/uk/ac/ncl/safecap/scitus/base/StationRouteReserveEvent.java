package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Route;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.routeinterlck.MBInterlocking;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

public class StationRouteReserveEvent extends WorldTimeEvent {
	private final S1TrainActor train;

	public StationRouteReserveEvent(S1TrainActor train, World world, double when) {
		super(world, when);
		this.train = train;
	}

	@Override
	public boolean fire(Object arg) {
		final Segment segment = train.getHeadSegment();
		final TrainLine line = train.getLine();
		final Route route = line.getSegmentRoute(segment);
		final Route next = line.getNextRoute(route);
		final SystemState state = ((MBInterlocking) world.getInterlocking()).getState();

		if (next != null && state.routeIsAvailable(next, line) && !state.routeIsSetFor(next, line)) {

		}
		return true;
	}

	private void tryToReserveNext() {

	}
}
