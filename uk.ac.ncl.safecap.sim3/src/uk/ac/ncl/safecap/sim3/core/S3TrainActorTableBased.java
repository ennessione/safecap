package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.gui.trainconfig.ExtendedTrainDescriptor;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.stat.Run2DRecord;

public class S3TrainActorTableBased extends S3TrainActor {
	private final ExtendedTrainDescriptor descr;
	private final double effectiveMass;
	private final Run2DRecord powerRecord;
	private final Run2DRecord energyRecord;
	private final Run2DRecord performanceRecord;
	private double energy = 0;

	public S3TrainActorTableBased(S3World world, TrainLine line, ExtendedTrainDescriptor descr) {
		super(world, line, descr);
		this.descr = descr;
		effectiveMass = descr.getTrainMass() * descr.getInertialCoefficient();
		powerRecord = trainRun.setupRecord("power", "Power profile");
		energyRecord = trainRun.setupRecord("energy", "Energy profile");
		performanceRecord = trainRun.setupRecord("forces", "Performance function");
	}

	@Override
	public boolean move(ISafetyProfile profile) {
		final boolean result = super.move(profile);

		if (acceleration > 0) {
			final double power = descr.getEnergy().value(acceleration * descr.getTrainMass(), speed);
			energy += power * getTimeDelta();
			powerRecord.insert(power);
		} else {
			powerRecord.insert(0);
		}
		energyRecord.insert(energy);

		if (descr.getPerformance() != null) {
			performanceRecord.insert(descr.getPerformance().value(getHead()));
		}

		return result;
	}

	@Override
	protected double getEffectiveTrainMass() {
		return descr.getTrainMass(); // * descr.getInertialCoefficient();
	}

	@Override
	protected double limitTractiveAcceleration(double a) {
		return Math.min(a, descr.getTractiveEffort().value(getSpeed()) / effectiveMass);
	}

	@Override
	protected double limitAccelerationbyPerformanceFunction(double a) {
		if (a > 0 && descr.getPerformance() != null) {
			final double perf = descr.getPerformance().value(getHead());
			if (perf < 0 || perf > 1) {
				return a;
			} else if (perf == 0.5) {
				return 0;
			} else if (perf > 0.5 && a > 0) {
				return a * ((perf - 0.5) / 0.5);
			} else if (perf < 0.5 && a < 0) {
				return a * (perf / 0.5);
			}
		}
		return a;
	}

}
