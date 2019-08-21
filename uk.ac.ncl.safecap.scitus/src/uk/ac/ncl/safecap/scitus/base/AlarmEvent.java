package uk.ac.ncl.safecap.scitus.base;

public class AlarmEvent extends WorldTimeEvent {

	public AlarmEvent(World world, double when) {
		super(world, when);
	}

	@Override
	public boolean fire(Object arg) {
		return true;
	}

}
