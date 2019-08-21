package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.io.Serializable;

import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.TrainDriver;
import uk.ac.ncl.safecap.scitus.base.TrainProgression;
import uk.ac.ncl.safecap.scitus.common.ImminentCollisionException;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

public class OptimalDriver extends TrainDriver implements Serializable {
	private static final long serialVersionUID = -8787213548410033058L;
	private TrainDescriptor tr;
	private double ma = 0;

	public OptimalDriver() {

	}

	@Override
	public void register(S1TrainActor train) {
		super.register(train);
		tr = train.getDescriptor();
	}

	@Override
	public double immediateAcceleration(double targetSpeed, double distance) {
		final double u = train.getSpeed();
		double d = 0d;
		assert targetSpeed <= train.getMaximumSpeed();

		if (Delta.isLess(targetSpeed, u)) {
			d = tr.getSafeBrakingDistance(u, targetSpeed);
		}

		if (Delta.Eql(d, distance)) { // decelerate
			return -tr.getMaxDeceleration();
		} else if (Delta.isLess(d, distance)) {
			if (Delta.isLess(u, train.getMaximumSpeed())) { // accelerate
				final double v = tr.optimalTargetSpeed(distance, u, targetSpeed);
				if (!Delta.Eql(u, v)) {
					return tr.getMaxAcceleration();
				} else {
					// steady travel
					return 0d;
				}
			} else { // steady
				return 0;
			}
		} else if (Delta.isLess(distance, d)) {
			return -tr.getMaxDeceleration();
		}

		return 0;
	}

	@Override
	public double travelTime(double targetSpeed, double distance) throws SimulationException {

		assert distance >= 0d;
		assert targetSpeed <= train.getMaximumSpeed();

		ma = distance;
		final double u = train.getSpeed();
		double d = 0d;

		// System.out.println("Currently looking at train: " +train +"\n with speed: "
		// +u +"\n with targetSpeed: " +targetSpeed );

		if (Delta.isZero(distance)) {
			if (!Delta.isZero(u) && !Delta.isLess(u, targetSpeed)) {
				throw new ImminentCollisionException(
						"train " + train.toString() + " given MA(" + distance + "," + targetSpeed + ") in violation of minimum BD=" + d);
			}
			return 0d;
		}

		if (Delta.isLess(targetSpeed, u)) {
			d = tr.getSafeBrakingDistance(u, targetSpeed);
		}

		if (Delta.Eql(d, distance)) { // decelerate
			train.setAcceleration(-tr.getMaxDeceleration());
			final double t = tr.bestDecelerationTime(u, targetSpeed);
			assert !Double.isNaN(t) || !Double.isInfinite(t);
			assert t != 0;
			return t;
		} else if (Delta.isLess(d, distance)) {
			if (Delta.isLess(u, train.getMaximumSpeed())) { // accelerate
				train.setAcceleration(tr.getMaxAcceleration());
				double v = tr.optimalTargetSpeed(distance, u, targetSpeed);
				v = Math.min(v, train.getMaximumSpeed());
				assert v <= train.getMaximumSpeed();
				if (!Delta.Eql(u, v)) {
					final double t = tr.bestAccelerationTime(u, v);
					assert !Double.isNaN(t) || !Double.isInfinite(t);
					assert t != 0;
					return t;
				} else {
					// steady travel
					train.setAcceleration(0);
					final double t = tr.steadyTravelTime(distance, u, targetSpeed);
					assert !Double.isNaN(t) || !Double.isInfinite(t);
					assert t != 0;
					return t;
				}
			} else { // steady
				train.setAcceleration(0);
				final double t = tr.steadyTravelTime(distance, u, targetSpeed);
				assert !Double.isNaN(t) || !Double.isInfinite(t);
				assert t != 0;
				return t;
			}
		} else if (Delta.isLess(distance, d)) {
			throw new ImminentCollisionException(
					"train " + train.toString() + " given MA(" + distance + "," + targetSpeed + ") in violation of minimum BD=" + d);
		}
		return 0;
	}

	@Override
	public Progression drive(double time) {
		final double a = train.getAcceleration();
		final double v = train.getSpeed() + a * time;
		final double s = 0.5d * (train.getSpeed() + v) * time;

		assert v <= train.getMaximumSpeed();

		final TrainProgression pg = new TrainProgression(train, s, a, train.getSpeed(), v);
		pg.setHeadPosition(train.getHead());

		assert Delta.isLess(s, ma) || Delta.Eql(s, ma);

		pg.setMovementAuthority(ma);
		train.setSpeed(v);
		train.move(s);
		pg.setCompleted(train.isCompleted());
		return pg;
		// train.setAccelration(0d);
	}

}
