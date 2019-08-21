package uk.ac.ncl.safecap.xmldata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStringRewrite implements IStringRewriter {
	private final String target;
	private Pattern pattern;

	public RegexStringRewrite(String template, String target) {
		this.target = target;

		if (template != null && target != null && template.trim().length() > 0 && target.trim().length() > 0) {
			pattern = Pattern.compile(template);
		}
	}

	@Override
	public String rewrite(String source) {
		if (pattern == null) {
			return source;
		}

		final Matcher matcher = pattern.matcher(source);
		if (matcher.matches()) {
			String result = target;
			for (int i = 1; i < matcher.groupCount() + 1; i++) {
				final String value = matcher.group(i);
				result = result.replaceAll("\\$" + i, value);
			}
			return result;
		}
		return source;
	}

}
