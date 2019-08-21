package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public class SpeedBoundManager implements ITrainSpeedBound, Serializable {
	private static final long serialVersionUID = -8391471820181875856L;
	private final List<ITrainSpeedBound> bounds;

	public SpeedBoundManager() {
		bounds = new ArrayList<>();
	}

	public void addSpeedBound(ITrainSpeedBound bound) {
		bounds.add(bound);
	}

	@Override
	public SpeedBound permissibleSpeedAt(S1TrainActor train, double distance) {
		double speed = Double.POSITIVE_INFINITY;

		SpeedBound result = null;
		for (final ITrainSpeedBound bound : bounds) {
			final SpeedBound s = bound.permissibleSpeedAt(train, distance);
			// System.out.println("\t\t\t " + s.toString());
			if (s.getBound() < speed) {
				speed = s.getBound();
				result = s;

				if (speed == 0) {
					break;
				}
			}
		}

		return result;
	}

}
