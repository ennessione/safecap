package uk.ac.ncl.safecap.scitus.routeinterlck;

import safecap.model.Route;
import uk.ac.ncl.safecap.scitus.base.IActor;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.RouteProgression;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

public class RouteActor implements IActor {
	private static final double switching_time = 0.05d;
	private final SystemState state;
	private final Route route;

	public Route getRoute() {
		return route;
	}

	public RouteActor(SystemState state, Route route) {
		this.state = state;
		this.route = route;
	}

	@Override
	public double eventHorizon() throws SimulationException {
		if (!state.routeIsSet(route)) {
			return Double.NEGATIVE_INFINITY; // no change
		}

		final int current = state.getRouteState(route).getAspect();
		final int actual = state.computeRouteAspect(route);

		if (current == actual) {
			return Double.NEGATIVE_INFINITY; // no change
		} else {
			return switching_time;
		}
	}

	@Override
	public Progression progress(double horizon) throws SimulationException {
		final int current = state.getRouteState(route).getAspect();
		final int actual = state.computeRouteAspect(route);
		assert current != actual;

		state.refreshRouteState(route);

		return new RouteProgression(this, current, actual, state.getSetLine(route));
	}

	@Override
	public String toString() {
		return "RouteActor(" + route.getLabel() + ")";
	}
}
