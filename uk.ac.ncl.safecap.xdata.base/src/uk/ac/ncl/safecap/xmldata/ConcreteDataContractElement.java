package uk.ac.ncl.safecap.xmldata;

import uk.ac.ncl.safecap.common.RELATION_KIND;

public class ConcreteDataContractElement extends DataContractElement {
	private final String canonicalName;
	private final String shortName;
	private final String tag;
	private final RELATION_KIND kind;
	private final boolean notEmpty;

	public ConcreteDataContractElement(String canonicalName, String shortName, String domain, String range, String kind, String description,
			String tag, boolean required) throws DataConfigurationException {
		super(domain, range, description);
		this.canonicalName = canonicalName;
		this.shortName = shortName;
		this.kind = RELATION_KIND.fromValue(kind);
		notEmpty = required;
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public boolean isNotEmpty() {
		return notEmpty;
	}

	public RELATION_KIND getKind() {
		return kind;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public String getShortName() {
		return shortName;
	}

	@Override
	public boolean match(String name) {
		return name.equals(canonicalName);
	}

	@Override
	public String getPreferredShortName(IXFunction f) {
		return shortName;
	}
}
