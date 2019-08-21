package uk.ac.ncl.safecap.sim3.movingblock;

import java.util.Set;

import safecap.model.Line;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.sim3.core.AggregatingSafetyProfile;
import uk.ac.ncl.safecap.sim3.core.ISafetyProfile;
import uk.ac.ncl.safecap.sim3.core.LineSpeedProfile;
import uk.ac.ncl.safecap.sim3.core.LineStationStops;
import uk.ac.ncl.safecap.sim3.core.S3Interlocking;
import uk.ac.ncl.safecap.sim3.core.S3World;
import uk.ac.ncl.safecap.sim3.routebased.LineRouteProfiles;

public class MovingBlockInterlocking extends AggregatingSafetyProfile<ISafetyProfile> implements S3Interlocking {
	private final S3World world;

	public MovingBlockInterlocking(S3World world) {
		this.world = world;
	}

	private void createProfiles() {
		// add line speed profiles and stations
		for (final Line line : world.getProject().getLines()) {
			final LineSpeedProfile lsp = new LineSpeedProfile(world.getTrainLine(line));
			add(lsp);

			final LineStationStops lss = new LineStationStops(world, world.getTrainLine(line));
			add(lss);

			final LineRouteProfiles lrp = new LineRouteProfiles(world, world.getTrainLine(line));
			add(lrp);
		}
	}

	@Override
	public void start() {
		createProfiles();
	}

	@Override
	public void step(Set<ITrain> activeTrains) {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getState() {
		// TODO Auto-generated method stub
		return null;
	}
}
