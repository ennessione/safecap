package uk.ac.ncl.safecap.scitus.stat;

public interface ITrainRun {

	int getDistancePoints();

	double[] getPosition();

	double[] getSpeed();

	double[] getTime();

	ITrainRun truncate();

	double getIntegralTime(double start_position, double end_position);

	int timeToIndex(double time);
}