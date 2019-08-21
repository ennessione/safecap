package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class ValidFlagUtil {
	private static final String VALID_FLAG = "Valid";

	public static Set<String> getPossiblyInvalidData(ISDADataProvider context, CLExpression expression) {

		final Set<String> result = new HashSet<>();

		for (final String i : expression.getFreeIdentifiers()) {
			final IXFunction f = context.getFunction(i);
			if (f != null) {
				final IXFunction validf = getValidFlagFunctionFor(context, f);
				if (validf != null && indicatesInvalidData(validf)) {
					result.add(i);
				}
			}
		}
		return result;
	}

	public static boolean hasPossiblyInvalidData(ISDADataProvider context, CLExpression expression) {
		for (final String i : expression.getFreeIdentifiers()) {
			final IXFunction f = context.getFunction(i);
			if (f != null) {
				final IXFunction validf = getValidFlagFunctionFor(context, f);
				if (validf != null) {
					return indicatesInvalidData(validf);
				}
			}
		}
		return false;
	}

	public static boolean hasPossiblyInvalidData(ISDADataProvider context, IXFunction f) {
		final IXFunction validf = getValidFlagFunctionFor(context, f);
		if (validf != null) {
			return indicatesInvalidData(validf);
		}
		return false;
	}

	private static boolean indicatesInvalidData(IXFunction f) {
		final XFunctionBasic ff = (XFunctionBasic) f;
		for (final Collection<Object> oo : ff.getMaps().values()) {
			for (final Object o : oo) {
				if (o instanceof Boolean && o.equals(Boolean.FALSE)) {
					return true;
				}
			}
		}
		return false;
	}

	public static IXFunction getValidFlagFunctionFor(ISDADataProvider context, String name) {
		final IXFunction self = context.getFunction(name);
		return getValidFlagFunctionFor(context, self);
	}

	public static IXFunction getValidFlagFunctionFor(ISDADataProvider context, IXFunction self) {
		if (self != null) {
			final String prefix = getNamePrefix(self.getCanonicalName());
			if (prefix != null) {
				if (prefix.length() > 0) {
					for (final String fn : context.getFunctionIds()) {
						final IXFunction ff = context.getFunction(fn);
						final String ff_prefix = getNamePrefix(ff.getCanonicalName());
						final String ff_suffix = getNameSuffix(ff.getCanonicalName());
						if (ff_prefix != null && ff_suffix != null && ff_suffix.equals(VALID_FLAG) && ff_prefix.equals(prefix)) {
							return ff;
						}
					}
				}
			}
		}
		return null;
	}

	private static String getNamePrefix(String name) {
		final int index = name.lastIndexOf('/');
		if (index >= 0) {
			return name.substring(0, index);
		}
		return null;
	}

	private static String getNameSuffix(String name) {
		final int index = name.lastIndexOf('/');
		if (index >= 0) {
			return name.substring(index + 1);
		}
		return null;
	}
}
