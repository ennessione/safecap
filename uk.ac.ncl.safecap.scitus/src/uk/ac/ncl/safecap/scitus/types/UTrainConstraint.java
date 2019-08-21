package uk.ac.ncl.safecap.scitus.types;

import uk.ac.ncl.safecap.scitus.base.S1TrainActor;

public abstract class UTrainConstraint extends UConstraint {

	public UTrainConstraint(UIConstraintCommand command) {
		super(command);
	}

	public abstract boolean evaluateBinder(S1TrainActor train);

}
