package uk.ac.ncl.safecap.scitus.base;

import org.eclipse.emf.ecore.EObject;

import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.SpeedBound;

public class TrainEvent {
	private EObject cause;
	private final double distance;
	private final double speed_at_end;
	private String distance_oracle;
	private String speed_oracle;
	private boolean fromhead;
	private SpeedBound speedbound;

	public static TrainEvent IMMEDIATE_STOP = new TrainEvent(0, SpeedBound.FULL_STOP);
	public static TrainEvent UNLIMITED = new TrainEvent(Double.POSITIVE_INFINITY, SpeedBound.UNBOUNDED);

	public TrainEvent(double distance, SpeedBound speed_at_end) {
		this.distance = distance;
		this.speed_at_end = speed_at_end.getBound();
		speed_oracle = speed_at_end.getReason();
		fromhead = true;
		speedbound = speed_at_end;
	}

	public TrainEvent(double distance, double speed_at_end, String speed_oracle) {
		this.distance = distance;
		this.speed_at_end = speed_at_end;
		this.speed_oracle = speed_oracle;
		fromhead = true;
	}

	/**
	 * Indicates whether the movemnt authority applies to head or tail of a train
	 * 
	 * @return true if the event is for a train head, false otherwise
	 */
	public boolean isForHead() {
		return fromhead;
	}

	public void setForHead(boolean fromhead) {
		this.fromhead = fromhead;
	}

	/**
	 * Returns the description of equipment or track element that has determined the
	 * movement authority extent
	 * 
	 * @return free-form string
	 */
	public String getDistanceOracle() {
		return distance_oracle;
	}

	public void setDistanceOracle(String oracle) {
		distance_oracle = oracle;
	}

	/**
	 * Returns the description of equipment or track element that has determined the
	 * speed at target
	 * 
	 * @return free-form string
	 */
	public String getSpeedOracle() {
		return speed_oracle;
	}

	public void setSpeedOracle(String speed_oracle) {
		this.speed_oracle = speed_oracle;
	}

	/**
	 * Returns the DSL object that is most responsible for determining the movement
	 * authority
	 * 
	 * @return a valid Safecap DSL object or null
	 */
	public EObject getCause() {
		return cause;
	}

	public void setCause(EObject cause) {
		this.cause = cause;
	}

	/**
	 * Movement authority extent, in meters
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Movement authority speed at target, in m/s
	 */
	public double getSpeedAtEnd() {
		return speed_at_end;
	}

	public double getDecelerationMetric(S1TrainActor actor) {
		return actor.getDriver().immediateAcceleration(speed_at_end, distance);
	}

	public double getLinearDeceleration(S1TrainActor actor) {
		final double u = actor.getSpeed();
		final double v = getSpeedAtEnd();
		final double S = getDistance();

		return (v * v - u * u) / (2.0d * S);
	}

	public boolean isLess(S1TrainActor actor, TrainEvent event) {
		if (this == TrainEvent.UNLIMITED) {
			return false;
		} else if (this == TrainEvent.IMMEDIATE_STOP) {
			return true;
		} else if (event == TrainEvent.UNLIMITED) {
			return true;
		} else if (event == TrainEvent.IMMEDIATE_STOP) {
			return false;
		} else {

			final double a1 = getDecelerationMetric(actor);
			final double a2 = event.getDecelerationMetric(actor);
//			double b1 = getLinearDeceleration(actor);
//			double b2 = event.getLinearDeceleration(actor);

			if (a1 < a2) {
				return true;
			} else if (distance < event.distance) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public String toString() {
		if (this == IMMEDIATE_STOP) {
			return "[STOP]";
		} else if (this == UNLIMITED) {
			return "[UNLIMITED]";
		}

		final StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(Delta.round2(distance) + "m");
		sb.append(", ");
		sb.append(Delta.round2(speed_at_end) + "m/s");
		if (distance_oracle != null) {
			sb.append(" by ");
			sb.append(distance_oracle);
		}
		if (cause != null) {
			sb.append(" due ");
			sb.append(cause.toString() + "(" + cause.getClass().getSimpleName() + ")");
			if (speed_oracle != null) {
				sb.append(" determined by ");
				sb.append(speed_oracle);
			}
		}
		sb.append("]");

		return sb.toString();
	}
}
