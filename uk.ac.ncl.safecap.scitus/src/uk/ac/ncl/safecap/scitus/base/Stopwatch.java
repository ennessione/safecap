/*****************************************************************************************
 * 	         .--.              															 *
 * ::\`--._,'.::.`._.--'/::     @author Ana M. Mihut	@email	anavmihut@gmail.com		 *
 * ::::.  ` __::__ '  .::::     @date   28.01.2014		@scope  Signal countdown	     *
 * ::::::-:.`'..`'.:-::::::          				 									 *
 * ::::::::\ `--' /::::::::     @detail compute dT of arrival; compute acc to get in dT	 *
 *																						 *
 *****************************************************************************************/

package uk.ac.ncl.safecap.scitus.base;

public class Stopwatch {

	private double segLen;
	private double velocity;
	private double acc;
	private long t_arrival;
	private long start;
	private boolean running;
	private S1TrainActor train;

	/** Constructor #1 empty **/
	public Stopwatch() {
		// TODO: might need to set all private fields to 0 here -.-'
	}

	/** Constructor #2 sets all parameters **/
	public Stopwatch(double sl, double v, double a, long t, S1TrainActor ta) {
		segLen = sl;
		velocity = v;
		acc = a;
		t_arrival = t;
		train = ta;
	}

	/** Aux - gets the time since watch was started **/
	public void Start() {
		running = true;
		start = (long) (System.currentTimeMillis() / 1000.0);
	}

	/** Aux - gets the time since watch was started **/
	public double DeltaTime() {
		running = false;
		return (System.currentTimeMillis() - start) / 1000.0;
	}

	/** Aux - tests if watch is running **/
	public boolean IsRunning() {
		return running ? true : false;
	}

	/** Aux - resets the stop watch object to 0 **/
	public void Resetwatch() {
		segLen = 0;
		velocity = 0;
		acc = 0;
		t_arrival = 0;
	}

	/**
	 * knowing the length d0 of current locked segment, estimate at what time
	 * (t_unlocked) the segment will be cleared by current train; this newly
	 * estimated t_unlocked will become the t_target of arrival for the following
	 * train to enter the segment;
	 * 
	 * In order to obtain the time, the following formulae were taken into account:
	 * d = v * 1/2a * 2t (d=displacement, v=initial velocity) t_arrival = [-v +
	 * sqrt(v^2 + 2ad)]/a (discard the negative solution to the quadratic equation)
	 * a = 2(d-vt)/t^2
	 */
	public long EstimateArrivalTime(double len, double v, double acceleration) {
		segLen = len;
		velocity = v;
		acc = acceleration;
		return (long) ((-velocity + Math.sqrt(velocity * velocity + 2 * acc * segLen)) / acc);
	}

	/**
	 * knowing the time of arrival, a better suited acceleration could be inferred
	 * in order to make the train arrive before or after the estimated time
	 */
	public double AdjustAcceleration(double len, double v, long arriveAt) {
		segLen = len;
		velocity = v;
		t_arrival = arriveAt;

		return 2 * (segLen - velocity * t_arrival) / t_arrival * t_arrival;
	}

	public double AdjustVelocity(double arriveAt, double acceleration) {
		return arriveAt * acceleration;
	}

	public void SetSegLength(double len) {
		segLen = len;
	}

	public double GetSegLength() {
		return segLen;
	}

	public void SetVelocity(double v) {
		velocity = v;
	}

	public double GetVelocity() {
		return velocity;
	}

	public void SetAcceleration(double a) {
		acc = a;
	}

	public double GetAcceleration() {
		return acc;
	}

	public void SetArrivalTime(long t) {
		t_arrival = t;
	}

	public long GetArrivalTime() {
		return t_arrival;
	}

	public S1TrainActor GetTrain() {
		return train;
	}

	public void SetTrain(S1TrainActor tr) {
		train = tr;
	}

	@Override
	public String toString() {

		return "Distance: " + segLen + "/n" + "Velocity: " + velocity + "/n" + "Acceler.: " + acc + "/n" + "Arrival: " + t_arrival + "/n"
				+ "Train info: " + train.toString() + "/n";
	}
}
