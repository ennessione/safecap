package uk.ac.ncl.safecap.sim3.routebased;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import safecap.model.Route;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public class FixedAdvanceRouteSetter implements IRouteSettingLogic {

	@Override
	public Collection<Route> setRoutes(RBInterlockingState state, ITrain train) {
		final Segment segment = train.getHeadSegment();
		final TrainLine line = train.getLine();
		final Route route = line.getSegmentRoute(segment);
//		if (!state.occupiesRoute(train, route))
//			return Collections.emptySet();

		Route next = train.getLine().getNextRoute(route);
		if (next == null) {
			return Collections.emptySet();
		}

		if (line.getControlLogic(next) == null) {
			return Collections.emptySet();
		}

		final int advance = line.getControlLogic(next).getAspects();

		int set = 0;
		while (next != null && state.routeIsSetFor(next, line)) {
			next = line.getNextRoute(next);
			set++;
		}

		final Set<Route> reserved = new HashSet<>();

		while (next != null && state.routeIsAvailable(next, line) && set < advance) {
			reserved.add(next);
			next = line.getNextRoute(next);
			set++;
		}

		return reserved;
	}

}
