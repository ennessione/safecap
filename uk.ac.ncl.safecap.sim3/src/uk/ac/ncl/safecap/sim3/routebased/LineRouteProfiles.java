package uk.ac.ncl.safecap.sim3.routebased;

import safecap.model.Route;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.sim3.core.LineBasedAggregatingSafetyProfile;
import uk.ac.ncl.safecap.sim3.core.S3World;

public class LineRouteProfiles extends LineBasedAggregatingSafetyProfile<RouteProfile> {
	public LineRouteProfiles(S3World world, TrainLine line) {
		super(line);

		for (final Route route : line.getSchemaLine().getRoutes()) {
			add(new RouteProfile(world, line, route));
		}
	}
}
