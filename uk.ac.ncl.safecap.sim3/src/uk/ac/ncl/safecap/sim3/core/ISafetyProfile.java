package uk.ac.ncl.safecap.sim3.core;

import uk.ac.ncl.safecap.scitus.base.ITrain;

public interface ISafetyProfile {
	int ALL = 0b1111;
	int LINE = 0b1;
	int ROUTE = 0b10;
	int STATION = 0b100;
	int TRAIN = 0b1000;

	double getSafeSpeedAt(ITrain train, double position, int kind, boolean probe);

}
