package uk.ac.ncl.safecap.scitus.common;

public class TrainOverlapException extends SimulationException {
	public TrainOverlapException(String information) {
		super("TrainOverlap", information);
	}

	private static final long serialVersionUID = -8850790208815561439L;
}
