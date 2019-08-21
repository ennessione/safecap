package uk.ac.ncl.safecap.sim3.core;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.scitus.base.ITrain;

public abstract class BaseSafetyProfile implements ISafetyProfile {
	private final Map<ITrain, Attractor> attractors;

	protected BaseSafetyProfile() {
		attractors = new HashMap<>();
	}

	@Override
	public double getSafeSpeedAt(ITrain train, double position, int kind, boolean probe) {
		if ((kind & getKind()) != getKind()) {
			return Double.MAX_VALUE;
		}

		final Speedpoint sp = getSpeedpoint(train, position, kind);
		if (sp == null) {
			attractors.remove(train);
			return Double.MAX_VALUE;
		}

		if (probe) {
			if (position < sp.position) {
				return Double.MAX_VALUE;
			} else {
				return sp.speed;
			}
		}

		if (train.getHead() >= sp.position) {
			// if (attractors.containsKey(train) && train.getHead() - sp.position > 0)
			// System.out.println("### overshoot by " + (train.getHead() - sp.position) + "
			// with extra speed " + (train.getSpeed() - sp.speed));
			attractors.remove(train);
			return sp.speed;
		}

		Attractor attractor = attractors.get(train);

		if (position < sp.position && attractor == null) {
			// attractors.remove(train);
			return Double.MAX_VALUE;
		}

		if (attractor != null) { // drive to attractor
			// if attractor is zero-locked, just return zero now
			if (attractor.isZeroLock()) {
				if (attractor.getZeroLockSpeed() != sp.speed) {
					attractors.remove(train);
				} else if (train.getSpeed() > 0) {
					return 0;
				}
			}

			// train head behind speed point, correct position to attractor
			if (position < sp.position) {
				position = sp.position;
			}
		} else {
			attractor = new Attractor(position);
			attractors.put(train, attractor);
			// System.out.println("### locked at " + position + " with speed " +
			// train.getSpeed() + " and distance " + (sp.position - train.getHead()));
		}

		// speed correction
		if (Math.abs(sp.position - train.getHead()) > 1 && train.getSpeed() > sp.speed) {
			final double s1 = sp.position - train.getHead();
			final double s2 = position - sp.position;
			final double u = train.getSpeed();
			final double v = sp.speed;
			assert s2 >= 0 && s1 > 0;
			double x = u > v ? Math.sqrt(v * v + (v * v - u * u) * (s2 / s1)) : v;
			if (Double.isNaN(x) || Delta.isZero(x)) {
				x = 0;
				attractor.setZeroLock(sp.speed);
			}
			// System.out.println("### speed correction from " + sp.speed + " to "
			// + x + " over distances " + s1 + " and " + s2);
			return x;
		} else {
			return sp.speed;
		}
	}

	public abstract Speedpoint getSpeedpoint(ITrain train, double position, int kind);

	public abstract int getKind();

	public class Speedpoint {
		private final double position;
		private final double speed;

		public Speedpoint(double position, double speed) {
			this.position = position;
			this.speed = speed;
		}

		public double getPosition() {
			return position;
		}

		public double getSpeed() {
			return speed;
		}
	}
}
