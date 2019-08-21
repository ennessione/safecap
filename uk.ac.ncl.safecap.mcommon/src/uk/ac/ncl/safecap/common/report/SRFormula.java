package uk.ac.ncl.safecap.common.report;

public class SRFormula extends SRContent {

	public SRFormula() {
	}

	public SRFormula(String code) {
		super.add(new SFPlain(code));
	}

	@Override
	public SRContent add(SRFormatted child) {
		throw new IllegalArgumentException();
	}
}
