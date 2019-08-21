package uk.ac.ncl.safecap.scitus.types;

import java.util.SortedMap;

import uk.ac.ncl.safecap.misc.core.Delta;

public class UDynamics {

	public static double g = 9.780327;

	// drag force Fd = drag_A + drag_B * v + drag_C * v^2
	private final double drag_A;
	private final double drag_B;
	private final double drag_C;

	private final double mass;
	private final double length;

	private final double braking; // dimension-less constant

	private double[] tractive_effort;
	private double[] max_speed_gradient;

	public UDynamics(double drag_A, double drag_B, double drag_C, double mass, double length, double braking,
			SortedMap<Double, Double> table) {
		super();
		this.drag_A = drag_A;
		this.drag_B = drag_B;
		this.drag_C = drag_C;
		this.mass = mass;
		this.length = length;
		this.braking = braking;
		buildTractiveEffort(table);
		buildMaxSpeedGradient();
	}

	private void buildTractiveEffort(SortedMap<Double, Double> table) {
		int index_start = 0;
		tractive_effort = new double[500];
		for (final double v : table.keySet()) {
			final int index_end = (int) v;
			for (int j = index_start; j <= index_end; j++) {
				if (j < tractive_effort.length) {
					tractive_effort[j] = table.get(v);
				}
			}
			index_start = index_end + 1;
		}
	}

	private void buildMaxSpeedGradient() {
		max_speed_gradient = new double[tractive_effort.length];
		for (int i = 0; i < tractive_effort.length; i++) {
			max_speed_gradient[i] = (tractive_effort[i] - drag(i)) / (mass * g);
		}
	}

	public double getMaxSpeed(double gradient) {
		for (int i = 0; i < max_speed_gradient.length; i++) {
			if (max_speed_gradient[i] >= gradient) {
				final double a = drag_A + mass * g * gradient - tractive_effort[i];
				final double b = drag_B;
				final double c = drag_C;

				final double x1 = -(drag_B + Math.sqrt(b * b - 4 * a * c)) / (2d * c);
				final double x2 = (-drag_B + Math.sqrt(b * b - 4 * a * c)) / (2d * c);

				if (x1 < 0) {
					return x2;
				} else {
					return x1;
				}
			}
		}

		return 0d;
	}

	public double getMinSpeed(double gradient) {
		for (int i = 0; i < max_speed_gradient.length; i++) {
			if (max_speed_gradient[i] >= gradient) {
				final double a = drag_A + mass * g * (gradient - braking);
				final double b = drag_B;
				final double c = drag_C;

				final double x1 = -(drag_B + Math.sqrt(b * b - 4 * a * c)) / (2d * c);
				final double x2 = (-drag_B + Math.sqrt(b * b - 4 * a * c)) / (2d * c);

				if (x1 < 0) {
					return x2;
				} else {
					return x1;
				}
			}
		}

		return 0d;
	}

	public double getTractiveEffortForSpeed(int speed) {
		if (speed >= tractive_effort.length) {
			return 0d;
		}
		return tractive_effort[speed];
	}

	public double getDragA() {
		return drag_A;
	}

	public double getDragB() {
		return drag_B;
	}

	public double getDragC() {
		return drag_C;
	}

	public double getMass() {
		return mass;
	}

	public double getLength() {
		return length;
	}

	public double getBrakeForce() {
		return braking;
	}

	/**
	 * An iterative computation of the safe entry speed
	 * 
	 * @param speed    the speed at an intermediate target
	 * @param distance distance between speed limit "speed" and the intermediate
	 *                 target
	 * @param gradient the applicable gradient between the targets
	 * @return the maximum safe speed of approaching the intermediate target
	 */
	public double getSafeEntrySpeed(double speed, double distance, double gradient) {
		double x = speed;

		double d = getBrakingDistance(x, speed, gradient);

		while (!Delta.Eql(d, distance)) {
			if (Delta.isLess(distance, d)) {
				x = x / (d - distance);
			} else if (Delta.isLess(d, distance)) {
				x = x * (distance - d);
			}

			d = getBrakingDistance(x, speed, gradient);
		}

		return x;
	}

	/**
	 * Computes braking time for a train at speed 'from_velocity' trying to reach
	 * lower speed 'to_velocity'
	 * 
	 * @param from_velocity initial speed
	 * @param to_velocity   target (lower speed), must be positive
	 * @param gradient      given as sin(alpha) where alpha is the gradient angle
	 * @return the time required to slow down to the target speed
	 */
	public double getBrakingTime(double from_velocity, double to_velocity, double gradient) {
		final double a = drag_C / mass;
		final double b = drag_B / mass;
		final double c = drag_A / mass + g * (braking + gradient);

		return term2(a, b, c, to_velocity, from_velocity);
	}

	/**
	 * Computes braking distance for a train at speed 'from_velocity' trying to
	 * reach lower speed 'to_velocity'
	 * 
	 * @param from_velocity initial speed
	 * @param to_velocity   target (lower speed), must be positive
	 * @param gradient      given as sin(alpha) where alpha is the gradient angle
	 * @return the minimal distance required to slow down to the target speed
	 */
	public double getBrakingDistance(double from_velocity, double to_velocity, double gradient) {
		if (to_velocity >= from_velocity) {
			return 0;
		}

		final double a = drag_C / mass;
		final double b = drag_B / mass;
		final double c = drag_A / mass + g * (braking + gradient);

		final double r = term3(a, b, c, to_velocity, from_velocity);
		if (Double.isNaN(r) || r < 0 || Double.isInfinite(r)) {
			System.out.println("xxx");
		}
		return r;
	}

	private double _getAccelerationTime(double from_velocity, double to_velocity, double gradient) {

		final int from_velocity_idx = (int) from_velocity;

		final double p0 = tractive_effort[from_velocity_idx];

		final double a = -drag_C;
		final double b = -drag_B;
		final double c = p0 - drag_A - mass * g * gradient;

		final double r = mass * term2(a, b, c, to_velocity, from_velocity);
		if (Double.isNaN(r) || r < 0 || Double.isInfinite(r)) {
			System.out.println("xxx");
		}
		return r;
	}

	private double _getAccelerationDistance(double from_velocity, double to_velocity, double gradient) {

		final int from_velocity_idx = (int) from_velocity;

		final double p0 = getTractiveEffortForSpeed(from_velocity_idx);

		final double a = -drag_C;
		final double b = -drag_B;
		final double c = p0 - drag_A - mass * g * gradient;

		final int xxx;

		final double r = Math.abs(mass * term3(a, b, c, to_velocity, from_velocity));

		if (Double.isNaN(r) || r < 0 || Double.isInfinite(r)) {
			System.out.println("xxx");
		}

		return r;
	}

	/**
	 * Follow the approximation of the tractive effort force to accurately compute
	 * acceleration time
	 * 
	 * @param from_velocity intial speed
	 * @param to_velocity   target speed
	 * @param gradient      gradient
	 * @return the minimal time necessary to accelerate to the target speed
	 */
	public double getAccelerationTime(double from_velocity, double to_velocity, double gradient) {

		final double s1 = Math.ceil(from_velocity);
		final double s2 = Math.floor(to_velocity);

		final int steps = (int) (s2 - s1);

		// step 1 : from_velocity to s1

		double t = 0;
		if (Delta.isLess(from_velocity, s1)) {
			t += _getAccelerationTime(from_velocity, s1, gradient);
		}

		// steps 2-k :
		for (int i = 0; i < steps; i++) {
			t += _getAccelerationTime(s1 + i, s1 + (i + 1), gradient);

		}

		// final step 1 : s2 + to_velocity
		if (Delta.isLess(s2, to_velocity)) {
			t += _getAccelerationTime(s2, to_velocity, gradient);
		}

		if (Double.isNaN(t)) {
			System.out.println("xxx");
		}

		return t;
	}

	/**
	 * Follow the approximation of the tractive effort force to accurately compute
	 * acceleration distance
	 * 
	 * @param from_velocity intial speed
	 * @param to_velocity   target speed
	 * @param gradient      gradient
	 * @return the minimal distance necessary to accelerate to the target speed
	 */
	public double getAccelerationDistance(double from_velocity, double to_velocity, double gradient) {

		final double s1 = Math.ceil(from_velocity);
		final double s2 = Math.floor(to_velocity);

		final int steps = (int) (s2 - s1);

		double t = 0;

		// step 1 : from_velocity to s1
		if (Delta.isLess(from_velocity, s1)) {
			t += _getAccelerationDistance(from_velocity, s1, gradient);
		}

		// steps 2-k :
		for (int i = 0; i < steps; i++) {
			t += _getAccelerationDistance(s1 + i, s1 + (i + 1), gradient);
		}

		// final step 1 : s2 + to_velocity
		if (Delta.isLess(s2, to_velocity)) {
			t += _getAccelerationDistance(s2, to_velocity, gradient);
		}

		return t;
	}

	private double term3(double a, double b, double c, double L1, double L2) {
		return term1(a, b, c, L1, L2) - b / (2d * a) * term2(a, b, c, L1, L2);
	}

	private double term2(double a, double b, double c, double xF, double xS) {
		if (xF <= xS) {
			throw new RuntimeException("Invalid integration bounds");
		}

		final double d = b * b - 4 * a * c;
		double r = Math.sqrt(Math.abs(d));
		if (d < 0) {
			final double t0_L2 = 2d / r * Math.atan((2 * a * xS + b) / r);
			final double t0_L1 = 2d / r * Math.atan((2 * a * xF + b) / r);

			r = t0_L1 - t0_L2;
			if (r < 0) {
				r = -r;
			}
			return r;
		} else if (d == 0) {
			final double sing = -b / (2 * a);
			if (xF < sing || sing < xS) {
				final double t0_L2 = -2d / (2d * a * xS + b);
				final double t0_L1 = -2d / (2d * a * xF + b);
				return t0_L1 - t0_L2;
			} else {
				throw new RuntimeException("Integration over singularity");
			}
		} else { // d > 0
			double s1, s2;
//			if ( b > 0) {
//				s1 = (-b - r) / (2 * a);
//				s2 = (2 * c) / (-b - r);
//			} else {
//				s1 = (2 * c) / (-b + r);
//				s2 = (-b + r) / (2 * a);
//			}

			s1 = (-b + r) / (2 * a);
			s2 = (-b - r) / (2 * a);

//			if (xF < s1 || s2 < xS || (s1 < xS && xF < s2)) {
			final double t0_L2 = 1 / r * Math.log(Math.abs((2 * a * xS + b - r) / (2 * a * xS + b + r)));
			final double t0_L1 = 1 / r * Math.log(Math.abs((2 * a * xF + b - r) / (2 * a * xF + b + r)));
			return Math.abs(t0_L1 - t0_L2);
//			} else {
//				throw new RuntimeException("Integration over singularity");
//			}
		}
	}

	private double term1(double a, double b, double c, double L1, double L2) {
		final double ww1_a = a * L1;
		final double ww1_b = ww1_a * L1;
		final double ww1 = a * L1 * L1;
		final double ww2 = b * L1 + c;
		final double ww = a * L1 * L1 + b * L1 + c;
		final double t0_L1 = 1 / (2d * a) * Math.log(Math.abs(a * L1 * L1 + b * L1 + c));
		final double t0_L2 = 1 / (2d * a) * Math.log(Math.abs(a * L2 * L2 + b * L2 + c));
		final double t0 = Math.abs(t0_L2 - t0_L1);
		return t0;
	}

	/**
	 * Determine the travel time for a train currently at speed "from_velocity"
	 * trying to reach target at distance "distance" that has upper speed limit
	 * "to_velocity". In general, the train might not reach the target velocity.
	 * 
	 * @param from_velocity current train speed
	 * @param gradient      the gradient of the considered region
	 * @param distance      distance to the target
	 * @param to_velocity   speed limit at the target
	 */
	public double travelTime(double from_velocity, double gradient, double distance, double to_velocity) {

		double xd1 = 0;
		if (to_velocity < from_velocity) {
			xd1 = getBrakingDistance(from_velocity, to_velocity, gradient);
		}

		if (Delta.isLess(distance, xd1)) { // oops
			throw new RuntimeException("Movement authority violation: decelerating from " + from_velocity + " to " + to_velocity
					+ " requires " + xd1 + " meters; The movemennt authority is " + distance + " meters.");
		} else if (Delta.Eql(xd1, distance)) { // only decelerate
			return getBrakingTime(from_velocity, to_velocity, gradient);
		} else { // there is an acceleration region
			final double max_v = getMaxSpeed(gradient);
			final double d3 = getBrakingDistance(max_v, to_velocity, gradient);
			final double d1 = getAccelerationDistance(from_velocity, max_v, gradient);
			final double d2 = distance - (d1 + d3);

			if (Delta.isLess(d2, 0)) { // reach some intermediate speed x (lower than max_v) and then slow down to
										// target velocity
				return XtravelTime(from_velocity, gradient, distance, to_velocity);
			} else if (Delta.Eql(d2, 0)) { // reach maximum speed and decelerate immediately
				return getAccelerationTime(from_velocity, max_v, gradient) + getBrakingTime(max_v, to_velocity, gradient);
			} else { // cruse at maximum speed for some time
				final double cruise_time = d2 / max_v;
				return getAccelerationTime(from_velocity, max_v, gradient) + cruise_time + getBrakingTime(max_v, to_velocity, gradient);
			}
		}
	}

	/**
	 * Determine travel when train accelerates for some time but has to slow down
	 * before reaching the maximum speed. The crux is to iteratively determine the
	 * top speed X to which the train must accelerate and then decelerate
	 * 
	 * The solution is in the form AD(u, X) + BD(X, v) = S
	 * 
	 * @param from_velocity current velocity
	 * @param gradient      overall gradient
	 * @param distance      distance to travel
	 * @param to_velocity   target velocity
	 * @return
	 */
	private double XtravelTime(double from_velocity, double gradient, double distance, double to_velocity) {
		// final double max_v = getMaxSpeed(gradient);

		// first see how much a bad fit the maximum speed is
		double x = from_velocity;
		double d1 = getAccelerationDistance(from_velocity, x, gradient);
		double d2 = getBrakingDistance(x, to_velocity, gradient);

		// iteratively find x
		while (!Delta.isLess(d1 + d2, distance)) {
			x += 0.1;
			d1 = getAccelerationDistance(from_velocity, x, gradient);
			d2 = getBrakingDistance(x, to_velocity, gradient);
		}

		return getAccelerationTime(from_velocity, x, gradient) + getBrakingTime(x, to_velocity, gradient);
	}

	public void getStableHorizon(UTrainHorizon horizon, double from_velocity, double gradient, double distance, double to_velocity) {
		double xd1 = 0;
		if (to_velocity < from_velocity) {
			xd1 = getBrakingDistance(from_velocity, to_velocity, gradient);
		}

		if (Delta.isLess(distance, xd1)) { // oops
			throw new RuntimeException("Movement authority violation: decelerating from " + from_velocity + " to " + to_velocity
					+ " requires " + xd1 + " meters; The movemennt authority is " + distance + " meters.");
		} else if (Delta.Eql(xd1, distance)) { // only decelerate
			horizon.set(UTrainHorizon.MODE_DECELERATION_MAX, getBrakingTime(from_velocity, to_velocity, gradient));
			return;
		} else { // there is an acceleration region
			final double max_v = getMaxSpeed(gradient);
			final double d3 = getBrakingDistance(max_v, to_velocity, gradient);
			final double d1 = getAccelerationDistance(from_velocity, max_v, gradient);
			final double d2 = distance - (d1 + d3);

			if (Delta.isLess(d2, 0)) { // reach some intermediate speed x (lower than max_v) and then slow down to
										// target velocity
				final double x = getXSpeed(from_velocity, gradient, distance, to_velocity);
				horizon.set(UTrainHorizon.MODE_ACCELERATION_MAX, getAccelerationTime(from_velocity, x, gradient));
				return;
			}
			if (Delta.Eql(d2, distance)) { // cruise
				horizon.set(UTrainHorizon.MODE_CRUISE_MAX, d2 / max_v);
				return;
			} else { // go to max speed
				final int xxx;
				final double ys = getYSpeed(from_velocity, gradient, distance, to_velocity);
				final double t1 = getAccelerationTime(from_velocity, ys, gradient);
				final double t2 = _getAccelerationTime(from_velocity, ys, gradient);
				horizon.set(UTrainHorizon.MODE_ACCELERATION_MAX, t1);
				return;

			}
		}
	}

	private double getXSpeed(double from_velocity, double gradient, double distance, double to_velocity) {
		// final double max_v = getMaxSpeed(gradient);

		// first see how much a bad fit the maximum speed is
		double x = from_velocity;
		double d1 = getAccelerationDistance(from_velocity, x, gradient);
		double d2 = getBrakingDistance(x, to_velocity, gradient);

		// iteratively find x
		while (Delta.isLess(d1 + d2, distance)) {
			x += 0.1;
			d1 = getAccelerationDistance(from_velocity, x, gradient);
			d2 = getBrakingDistance(x, to_velocity, gradient);
		}

		return x;
	}

	private double getYSpeed(double from_velocity, double gradient, double distance, double to_velocity) {
		// final double max_v = getMaxSpeed(gradient);

		double x = from_velocity;
		double d = getAccelerationDistance(from_velocity, x, gradient);

		// iteratively find x
		while (!Delta.isLess(d, distance)) {
			x += 0.1;
			d = getAccelerationDistance(from_velocity, x, gradient);
		}

		return x;
	}

	public void trainMove(UTrainState state, UTrainHorizon horizon, double gradient) {
		switch (horizon.getMode()) {
		case UTrainHorizon.MODE_ACCELERATION_MAX:
			double a = acceleration(state.getVelocity(), gradient);
			double v = state.getVelocity() + a * horizon.getTimeHorizon();
			double S = v * horizon.getTimeHorizon();
			state.setPosition(state.getPosition() + S);
			state.setAcceleration(a);
			state.setVelocity(v);
			break;
		case UTrainHorizon.MODE_CRUISE_MAX:
			a = 0;
			v = getMaxSpeed(gradient);
			S = v * horizon.getTimeHorizon();
			state.setPosition(state.getPosition() + S);
			state.setAcceleration(a);
			state.setVelocity(v);
			break;
		case UTrainHorizon.MODE_DECELERATION_MAX:
			a = deceleration(state.getVelocity(), gradient);
			v = state.getVelocity() + a * horizon.getTimeHorizon();
			if (v < 0) {
				throw new RuntimeException("Train moving backwards!");
			}
			S = v * horizon.getTimeHorizon();
			state.setPosition(state.getPosition() + S);
			state.setAcceleration(a);
			state.setVelocity(v);
			break;
		}
	}

	public double drag(double speed) {
		return drag_A + drag_B * speed + drag_C * speed * speed;
	}

	public double traction(double speed) {
		return getTractiveEffortForSpeed((int) speed);
	}

	public double gravity(double gradient) {
		return mass * g * gradient;
	}

	public double braking() {
		return mass * g * braking;
	}

	public double acceleration(double speed, double gradient) {
		final double r = (traction(speed) - drag(speed) - gravity(gradient)) / mass;
		if (r < 0) {
//			System.out.println("Negative acceleration:" + r);
		}

		return r;
	}

	public double deceleration(double speed, double gradient) {
		return (-braking() - drag(speed) - gravity(gradient)) / mass;
	}

}
