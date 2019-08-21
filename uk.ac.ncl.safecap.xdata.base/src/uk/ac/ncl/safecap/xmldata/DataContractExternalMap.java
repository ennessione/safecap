package uk.ac.ncl.safecap.xmldata;

public class DataContractExternalMap {
	private final String canonicalName;
	private final String templateDomain;
	private final String targetDomain;
	private final String templateRange;
	private final String targetRange;

	public DataContractExternalMap(String canonicalName, String templateDomain, String targetDomain, String templateRange,
			String targetRange) {
		this.canonicalName = canonicalName;
		this.templateDomain = templateDomain;
		this.targetDomain = targetDomain;
		this.templateRange = templateRange;
		this.targetRange = targetRange;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public String getTemplateDomain() {
		return templateDomain;
	}

	public String getTargetDomain() {
		return targetDomain;
	}

	public String getTemplateRange() {
		return templateRange;
	}

	public String getTargetRange() {
		return targetRange;
	}

}
