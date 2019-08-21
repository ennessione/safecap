package uk.ac.ncl.safecap.capacity.experiment;

import java.util.List;

import safecap.model.ControlLogic;
import safecap.model.Route;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.InitialRouteClaimInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.InstanceEvent;
import uk.ac.ncl.safecap.scitus.base.InstanceProgression;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.RouteProgression;
import uk.ac.ncl.safecap.scitus.base.RouteUnsetInstanceEvent;
import uk.ac.ncl.safecap.scitus.base.World;

public class SignalRun {
	private static final double DT = 0.2;

	private final Signal _signal;
	private final World _world;
	private final SimulationExperiment _exp;
	private final List<Route> _routes;

	private final int[] _state;
	private final int[] _maxAspects;

	public SignalRun(Signal signal, World world, SimulationExperiment exp) {
		_world = world;
		_signal = signal;
		_exp = exp;
		final List<Route> routes = SignalUtil.getRoutesProtectedBy(signal);
		_routes = routes;

		final int count = (int) (exp.getTotalTime() / DT);
		_state = new int[count + 1];
		_maxAspects = new int[count + 1];

		int curState = 0;
		int curIndex = 0;
		int curMaxAspects = 2;
		double curTime = 0;
		for (final Evolution ev : exp.getHistory()) {
			int state = 0, aspects = 2;
			boolean changeState = false;

			for (final Progression pr : ev.getProgressions()) {
				// if (pr instanceof RouteSettingProgression)
				// {
				// RouteSettingProgression pr1 = (RouteSettingProgression) pr;
				// for (Route route: pr1.getRoutes())
				// {
				// if (routes.contains(route))
				// {
				// aspects =
				// break;
				// }
				// }
				// }

				if (pr instanceof RouteProgression) {
					final RouteProgression routeProgression = (RouteProgression) pr;
					if (routes.contains(routeProgression.getRoute())) {
						final ControlLogic logic = routeProgression.getLine().getControlLogic(routeProgression.getRoute());
						// RouteActor actor = (RouteActor)
						// routeProgression.getActor();
						// =
						// actor.getLine().getControlLogic(routeProgression.getRoute());
						// System.out.println("Signal " + _signal.getLabel() + "
						// Route " + routeProgression.getRoute().getLabel() + "
						// change state " + curState + " to " +
						// routeProgression.getNewState() + " aspects " +
						// logic.getAspects() + " at " + (curTime +
						// ev.getExtent()));

						if (routeProgression.getNewState() > state) {
							state = routeProgression.getNewState();
							aspects = logic.getAspects();
						}
						changeState = true;
					}
				}

				if (pr instanceof InstanceProgression) {
					final InstanceProgression pr1 = (InstanceProgression) pr;
					for (final InstanceEvent event : pr1.getInstances()) {
						if (event instanceof InitialRouteClaimInstanceEvent) {
							final InitialRouteClaimInstanceEvent init = (InitialRouteClaimInstanceEvent) event;
							if (routes.contains(init.getRoute())) {
								if (state < 0) {
									state = 0;
									aspects = 2;
								}
								changeState = true;
							}
						}

						if (event instanceof RouteUnsetInstanceEvent) {
							final RouteUnsetInstanceEvent ev1 = (RouteUnsetInstanceEvent) event;
							if (routes.contains(ev1.getRoute())) {
								// System.out.println("*Signal " +
								// _signal.getLabel() + " Route " +
								// ev1.getRoute().getLabel() + " change state "
								// + curState + " to " + 0 + " at " + (curTime +
								// ev.getExtent()));
								if (state < 0) {
									state = 0;
									aspects = 2;
								}
								changeState = true;
							}
						}
					}
				}
			}

			if (changeState) {
				fillState(curState, curMaxAspects, curIndex, curTime);
				curState = state;
				curMaxAspects = aspects;
				curIndex = (int) (curTime / DT);
			}

			curTime += ev.getExtent();
		}
		fillState(curState, curMaxAspects, curIndex, exp.getTotalTime());
	}

	public List<Route> getRoutes() {
		return _routes;
	}

	private void fillState(int state, int aspects, int startIndex, double time) {
		int curIndex = startIndex;
		double curTime = startIndex * DT;
		while (curTime < time) {
			_state[curIndex] = state;
			_maxAspects[curIndex] = aspects;
			curIndex++;
			curTime = curIndex * DT;
		}
	}

	public int getMaxAspects(double time) {
		int index = (int) Math.round(time / DT);
		if (index < 0) {
			index = 0;
		}
		if (index >= _state.length) {
			index = _state.length - 1;
		}
		return _maxAspects[index];
	}

	public int getState(double time) {
		int index = (int) Math.round(time / DT);
		if (index < 0) {
			index = 0;
		}
		if (index >= _state.length) {
			index = _state.length - 1;
		}
		return _state[index];
	}
}
