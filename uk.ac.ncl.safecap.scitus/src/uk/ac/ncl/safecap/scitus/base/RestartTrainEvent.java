package uk.ac.ncl.safecap.scitus.base;

public class RestartTrainEvent extends WorldTimeEvent {
	private final S1TrainActor train;
	private final ITrainReleaseCondition releaseCondition;
	private final Object restartSource;

	public RestartTrainEvent(S1TrainActor train, World world, double when, ITrainReleaseCondition releaseCondition, Object restartSource) {
		super(world, when);
		this.train = train;
		this.releaseCondition = releaseCondition;
		this.restartSource = restartSource;
	}

	@Override
	public boolean fire(Object arg) {
		world.addActor(train);
		if (releaseCondition != null) {
			train.addReleaseCondition(releaseCondition);
		}

		train.setRestartSource(restartSource);
		return true;
	}
}
