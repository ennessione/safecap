package uk.ac.ncl.safecap.scitus.base;

public abstract class ReservationActor implements IActor {
	private S1TrainActor train;

	public ReservationActor(S1TrainActor train) {
		this.train = train;
	}

	public S1TrainActor getTrain() {
		return train;
	}

	public void setTrain(S1TrainActor train) {
		this.train = train;
	}

}
