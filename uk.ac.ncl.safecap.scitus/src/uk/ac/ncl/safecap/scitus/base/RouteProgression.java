package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Route;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.routeinterlck.RouteActor;

public class RouteProgression extends Progression {
	private final int old_state; // -1 encodes 'not set'
	private final int new_state; // -1 encodes 'not set'
	private final TrainLine line; // the line in context

	public RouteProgression(RouteActor route, int old_state, int new_state, TrainLine line) {
		super(route);
		this.old_state = old_state;
		this.new_state = new_state;
		this.line = line;
	}

	/**
	 * Returns route state at the beginning of the extent
	 * 
	 * @return -1 for route unset, 0 .. route.getAspects() for a route aspect
	 */
	public int getOldState() {
		return old_state;
	}

	/**
	 * Returns route state at the end of the extent
	 * 
	 * @return -1 for route unset, 0 .. route.getAspects() for a route aspect
	 */
	public int getNewState() {
		return new_state;
	}

	public Route getRoute() {
		return ((RouteActor) getActor()).getRoute();
	}

	public TrainLine getLine() {
		return line;
	}

	@Override
	public String toString() {
		return "ROUTE " + getRoute() + " from " + old_state + " TO " + new_state + " FOR " + line;
	}
}
