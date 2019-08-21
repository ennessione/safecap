package uk.ac.ncl.safecap.scitus.base;

import java.util.List;

import uk.ac.ncl.safecap.misc.core.TrainLine;

public abstract class WorldTrainTailPositionEvent implements IWorldEvent {
	private final double where;
	private final double delta;
	private final List<TrainLine> lines;
	private final World world;

	public WorldTrainTailPositionEvent(World world, List<TrainLine> lines, double where, double delta) {
		this.where = where;
		this.delta = delta;
		this.world = world;
		this.lines = lines;
	}

	@Override
	public Object happens() {
		for (final IActor actor : world.getActors()) {
			if (actor instanceof S1TrainActor) {
				final S1TrainActor train = (S1TrainActor) actor;
				if (lines.contains(train.getLine())) {
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
