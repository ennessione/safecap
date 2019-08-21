package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.safecap.xmldata.IXFunction;

public class SinglePointInjector implements IErrorInjectorManager {
	private final IErrorInjector injection;

	public SinglePointInjector(IErrorInjector injection) {
		this.injection = injection;
	}

	@Override
	public List<Object> injectError(IXFunction f, Object domain) {
		final List<Object> r = injection.injectError(f, domain);
		if (r == null) {
			return f.get(domain);
		} else {
			return r;
		}
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
		return 1;
	}

	@Override
	public IErrorInjector getCurrentInjection() {
		return injection;
	}

	@Override
	public List<Object> getExtraDomain(IXFunction function) {
		return Collections.emptyList();
	}
}
