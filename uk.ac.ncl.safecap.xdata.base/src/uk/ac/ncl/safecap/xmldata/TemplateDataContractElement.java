package uk.ac.ncl.safecap.xmldata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateDataContractElement extends DataContractElement {
	private Pattern pattern;

	public TemplateDataContractElement(String template, String domain, String range, String description) throws DataConfigurationException {
		super(domain, range, description);
		try {
			pattern = Pattern.compile(template);
		} catch (final Throwable e) {
			throw new DataConfigurationException("Invalid template \"" + template + "\" in element contract: " + e.getMessage());
		}
	}

	@Override
	public boolean match(String name) {
		final Matcher matcher = pattern.matcher(name);
		final boolean result = matcher.matches();
		return result;
	}

	@Override
	public String getPreferredShortName(IXFunction f) {
		return f.getCanonicalName();
	}

}
