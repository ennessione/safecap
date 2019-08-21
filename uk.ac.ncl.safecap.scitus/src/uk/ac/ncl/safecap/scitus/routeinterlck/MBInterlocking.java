package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.ArrayList;
import java.util.List;

import safecap.model.Point;
import safecap.model.Route;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.scitus.base.IActor;
import uk.ac.ncl.safecap.scitus.base.ITrainListener;
import uk.ac.ncl.safecap.scitus.base.InitialRouteClaimInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.SpeedChangeEvent;
import uk.ac.ncl.safecap.scitus.base.StationStopEvent;
import uk.ac.ncl.safecap.scitus.base.TrainController;
import uk.ac.ncl.safecap.scitus.base.TrainEvent;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.common.TrainOverlapException;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.HeadToAmbitBoundary;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.HeadToSignal;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.HeadToSpeedLimit;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.HeadToStation;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.SpeedBound;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.TailToAmbitBoundary;

public class MBInterlocking extends TrainController implements ITrainListener {
	private static final boolean PLACE_AT_SPEED = true;
	private final SystemState state;
	private final List<S1TrainActor> waiting_list; // train waiting list
	private final TrainEventManager train_event_manager;

	public MBInterlocking(World world) {
		super(world);
		world.setInterlocking(this);
		state = new SystemState(world);
		waiting_list = new ArrayList<>(world.getActors().size());

		train_event_manager = new TrainEventManager();
		train_event_manager.addEventSource(new TailToAmbitBoundary(state));
		train_event_manager.addEventSource(new HeadToAmbitBoundary(state));
		train_event_manager.addEventSource(new HeadToSpeedLimit(state));
		train_event_manager.addEventSource(new HeadToStation(state));
		train_event_manager.addEventSource(new HeadToSignal(state));

		// train_event_manager.addEventSource(new HeadToRouteBoundary(state));
		// train_event_manager.addEventSource(new HeadToNextAmbitBoundary());
		// train_event_manager.addEventSource(new HeadToNextRouteBoundary(state));

		for (final Point point : state.getPoints()) {
			world.addActor(new PointActor(state, point));
		}

		for (final Route route : world.getProject().getRoutes()) {
			world.addActor(new RouteActor(state, route));
		}

		// add speed sign events
		SpeedChangeEvent.placeSpeedChangers(world);
		StationStopEvent.placeStationEvents(world);

	}

	@Override
	public void register(S1TrainActor train) {
		train.addListener(this);
		final IActor actor = new SmartFixedAdvanceReservationActor(state, train, 5);
		world.addActor(actor);

		final boolean satisfiable = trainOccupationSatisfiable(train);
		if (satisfiable) {
			state.refreshPosition(train);
			announcePlacement(train);
			// state.getHistorian().sees("TRAIN " + train.getName() + " placed");
		} else {
			waiting_list.add(train);
			// state.getHistorian().sees("TRAIN " + train.getName() + " queued");
		}
	}

	@Override
	public void unregister(S1TrainActor train) {
		train.removeListener(this);
		state.removePosition(train);
		waiting_list.remove(train);

		// state.getHistorian().sees("TRAIN " + train.getName() + " unregister");
	}

	@Override
	public void trainMoved(S1TrainActor train) {
		state.refreshPosition(train);
		// state.getHistorian().sees("TRAIN " + train.getName() + " moved");
		attendWaitingList();
	}

	@Override
	public double[] movementAuthority(S1TrainActor train) throws TrainOverlapException {
		if (waiting_list.contains(train)) {
			return new double[] { 0d, 0d };
		} else {
			TrainEvent event = train_event_manager.getResolvedTrainEvent(train);

			// unlimited resolution means no movement authority
			if (event.getDistance() == Double.POSITIVE_INFINITY) {
				event = TrainEvent.IMMEDIATE_STOP;
			}

			// state.getHistorian().sees("TRAIN " + train.getName() + " authority\t" +
			// event);

			return new double[] { event.getDistance(), event.getSpeedAtEnd() };
		}
	}

	private boolean trainOccupationSatisfiable(S1TrainActor train) {
		// TODO: why are these two getting the same value ?
		final Segment hs = train.getHeadSegment();
		final Route hr = train.getLine().getSegmentRoute(hs);
		final Segment ts = train.getHeadSegment();
		final Route tr = train.getLine().getSegmentRoute(ts);
		return state.routeIsAvailable(hr, train.getLine()) && state.routeIsAvailable(tr, train.getLine());
	}

	// a train is placed, its initial speed is set and the occupation/reservation
	// status of track is updated
	private void announcePlacement(S1TrainActor train) {

		train.setMaximumSpeed(
				train.getLine().hasEntrySpeedLimit() ? train.getLine().getEntrySpeedLimit() : train.getDescriptor().getMaxSpeed());
		train.setPlacementTime(world.getTime());

		if (PLACE_AT_SPEED) {
			final SpeedBound bound = state.getSpeedManager().permissibleSpeedAt(train, 0);
			final Double max_speed = bound.getBound();
			train.setSpeed(max_speed);
		}

		final Segment hs = train.getHeadSegment();
		final Route hr = train.getLine().getSegmentRoute(hs);

		world.getInstanceProgressionAtStart().add(new InitialRouteClaimInstanceEvent(hr));

		final Segment ts = train.getHeadSegment();
		if (ts != hs) {
			final Route tr = train.getLine().getSegmentRoute(ts);
			if (tr != hr) {
				world.getInstanceProgressionAtStart().add(new InitialRouteClaimInstanceEvent(tr));
			}
		}
	}

	private void attendWaitingList() {
		final List<S1TrainActor> satisfied = new ArrayList<>();
		for (final S1TrainActor train : waiting_list) {
			final boolean satisfiable = trainOccupationSatisfiable(train);
			if (satisfiable) {
				state.refreshPosition(train);
				satisfied.add(train);
				announcePlacement(train);
				// state.getHistorian().sees("TRAIN " + train.getName() + " dequeued");
			}
		}

		waiting_list.removeAll(satisfied);
	}

	public SystemState getState() {
		return state;
	}

}
