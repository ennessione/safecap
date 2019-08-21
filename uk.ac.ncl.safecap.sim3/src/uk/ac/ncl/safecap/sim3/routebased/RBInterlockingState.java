package uk.ac.ncl.safecap.sim3.routebased;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import safecap.model.Point;
import safecap.model.Route;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.common.PointState;
import uk.ac.ncl.safecap.scitus.routeinterlck.BaseSystemState;
import uk.ac.ncl.safecap.sim3.core.IGlobalState;
import uk.ac.ncl.safecap.sim3.core.IS3Train;
import uk.ac.ncl.safecap.sim3.core.S3World;

public class RBInterlockingState extends BaseSystemState implements IGlobalState {

	private final Map<Route, List<ITrain>> routelockQueue;
	private final Collection<S3DelayedInsertion> pendingTrains;
	private final IRouteSettingLogic routeSetter;

	public RBInterlockingState(IWorld world, IRouteSettingLogic routeSetter) {
		super(world);
		routelockQueue = new HashMap<>();
		pendingTrains = new HashSet<>();
		this.routeSetter = routeSetter;
	}

	public void addPendingTrain(ITrain train, double start) {
		pendingTrains.add(new S3DelayedInsertion(train, start));
	}

	public void tryToLock(Route route, ITrain train) {
		List<ITrain> queue = routelockQueue.get(route);
		if (queue == null) {
			queue = new ArrayList<>();
			routelockQueue.put(route, queue);
		}

		if (!queue.contains(train)) {
			queue.add(train);
			System.out.println("Train " + train + " attempts to lock route " + route.getLabel() + " at " + getWorld().getTime());
		}
	}

	private void movePoints() {
		for (final Point point : getPoints()) {
			if (pointIsLocked(point)) {
				final PointState current = getPointState(point);
				final PointState actual = computePointState(point);
				if (current != actual) {
					pointMove(point);
				}
			}
		}
	}

	private void processPendingTrains() {
		final Set<S3DelayedInsertion> toRemove = new HashSet<>();
		for (final S3DelayedInsertion dt : pendingTrains) {
			final Route route = dt.getTrain().getLine().getSchemaLine().getRoutes().get(0);
			if (!dt.isRouteWaitingStage() && dt.getTime() <= getWorld().getTime()) {
				tryToLock(route, dt.getTrain());
				dt.toRouteWaitingStage();
				System.out.println(
						"Train " + dt.getTrain().toString() + " to route wating at " + getWorld().getTime() + "; start = " + dt.getTime());
			} else if (dt.isRouteWaitingStage() && routeIsSetFor(route, dt.getTrain().getLine())) {
				final S3World world = (S3World) getWorld();
				world.addTrain((IS3Train) dt.getTrain());
				System.out.println("Train " + dt.getTrain().toString() + " added at " + getWorld().getTime());
				toRemove.add(dt);
			}
		}

		pendingTrains.removeAll(toRemove);
	}

	private void processRouteLocking() {
		final Set<ITrain> affectedTrains = new HashSet<>();
		for (final Route route : routelockQueue.keySet()) {
			final List<ITrain> queue = routelockQueue.get(route);
			if (!queue.isEmpty()) {
				final ITrain head = queue.get(0);
				if (routeIsAvailable(route, head.getLine())) {
					routeSet(route, head);
					System.out.println("Train " + head + " set route " + route.getLabel());
					queue.remove(0);
					affectedTrains.add(head);
				}
			}
		}
	}

	private void processRouteSetting() {
		for (final ITrain train : getWorld().getTrains()) {
			for (final Route r : routeSetter.setRoutes(this, train)) {
				tryToLock(r, train);
			}
		}
	}

	private void removedCompletedTrains() {
		final S3World world = (S3World) getWorld();
		for (final ITrain train : getWorld().getTrains()) {
			if (train.isCompleted()) {
				world.removeTrain(null);
				super.removePosition(train);
			}
		}
	}

	@Override
	public void refreshGlobalState(Set<ITrain> movedTrains) {
		removedCompletedTrains();
		refreshState(movedTrains);
		processPendingTrains();
		processRouteLocking();
		processRouteSetting();
		movePoints();
	}

	@Override
	public boolean isCompleted() {
		return pendingTrains.isEmpty();
	}

	public void debugRouteLockQueue() {
		for (final Route route : routelockQueue.keySet()) {
			if (!routelockQueue.get(route).isEmpty()) {
				System.out.print(route.getLabel());
				System.out.print(" : ");
				System.out.println(routelockQueue.get(route));
			}
		}
	}

}

class S3DelayedInsertion {
	private final ITrain train;
	private final double time;
	private boolean routeWaiting;

	public S3DelayedInsertion(ITrain train, double time) {
		super();
		this.train = train;
		this.time = time;
		routeWaiting = false;
	}

	public void toRouteWaitingStage() {
		routeWaiting = true;
	}

	public ITrain getTrain() {
		return train;
	}

	public double getTime() {
		return time;
	}

	public boolean isRouteWaitingStage() {
		return routeWaiting;
	}

	@Override
	public String toString() {
		return "[train=" + train + ", time=" + time + ", routeWaiting=" + routeWaiting + "]";
	}

}
