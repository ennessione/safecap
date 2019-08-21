package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.BaseTrainActor;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.stat.ITrainWithRun;
import uk.ac.ncl.safecap.scitus.stat.Run2DRecord;
import uk.ac.ncl.safecap.scitus.stat.SimHistoryRecord;

public class S3TrainActor extends BaseTrainActor implements IS3Train, ITrainWithRun {
	private static final double SPEED_DELTA = 0.1;
	private static final double SPEED_APPROACH_DELTA = 2.0;
	private static final double ACC_MAX = 1000;
	private static final double G = 9.78033;
	private static final double A = 3000, B = 112, C = 8;
	protected SimHistoryRecord trainRun;
	protected S3World world;
	private final Run2DRecord lineSpeedRecord;
	private final Run2DRecord brakingDistanceIntRecord;
	private final Run2DRecord brakingDistanceSpeedRecord;
	private final Run2DRecord brakingDistanceAltRecord;
	private final Run2DRecord accActiveRecord;
	// private Run2DRecord accGradientRecord;
	// private Run2DRecord accDragRecord;
	private final Run2DRecord accModeRecord;
	// private Run2DRecord gradientRecord;
	// private Run2DRecord accOverall;
	// private Run2DRecord timeDeltaRecord;

	public S3TrainActor(S3World world, TrainLine line, TrainDescriptor descr) {
		super(line, descr);
		this.world = world;
		trainRun = new SimHistoryRecord();
		lineSpeedRecord = trainRun.setupRecord(Run2DRecord.MAIN, "Line Speed");
		brakingDistanceIntRecord = trainRun.setupRecord("BD", "Braking Distance");
		brakingDistanceSpeedRecord = trainRun.setupRecord(Run2DRecord.MAIN, "Braking Distance Speed", 3);
		brakingDistanceAltRecord = trainRun.setupRecord("BD", "ALTBD", 2);

		brakingDistanceIntRecord.setDashed(true);

		brakingDistanceIntRecord.setShow(false);
		brakingDistanceSpeedRecord.setShow(false);
		brakingDistanceAltRecord.setShow(false);

		accActiveRecord = trainRun.setupRecord("forces", "Active Acceleration");
		// accOverall = trainRun.setupRecord("forces", "Acceleration Overall");
		// accGradientRecord = trainRun.setupRecord("forces", "Gradient Acceleration");
		// accDragRecord = trainRun.setupRecord("forces3", "Drag Acceleration");
		accModeRecord = trainRun.setupRecord("forces", "Driving Model", 3);
		// gradientRecord = trainRun.setupRecord("forces", "Gradient");
		// timeDeltaRecord = trainRun.setupRecord("extra", "Time step");

		if (line.hasEntrySpeedLimit()) {
			speed = line.getEntrySpeedLimit();
		}
	}

	@Override
	public SimHistoryRecord getTrainRun() {
		return trainRun;
	}

	@Override
	public void start(IWorld world) {
		trainRun.setStartTime(world.getTime());
	}

	@Override
	public boolean move(ISafetyProfile profile) {
		final double distanceToMove = distanceToMove(profile);
		assert !Double.isInfinite(distanceToMove) && !Double.isNaN(distanceToMove);
		move(distanceToMove);
		trainRun.insertPositionSpeed(getHead(), getSpeed());
		lineSpeedRecord.insert(Math.min(profile.getSafeSpeedAt(this, getHead(), ISafetyProfile.LINE, true), 100));
		// gradientRecord.insert(line.getGradient(getHead()) * 10);
		// timeDeltaRecord.insert(world.getTimeDelta());
		trainRun.timestep(world.getTime());
		return distanceToMove > 0;
	}

	protected double getTimeDelta() {
		return world.getTimeDelta();
	}

	private double distanceToMove(ISafetyProfile profile) {
		final double brakingDistance = integralBrakingDistance();

		final double speedAtBD = profile.getSafeSpeedAt(this, getHead() + brakingDistance, ISafetyProfile.ALL, false);
		final double speedAtHead = Math.min(profile.getSafeSpeedAt(this, getHead(), ISafetyProfile.ALL, true),
				getDescriptor().getMaxSpeed());
		final double currentSpeed = getSpeed();
		double currentAcceleration = getAcceleration();
		final double drag = getDragAcceleration(speed);
		final double grad = getGradientAcceleration();
		double accelerationDelta = 0;

		// boolean cruising = false;
		// determine mode: acceleration, cruising, coasting or braking
		// start with braking
		if (speedAtBD <= 0 || currentSpeed >= speedAtHead + SPEED_APPROACH_DELTA) {
			// do braking, request max effort
			accelerationDelta = -ACC_MAX;
			accModeRecord.insert(-0.5);
		} else if (Delta.Eql(currentSpeed, speedAtHead, SPEED_APPROACH_DELTA)) {
			if (!Delta.Eql(currentSpeed, speedAtHead, SPEED_DELTA)) {
				// do cruising control
				final double a = (speedAtHead * speedAtHead - currentSpeed * currentSpeed) / (2 * speed * getTimeDelta()) - (drag + grad);
				accelerationDelta = a - currentAcceleration;
			} else {
				final double a = -(drag + grad);
				accelerationDelta = a - currentAcceleration;
			}
			accModeRecord.insert(0.25);
			// cruising = true;
//		} else if (false) {
//			// do coasting
//			accelerationDelta = -currentAcceleration;
//			accModeRecord.insert(0);
		} else {
			// do acceleration, request max effort
			accelerationDelta = ACC_MAX;
			accModeRecord.insert(0.5);
		}

		// limit by jerk
		accelerationDelta = Math.signum(accelerationDelta) * Math.min(Math.abs(accelerationDelta), jerkLimit() * getTimeDelta());

		currentAcceleration += accelerationDelta;

		// limit by absolute value
		currentAcceleration = limitAcceleration(currentAcceleration);

		// if (!cruising)
		// currentAcceleration =
		// limitAccelerationbyPerformanceFunction(currentAcceleration);

		// record
		// accDragRecord.insert(drag + grad);
		// accGradientRecord.insert(grad);
		accActiveRecord.insert(currentAcceleration);
		brakingDistanceSpeedRecord.insert(Math.min(speedAtBD, getDescriptor().getMaxSpeed()));

		// sum of all forces
		final double effectiveAcceleration = currentAcceleration + drag + grad;
		// accOverall.insert(effectiveAcceleration);

		super.speed = Math.max(getSpeed() + effectiveAcceleration * getTimeDelta(), 0);
		super.acceleration = currentAcceleration;

		return speed * getTimeDelta();
	}

	/**
	 * Braking distance calculated by considering each constant gradient interval
	 * independently
	 * 
	 * @return
	 */
	private double integralBrakingDistance() {
		final double cspeed = getSpeed();
//		double cposition = getHead();
//
//		for(Entry<Double, Double> entry : line.getGradientMap().entrySet()) {
//			  double p = entry.getKey();
//			  double g = entry.getValue();
//			  if (p > cposition) {
//				  double bd = termBD(cspeed, g);
//				  if (cposition + bd < p || p >= line.getLineLength() )
//					  return (cposition + bd) - getHead();
//				  else {
//					  double ed = termBD(cspeed, g);
//					  cspeed = Math.sqrt(cspeed * cspeed + 2 * ed * (p - cposition));
//					  cposition = p;
//				  }
//			  }
//		}

		return termBD(cspeed, 0);
	}

	protected double getGradientAcceleration() {
		return 0;
//		double p1 = line.getGradientPoint(getTail());
//		double p2 = line.getGradientPoint(getHead());
//		if (Double.isNaN(p2))
//			p2 = p1;
//		if (Double.isNaN(p1))
//			p1 = p2;
//		if (p1 != p2 && !Double.isNaN(p1)) {
//			double lratio = (p1 - getTail()) / (getHead() - getTail());
//			double grade1 = line.getGradientMap().get(p1);
//			double grade2 = line.getGradientMap().get(p2);
//			return lratio * (-grade1 * G) + (1 - lratio)  * (-grade2 * G);
//		} else {
//			if (Double.isNaN(p1))
//				return 0;
//			double grade = line.getGradientMap().get(p1);
//			return -grade * G;
//		}
	}

	protected double jerkLimit() {
		return 5000;
	}

	protected double limitAccelerationbyPerformanceFunction(double a) {
		return a;
	}

	private double limitAcceleration(double a) {
		if (a > 0) {
			return limitTractiveAcceleration(a);
		} else {
			return limitBrakingAcceleration(a);
		}
	}

	protected double limitBrakingAcceleration(double a) {
		return -Math.min(getDescriptor().getMaxDeceleration(), -a);
	}

	protected double limitTractiveAcceleration(double a) {
		return Math.min(getDescriptor().getMaxAcceleration(), a);
	}

	private double termBD(double f, double grade) {
		if (f <= 0) {
			return 0;
		}

		// q0=A, q1=B, q2=C

		final double a = C / getEffectiveTrainMass();
		final double b = B / getEffectiveTrainMass();
		final double c = A / getEffectiveTrainMass() + getDescriptor().getMaxDeceleration() + grade * G;

		final double tI = termI(a, b, c, 0, f);
		if (Double.isNaN(tI)) {
			return tI;
		}

		final double ta0 = 1 / (2 * a) * Math.log(c);
		final double ta1 = 1 / (2 * a) * Math.log(a * f * f + b * f + c);
		final double ta = ta1 - ta0;

		final double tb = b / (2 * a) * tI;

		// System.out.println("tI=" + tI + "; f=" + f + "; ta0=" + ta0 + "; ta1=" + ta1
		// + "; r = " + (ta - tb));

		return ta - tb;
	}

	private double termI(double a, double b, double c, double s, double f) {
		final double delta = b * b - 4 * a * c;
		final double R = Math.sqrt(Math.abs(delta));
		if (delta < 0) {
			final double t0 = 2 / R * Math.atan((2 * a * s + b) / R);
			final double t1 = 2 / R * Math.atan((2 * a * f + b) / R);
			return t1 - t0;
		} else if (delta == 0) {
			final double x = -b / (2 * a);
			if (f < x || x < s) {
				final double t0 = -2 / (2 * a * s + b);
				final double t1 = -2 / (2 * a * f + b);
				return t1 - t0;
			} else {
				return Double.NaN;
			}
		} else if (delta > 0) {
			double x1, x2;
			if (b > 0) {
				x1 = (-b - Math.sqrt(delta)) / (2 * a);
				x2 = 2 * c / (-b - Math.sqrt(delta));

				if (f < x1 || x2 < s || x1 < s && f < x2) {
					final double t0 = 1 / R * Math.log((2 * a * s + b - R) / (2 * a * s + b + R));
					final double t1 = 1 / R * Math.log((2 * a * f + b - R) / (2 * a * f + b + R));
					return t1 - t0;
				} else {
					return Double.NaN;
				}
			} else {
				x1 = 2 * c / (-b + Math.sqrt(delta));
				x2 = (-b + Math.sqrt(delta)) / (2 * a);
				if (f < x1 || x2 < s || x1 < s && f < x2) {
					final double t0 = 1 / R * Math.log((2 * a * s + b - R) / (2 * a * s + b + R));
					final double t1 = 1 / R * Math.log((2 * a * f + b - R) / (2 * a * f + b + R));
					return t1 - t0;
				} else {
					return Double.NaN;
				}
			}
		}

		return Double.NaN;
	}

	protected double getEffectiveTrainMass() {
		return 100000;
	}

	protected double getDragAcceleration(double speed) {
		return -(A + B * speed + C * speed * speed) / getEffectiveTrainMass();
	}

	protected double getLimitedAcceleration(double requiredAcc) {
		return requiredAcc > 0 ? Math.min(getDescriptor().getMaxAcceleration(), requiredAcc)
				: -Math.min(getDescriptor().getMaxDeceleration() * 2, -requiredAcc);
	}

	/**
	 * Acceleration due to braking or tractive effort
	 * 
	 * @param requiredAcc the ideal acceleration determined by the target speed
	 * @return actual acceleration
	 */
	protected double getActiveAcceleration(double requiredAcc) {
		return getLimitedAcceleration(requiredAcc);
	}

	@Override
	public double getTimeExtent() {
		return world.getTime();
	}
}
