package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import java.util.List;

import safecap.model.Ambit;
import safecap.model.Route;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

public class Util {
	public static double permissibleSpeedAtAmbitEnd(SystemState state, Ambit ambit, S1TrainActor train) {
		final Route route = train.getLine().getSegmentRoute(ambit.getSegments().get(0));

		assert route != null;

		final Route next = train.getLine().getNextRoute(route);

		if (next == null) {
			return train.getMaximumSpeed();
		}

		final double[] sp = signalSpeedRange(state, train, next);

		// TODO: consult driver on preferred speed

		final double speed = sp[1];

		final double ambit_position = train.getLine().getAmbitOffset(ambit) + train.getLine().getAmbitLength(ambit);
		final double next_route_position = train.getLine().getAmbitOffset(next.getAmbits().get(0));
		final double offset = next_route_position - ambit_position;
		final double speed_limit = Math.sqrt(2 * train.getDescriptor().getMaxDeceleration() * offset + speed * speed);

		return speed_limit;
	}

	public static double[] signalSpeedRange(SystemState state, S1TrainActor train, Route route) {
		final List<Route> route_path = state.effectiveSignals(train.getLine(), route);

		double len = 0;

		for (final Route r : route_path) {
			len += RouteUtil.getLength(r);
		}

		final double qdr_limit = 2 * train.getDescriptor().getMaxDeceleration() * len;

		double speed = Math.sqrt(qdr_limit);

		speed = Math.min(train.getMaximumSpeed(), speed);

		if (train.getSpeed() <= speed) {
			return new double[] { train.getSpeed(), speed };
		} else {
			return new double[] { speed / 2, speed };
		}
	}
}
