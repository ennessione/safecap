package uk.ac.ncl.safecap.scitus.base;

import java.util.List;

import safecap.model.Route;

public class RouteSettingProgression extends Progression {
	private final List<Route> routes;

	public RouteSettingProgression(ReservationActor actor, List<Route> routes) {
		super(actor);
		this.routes = routes;
	}

	/**
	 * @return set of routes reserved (set) during the current extent
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	public S1TrainActor getTrain() {
		return ((ReservationActor) getActor()).getTrain();
	}

	@Override
	public String toString() {
		return "SET " + getRoutes() + " for line " + getTrain().getLine().getSchemaLine().getLabel();
	}
}
