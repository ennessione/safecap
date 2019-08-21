package uk.ac.ncl.safecap.scitus.routeinterlck.trainevents;

import uk.ac.ncl.safecap.misc.core.EquipmentPosition;
import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public class StationSpeedBound implements ITrainSpeedBound {

	@Override
	public SpeedBound permissibleSpeedAt(S1TrainActor train, double distance) {
		final EquipmentPosition limit = train.getLine().getNextStation(train.getHead() + distance);

		if (limit.getEquipment() == null) {
			return SpeedBound.UNBOUNDED;
		}

		final double speed = 0d;

		final double offset = limit.getDistance();
		double speed_limit = Math.sqrt(2 * train.getDescriptor().getMaxDeceleration() * offset + speed * speed);

		speed_limit = Math.min(speed_limit, train.getMaximumSpeed());

		return new SpeedBound(speed_limit, limit.toString());
	}

}
