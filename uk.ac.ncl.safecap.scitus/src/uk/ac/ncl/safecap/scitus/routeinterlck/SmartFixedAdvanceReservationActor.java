package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import safecap.model.Route;
import safecap.schema.Segment;
import safecap.trackside.StopAndGo;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.ReservationActor;
import uk.ac.ncl.safecap.scitus.base.RouteSettingProgression;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

/**
 * This actor sets routes for a given line
 * 
 * @author alex
 *
 */
public class SmartFixedAdvanceReservationActor extends ReservationActor {
	private static final double reaction_time = 3d;
	private final S1TrainActor train; // the train for whom reservations are managed
	private final SystemState state;
	private int advance; // how many routes should be set, when possible, in front of a train

	public SmartFixedAdvanceReservationActor(SystemState state, S1TrainActor train, int advance) {
		super(train);
		this.train = train;
		this.state = state;
		this.advance = advance;

		assert advance >= 1;
	}

	@Override
	public double eventHorizon() throws SimulationException {
		final Segment segment = train.getHeadSegment();
		final TrainLine line = train.getLine();

		// the current route
		final Route route = line.getSegmentRoute(segment);

		if (!state.occupiesRoute(train, route)) {
			return Double.NEGATIVE_INFINITY;
		}

		// a station on the route
		final StopAndGo station = gotStation(line, route);
		if (station != null) { // if there is a station, no progression is permitted
			final double d = line.getSpeedLimiterPosition(station);
			if (train.getRestartSource() != station && (Delta.isLess(train.getHead(), d) || Delta.Eql(train.getHead(), d))) {
				return Double.NEGATIVE_INFINITY;
			}
		}

		// a next route
		Route next = line.getNextRoute(route);
		if (next == null) {
			return Double.NEGATIVE_INFINITY;
		}

		// maximum number of routes reserved in front of a train
		advance = Math.min(line.getControlLogic(next).getAspects(), advance);

		int set = 0; // reservation counter

		// skip routes already reserved
		while (next != null && state.routeIsSetFor(next, line)) {
			if (gotStation(line, next) != null) {
				break;
			}
			next = line.getNextRoute(next);
			set++;
		}

		// on loop completion, next is the first route to be reserved

		if (next != null && !state.routeIsSetFor(next, line) && state.routeIsAvailable(next, line) && set < advance) {
			return reaction_time;
		} else {
			return Double.NEGATIVE_INFINITY;
		}
	}

	private StopAndGo gotStation(TrainLine line, Route r) {
		if (r == null) {
			return null;
		}

		final Segment last = r.getSegments().get(r.getSegments().size() - 1);

		final double start = line.getSegmentOffset(r.getSegments().get(0));
		final double end = line.getSegmentOffset(last) + last.getLength();

		for (final EObject _o : line.getSpeedLimiterObjects()) {
			final double d = line.getSpeedLimiterPosition(_o);
			if (start <= d && d <= end && _o instanceof StopAndGo) {
				return (StopAndGo) _o;
			}
		}

		return null;
	}

	@Override
	public Progression progress(double horizon) throws SimulationException {
		final Segment segment = train.getHeadSegment();
		final TrainLine line = train.getLine();

		// the current route
		final Route route = line.getSegmentRoute(segment);

		// a next route
		Route next = line.getNextRoute(route);

		// maximum number of routes reserved in front of a train
		advance = Math.min(line.getControlLogic(next).getAspects(), advance);

		int set = 0; // reservation counter

		// skip routes already reserved
		while (next != null && state.routeIsSetFor(next, line)) {
			next = line.getNextRoute(next);
			set++;
		}

		// reserved routes
		final List<Route> reserved = new ArrayList<>(10);

		while (next != null && state.routeIsAvailable(next, line) && set < advance) {
			reserved.add(next);
			set++;
			if (gotStation(line, next) != null) {
				break;
			}
			next = line.getNextRoute(next);
		}

		for (final Route r : reserved) {
			state.routeSet(r, train);
		}

		// System.out.println("Locks:" + state.getLocks());

		if (reserved.size() > 0) {
			return new RouteSettingProgression(this, reserved);
		} else {
			return null;
		}

	}

	@Override
	public String toString() {
		return "FARActor(" + train.getName() + ")";
	}
}
