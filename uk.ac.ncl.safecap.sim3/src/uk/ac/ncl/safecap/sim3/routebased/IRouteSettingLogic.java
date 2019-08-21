package uk.ac.ncl.safecap.sim3.routebased;

import java.util.Collection;

import safecap.model.Route;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public interface IRouteSettingLogic {
	Collection<Route> setRoutes(RBInterlockingState state, ITrain train);
}
