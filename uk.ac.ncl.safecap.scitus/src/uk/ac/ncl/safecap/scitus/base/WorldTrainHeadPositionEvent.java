package uk.ac.ncl.safecap.scitus.base;

import uk.ac.ncl.safecap.misc.core.TrainLine;

public abstract class WorldTrainHeadPositionEvent implements IWorldEvent {
	private final double where;
	private final double delta;
	private final TrainLine line;
	private final World world;

	public WorldTrainHeadPositionEvent(World world, TrainLine line, double where, double delta) {
		this.where = where;
		this.delta = delta;
		this.world = world;
		this.line = line;
	}

	public double getWhere() {
		return where;
	}

	public double getDelta() {
		return delta;
	}

	public TrainLine getLine() {
		return line;
	}

	public World getWorld() {
		return world;
	}

	@Override
	public Object happens() {

		for (final IActor actor : world.getActors()) {
			if (actor instanceof S1TrainActor) {
				final S1TrainActor train = (S1TrainActor) actor;
				if (train.getLine() == line) {
					if (where - delta <= train.getHead() && train.getHead() <= where + delta) {
						return train;
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean fire(Object _train) {
		if (_train instanceof S1TrainActor) {
			final S1TrainActor train = (S1TrainActor) _train;
			handle(train);
		}

		return false;
	}

	public abstract void handle(S1TrainActor train);
}
