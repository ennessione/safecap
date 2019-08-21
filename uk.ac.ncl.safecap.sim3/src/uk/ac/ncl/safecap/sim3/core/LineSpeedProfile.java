package uk.ac.ncl.safecap.sim3.core;

import java.util.Collections;

import safecap.trackside.Equipment;
import safecap.trackside.SpeedLimit;
import uk.ac.ncl.safecap.misc.core.TrainLine;

public class LineSpeedProfile extends LineBasedAggregatingSafetyProfile<FixedSpeedLimit> {
	public LineSpeedProfile(TrainLine line) {
		super(line);

		if (line.hasEntrySpeedLimit()) {
			add(new FixedSpeedLimit(0, line.getEntrySpeedLimit()));
		}

		for (final Equipment eq : line.getSpeedLimiterObjects()) {
			if (eq instanceof SpeedLimit) {
				final SpeedLimit sl = (SpeedLimit) eq;
				add(new FixedSpeedLimit(line.getSpeedLimiterPosition(eq), sl.getLimit()));
			}
		}

		if (line.hasExitSpeedLimit()) {
			add(new FixedSpeedLimit(line.getLineLength(), line.getExitSpeedLimit()));
		}

		Collections.sort(getProfiles());
		for (int i = 0; i < getProfiles().size() - 1; i++) {
			getProfiles().get(i).setLimitPosition(getProfiles().get(i + 1).getPosition());
		}

		if (getProfiles().size() > 0) {
			getProfiles().get(getProfiles().size() - 1).setLimitPosition(Double.MAX_VALUE);
		}
	}

}
