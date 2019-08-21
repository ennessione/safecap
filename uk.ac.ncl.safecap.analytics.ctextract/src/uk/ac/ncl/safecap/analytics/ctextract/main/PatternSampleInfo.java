package uk.ac.ncl.safecap.analytics.ctextract.main;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;

public class PatternSampleInfo {
	private final Object domain;
	private final CTEPartBase pattern;
	private final String sample;

	public PatternSampleInfo(Object domain, CTEPartBase pattern, String sample) {
		this.domain = domain;
		this.pattern = pattern;
		this.sample = sample;
	}

	public Object getDomain() {
		return domain;
	}

	public CTEPartBase getPattern() {
		return pattern;
	}

	public String getSample() {
		return sample;
	}
}
