package uk.ac.ncl.safecap.sim3.routebased;

import safecap.model.ControlLogic;
import safecap.model.Route;
import uk.ac.ncl.safecap.misc.core.RouteUtil;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.common.RouteState;
import uk.ac.ncl.safecap.scitus.routeinterlck.IRBSystemState;
import uk.ac.ncl.safecap.sim3.core.BaseLineFixedPositionSafetyProfile;
import uk.ac.ncl.safecap.sim3.core.ISafetyProfile;
import uk.ac.ncl.safecap.sim3.core.S3World;

public class RouteProfile extends BaseLineFixedPositionSafetyProfile {
	private final S3World world;
	private final Route route;
	private final Speedpoint RED, UNLIMITED;
	private final double[] movementAuthority;

	public RouteProfile(S3World world, TrainLine line, Route route) {
		super(line, line.getRouteSignalPosition(route));
		this.world = world;
		this.route = route;
		RED = new Speedpoint(getPosition(), 0);
		UNLIMITED = new Speedpoint(getPosition(), Double.MAX_VALUE);

		final ControlLogic logic = line.getControlLogic(route);

		movementAuthority = new double[logic.getAspects() - 1];
		for (int i = 0; i < logic.getAspects() - 1; i++) {
			movementAuthority[i] = RouteUtil.getMovementAuthority(route, getLine(), i + 1);
		}
	}

	@Override
	public Speedpoint getSpeedpoint(ITrain train, double position, int kind) {
		final double routeHead = getPosition();

		if (position < routeHead || train.getHead() >= routeHead) {
			return UNLIMITED;
		}

		final int aspect = ((IRBSystemState) world.getState()).getRouteState(route).getAspect();

		if (aspect == RouteState.RED) {
			return RED;
		}

		final double ma = movementAuthority[aspect - 1];

		final double vspeed = train.getDescriptor().getMaximumEntrySpeed(ma);

		return new Speedpoint(routeHead, vspeed);
	}

	@Override
	public int getKind() {
		return ISafetyProfile.ROUTE;
	}

}
