package uk.ac.ncl.safecap.xmldata.normalisation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalisationPattern extends Normalisation {
	private final String myType;
	private final String template;
	private final Pattern pattern;

	public NormalisationPattern(String type, String pattern, String template) {
		super();
		this.pattern = Pattern.compile(pattern);
		this.template = template;
		myType = type;
	}

	@Override
	public String normalise(String literal, String type) {
		if (myType.equals(type)) {
			final Matcher matcher = pattern.matcher(literal);
			if (matcher.matches()) {
				String newLiteral = template;
				for (int i = 0; i < matcher.groupCount() + 1; i++) {
					final String value = matcher.group(i);
					newLiteral = newLiteral.replaceAll("\\$" + i, value);
				}

				return newLiteral;
			}
		}
		return null;
	}

}
