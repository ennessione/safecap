package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.util.List;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPartBase;

public class PatternInfo {
	public CTEPartBase pattern;
	public List<PatternSampleInfo> samples;

	public PatternInfo(CTEPartBase pattern, List<PatternSampleInfo> samples) {
		this.pattern = pattern;
		this.samples = samples;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (pattern == null ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PatternInfo other = (PatternInfo) obj;
		if (pattern == null) {
			if (other.pattern != null) {
				return false;
			}
		} else if (!pattern.equals(other.pattern)) {
			return false;
		}
		return true;
	}

}