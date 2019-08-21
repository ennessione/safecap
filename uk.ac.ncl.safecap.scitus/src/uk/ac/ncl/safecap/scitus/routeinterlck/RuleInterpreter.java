package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.io.Serializable;

import org.eclipse.emf.common.util.EList;

import safecap.model.Ambit;
import safecap.model.ControlLogic;
import safecap.model.Point;
import safecap.model.Route;
import safecap.model.RouteStateRule;
import safecap.model.Rule;
import safecap.model.TimedOccupationRule;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.AlarmEvent;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.common.PointState;

public class RuleInterpreter implements Serializable {
	private static final long serialVersionUID = -3228637729185779045L;
	private final IRBSystemState state;

	public RuleInterpreter(IRBSystemState state) {
		this.state = state;
	}

	public boolean evaluate(Rule rule, int aspect, TrainLine line, Route route, ControlLogic logic) {
		if (rule != null) {
			final boolean core = evaluate(rule);
			if (!core) {
				return false;
			}
		}

		if (aspect == 0) {
			return true;
		}

		if (aspect < 0 || aspect > logic.getAspects()) {
			return false;
		}

		Route current = line.getNextRoute(route);
		while (aspect > 0 && current != null) {
			if (state.getRouteState(current).getAspect() < aspect - 1) {
				return false;
			}
			current = line.getNextRoute(current);
			aspect--;
		}

		return true;
	}

	public boolean evaluate(Rule rule) {
		if (rule == null) {
			return true;
		}

		final boolean clear = evaluateClear(rule.getClear());
		if (!clear) {
			return false;
		}

		final boolean timedoccupied = evaluateTimedOccupied(rule.getOccupied());
		if (!timedoccupied) {
			return false;
		}

		final boolean normal = evaluateNormal(rule.getNormal());
		if (!normal) {
			return false;
		}

		final boolean reverse = evaluateReverse(rule.getReverse());
		if (!reverse) {
			return false;
		}

		final boolean route_state = evaluateRouteState(rule.getRouteState());
		if (!route_state) {
			return false;
		}

		return true;
	}

	public boolean debug(Rule rule) {
		if (rule == null) {
			System.out.println("\tinvalid rule");
			return false;
		}

		final boolean clear = debugClear(rule.getClear());
		if (!clear) {
			return false;
		}

		final boolean timedoccupied = debugTimedOccupied(rule.getOccupied());
		if (!timedoccupied) {
			return false;
		}

		final boolean normal = debugNormal(rule.getNormal());
		if (!normal) {
			return false;
		}

		final boolean reverse = debugReverse(rule.getReverse());
		if (!reverse) {
			return false;
		}

		final boolean route_state = debugRouteState(rule.getRouteState());
		if (!route_state) {
			return false;
		}

		return true;
	}

	/**
	 * checks the signal reference satisfaction
	 * 
	 * @param routeState
	 * @return
	 */
	private boolean evaluateRouteState(EList<RouteStateRule> routeState) {
		for (final RouteStateRule rs : routeState) {
			final int rstate = state.getRouteState(rs.getRoute()).getAspect();

			// the required state is same or higher
			if (rs.getState() > rstate) {
				return false;
			}
		}
		return true;
	}

	private boolean debugRouteState(EList<RouteStateRule> routeState) {
		for (final RouteStateRule rs : routeState) {
			final int rstate = state.getRouteState(rs.getRoute()).getAspect();

			// the required state is same or higher
			if (rs.getState() > rstate) {
				System.out.println("\tviolated route state rule " + rs.getRoute().getLabel() + ":" + rs.getState());
				return false;
			}
		}
		return true;
	}

	private boolean evaluateReverse(EList<Point> reverse) {
		for (final Point point : reverse) {
			if (state.getPointState(point) != PointState.REVERSE) {
				return false;
			}
		}
		return true;
	}

	private boolean debugReverse(EList<Point> reverse) {
		for (final Point point : reverse) {
			if (state.getPointState(point) != PointState.REVERSE) {
				System.out.println("\tviolated point reverse rule for point " + point.getNode().getLabel());
				return false;
			}
		}
		return true;
	}

	private boolean evaluateNormal(EList<Point> normal) {
		for (final Point point : normal) {
			if (state.getPointState(point) != PointState.NORMAL) {
				return false;
			}
		}
		return true;
	}

	private boolean debugNormal(EList<Point> normal) {
		for (final Point point : normal) {
			if (state.getPointState(point) != PointState.NORMAL) {
				System.out.println("\tviolated point normal rule for point " + point.getNode().getLabel());
				return false;
			}
		}
		return true;
	}

	private boolean evaluateTimedOccupied(EList<TimedOccupationRule> timedOccupied) {

		boolean time_unsatisfied = false;
		double min_gap = Double.POSITIVE_INFINITY;

		for (final TimedOccupationRule rule : timedOccupied) {
			final Ambit ambit = rule.getAmbit();

			final boolean occupation_status = state.getOccupation().keySet().contains(ambit);
			if (!occupation_status) {
				return false;
			}

			if (rule.getTime() >= 0) {
				final double ot = state.getWorldTime() - state.getOccupationTime(ambit);
				final boolean result = rule.getTime() <= ot;
				if (!result) {
					time_unsatisfied = true;
					final double gap = rule.getTime() - ot;
					assert gap > 0;
					if (gap < min_gap) {
						min_gap = gap;
					}
				}
			}
		}

		if (time_unsatisfied && state instanceof SystemState) {
			assert !Double.isInfinite(min_gap);
			assert min_gap > 0;

			final SystemState sstate = (SystemState) state;
			final World sworld = (World) sstate.getWorld();

			final AlarmEvent alarm = new AlarmEvent(sworld, sworld.getTime() + min_gap);
			sworld.addEvent(alarm);

			return false;
		}

		return true;
	}

	private boolean debugTimedOccupied(EList<TimedOccupationRule> timedOccupied) {

		boolean time_unsatisfied = false;
		double min_gap = Double.POSITIVE_INFINITY;

		for (final TimedOccupationRule rule : timedOccupied) {
			final Ambit ambit = rule.getAmbit();

			final boolean occupation_status = state.getOccupation().keySet().contains(ambit);
			if (!occupation_status) {
				System.out.println("\tviolated timed occupation: ambit " + ambit.getLabel() + " is not occupied");
				return false;
			}

			if (rule.getTime() >= 0) {
				final double ot = state.getWorldTime() - state.getOccupationTime(ambit);
				final boolean result = rule.getTime() <= ot;
				if (!result) {
					time_unsatisfied = true;
					final double gap = rule.getTime() - ot;
					assert gap > 0;
					if (gap < min_gap) {
						min_gap = gap;
					}
				}
			}
		}

		if (time_unsatisfied && state instanceof SystemState) {
			assert !Double.isInfinite(min_gap);
			assert min_gap > 0;

			final SystemState sstate = (SystemState) state;
			final World sworld = (World) sstate.getWorld();

			final AlarmEvent alarm = new AlarmEvent(sworld, sworld.getTime() + min_gap);
			sworld.addEvent(alarm);

			System.out.println("\tviolated timed occupation: time constraint");
			return false;
		}

		return true;
	}

	private boolean evaluateClear(EList<Ambit> clear) {
		for (final Ambit ambit : clear) {
			if (state.getOccupation().containsKey(ambit)) {
				return false;
			}
		}

		return true;
	}

	private boolean debugClear(EList<Ambit> clear) {
		for (final Ambit ambit : clear) {
			if (state.getOccupation().containsKey(ambit)) {
				System.out.println("\tviolated ambit ckear for " + ambit.getLabel());
				return false;
			}
		}

		return true;
	}

}
