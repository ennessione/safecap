package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Route;

public class InitialRouteClaimInstanceEvent extends InstanceEvent {
	public InitialRouteClaimInstanceEvent(Route route) {
		super(route);
	}

	public Route getRoute() {
		return (Route) getSubject();
	}

	@Override
	public String toString() {
		return "ROUTE " + getRoute() + " claimed";
	}

}
