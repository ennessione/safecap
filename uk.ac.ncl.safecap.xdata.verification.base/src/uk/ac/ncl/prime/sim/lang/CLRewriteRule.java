package uk.ac.ncl.prime.sim.lang;

import java.util.Map;

import uk.ac.ncl.prime.sim.lang.typing.TypingContext;

public class CLRewriteRule {
	private final String name;
	private final CLExpression template;
	private final CLExpression pattern;

	public CLRewriteRule(String name, CLExpression template, CLExpression pattern) {
		this.template = template;
		this.pattern = pattern;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public RewriteRuleState isApplicable(CLExpression target, Object userData, TypingContext context) {
		final Map<String, CLExpression> map = target.match(template, context);
		if (map == null) {
			return null;
		} else {
			return new RewriteRuleState(this, map, userData);
		}
	}

	protected CLExpression apply(Map<String, CLExpression> map) {
		return pattern.rewrite(map);
	}

	public class RewriteRuleState {
		protected Map<String, CLExpression> map;
		protected CLRewriteRule rule;
		private final Object userData;

		public RewriteRuleState(CLRewriteRule rule, Map<String, CLExpression> map, Object userData) {
			this.rule = rule;
			this.map = map;
			this.userData = userData;
		}

		public Object getUserData() {
			return userData;
		}

		public CLExpression apply() {
			return rule.apply(map);
		}

		public String getName() {
			return rule.getName();
		}
	}

}
