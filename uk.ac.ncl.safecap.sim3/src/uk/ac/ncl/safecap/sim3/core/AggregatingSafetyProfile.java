package uk.ac.ncl.safecap.sim3.core;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public class AggregatingSafetyProfile<T extends ISafetyProfile> implements ISafetyProfile {
	private final List<T> profiles;

	public AggregatingSafetyProfile() {
		profiles = new ArrayList<>();
	}

	public void add(T profile) {
		profiles.add(profile);
	}

	public List<T> getProfiles() {
		return profiles;
	}

	@Override
	public double getSafeSpeedAt(ITrain train, double position, int kind, boolean probe) {
		double min = Double.MAX_VALUE;

		for (final ISafetyProfile profile : profiles) {
			final double limit = profile.getSafeSpeedAt(train, position, kind, probe);
			if (limit == 0) {
				return 0;
			} else if (limit < min) {
				min = limit;
			}
		}

		return min;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (final ISafetyProfile p : profiles) {
			sb.append(p.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
