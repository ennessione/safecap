package uk.ac.ncl.safecap.sim3.routebased;

import java.util.Collections;
import java.util.Set;

import safecap.model.Line;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.routeinterlck.IRBSystemState;
import uk.ac.ncl.safecap.sim3.core.AggregatingSafetyProfile;
import uk.ac.ncl.safecap.sim3.core.IGlobalState;
import uk.ac.ncl.safecap.sim3.core.ISafetyProfile;
import uk.ac.ncl.safecap.sim3.core.LineSpeedProfile;
import uk.ac.ncl.safecap.sim3.core.LineStationStops;
import uk.ac.ncl.safecap.sim3.core.S3Interlocking;
import uk.ac.ncl.safecap.sim3.core.S3World;

public class S3RouteBasedInterlocking extends AggregatingSafetyProfile<ISafetyProfile> implements S3Interlocking {
	private final S3World world;
	private final IGlobalState state;

	public S3RouteBasedInterlocking(S3World world, IGlobalState state) {
		this.world = world;
		this.state = state;
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
		state.refreshGlobalState(Collections.<ITrain>emptySet());
	}

	@Override
	public void step(Set<ITrain> activeTrains) {
		state.refreshGlobalState(activeTrains);
	}

	@Override
	public void end() {

	}

	@Override
	public IRBSystemState getState() {
		return (IRBSystemState) state;
	}

	@Override
	public boolean isCompleted() {
		return getState().isCompleted();
	}

}
