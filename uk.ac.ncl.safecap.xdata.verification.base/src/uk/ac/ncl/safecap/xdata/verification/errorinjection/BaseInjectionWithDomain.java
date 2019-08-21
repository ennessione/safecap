package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.Collections;
import java.util.List;

import uk.ac.ncl.safecap.xmldata.IXFunction;

public class BaseInjectionWithDomain extends BaseInjection {
	private final List<Object> extra;

	protected BaseInjectionWithDomain(String kind, IXFunction function, Object domain, List<Object> replacement, List<Object> extra) {
		super(kind, function, domain, replacement);
		this.extra = extra;
	}

	@Override
	public List<Object> getExtraDomain(IXFunction function) {
		if (function == getFunction()) {
			return extra;
		} else {
			return Collections.emptyList();
		}
	}
}
