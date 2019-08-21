package uk.ac.ncl.safecap.scitus.base;

public class RemoveActorWorldEvent implements IWorldEvent {
	private final IActor actor;
	private final World world;

	public RemoveActorWorldEvent(World world, IActor actor) {
		this.actor = actor;
		this.world = world;
	}

	@Override
	public Object happens() {
		return Boolean.TRUE;
	}

	@Override
	public boolean fire(Object arg) {
		world.getActors().remove(actor);
		return true;
	}

}
