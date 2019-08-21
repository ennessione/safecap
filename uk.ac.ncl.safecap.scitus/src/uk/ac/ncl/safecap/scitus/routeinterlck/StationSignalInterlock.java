package uk.ac.ncl.safecap.scitus.routeinterlck;

import safecap.model.Route;
import uk.ac.ncl.safecap.scitus.base.ITrainReleaseCondition;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public class StationSignalInterlock implements ITrainReleaseCondition {
	private final Route route;
	private final SystemState state;

	public StationSignalInterlock(Route route, SystemState state) {
		this.route = route;
		this.state = state;
	}

	@Override
	public boolean isConditionCleared(S1TrainActor actor) {
		return state.getRouteState(route).isProceedable();
	}

}
