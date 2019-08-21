package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Route;

public class RouteUnsetInstanceEvent extends InstanceEvent {
	public RouteUnsetInstanceEvent(Route route) {
		super(route);
	}

	public Route getRoute() {
		return (Route) getSubject();
	}

	@Override
	public String toString() {
		return "ROUTE " + getRoute() + " unset";
	}

}
