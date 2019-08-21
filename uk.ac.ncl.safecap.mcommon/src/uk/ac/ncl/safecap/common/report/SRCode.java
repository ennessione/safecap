package uk.ac.ncl.safecap.common.report;

public class SRCode extends SRContent {

	public SRCode() {
	}

	public SRCode(String code) {
		super.add(new SFPlain(code));
	}

	public StringBuilder getBuilder() {
		return getChildren().get(0).getBuilder();
	}
}
