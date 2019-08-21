package uk.ac.ncl.safecap.analytics.ctextract.main;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;
import uk.ac.ncl.safecap.analytics.ctextract.core.RWERule;
import uk.ac.ncl.safecap.analytics.ctextract.parser.RWEBuilder;
import uk.ac.ncl.safecap.cteparser.TEContext;

public class CTRewriteRule extends CTRule {
	private transient RWERule rule;

	public RWERule computeRWERule() {
		if (rule != null || context != null && !context.getErrors().isEmpty()) {
			return rule;
		}
		context = new TEContext();
		final CTEPartBase left = RWEBuilder.build(getPattern(), context, true);
		if (left != null && context.getErrors().isEmpty()) {
			final CTEPartBase right = RWEBuilder.build(getTemplate(), context, false);
			if (right != null && context.getErrors().isEmpty()) {
				rule = new RWERule(left, right);
				return rule;
			}
		}

		return null;
	}

	@Override
	public void setPattern(String pattern) {
		rule = null;
		if (context != null) {
			context.clear();
		}
		super.setPattern(pattern);
	}

	@Override
	public void setTemplate(String template) {
		rule = null;
		if (context != null) {
			context.clear();
		}
		super.setTemplate(template);
	}

	@Override
	public String toString() {
		if (rule == null) {
			return "?";
		} else {
			return rule.toString();
		}
	}

}
