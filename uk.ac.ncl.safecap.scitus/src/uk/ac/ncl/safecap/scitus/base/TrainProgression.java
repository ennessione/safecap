package uk.ac.ncl.safecap.scitus.base;

import java.util.List;

import uk.ac.ncl.safecap.misc.core.Delta;

public class TrainProgression extends Progression {
	private double head_position;
	protected double distance;
	protected double acceleration;
	protected double start_speed;
	protected double end_speed;
	protected double max_speed;
	protected double movement_authority = Double.POSITIVE_INFINITY; // optional
	protected boolean completed = false; // optional
	protected TrainEvent mrt;
	protected List<TrainEvent> mrt_list;

	public TrainProgression(S1TrainActor actor, double distance, double acceleration, double start_speed, double end_speed) {
		super(actor);
		this.distance = distance;
		this.acceleration = acceleration;
		this.start_speed = start_speed;
		this.end_speed = end_speed;
		mrt = actor.mrt;
		mrt_list = actor.mrt_list;
		max_speed = actor.getMaximumSpeed();
		mrt_list.remove(mrt);
	}

	/**
	 * Returns the maximum safe speed during the progression
	 * 
	 * @return speed, in meters per second
	 */
	public double getMaxSpeed() {
		return max_speed;
	}

	/**
	 * Returns the Most Restrictive Target used by a train to determine movement
	 * authority and speed limit
	 * 
	 * @return MRT object
	 */
	public TrainEvent getMRT() {
		return mrt;
	}

	/**
	 * Returns the other targets such a less restrictive than the MRT
	 * 
	 * @return MRT candidate object
	 */
	public List<TrainEvent> getOtherTargets() {
		return mrt_list;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public double getMovementAuthority() {
		return movement_authority;
	}

	public void setMovementAuthority(double movement_authority) {
		this.movement_authority = movement_authority;
	}

	public double getDistance() {
		return distance;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public double getStartSpeed() {
		return start_speed;
	}

	public double getEndSpeed() {
		return end_speed;
	}

	public S1TrainActor getTrain() {
		return (S1TrainActor) actor;
	}

	@Override
	public String toString() {
		return "TRAIN " + ((S1TrainActor) actor).getName() + ": dist=" + Delta.round2(getDistance()) + "; speed="
				+ Delta.round2(getStartSpeed()) + "/" + Delta.round2(getEndSpeed()) + "; vel:" + Delta.round2(getAcceleration());
	}

	public double getHeadPosition() {
		return head_position;
	}

	public void setHeadPosition(double head_position) {
		this.head_position = head_position;
	}

}
