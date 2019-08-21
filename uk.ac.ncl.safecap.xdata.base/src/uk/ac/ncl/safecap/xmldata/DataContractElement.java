package uk.ac.ncl.safecap.xmldata;

import uk.ac.ncl.safecap.xmldata.types.XType;

public abstract class DataContractElement {
	private final XType domain;
	private final XType range;
	private final String description;

	public DataContractElement(String domain, String range, String description) throws DataConfigurationException {
		this.domain = domain != null ? XType.parse(domain) : null;
		this.range = range != null ? XType.parse(range) : null;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public boolean match(IXFunction f) {
		return match(f.getCanonicalName());
	}

	public abstract boolean match(String name);

	public abstract String getPreferredShortName(IXFunction f);

	public XType getDomain() {
		return domain;
	}

	public XType getRange() {
		return range;
	}
}
