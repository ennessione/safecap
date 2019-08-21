package uk.ac.ncl.safecap.scitus.base;

public abstract class WorldTimeEvent implements IWorldEvent {
	double when;
	World world;

	public WorldTimeEvent(World world, double when) {
		this.when = when;
		this.world = world;
	}

	@Override
	public Object happens() {
		if (when == world.time) {
			return Boolean.TRUE;
		} else {
			return null;
		}
	}

	public double getWhen() {
		return when;
	}
}
