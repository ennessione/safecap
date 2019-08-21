package uk.ac.ncl.safecap.scitus.base;

import safecap.model.Point;
import uk.ac.ncl.safecap.scitus.common.PointState;
import uk.ac.ncl.safecap.scitus.routeinterlck.PointActor;

public class PointProgression extends Progression {
	private final PointState old_state;
	private final PointState new_state;

	public PointProgression(PointActor route, PointState old_state, PointState new_state) {
		super(route);
		this.old_state = old_state;
		this.new_state = new_state;
	}

	/**
	 * Returns point state at the beginning of the extent
	 */
	public PointState getOldState() {
		return old_state;
	}

	/**
	 * Returns point state at the end of the extent
	 */
	public PointState getNewState() {
		return new_state;
	}

	public Point getPoint() {
		return ((PointActor) getActor()).getPoint();
	}

	@Override
	public String toString() {
		return "POINT " + getPoint() + " from " + old_state + " TO " + new_state;
	}
}
