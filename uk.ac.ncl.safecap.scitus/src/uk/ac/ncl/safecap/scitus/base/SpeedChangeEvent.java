package uk.ac.ncl.safecap.scitus.base;

import org.eclipse.emf.ecore.EObject;

import safecap.trackside.SpeedLimit;
import uk.ac.ncl.safecap.misc.core.Delta;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public class SpeedChangeEvent extends WorldTrainHeadPositionEvent {
	private static double delta = Delta.DELTA;
	private final double speed_limit;

	private SpeedChangeEvent(World world, TrainLine line, double where, double speed_limit) {
		super(world, line, where, delta);
		this.speed_limit = speed_limit;
	}

	public static SpeedChangeEvent getSpeedChanger(World world, TrainLine line, SpeedLimit limit, double position) {
		return new SpeedChangeEvent(world, line, position, limit.getLimit());
	}

	public static SpeedChangeEvent getSpeedChanger(World world, TrainLine line, Double limit, double position) {
		return new SpeedChangeEvent(world, line, position, limit);
	}

	public static void placeSpeedChangers(World world, TrainLine line) {
		for (final EObject _sl : line.getSpeedLimiterObjects()) {
			if (_sl instanceof SpeedLimit) {
				final SpeedLimit sl = (SpeedLimit) _sl;
				final double position = line.getSpeedLimiterPosition(sl);
				final SpeedChangeEvent ce = getSpeedChanger(world, line, sl, position);
				world.addEvent(ce);
			}
		}

		// place line exit speed limit
		if (line.hasExitSpeedLimit()) {
			final SpeedChangeEvent ce = getSpeedChanger(world, line, line.getExitSpeedLimit(), line.getLineLength());
			world.addEvent(ce);
		}
	}

	public static void placeSpeedChangers(World world) {
		for (final TrainLine line : world.getLines()) {
			placeSpeedChangers(world, line);
		}
	}

	@Override
	public void handle(S1TrainActor train) {
		train.setMaximumSpeed(speed_limit);
	}

}
