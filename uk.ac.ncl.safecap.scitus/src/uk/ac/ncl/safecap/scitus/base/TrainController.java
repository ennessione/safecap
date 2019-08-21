package uk.ac.ncl.safecap.scitus.base;

import uk.ac.ncl.safecap.scitus.common.TrainOverlapException;

public abstract class TrainController {
	protected World world;

	public TrainController(World world) {
		this.world = world;
	}

	public abstract void register(S1TrainActor train);

	public abstract void unregister(S1TrainActor train);

	public abstract double[] movementAuthority(S1TrainActor train) throws TrainOverlapException;
}
