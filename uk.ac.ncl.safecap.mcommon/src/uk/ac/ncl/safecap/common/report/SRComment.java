package uk.ac.ncl.safecap.common.report;

public class SRComment extends SRContent {

	public SRComment() {
	}

	public SRComment(String code) {
		super.add(new SFPlain(code));
	}

	public StringBuilder getBuilder() {
		return getChildren().get(0).getBuilder();
	}

	@Override
	public SRContent add(SRFormatted child) {
		throw new IllegalArgumentException();
	}
}
