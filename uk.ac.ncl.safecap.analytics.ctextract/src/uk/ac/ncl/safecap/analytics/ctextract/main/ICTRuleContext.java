package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.util.Collection;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;

public interface ICTRuleContext<T extends CTRule> {
	boolean isReady(T rule);

	int span(T rule);

	Collection<T> getRulesLocal();

	Collection<T> getRulesGlobal();

	CTEPartBase apply(T rule, CTEPartBase target);
}
