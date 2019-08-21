package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.safecap.xmldata.IXFunction;

public class NullInjector implements IErrorInjectorManager {
	public static final NullInjector INSTANCE = new NullInjector();

	private NullInjector() {
	}

	@Override
	public List<Object> injectError(IXFunction f, Object domain) {
		return f.get(domain);
	}

	@Override
	public boolean nextExperimentStep() {
		return false;
	}

	@Override
	public List<IXFunction> getFunctions() {
		return Collections.emptyList();
	}

	@Override
	public int getPoints() {
		return 0;
	}

	@Override
	public IErrorInjector getCurrentInjection() {
		return null;
	}

	@Override
	public List<Object> getExtraDomain(IXFunction function) {
		return Collections.emptyList();
	}
}
