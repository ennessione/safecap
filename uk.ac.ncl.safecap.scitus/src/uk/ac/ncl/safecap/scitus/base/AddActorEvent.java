package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Line;
import uk.ac.ncl.safecap.common.TrainDescriptor;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.routeinterlck.OptimalDriver;

public class AddActorEvent extends WorldTimeEvent {
	private final Line line;
	private final TrainDescriptor descr;

	public AddActorEvent(Line line, TrainDescriptor descr, World world, double when) {
		super(world, when);
		this.line = line;
		this.descr = descr;
	}

	@Override
	public boolean fire(Object arg) {
		assert line != null;
		final TrainLine tl = world.getTrainLine(line);
		final S1TrainActor tr = new S1TrainActor(tl, descr, world.interlocking, new OptimalDriver());
		world.addActor(tr);

		return true;
	}
}
