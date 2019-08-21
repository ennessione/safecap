package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.Map;
import java.util.Set;

import safecap.model.Ambit;
import safecap.model.Point;
import safecap.model.Route;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.common.PointState;
import uk.ac.ncl.safecap.scitus.common.RouteState;

public interface IRBSystemState {
	RouteState getRouteState(Route route);

	PointState getPointState(Point point);

	Map<Ambit, ITrain> getOccupation();

	double getOccupationTime(Ambit ambit);

	double getWorldTime();

	void removePosition(ITrain train);

	void refreshState(Set<ITrain> trains);

	boolean isCompleted();

	void debugRouteState(Route route);
}
