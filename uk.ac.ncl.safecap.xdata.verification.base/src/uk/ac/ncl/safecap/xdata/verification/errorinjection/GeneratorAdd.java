package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.safecap.common.RELATION_KIND;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class GeneratorAdd extends BaseGenerator {
	public static final GeneratorAdd INSTANCE = new GeneratorAdd();

	private GeneratorAdd() {
	}

	@Override
	public IErrorInjector generate(SDAContext context, Random rangen, IXFunction function) {
		final XFunctionBasic ff = (XFunctionBasic) function;
		final XType domainType = ((XRelationType) function.getFunctionType()).getDomain();
		Object domain;

		final XRelationType rtype = (XRelationType) ff.getFunctionType();
		final RELATION_KIND kind = rtype.getKind();
		if (kind.isFunction()) {
			if (domainType instanceof XEnumType) {
				final XEnumType ed = (XEnumType) domainType;
				final List<Object> domainSet = new ArrayList<>(context.getEnumMembers(ed));
				domainSet.removeAll(ff.domain());
				domain = pickRandomElement(domainSet, rangen);
			} else {
				return null;
			}
		} else {
			if (domainType instanceof XEnumType) {
				final XEnumType ed = (XEnumType) domainType;
				domain = pickRandomElement(context.getEnumMembers(ed), rangen);
			} else {
				if (ff.domain().isEmpty()) {
					return null;
				}
				domain = ff.domain().toArray()[rangen.nextInt(ff.domain().size())];
			}
		}

		List<Object> data = ff.get(domain);
		if (data == null) {
			data = Collections.emptyList();
		}

		List<Object> extra = Collections.emptyList();
		boolean found = false;
		for (final Object k : ff.getMaps().keySet()) {
			if (k.equals(domain)) {
				found = true;
				break;
			}
		}

		if (!found) {
			extra = Collections.singletonList(domain);
		}

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
			return new BaseInjectionWithDomain(getKind(), function, domain, replacement, extra);
		} else if (range instanceof XProductType) {
			final XProductType ptype = (XProductType) range;
			final XEnumType enumt = (XEnumType) ptype.get(1);
			final Object newValue = pickReplacementValue(context.getEnumMembers(enumt), data, rangen);
			if (newValue == null) {
				return null;
			}
			final List<Object> replacement = new ArrayList<>(data);
			if (data.size() > 0) {
				final ValueList last = (ValueList) data.get(data.size() - 1);
				final ValueList repl = new ValueList(last.get(0) instanceof Integer ? (Integer) last.get(0) + 1 : last.get(0), newValue);
				repl.setSet(last.isSet());
				repl.setType(last.getType());
				replacement.add(repl);
			} else {
				final ValueList repl = new ValueList(0, newValue);
				repl.setType(range);
				replacement.add(repl);
			}

			return new BaseInjectionWithDomain(getKind(), function, domain, replacement, extra);
		}

		return null;

	}

	@Override
	protected String getKind() {
		return "A";
	}

}
