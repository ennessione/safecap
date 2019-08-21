package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class GeneratorSwap extends BaseGenerator {
	public static final GeneratorSwap INSTANCE = new GeneratorSwap();

	private GeneratorSwap() {
	}

	@Override
	public IErrorInjector generate(SDAContext context, Random rangen, IXFunction function) {
		final XFunctionBasic ff = (XFunctionBasic) function;
		final Object domain = ff.domain().toArray()[rangen.nextInt(ff.domain().size())];
		final List<Object> data = ff.get(domain);
		if (data.size() < 2) {
			return null;
		}
		final int index1 = rangen.nextInt(data.size());
		int index2 = rangen.nextInt(data.size());
		while (index2 == index1) {
			index2 = rangen.nextInt(data.size());
		}

		final List<Object> replacement = new ArrayList<>(data);
		final Object v0 = replacement.get(index2);
		replacement.set(index2, replacement.get(index1));
		replacement.set(index1, v0);
		return new BaseInjection(getKind(), function, domain, replacement);
	}

	@Override
	protected String getKind() {
		return "S";
	}

}
