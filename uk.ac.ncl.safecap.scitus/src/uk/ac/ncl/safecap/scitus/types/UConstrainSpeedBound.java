package uk.ac.ncl.safecap.scitus.types;

import uk.ac.ncl.safecap.scitus.base.S1TrainActor;
import uk.ac.ncl.safecap.scitus.routeinterlck.SystemState;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.ITrainSpeedBound;
import uk.ac.ncl.safecap.scitus.routeinterlck.trainevents.SpeedBound;

public class UConstrainSpeedBound implements ITrainSpeedBound {
	private final UTrainConstraint cstr;
	private final SystemState state;

	public UConstrainSpeedBound(SystemState state, UTrainConstraint cstr) {
		this.cstr = cstr;
		this.state = state;
	}

	@Override
	public SpeedBound permissibleSpeedAt(S1TrainActor train, double distance) {

		if (cstr.evaluateBinder(train)) {
			if (cstr.evaluateGuard(state)) {
				if (cstr.getCommand() instanceof USpeedCommand) {
					final USpeedCommand scmd = (USpeedCommand) cstr.getCommand();
					scmd.getHigherBound();
				}
			}
		}

		return null;
	}

}
