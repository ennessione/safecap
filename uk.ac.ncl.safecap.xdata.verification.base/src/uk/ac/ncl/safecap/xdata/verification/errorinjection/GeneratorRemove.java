package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class GeneratorRemove extends BaseGenerator {
	public static final GeneratorRemove INSTANCE = new GeneratorRemove();

	private GeneratorRemove() {
	}

	@Override
	public IErrorInjector generate(SDAContext context, Random rangen, IXFunction function) {
		final XFunctionBasic ff = (XFunctionBasic) function;
		final Object domain = ff.domain().toArray()[rangen.nextInt(ff.domain().size())];
		final List<Object> data = ff.get(domain);
		if (data == null || data.isEmpty())
			return null;
		final int index = rangen.nextInt(data.size());
		final List<Object> replacement = new ArrayList<>(data);
		replacement.remove(index);
		
		return new BaseInjection(getKind(), function, domain, replacement);
	}

	@Override
	protected String getKind() {
		return "R";
	}

}
