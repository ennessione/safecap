package uk.ac.ncl.safecap.sim3.movingblock;

import java.util.HashMap;
import java.util.Map;

import safecap.Project;
import safecap.model.Point;
import uk.ac.ncl.safecap.misc.core.TrainLine;
import uk.ac.ncl.safecap.scitus.base.ITrain;
import uk.ac.ncl.safecap.scitus.base.IWorld;
import uk.ac.ncl.safecap.scitus.common.PointState;

public class MovingBlockState {
	protected Project root;
	private final IWorld world;
	protected Map<Point, PointState> point_state;
	protected Map<Point, TrainLine> point_lock;

	public MovingBlockState(IWorld world) {
		this.world = world;
		root = world.getProject();
		point_state = new HashMap<>();
		point_lock = new HashMap<>();
	}

	public boolean pointIsLocked(Point point) {
		return point_lock.containsKey(point);
	}

	public boolean pointIsLockedFor(Point point, TrainLine line) {
		return point_lock.get(point) == line;
	}

	public void pointUnLock(ITrain train, Point point) {
		if (point_lock.containsKey(point)) {
			point_lock.remove(point);
		}
	}

	public void pointLock(Point point, ITrain train) {
		final TrainLine line = train.getLine();

		assert !point_lock.containsKey(point);
		point_lock.put(point, line);
	}

	public void pointMove(Point point) {
		final PointState new_state = computePointState(point);
		// TODO

	}

	private PointState computePointState(Point point) {
		if (!point_lock.containsKey(point)) {
			return null;
		}

		final TrainLine line = point_lock.get(point);

		// TODO
		return null;
	}

	public Project getRoot() {
		return root;
	}

	public IWorld getWorld() {
		return world;
	}

	public Map<Point, PointState> getPoint_state() {
		return point_state;
	}

}
