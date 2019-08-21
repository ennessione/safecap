package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;
import safecap.schema.Segment;
import uk.ac.ncl.safecap.misc.core.PointSequence;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.adaptive.ScenarioPoint;
import uk.ac.ncl.safecap.scitus.base.AmbitDeoccupationInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.AmbitUnlockedInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.RouteUnsetInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.LimitSpeedBound;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.RouteSpeedBound;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.SpeedBoundManager;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.StationSpeedBound;

//import uk.ac.ncl.safecap.common.Stopwatch;

public class SystemState extends BaseSystemState {
	private Historian historian;
	private final SpeedBoundManager speed_manager;
	// adaptive control
	private final Map<Point, ScenarioPoint> scen_point;
	private final World world;

	public SystemState(World world) {
		super(world);
		this.world = world;

		if (world.getKind() == World.KIND.EXTENSIVE) {
			historian = new Historian(world);
		} else {
			historian = new DumbHistorian(world);
		}

		historian.setLive(false);
		historian.setPrintTime(false);

		speed_manager = new SpeedBoundManager();
		speed_manager.addSpeedBound(new RouteSpeedBound(this));
		speed_manager.addSpeedBound(new LimitSpeedBound());
		speed_manager.addSpeedBound(new StationSpeedBound());

		// adaptive control
		scen_point = new HashMap<>();

		// Initialize all scenario points
		for (final Ambit ambit : world.getProject().getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction j = (Junction) ambit;
				for (final Point p : j.getPoints()) {
					if (!scen_point.keySet().contains(p)) {
						scen_point.put(p, new ScenarioPoint(p));
					}
				}
			}
		}

		// save point to the adaptive points info
		world.getAdaptiveControlState().setScenarioPoints(scen_point);
	}

	public Historian getHistorian() {
		return historian;
	}

	public SpeedBoundManager getSpeedManager() {
		return speed_manager;
	}

	public ScenarioPoint getScenarioPoint(Point point) {
		return scen_point.get(point);
	}

	@Override
	public void routeUnSet(Route route) {
		super.routeUnSet(route);
		world.getInstanceProgressionAtStart().add(new RouteUnsetInstanceEvent(route));
	}

	@Override
	public void pointUnLock(ITrain train, Point point) {
		if (point_lock.containsKey(point)) {
			getScenarioPoint(point).annouceUnlocking((S1TrainActor) train, world.getTime());
			point_lock.remove(point);
			historian.sees("POINT " + point.getNode().getLabel() + " unlocked [" + world.getTime() + "]");
		}
	}

	@Override
	public void pointLock(Point point, ITrain train) {
		final TrainLine line = train.getLine();

		assert !point_lock.containsKey(point);
		point_lock.put(point, line);

		// adaptive control
		getScenarioPoint(point).annouceLocking((S1TrainActor) train, world.getTime());

		final PointSequence ps = world.getSequence(point);

		// System.out.println("POINT " + point.getNode().getLabel() + " locked [" +
		// world.getTime() + "]");
		if (ps != null) {
			// System.out.println("POINT SCENARIO OLD " +
			// ps.getCurrent(line.getSchemaLine()));
			ps.advance();
			// System.out.println("POINT SCENARIO NEW " +
			// ps.getCurrent(line.getSchemaLine()));
		}

		((S1TrainActor) train).setLastPoint(point);

		historian.sees("POINT " + point.getNode().getLabel() + " locked [" + world.getTime() + "]");
	}

	@Override
	public final boolean routeIsAvailable(Route route, TrainLine line) {
		// route is not being set
		if (route_setting.containsKey(route)) {
			return false;
		}

		final ControlLogic logic = line.getControlLogic(route);

		// the minimal proceed rule
		final Rule base_rule = logic.getRule().get(0);

		// route does not contain any locked ambits
		for (final Ambit ambit : base_rule.getClear()) {
			if (!isAmbitAvailableFor(ambit, line)) {
				return false;
			}
		}

		// route does not contain any locked points
		for (final Point point : point_routes.keySet()) {
			if (point_routes.get(point).contains(route)) {
				if (pointIsLocked(point)) {
					return false;
				}

				if (!isPointAccepting(point, line)) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean isPointAccepting(Point point, TrainLine line) {
		final PointSequence ps = world.getSequence(point);
		if (ps != null) {
			if (ps.getCurrent(line.getSchemaLine()) == line.getSchemaLine()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	protected void refreshOccupation(ITrain _train) {
		final S1TrainActor train = (S1TrainActor) _train;

		final int tail = train.getTailSegmentIndex();
		final int head = train.getHeadSegmentIndex();

		// removeOccupation(train);

		final Set<Ambit> occupied = new HashSet<>();

		for (int i = tail; i <= head; i++) {
			final Segment s = train.getLine().getPath().get(i);
			final Ambit a = train.getLine().getSegmentAmbit(s);

			assert i == tail || !occupation.containsKey(a) || occupation.get(a) == train;

			// adaptive control
			// a train rolls on an ambit containing a point
			if (!occupation.containsKey(a) && a instanceof Junction) {
				final Junction j = (Junction) a;
				announcePointOccupation(j, train);
			}

			occupation.put(a, train);
			if (!occupation_time.containsKey(a)) {
				occupation_time.put(a, world.getTime());
			}

			occupied.add(a);
		}

		removeOccupation(train, occupied);
	}

	@Override
	protected void removeOccupation(ITrain _train, Set<Ambit> occupied) {
		final Set<Ambit> to_free = new HashSet<>();
		final S1TrainActor train = (S1TrainActor) _train;

		for (final Ambit a : occupation.keySet()) {
			if (occupation.get(a) == train && !occupied.contains(a)) {
				to_free.add(a);
			}
		}

		for (final Ambit a : to_free) {
			if (a instanceof Junction) {
				final Junction j = (Junction) a;
				announcePointDeoccupation(j, train);

				for (final Point point : j.getPoints()) {
					// unlock point
					if (point_lock.containsKey(point)) {
						pointUnLock(train, point);
					}
				}
			}
			occupation.remove(a);
			occupation_time.remove(a);
			world.getInstanceProgressionAtStart().add(new AmbitDeoccupationInstanceEvent(a));
		}
	}

	private void announcePointOccupation(Junction j, S1TrainActor train) {
		for (final Point p : j.getPoints()) {
			getScenarioPoint(p).announceOccuputation(world.getTime(), train, train.getSpeed(), train.getAcceleration());
		}
	}

	private void announcePointDeoccupation(Junction j, S1TrainActor train) {
		for (final Point p : j.getPoints()) {
			getScenarioPoint(p).announceDeoccuputation(occupation_time.get(j), world.getTime(), train.getSpeed(), train.getAcceleration());
		}
	}

	@Override
	protected void ambitUnLock(Ambit to_lock) {

		if (ambit_lock.containsKey(to_lock)) {
			ambit_lock.remove(to_lock);
			if (world.getInstanceProgressionAtEnd() != null) {
				world.getInstanceProgressionAtEnd().add(new AmbitUnlockedInstanceEvent(to_lock));
			}
			historian.sees("AMBIT " + to_lock.getLabel() + " unlocked");
		}
	}
}
