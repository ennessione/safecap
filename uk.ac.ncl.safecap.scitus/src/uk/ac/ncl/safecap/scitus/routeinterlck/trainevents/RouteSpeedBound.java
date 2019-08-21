package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import java.util.List;

import safecap.model.Route;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

public class RouteSpeedBound implements ITrainSpeedBound {
	private final SystemState state;

	public RouteSpeedBound(SystemState state) {
		this.state = state;
	}

	@Override
	public SpeedBound permissibleSpeedAt(S1TrainActor train, double distance) {

		final Segment segment = train.getLine().getSegment(train.getHead() + distance);

		final Route route = train.getLine().getSegmentRoute(segment);
		return permissibleSpeedAt(train, route, distance);
	}

	public SpeedBound permissibleSpeedAt(S1TrainActor train, Route route, double distance) {

		assert route != null;

		final Route next = train.getLine().getNextRoute(route);

		if (next == null) {
			// speed is determined by the end of line
//			if (train.getLine().hasExitSpeedLimit()) {
//				double speed = Math.min(train.getDescriptor().getMaxSpeed(), train.getLine().getExitSpeedLimit());
//				return new SpeedBound(speed, "Line end (" + speed + ")");
//			} else {
			return new SpeedBound(train.getMaximumSpeed(), "Line end (UNLIMITED)");
//			}
		}

		final double speed = signalSpeed(train, next);

		final Signal guardian = train.getLine().getGuardian(next);
		final double guardian_pos = train.getLine().getSignalPosition(guardian);

		final double next_route_position = guardian_pos;

		final double offset = next_route_position - (train.getHead() + distance);
		if (offset < 0) {
			final Route next2 = train.getLine().getNextRoute(next);
			if (next2 != null) {
				return permissibleSpeedAt(train, next, distance);
			} else {
				return new SpeedBound(train.getMaximumSpeed(), "Passed permissive route aspect for " + next.getLabel());
				// return SpeedBound.UNBOUNDED;
			}
		}

		double speed_limit = Math.sqrt(2 * train.getDescriptor().getMaxDeceleration() * offset + speed * speed);
		speed_limit = Math.min(speed_limit, train.getMaximumSpeed());

		assert !Double.isNaN(speed_limit);

		// return new SpeedBound(speed_limit, "Route " + next.getLabel() + " showing " +
		// RouteUtil.getAspectLabel(next, state.getRouteState(next).getAspect()));
		return new SpeedBound(speed_limit, "Route " + next.getLabel() + " showing " + state.getRouteState(next).getAspect());

	}

	private double signalSpeed(S1TrainActor train, Route route) {
		final List<Route> route_path = state.effectiveSignals(train.getLine(), route);
		if (route_path.size() == 0) {
			return 0d;
		}

		double len = 0;

		for (final Route r : route_path) {
			if (train.getLine().getNextRoute(r) == null) { // the last route on the line - no speed limit or exit speed limit
//				if (train.getLine().hasExitSpeedLimit()) {
//					return Math.min(train.getDescriptor().getMaxSpeed(), train.getLine().getExitSpeedLimit());
//				} else {
				return train.getMaximumSpeed();
//				}
			}
			len += RouteUtil.getLength(r);
		}

		// for the last route we take into consideration the signal position
		final Route lst = route_path.get(route_path.size() - 1);
		final Signal lst_grd = train.getLine().getGuardian(lst);
		if (lst_grd != null) {
			final double spos = train.getLine().getSignalPosition(lst_grd);
			final double fpos = train.getLine().getAmbitOffset(lst.getAmbits().get(0)); // the position of last route start
			final double sig_offset = fpos - spos; // the 'lost' track position due to forward signal position
			len -= sig_offset;
		}

		final double qdr_limit = 2 * train.getDescriptor().getMaxDeceleration() * len;

		double speed = Math.sqrt(qdr_limit);

		speed = Math.min(train.getMaximumSpeed(), speed);

		assert !Double.isNaN(speed);

		return speed;
	}

}
