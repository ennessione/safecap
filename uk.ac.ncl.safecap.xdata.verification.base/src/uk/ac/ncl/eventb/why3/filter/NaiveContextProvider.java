package uk.ac.ncl.eventb.why3.filter;

import java.util.Collection;
import java.util.Collections;

public class NaiveContextProvider extends ContextProviderBase {
	public static final NaiveContextProvider INSTANCE = new NaiveContextProvider();

	private NaiveContextProvider() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Why3FilterableLemma> getInplaceLemmas() {
		return Collections.EMPTY_SET;
	}

	@Override
	public Collection<String> getTheoryImports() {
		return ALL_THEORIES;
	}

	@Override
	public FilterContext getFilterContext() {
		return null;
	}
}
