package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.Collections;
import java.util.Random;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class GeneratorExclude extends BaseGenerator {
	public static final GeneratorExclude INSTANCE = new GeneratorExclude();

	private GeneratorExclude() {
	}

	@Override
	public IErrorInjector generate(SDAContext context, Random rangen, IXFunction function) {
		final XFunctionBasic ff = (XFunctionBasic) function;
		if (ff.domain().size() == 0)
			return null;
		final Object domain = ff.domain().toArray()[rangen.nextInt(ff.domain().size())];
		return new BaseInjection(getKind(), function, domain, Collections.emptyList());
	}

	@Override
	protected String getKind() {
		return "X";
	}

}
