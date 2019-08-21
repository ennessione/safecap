package uk.ac.ncl.safecap.scitus.routeinterlck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.scitus.base.IActor;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.base.World;

public class Historian {
	private final List<Observation> log;
	private final World world;
	private boolean live = false;
	private boolean print_time = false;

	public Historian(World world) {
		log = new ArrayList<>();
		this.world = world;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void setPrintTime(boolean print_time) {
		this.print_time = print_time;
	}

	public void sees(IActor actor, String data) {
		final Observation o = new Observation(actor, world.getTime(), data);
		log.add(o);
		if (live) {
			System.out.println(o.toString(print_time));
		}
	}

	public void sees(String data) {
		final Observation o = new Observation(world.getTime(), data);
		log.add(o);
		if (live) {
			System.out.println(o.toString(print_time));
		}
	}

}

class Observation implements Serializable {

	private static final long serialVersionUID = -687350320365086595L;
	double time;
	IActor actor;
	String information;

	public Observation(IActor actor, double time, String info) {
		this.time = time;
		this.actor = actor;
		information = info;
	}

	public Observation(double time, String info) {
		this.time = time;
		information = info;
	}

	public String toString(boolean print_time) {
		final int t = (int) (time * 100d);
		final double tt = t / 100d;

		String actorl = "-";

		if (actor instanceof S1TrainActor) {
			final S1TrainActor train = (S1TrainActor) actor;
			actorl = train.getName();
		} else if (actor != null) {
			actorl = "X";
		}

		if (print_time) {
			return "" + tt + ": " + actorl + "> " + information;
		} else {
			return actorl + "> " + information;
		}
	}
}