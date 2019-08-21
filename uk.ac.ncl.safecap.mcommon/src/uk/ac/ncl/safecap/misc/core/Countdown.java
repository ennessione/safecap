/*****************************************************************************************
 * 	         .--.              															 *
 * ::\`--._,'.::.`._.--'/::     @author Ana M. Mihut	@email	anavmihut@gmail.com		 *
 * ::::.  ` __::__ '  .::::     @date   28.01.2014		@scope  Signal countdown	     *
 * ::::::-:.`'..`'.:-::::::          				 									 *
 * ::::::::\ `--' /::::::::     @detail delta t until the signal resets (stopwatch)		 *
 *																						 *
 *****************************************************************************************/

package uk.ac.ncl.safecap.misc.core;

public class Countdown {

	private double segLen;
	private double velocity;
	private double acc;
	private long t_arrival;
	private long start;
	private boolean running;

	public Countdown() {
		start = System.currentTimeMillis();
		running = true;
	}

	public Countdown(double sl, double v, double a, long t) {
		segLen = sl;
		velocity = v;
		acc = a;
		t_arrival = t;
	}

	public double DeltaTime() {
		running = false;
		return (System.currentTimeMillis() - start) / 1000.0;
	}

	public boolean IsRunning() {
		return running ? true : false;
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
	public long EstimateArrivalTime() {
		return (long) ((-velocity + Math.sqrt(velocity * velocity + 2 * acc * segLen)) / acc);
	}

	/**
	 * knowing the time of arrival, a better suited acceleration could be inferred
	 * in order to make the train arrive before or after the estimated time
	 */
	public double AdjustAcceleration(double segLen, double speed, long time) {
		return 2 * (segLen - velocity * t_arrival) / t_arrival * t_arrival;
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

	@Override
	public String toString() {
		return "Distance: " + segLen + "\n" + "Velocity: " + velocity + "\n" + "Acc: " + acc + "\n" + "Arrival: " + t_arrival + "\n";
	}
}
