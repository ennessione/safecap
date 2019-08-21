package uk.ac.ncl.safecap.scitus.base;

import org.eclipse.emf.ecore.EObject;

import safecap.trackside.StopAndGo;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.routeinterlck.MBInterlocking;
import uk.ac.ncl.safecap.scitus.routeinterlck.StationSignalInterlock;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;

public class StationStopEvent extends WorldTrainHeadPositionEvent {
	private static double delta = Delta.DELTA;
	private final double delay;
	private ITrainReleaseCondition releaseCondition;
	private final StopAndGo station;

	private StationStopEvent(StopAndGo station, World world, TrainLine line, double where, double delay) {
		super(world, line, where, delta);
		this.delay = delay;
		this.station = station;

		if (station.getReleaseRoute() != null) {
			final SystemState state = ((MBInterlocking) getWorld().getInterlocking()).getState();
			releaseCondition = new StationSignalInterlock(station.getReleaseRoute(), state);
		}
	}

	public static StationStopEvent getStationStop(World world, TrainLine line, StopAndGo station, double position) {
		return new StationStopEvent(station, world, line, position, station.getDelay());
	}

	public static void placeStationEvents(World world, TrainLine line) {
		for (final EObject _sl : line.getSpeedLimiterObjects()) {
			if (_sl instanceof StopAndGo) {
				final StopAndGo sl = (StopAndGo) _sl;
				final double position = line.getSpeedLimiterPosition(sl);
				final StationStopEvent ce = getStationStop(world, line, sl, position);
				world.addEvent(ce);
			}
		}
	}

	public static void placeStationEvents(World world) {
		for (final TrainLine line : world.getLines()) {
			placeStationEvents(world, line);
		}
	}

	@Override
	public void handle(S1TrainActor train) {
		getWorld().getActors().remove(train); // remove the train
		getWorld().addGrayEvent(new RestartTrainEvent(train, getWorld(), getWorld().time + delay, releaseCondition, station)); // and put it
																																// back
																																// later
	}

}
