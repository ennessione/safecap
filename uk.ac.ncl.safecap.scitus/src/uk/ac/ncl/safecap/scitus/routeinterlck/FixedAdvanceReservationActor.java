package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.ArrayList;
import java.util.List;

import safecap.model.Route;
import safecap.schema.Segment;
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
public class FixedAdvanceReservationActor extends ReservationActor {
	private static final double reaction_time = 3d;
	private final S1TrainActor train; // the train for whom reservations are managed
	private final SystemState state;
	private int advance; // how many routes should be set, when possible, in front of a train

	public FixedAdvanceReservationActor(SystemState state, S1TrainActor train, int advance) {
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
		final Route route = line.getSegmentRoute(segment);
		if (!state.occupiesRoute(train, route)) {
			return Double.NEGATIVE_INFINITY;
		}

		Route next = line.getNextRoute(route);
		if (next == null) {
			return Double.NEGATIVE_INFINITY;
		}

		advance = Math.min(line.getControlLogic(next).getAspects(), advance);

		int set = 0;
		while (next != null && state.routeIsSetFor(next, line)) {
			next = line.getNextRoute(next);
			set++;
		}

		// next = line.getNextRoute(next);

		if (next != null && state.routeIsAvailable(next, line) && set < advance) {
			return reaction_time;
		} else {
			return Double.NEGATIVE_INFINITY;
		}
	}

	@Override
	public Progression progress(double horizon) throws SimulationException {
		final Segment segment = train.getHeadSegment();
		final TrainLine line = train.getLine();
		final Route route = line.getSegmentRoute(segment);

		Route next = line.getNextRoute(route);

		final List<Route> reserved = new ArrayList<>(10);

		advance = Math.min(line.getControlLogic(next).getAspects(), advance);

		int set = 0;
		while (next != null && state.routeIsSetFor(next, line)) {
			next = line.getNextRoute(next);
			set++;
		}

		// next = line.getNextRoute(next);

		while (next != null && state.routeIsAvailable(next, line) && set < advance) {
			state.routeSet(next, train);
			reserved.add(next);
			next = line.getNextRoute(next);
			set++;
		}

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
