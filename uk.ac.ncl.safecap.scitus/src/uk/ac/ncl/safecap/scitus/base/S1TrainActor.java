package uk.ac.ncl.safecap.scitus.base;

import java.util.ArrayList;
import java.util.List;

import safecap.model.Point;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

public class S1TrainActor extends BaseTrainActor implements IActor {
	protected TrainController controller;
	protected TrainDriver driver;
	protected List<ITrainListener> listeners;

	private final List<ITrainReleaseCondition> releaseConditions;

	protected double max_speed; // maximum allowed current speed according to any speed limits in effect
	private double placement_time = 0.0;
	private Point lastPoint = null;

	public List<TrainEvent> mrt_list;
	public TrainEvent mrt;

	private Object restartSource;

	public double getMaximumSpeed() {
		return max_speed;
	}

	public void setMaximumSpeed(double sp) {
		assert sp > 0d;

		if (sp <= descr.getMaxSpeed()) {
			max_speed = sp;
		} else {
			max_speed = descr.getMaxSpeed();
		}
	}

	public S1TrainActor(TrainLine line, TrainDescriptor descr, TrainController controller, TrainDriver driver) {
		super(line, descr);
		this.controller = controller;
		this.driver = driver;
		max_speed = descr.getMaxSpeed();

		listeners = new ArrayList<>();

		releaseConditions = new ArrayList<>();

		driver.register(this);
		controller.register(this);
	}

	/**
	 * Returns the cause that made the train to re-start (e.g., a station), null if
	 * none
	 * 
	 * @return
	 */
	public Object getRestartSource() {
		return restartSource;
	}

	public void setRestartSource(Object restartSource) {
		this.restartSource = restartSource;
	}

	public void addReleaseCondition(ITrainReleaseCondition condition) {
		releaseConditions.add(condition);
	}

	public void addListener(ITrainListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ITrainListener listener) {
		listeners.remove(listener);
	}

	private void notifyListeners() {
		for (final ITrainListener listener : listeners) {
			listener.trainMoved(this);
		}
	}

	private boolean isReleaseClear() {
		List<ITrainReleaseCondition> removedConditions = null;

		boolean aggregateState = true;
		for (final ITrainReleaseCondition c : releaseConditions) {
			final boolean state = c.isConditionCleared(this);
			if (state) {
				if (removedConditions == null) {
					removedConditions = new ArrayList<>();
				}
				removedConditions.add(c);
			}
			aggregateState &= state;
		}

		if (removedConditions != null) {
			releaseConditions.removeAll(removedConditions);
		}

		return aggregateState;
	}

	@Override
	public double eventHorizon() throws SimulationException {
		if (isCompleted()) {
			controller.unregister(this);
			return Double.NaN;
		}

		// if release is not cleared cannot go
		if (!isReleaseClear()) {
			return Double.NEGATIVE_INFINITY;
		}

		final double[] ma = controller.movementAuthority(this);
		final double distance = ma[0];
		final double target_speed = ma[1];
		final double t = driver.travelTime(target_speed, distance);

		return t;
	}

	@Override
	public Progression progress(double horizon) throws SimulationException {
		if (!isCompleted()) {
			return driver.drive(horizon);
		}

		return null;
	}

	public TrainController getController() {
		return controller;
	}

	public TrainDriver getDriver() {
		return driver;
	}

	public void setSpeed(double speed) {
		assert speed <= max_speed;
		this.speed = speed;
	}

	@Override
	public void move(double distance) {
		if (distance != 0d) {
			super.move(distance);
			notifyListeners();
		}
	}

	@Override
	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public String getName() {
		return descr.toString() + "@" + line.getSchemaLine().getLabel();
	}

	public Point getLastPoint() {
		return lastPoint;
	}

	public void setLastPoint(Point lastPoint) {
		this.lastPoint = lastPoint;
	}

	public double getPlacementTime() {
		return placement_time;
	}

	public void setPlacementTime(double placement_time) {
		this.placement_time = placement_time;
	}

	@Override
	public String toString() {
		return getName() + " " + Delta.round2(getTail()) + "m(" + tail_segment.getLabel() + ")/" + Delta.round2(getHead()) + "m("
				+ head_segment.getLabel() + "):" + Delta.round2(speed) + "/" + Delta.round2(acceleration);
	}

	@Override
	public double getTimeExtent() {
		throw new RuntimeException("Unimplemented");
	}
}
