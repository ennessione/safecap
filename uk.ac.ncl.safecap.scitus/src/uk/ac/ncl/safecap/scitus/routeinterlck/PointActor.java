package uk.ac.ncl.safecap.scitus.routeinterlck;

import safecap.model.Point;
import uk.ac.ncl.safecap.scitus.base.IActor;
import uk.ac.ncl.safecap.scitus.base.PointProgression;
import uk.ac.ncl.safecap.scitus.base.Progression;
import uk.ac.ncl.safecap.scitus.common.PointState;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

public class PointActor implements IActor {
	private static final double switching_and_locking_time = 5d;
	private final SystemState state;
	private final Point point;

	public Point getPoint() {
		return point;
	}

	public PointActor(SystemState state, Point point) {
		this.state = state;
		this.point = point;
	}

	@Override
	public double eventHorizon() throws SimulationException {
		if (state.pointIsLocked(point)) {

//			if (state.getWorld().getTime() > 200 && point.getNode().getLabel().equals("6B")) {
//				int x = 2;
//			}				

			final PointState current = state.getPointState(point);
			final PointState actual = state.computePointState(point);

			if (current.equals(actual)) {
				return Double.NEGATIVE_INFINITY;
			} else {
				return switching_and_locking_time;
			}
		}

		return Double.NEGATIVE_INFINITY; // no change
	}

	@Override
	public Progression progress(double horizon) throws SimulationException {
		final PointState current = state.getPointState(point);
		final PointState actual = state.computePointState(point);

		assert !current.equals(actual);

		state.pointMove(point);

		return new PointProgression(this, current, actual);
	}

	@Override
	public String toString() {
		return "PointActor(" + point.getNode().getLabel() + ")";
	}

}
