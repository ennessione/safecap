package uk.ac.ncl.safecap.sim3.core;

import safecap.trackside.Equipment;
import safecap.trackside.StopAndGo;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public class LineStationStops extends LineBasedAggregatingSafetyProfile<StationStop> {
	public LineStationStops(S3World world, TrainLine line) {
		super(line);

		for (final Equipment eq : line.getSpeedLimiterObjects()) {
			if (eq instanceof StopAndGo) {
				final StopAndGo sl = (StopAndGo) eq;
				add(new StationStop(world, line.getSpeedLimiterPosition(eq), sl.getDelay()));
			}
		}
	}
}
