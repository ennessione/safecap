package uk.ac.ncl.safecap.sim3.core;

import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public class StationStop extends BaseFixedPositionSafetyProfile {
	private final S3World world;
	private final Set<ITrain> released;
	private final Speedpoint speedpoint;
	private final double delay;
	private double timer;
	private boolean timerStart = false;

	public StationStop(S3World world, double position, double delay) {
		super(position);
		this.world = world;
		speedpoint = new Speedpoint(position, 0);
		released = new HashSet<>();
		this.delay = delay;

	}

	@Override
	public Speedpoint getSpeedpoint(ITrain train, double position, int kind) {
		if (released.contains(train)) {
			return null;
		}

		if (!timerStart && train.getHead() >= getPosition()) {
			timerStart = true;
			timer = world.getTime();
		}

		if (delay <= world.getTime() - timer) {
			timerStart = false;
			released.add(train);
			return null;
		}
		return speedpoint;
	}

	@Override
	public String toString() {
		return "station stop for " + delay + " at (" + speedpoint.getPosition() + ")";
	}

	@Override
	public int getKind() {
		return ISafetyProfile.STATION;
	}

}
