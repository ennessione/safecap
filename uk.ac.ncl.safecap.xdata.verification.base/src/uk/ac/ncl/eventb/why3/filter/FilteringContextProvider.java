package uk.ac.ncl.eventb.why3.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FilteringContextProvider extends ContextProviderBase {
	private final FilterContext filterContext;
	private final GlobalLemmataLibrary library;
	private List<Why3FilterableLemma> filtered;

	public FilteringContextProvider(GlobalLemmataLibrary library, FilterContext filterContext) {
		this.filterContext = filterContext;
		this.library = library;
	}

	private boolean doFiltering() {
		return library != null && FilterGlobals.isFilterEnabled();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Why3FilterableLemma> getInplaceLemmas() {
		if (library == null) {
			return Collections.EMPTY_SET;
		}

		if (doFiltering()) {
			if (filtered == null) {
				computeInplaceTheories();
			}

			if (filtered != null && filtered.size() > 0) {
				return filtered;
			}
		} else {
			if (library.getLemmata().size() > 0) {
				return library.getLemmata();
			}
		}

		return Collections.EMPTY_SET;
	}

	private void computeInplaceTheories() {
		try {
			filtered = FilterGlobals.lemmatFilter(library, filterContext);

			if (filtered.size() > 0) {
				for (final Why3FilterableLemma l : filtered) {
					filterContext.injectExtraLemmataFilter(l.getBitSet());
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			filtered = null;
		}
	}

	@Override
	public Collection<String> getTheoryImports() {
		if (doFiltering()) {
			computeInplaceTheories();
			return library.getTheoryDependencies(filterContext);
		} else {
			return NaiveContextProvider.INSTANCE.getTheoryImports();
		}
	}

	@Override
	public FilterContext getFilterContext() {
		return filterContext;
	}

}
