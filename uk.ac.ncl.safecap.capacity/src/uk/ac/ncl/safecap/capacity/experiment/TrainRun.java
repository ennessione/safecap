package uk.ac.ncl.safecap.capacity.experiment;

import uk.ac.ncl.safecap.scitus.base.Evolution;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.base.TrainProgression;
import uk.ac.ncl.safecap.scitus.base.World;
import uk.ac.ncl.safecap.scitus.stat.ITrainRun;

public class TrainRun implements ITrainRun {
	private int POINTS = 10000;

	private final ITrain train;
	private final double deltaT;
	private final double deltaD;
	private double[] position;
	private double[] speed;
	private double[] time;
	private int position_index;
	private double current_distance;
	private double current_time;

	private Evolution last_evolution;
	private double max_time;
	private final double max_position;

	public TrainRun(ITrain train, World world, int points) {
		POINTS = points;
		this.train = train;
		computeHorizon(world);

		deltaT = max_time / POINTS;
		deltaD = (train.getLine().getLineLength() + train.getDescriptor().getLength()) / POINTS;

		max_position = train.getLine().getLineLength() + train.getDescriptor().getLength();

		position = new double[POINTS + 1];
		time = new double[POINTS + 1];
		speed = new double[POINTS + 1];

		position[0] = 0d;
		position_index = 1;

		extractRun(world);
	}

	private void computeHorizon(World world) {
		for (final Evolution e : world.getHistory()) {
			final TrainProgression tp = getProgression(e);
			if (tp != null) {
				last_evolution = e;
			}
		}

		for (final Evolution e : world.getHistory()) {
			max_time += e.getExtent();
			if (e == last_evolution) {
				break;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.safecap.capacity.experiment.ITrainRun#getDistancePoints()
	 */
	@Override
	public int getDistancePoints() {
		return position.length;
	}

	public int getTimePoints() {
		return time.length;
	}

	public ITrain getTrain() {
		return train;
	}

	@Override
	public int timeToIndex(double time) {
		return (int) (time / deltaT);
	}

	public double getDeltaD() {
		return deltaD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.safecap.capacity.experiment.ITrainRun#getPosition()
	 */
	@Override
	public double[] getPosition() {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.ncl.safecap.capacity.experiment.ITrainRun#getSpeed()
	 */
	@Override
	public double[] getSpeed() {
		return speed;
	}

	@Override
	public double[] getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.ncl.safecap.capacity.experiment.ITrainRun#getIntegralTime(double,
	 * double)
	 */
	@Override
	public double getIntegralTime(double start_position, double end_position) {
		int start_index = (int) (start_position / deltaD);
		int end_index = (int) (end_position / deltaD);

		if (start_index < 0) {
			start_index = 0;
		}
		if (end_index >= time.length) {
			end_index = time.length - 1;
		}

		if (end_index <= start_index) {
			return 0d;
		}

		return time[end_index] - time[start_index];

	}

	private void extractRun(World world) {
		for (final Evolution e : world.getHistory()) {
			final TrainProgression tp = getProgression(e);
			doTrainProgression(tp, e.getExtent());
			current_time += e.getExtent();
			if (e == last_evolution) {
				break;
			}
		}

		position[position_index] = max_position;
		position_index++;

		position = (double[]) resizeArray(position, position_index);
		speed = (double[]) resizeArray(speed, position_index);

		// double v = position[position_index-1];
		// for(int i = position_index; i < position.length; i++)
		// position[i] = v;

		buildTimeProgression();
	}

	@SuppressWarnings("rawtypes")
	private static Object resizeArray(Object oldArray, int newSize) {
		final int oldSize = java.lang.reflect.Array.getLength(oldArray);
		final Class elementType = oldArray.getClass().getComponentType();
		final Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		final int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}
		return newArray;
	}

	private TrainProgression getProgression(Evolution e) {
		for (final Progression p : e.getProgressions()) {
			if (p instanceof TrainProgression) {
				final TrainProgression tp = (TrainProgression) p;
				if (tp.getActor() == train) {
					return tp;
				}
			}
		}

		return null;
	}

	private void buildTimeProgression() {
		int old_index = 0;
		time[0] = 0;
		for (int i = 1; i < position.length; i++) {

			int index;

			if (flip) {
				index = (int) Math.floor(position[i] / deltaD);
			} else {
				index = (int) Math.ceil(position[i] / deltaD);
			}

			flip = !flip;

			final double value = deltaT * i;

			for (int j = old_index + 1; j <= index; j++) {
				if (index < time.length) {
					time[j] = value;
				}
			}

			old_index = index;
		}

		time = (double[]) resizeArray(time, old_index);

		// fill the run tail with stationary data
		// if (old_index < time.length - 1) {
		// double l = time[old_index];
		// for(int i = old_index+1; i <= time.length - 1; i++) {
		// time[i] = l;
		// }
		// }
	}

	private void doTrainProgression(TrainProgression tp, double extent) {
		final double u = tp != null ? tp.getStartSpeed() : 0d;
		final double a = tp != null ? tp.getAcceleration() : 0d;
		final double d = tp != null ? tp.getDistance() : 0d;

		approxDistance(current_distance, // s0
				current_time, // t0
				u, // u
				a, // a
				extent // duration
		);

		current_distance += d;
	}

	boolean flip = true;

	private void approxDistance(double s0, double t0, double u, double a, double duration) {

		double rr;

		final double x = duration;
		final double y = deltaT;

		rr = x / y;

		// if (flip)
		// rr = Math.floor((duration + extra) / deltaT);
		// else
		// rr = Math.ceil((duration + extra) / deltaT);

		flip = !flip;

		// extra = duration - rr;
		int steps = (int) rr;

		double ss = s0;
		double vv = u;

		while (steps > 0) {
			ss += vv * deltaT + 0.5 * a * deltaT * deltaT;
			vv += a * deltaT;
			if (position_index < position.length && ss <= max_position) {
				position[position_index] = ss;
				speed[position_index] = vv;
				position_index++;
			}
			steps--;
		}
	}

	public double positionAtTime(double time) {
		return 0d;
	}

	public double speedAtTime(double time) {
		return 0d;
	}

	public double accelerationAtTime(double time) {
		return 0d;
	}

	public double timeAtPosition(double position) {
		return 0d;
	}

	@Override
	public ITrainRun truncate() {
		return this;
	}

}