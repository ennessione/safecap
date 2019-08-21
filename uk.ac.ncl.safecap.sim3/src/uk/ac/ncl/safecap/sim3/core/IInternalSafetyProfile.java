package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public interface IInternalSafetyProfile {
	public enum PROFILE_KIND {
		ANY, LINE, ROUTE, STATION, TRAIN
	}

	Speedpoint getSafeSpeedAt(ITrain train, double position);

	Speedpoint getSafeSpeedAt(ITrain train, double position, PROFILE_KIND filter);

	class Speedpoint {
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
