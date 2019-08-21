package uk.ac.ncl.safecap.common.report;

public class SRSection extends SRContainer {
	public static final String HAS_INDEX = "hasIndex";

	public SRSection() {

	}

	public SRSection(String title) {
		set(SRPart.TITLE, title);
		set(HAS_INDEX, true);
	}

	public SRSection(String title, boolean hasIndex) {
		set(SRPart.TITLE, title);
		set(HAS_INDEX, hasIndex);
	}
}
