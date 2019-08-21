package uk.ac.ncl.safecap.xmldata.normalisation;

public class NormalisationNaive extends Normalisation {
	public static final NormalisationNaive INSTANCE = new NormalisationNaive();

	private NormalisationNaive() {
	}

	@Override
	public String normalise(String literal, String type) {
		if ("Signal".equals(type) || "Track".equals(type)) {
			final int bracket_open = literal.indexOf('(');
			final int bracket_close = literal.indexOf(')');
			if (bracket_open > 0 && bracket_open < bracket_close) {
				literal = literal.substring(0, bracket_open);
				final int slash = literal.indexOf('/');
				if (slash > 0) {
					literal = literal.substring(0, slash);
				}
				final int dash = literal.indexOf('-');
				if (dash > 0) {
					literal = literal.substring(0, dash);
				}
			}
		}
		return null;
	}

}
