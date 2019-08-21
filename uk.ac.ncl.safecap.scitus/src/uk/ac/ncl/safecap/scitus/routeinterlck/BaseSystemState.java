package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import safecap.Project;
import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Junction;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.Rule;
import safecap.schema.Segment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.PointConf;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.common.PointState;
import uk.ac.ncl.safecap.scitus.common.RouteState;
//import uk.ac.ncl.safecap.common.Stopwatch;

public class BaseSystemState implements IRBSystemState {
	// state
	protected Map<Ambit, ITrain> occupation;
	protected Map<Ambit, TrainLine> ambit_lock;
	protected Map<Route, TrainLine> route_setting;
	protected Map<Route, RouteState> route_state;
	protected Map<Point, PointState> point_state;
	protected Map<Point, TrainLine> point_lock;
	protected Map<Ambit, Double> occupation_time;

	protected Project root;
	private final IWorld world;

	protected RuleInterpreter interpreter;

	// auxiliary variables
	protected Map<Point, List<Route>> point_routes; // routes containing a given point
	protected Map<Route, PointConf> point_conf; // the point configuration of a route

	public BaseSystemState(IWorld world) {
		this.world = world;
		root = world.getProject();

		occupation = new HashMap<>();
		ambit_lock = new HashMap<>();
		route_state = new HashMap<>();
		point_state = new HashMap<>();
		route_setting = new HashMap<>();
		point_lock = new HashMap<>();
		occupation_time = new HashMap<>();

		point_routes = new HashMap<>();
		point_conf = new HashMap<>();

		interpreter = new RuleInterpreter(this);

		populateAuxiliary();
	}

	@Override
	public void debugRouteState(Route route) {
		final TrainLine line = route_setting.get(route);
		if (line == null) {
			System.out.println("\tnot set");
			return;
		}

		final ControlLogic logic = line.getControlLogic(route);
		if (logic == null) {
			System.out.println("\tno control logic defined");
			return;
		}

		for (final Rule r : logic.getRule()) {
			final boolean result = interpreter.debug(r);
			if (!result) {
				break;
			}
		}
	}

	public boolean occupiesRoute(ITrain train, Route route) {
		for (final Ambit a : route.getAmbits()) {
			if (occupation.get(a) == train) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks whether the train head may move to a given location on the line
	 */
	public boolean canMoveToPoint(ITrain train, double position) {
		final Segment s = train.getLine().getSegment(position);
		final Route current = train.getLine().getSegmentRoute(train.getLine().getSegment(train.getHead()));
		Route r = train.getLine().getSegmentRoute(s);

		if (r == current) { // if moving within the same route
			// .. check that train head does not move beyond the guardian signal position
			final Route nxt = train.getLine().getNextRoute(current);
			if (nxt == null) {
				return true;
			}

			final Signal sig = train.getLine().getGuardian(nxt);
			if (sig == null) {
				return true;
			}

			final double spos = train.getLine().getSignalPosition(sig);
			if (position <= spos) {
				return true;
			} else {
				r = train.getLine().getNextRoute(current); // check the availability of the next route
			}
		}

		return r != null && // the new route is known
				r == train.getLine().getNextRoute(current) && // the same as current or the next one
				routeIsSetFor(r, train.getLine()) && // the route is set properly
				getRouteState(r).isProceedable(); // the route is proceedable
	}

	/**
	 * Checks whether a train can move a given distance from the current position
	 */
	public boolean canMove(ITrain train, double distance) {
		return canMoveToPoint(train, train.getHead() + distance);
	}

	/**
	 * Starting from the given signal, build the list ending with a RED signal but
	 * not longer than start.getAspects() - 1
	 * 
	 * @param start the route (signal) to start
	 * @return a list of routes
	 */
	public List<Route> effectiveSignals(TrainLine line, Route start) {
		final List<Route> result = new ArrayList<>();

		int max_len = line.getControlLogic(start).getAspects() - 1;

		Route current = start;

		while (max_len > 0 && routeIsSetFor(current, line) && getRouteState(current).isProceedable()) {
			result.add(current);
			current = line.getNextRoute(current);
			max_len--;
		}

		return result;
	}

	protected void setRouteAspect(Route route, int aspect) {
		final TrainLine line = route_setting.get(route);
		assert line != null;

		final ControlLogic logic = line.getControlLogic(route);
		assert logic != null;

		assert aspect >= 0 && aspect <= logic.getAspects();

		final RouteState state = route_state.get(route);
		state.setAspect(aspect);
	}

	public TrainLine getSetLine(Route route) {
		assert route_setting.containsKey(route);
		return route_setting.get(route);
	}

	public int computeRouteAspect(Route route) {
		int i;
		final TrainLine line = route_setting.get(route);
		if (line == null) {
			return 0;
		}

		final ControlLogic logic = line.getControlLogic(route);
		assert logic != null;

		for (i = 0; i < logic.getAspects(); i++) {
			final Rule r = i < logic.getRule().size() ? logic.getRule().get(i) : null;
			final boolean result = interpreter.evaluate(r, i, line, route, logic);
			if (!result) {
				return i;
			}
		}

		return logic.getAspects() - 1;
	}

	public void refreshRouteState(Route route) {
		final int i = computeRouteAspect(route);

		if (i != getRouteState(route).getAspect()) {
			setRouteAspect(route, i);
		}
	}

	@Override
	public RouteState getRouteState(Route route) {
		final RouteState state = route_state.get(route);

		assert routeIsSet(route) || !state.isProceedable();

		return state;
	}

	public void routeSet(Route route, ITrain train) {

		final TrainLine line = train.getLine();

		assert routeIsAvailable(route, line);

		route_setting.put(route, line);
		route_state.get(route).setAspect(0);

		// lock points
		for (final Point point : point_routes.keySet()) {
			if (point_routes.get(point).contains(route)) {
				assert !pointIsLocked(point);
				pointLock(point, train);
			}
		}

		final ControlLogic logic = line.getControlLogic(route);

		// the minimal proceed rule
		final Rule base_rule = logic.getRule().get(0);

		// lock ambits
		for (final Ambit ambit : base_rule.getClear()) {
			assert isAmbitAvailableFor(ambit, line);
			ambit_lock.put(ambit, line);
		}

	}

	public boolean routeIsSet(Route route) {
		return route_setting.keySet().contains(route);
	}

	public boolean routeIsSetFor(Route route, TrainLine line) {
		return route_setting.get(route) == line;
	}

	public void routeUnSet(Route route) {
		assert routeIsSet(route);

		route_state.get(route).setAspect(0);
		route_setting.remove(route);
		// System.out.println("Unset route " + route);

	}

	public boolean pointIsLocked(Point point) {
		return point_lock.containsKey(point);
	}

	public boolean pointIsLockedFor(Point point, TrainLine line) {
		return point_lock.get(point) == line;
	}

	public boolean pointInSetRoute(Point point) {
		for (final Route route : point_routes.get(point)) {
			if (routeIsSet(route)) {
				return true;
			}
		}

		return false;
	}

	public void pointUnLock(ITrain train, Point point) {
		if (point_lock.containsKey(point)) {
			point_lock.remove(point);
		}
	}

	public void pointLock(Point point, ITrain train) {
		final TrainLine line = train.getLine();

		assert !point_lock.containsKey(point);
		point_lock.put(point, line);
	}

	public void pointMove(Point point) {
		final PointState new_state = computePointState(point);

		if (getPointState(point) != new_state) {
			point_state.put(point, new_state);
		}
	}

	public PointState computePointState(Point point) {
		final PointState state = point_state.get(point);
		Route set_route = null;

		for (final Route route : point_routes.get(point)) {
			if (route_setting.containsKey(route)) {
				set_route = route;
				break;
			}
		}

		if (set_route == null) {
			return state;
		}

		final PointConf conf = point_conf.get(set_route);
		final Rule rule = point.getRule();

		final boolean can_switch = interpreter.evaluate(rule);

		if (!can_switch) {
			return state;
		}

		boolean need_switching = false;

		switch (state) {
		case NORMAL:
			if (conf.getReverse().contains(point)) {
				need_switching = true;
			}
			break;
		case REVERSE:
			if (conf.getNormal().contains(point)) {
				need_switching = true;
			}
			break;
		}

		// switch point
		if (need_switching) {
			return state.next();
		} else {
			return state;
		}
	}

	public boolean routeIsAvailable(Route route, TrainLine line) {

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
			}
		}

		return true;
	}

	public boolean debugRouteIsAvailable(Route route, TrainLine line) {
		// route is not being set
		if (route_setting.containsKey(route)) {
			System.out.println("\troute is being set\n");
			return false;
		}

		final ControlLogic logic = line.getControlLogic(route);

		// the minimal proceed rule
		final Rule base_rule = logic.getRule().get(0);

		// route does not contain any locked ambits
		for (final Ambit ambit : base_rule.getClear()) {
			if (!isAmbitAvailableFor(ambit, line)) {
				System.out.println("\tambit " + ambit.getLabel() + " is not available for line " + line.getSchemaLine().getLabel());
				if (occupation.containsKey(ambit)) {
					System.out.println("\t\tambit " + ambit.getLabel() + " is occupied by " + occupation.get(ambit));
				}
				if (ambit_lock.get(ambit) != null) {
					System.out.println("\t\tambit " + ambit.getLabel() + " is locked for "
							+ ambit_lock.get(ambit).getSchemaLine().getLabel() + "\n\t\t\t:" + ambit_lock.toString());
				}
				return false;
			}
		}

		// route does not contain any locked points
		for (final Point point : point_routes.keySet()) {
			if (point_routes.get(point).contains(route)) {
				if (pointIsLocked(point)) {
					System.out.println("\tpoint " + point.getNode().getLabel() + " is locked");
					return false;
				}
			}
		}

		return true;
	}

	public boolean isAmbitAvailableFor(Ambit ambit, TrainLine line) {
		return !occupation.containsKey(ambit) && (ambit_lock.get(ambit) == null || ambit_lock.get(ambit) == line);
	}

	public void refreshPosition(ITrain train) {
		// refresh ambit occupation and locking
		refreshOccupation(train);
		refreshLocking(train);
		// if the train has moved on the next route, refresh route setting
		refreshRouteSetting(train);

	}

	@Override
	public void removePosition(ITrain train) {
		removeOccupation(train, Collections.<Ambit>emptySet());
	}

	private void refreshRouteSetting(ITrain train) {
		final Segment head = train.getHeadSegment();
		final Route head_route = train.getLine().getSegmentRoute(head);
		if (routeIsSet(head_route)) {
			// assert route_setting.get(head_route) == train.getLine();
			// System.out.println("Unsetting route " + head_route + " for " + train);
			routeUnSet(head_route);
		}
	}

	protected void refreshOccupation(ITrain train) {
		final int tail = train.getTailSegmentIndex();
		final int head = train.getHeadSegmentIndex();

		// removeOccupation(train);

		final Set<Ambit> occupied = new HashSet<>();

		for (int i = tail; i <= head; i++) {
			final Segment s = train.getLine().getPath().get(i);
			final Ambit a = train.getLine().getSegmentAmbit(s);

			// assert i == tail || !occupation.containsKey(a) || occupation.get(a) == train;

			occupation.put(a, train);
			if (!occupation_time.containsKey(a)) {
				occupation_time.put(a, world.getTime());
			}

			occupied.add(a);
		}

		removeOccupation(train, occupied);
	}

	private void refreshLocking(ITrain train) {
		final Segment head = train.getHeadSegment();
		final Route head_route = train.getLine().getSegmentRoute(head);
		final Ambit head_ambit = train.getLine().getSegmentAmbit(head);

		final int head_ambit_index = head_route.getAmbits().indexOf(head_ambit);
		assert head_ambit_index != -1;

		for (int i = 0; i <= head_ambit_index; i++) {
			final Ambit to_unlock = head_route.getAmbits().get(i);

			if (ambit_lock.get(to_unlock) == train.getLine()) {
				ambitUnLock(to_unlock);
			}
		}

		// removeLocks(train);

		// System.out.println("locks:" + ambit_lock);

//		for(int i = head_ambit_index + 1; i < head_route.getAmbits().size(); i++) {
//			Ambit to_lock = head_route.getAmbits().get(i);
//			ambitLock(to_lock, train);
//		}
	}

	protected void removeOccupation(ITrain train, Set<Ambit> occupied) {
		final Set<Ambit> to_free = new HashSet<>();

		for (final Ambit a : occupation.keySet()) {
			if (occupation.get(a) == train && !occupied.contains(a)) {
				to_free.add(a);
			}
		}

		for (final Ambit a : to_free) {
			if (a instanceof Junction) {
				final Junction j = (Junction) a;

				for (final Point point : j.getPoints()) {
					// unlock point
					if (point_lock.containsKey(point)) {
						pointUnLock(train, point);
					}
				}
			}
			occupation.remove(a);
			occupation_time.remove(a);
		}
	}

	protected void ambitUnLock(Ambit to_unlock) {

		if (ambit_lock.containsKey(to_unlock)) {
			ambit_lock.remove(to_unlock);
			// System.out.println("Unlock " + to_unlock.getLabel());
		}
	}

//	private void ambitLock(Ambit to_lock, ITrain train) {
//		if (ambit_lock.get(to_lock) != train.getLine()) {
//			ambit_lock.put(to_lock, train.getLine());
//			if (world.getInstanceProgressionAtEnd() != null)
//				world.getInstanceProgressionAtEnd().add(new AmbitLockedInstanceEvent(to_lock));
//			historian.sees("AMBIT " + to_lock.getLabel() + " locked for " + train.getName());
//		}
//	}
//
//	private void removeLocks(ITrain train)
//	{
//		Set<Ambit> to_free = new HashSet<Ambit>();
//		for(Ambit a: ambit_lock.keySet()) {
//			if (ambit_lock.get(a) == train)
//				to_free.add(a);
//		}
//
//		for(Ambit a: to_free) {
//			ambitUnLock(a);
//		}
//	}

	private void populateAuxiliary() {
		// collect all the project points
		for (final Ambit ambit : root.getAmbits()) {
			if (ambit instanceof Junction) {
				final Junction junction = (Junction) ambit;
				for (final Point point : junction.getPoints()) {

					assert !point_routes.containsKey(point);
					point_routes.put(point, new ArrayList<Route>());

					// set all points to normal
					point_state.put(point, PointState.NORMAL);
				}
			}
		}

		for (final Route route : root.getRoutes()) {
			final PointConf pc = new PointConf(route);
			point_conf.put(route, pc);

			// set all routes to RED
			route_state.put(route, new RouteState(RouteState.RED));

			for (final Point p : pc.getNormal()) {
				if (!point_routes.get(p).contains(route)) {
					point_routes.get(p).add(route);
				}
			}
			for (final Point p : pc.getReverse()) {
				if (!point_routes.get(p).contains(route)) {
					point_routes.get(p).add(route);
				}
			}
		}
	}

	// public void SetStateStopwatch(Stopwatch sw){
	// stopwatch = sw;
	// }

	// public Stopwatch GetStateStopwatch(){
	// return stopwatch;
	// }

	@Override
	public Map<Ambit, ITrain> getOccupation() {
		return occupation;
	}

	@Override
	public double getOccupationTime(Ambit ambit) {
		if (occupation_time.containsKey(ambit)) {
			return occupation_time.get(ambit);
		} else {
			return 0d;
		}
	}

	@Override
	public double getWorldTime() {
		return world.getTime();
	}

	public Map<Ambit, TrainLine> getLocks() {
		return ambit_lock;
	}

	public IWorld getWorld() {
		return world;
	}

	@Override
	public PointState getPointState(Point point) {
		return point_state.get(point);
	}

	public Set<Point> getPoints() {
		return point_state.keySet();
	}

	public void printState() {
		System.out.println("Occupation:");
		for (final Ambit a : occupation.keySet()) {
			System.out.print(a.toString() + " -> " + occupation.get(a) + " for " + occupation_time.get(a) + "s ; ");
		}

		System.out.println("\nAmbit locking:");
		for (final Ambit a : ambit_lock.keySet()) {
			System.out.print(a.toString() + " -> " + ambit_lock.get(a).getSchemaLine().getLabel() + "; ");
		}

		System.out.println("\nRoute setting:");
		for (final Route r : route_setting.keySet()) {
			System.out.print(r.toString() + " -> " + route_setting.get(r) + "; ");
		}

		System.out.println("\nRoute state:");
		for (final Route r : route_state.keySet()) {
			System.out.print(r.toString() + " -> " + route_state.get(r) + "; ");
		}

		System.out.println("\n");
	}

	@Override
	public void refreshState(Set<ITrain> trains) {
		for (final ITrain t : trains) {
			refreshPosition(t);
		}

		for (final Route r : getWorld().getProject().getRoutes()) {
			refreshRouteState(r);
		}

	}

	@Override
	public boolean isCompleted() {
		throw new RuntimeException("Unimplemented method");
	}
}
