package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.List;

import uk.ac.ncl.safecap.xmldata.IXFunction;

public interface IErrorInjector {
	List<Object> getExtraDomain(IXFunction function);

	List<Object> injectError(IXFunction f, Object domain);

}
