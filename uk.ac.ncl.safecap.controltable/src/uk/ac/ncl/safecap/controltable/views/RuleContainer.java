package uk.ac.ncl.safecap.controltable.views;

import java.util.ArrayList;
import java.util.List;

import safecap.model.ControlLogic;
import safecap.model.Line;
import safecap.model.Route;
import safecap.model.Rule;
import uk.ac.ncl.safecap.misc.core.RuleUtil;

public class RuleContainer {
	public Line line;
	public Route route;

	public RuleContainer(Line line, Route route) {
		super();
		this.line = line;
		this.route = route;
	}

	public List<RuleWrap> getWraps() {
		final ControlLogic logic = RuleUtil.getControlLogic(route, line);
		final List<Rule> rules = RuleUtil.getRouteAspectRules(logic);
		final List<RuleWrap> wraps = new ArrayList<>();
		for (final Rule rule : rules) {
			wraps.add(new RuleWrap(rule, this));
		}
		return wraps;
	}
}