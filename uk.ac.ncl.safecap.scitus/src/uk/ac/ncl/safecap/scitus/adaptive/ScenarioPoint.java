package uk.ac.ncl.safecap.scitus.adaptive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import safecap.model.Line;
import safecap.model.Point;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public class ScenarioPoint implements Serializable {
	private static final long serialVersionUID = 7943419699231323722L;

	private final Point point;
	private final List<PointLockingStep> lockingSteps;
	private final List<PointOccupationStep> occupationSteps;

	private Line locking_line;
	private double locking_start;

	private S1TrainActor occ_train;
	private double occ_start;
	private double occ_entry_speed;
	private double occ_entry_acc;

	PointLockingStep locking_step;

	public ScenarioPoint(Point point) {
		this.point = point;
		lockingSteps = new ArrayList<>();
		occupationSteps = new ArrayList<>();
	}

	public Set<S1TrainActor> getAllTrains() {
		final Set<S1TrainActor> trains = new HashSet<>();

		for (final PointLockingStep o : lockingSteps) {
			trains.add(o.getTrain());
		}

		return trains;
	}

	public void annouceLocking(S1TrainActor train, double time) {
		assert locking_line == null;
		assert occ_train == null;

		locking_line = train.getLine().getSchemaLine();
		locking_start = time;

		locking_step = new PointLockingStep(train, locking_start, time - locking_start);
		lockingSteps.add(locking_step);
	}

	public void annouceUnlocking(S1TrainActor train, double time) {
		assert locking_line == train.getLine().getSchemaLine() && locking_start >= 0;
		assert time > locking_start;

		locking_step.duration = time - locking_start;

		locking_line = null;
		locking_start = -1;
	}

	public void announceOccuputation(double time, S1TrainActor train, double speed, double acc) {
		assert occ_train == null;

		occ_train = train;
		occ_start = time;
		occ_entry_speed = speed;
		occ_entry_acc = acc;
	}

	public void announceDeoccuputation(Double occupationTime, double time, double speed, double acc) {
		assert occ_train != null && occ_start >= 0;
		assert time > occ_start;

		final PointOccupationStep step = new PointOccupationStep(occ_train, occ_start, time - occ_start, occ_entry_speed, speed,
				occ_entry_acc, acc);
		occupationSteps.add(step);
		occ_train = null;
		occ_start = -1;
	}

	public List<PointLockingStep> getLockingSteps() {
		return lockingSteps;
	}

	public List<PointOccupationStep> getOccupationSteps() {
		return occupationSteps;
	}

	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return lockingSteps.toString();
	}
}
