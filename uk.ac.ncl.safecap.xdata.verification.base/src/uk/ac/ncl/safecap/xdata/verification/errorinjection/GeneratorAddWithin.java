package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class GeneratorAddWithin extends BaseGenerator {
	public static final GeneratorAddWithin INSTANCE = new GeneratorAddWithin();

	private GeneratorAddWithin() {
	}

	@Override
	public IErrorInjector generate(SDAContext context, Random rangen, IXFunction function) {
		final XFunctionBasic ff = (XFunctionBasic) function;
		final Object domain = ff.domain().toArray()[rangen.nextInt(ff.domain().size())];
		final List<Object> data = ff.get(domain);
		final XType range = ((XRelationType) function.getFunctionType()).getRange();

		// assert range instanceof XEnumType || range instanceof XProductType;

		if (range instanceof XEnumType) {
			final XEnumType enumt = (XEnumType) range;
			final Object newValue = pickReplacementValue(context.getEnumMembers(enumt), data, rangen);
			if (newValue == null) {
				return null;
			}
			final List<Object> replacement = new ArrayList<>(data);
			replacement.add(newValue);
			return new BaseInjection(getKind(), function, domain, replacement);
		} else if (range instanceof XProductType) {
			final XProductType ptype = (XProductType) range;
			final XEnumType enumt = (XEnumType) ptype.get(1);
			final Object newValue = pickReplacementValue(context.getEnumMembers(enumt), data, rangen);
			if (newValue == null) {
				return null;
			}
			final List<Object> replacement = new ArrayList<>(data);
			final ValueList last = (ValueList) data.get(data.size() - 1);
			final ValueList repl = new ValueList(last.get(0) instanceof Integer ? (Integer) last.get(0) + 1 : last.get(0), newValue);
			repl.setSet(last.isSet());
			repl.setType(last.getType());
			replacement.add(repl);
			return new BaseInjection(getKind(), function, domain, replacement);
		}

		return null;

	}

	@Override
	protected String getKind() {
		return "A";
	}

}
