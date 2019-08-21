package uk.ac.ncl.safecap.xdata.verification.registry;

public class TransparentRunner implements ISafeRunner {
	public static final TransparentRunner INSTANCE = new TransparentRunner();

	private TransparentRunner() {
	}

	@Override
	public void execute(Runnable runnable) throws Exception {
		runnable.run();
	}

}
