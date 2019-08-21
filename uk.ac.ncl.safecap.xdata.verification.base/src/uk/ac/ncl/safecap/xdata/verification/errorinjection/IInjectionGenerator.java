package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.Random;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;

public interface IInjectionGenerator {
	IErrorInjector generate(SDAContext context, Random rangen, IXFunction function);
}
