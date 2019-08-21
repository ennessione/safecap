package uk.ac.ncl.safecap.controltable.views;

import safecap.model.Rule;

public class RuleWrap {
	public final Rule rule;
	public final Object container;

	public RuleWrap(Rule rule, Object container) {
		this.rule = rule;
		this.container = container;
	}
}
