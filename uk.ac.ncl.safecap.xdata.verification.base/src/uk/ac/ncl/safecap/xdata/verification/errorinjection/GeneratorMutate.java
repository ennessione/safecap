package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class GeneratorMutate extends BaseGenerator {
	public static final GeneratorMutate INSTANCE = new GeneratorMutate();

	private GeneratorMutate() {
	}

	@Override
	public IErrorInjector generate(SDAContext context, Random rangen, IXFunction function) {
		final XFunctionBasic ff = (XFunctionBasic) function;
		final Object domain = ff.domain().toArray()[rangen.nextInt(ff.domain().size())];
		final List<Object> data = ff.get(domain);
		final int index = rangen.nextInt(data.size());
		final XType range = ((XRelationType) function.getFunctionType()).getRange();

		// assert range instanceof XEnumType || range instanceof XProductType;

		if (range instanceof XEnumType) {
			final XEnumType enumt = (XEnumType) range;
			final Object replacementValue = pickReplacementValue(context.getEnumMembers(enumt), data, rangen);
			if (replacementValue == null) {
				return null;
			}

			final List<Object> replacement = new ArrayList<>(data);
			replacement.set(index, replacementValue);
			return new BaseInjection(getKind(), function, domain, replacement);
		} else if (range instanceof XProductType) {
			final XProductType ptype = (XProductType) range;
			final XEnumType enumt = (XEnumType) ptype.get(1);
			final Object replacementValue = pickReplacementValue(context.getEnumMembers(enumt), data, rangen);
			if (replacementValue == null) {
				return null;
			}

			final List<Object> replacement = new ArrayList<>(data);
			final ValueList original = (ValueList) data.get(index);
			final ValueList repl = new ValueList(original.get(0), replacementValue);
			repl.setSet(original.isSet());
			repl.setType(original.getType());
			replacement.set(index, repl);
			return new BaseInjection(getKind(), function, domain, replacement);
		} else if (range instanceof XIntegerType) {
			final int intValue = Integer.parseInt(ff.get(domain).get(0).toString());
			final int spread = (int) Math.ceil(intValue * 2.0);
			final int bottom = intValue - spread;
			final int top = intValue + spread;
			final int replacementValue = rangen.nextInt(top - bottom + 1) + bottom;
			final List<Object> replacement = new ArrayList<>(data);
			replacement.set(index, replacementValue);
			return new BaseInjection(getKind(), function, domain, replacement);
		}

		return null;

	}

	@Override
	protected String getKind() {
		return "M";
	}

}
