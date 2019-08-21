package uk.ac.ncl.eventb.why3.filter;

public class Why3RawSexprLemma {
	private final String why3LemmaName;
	private final String why3LemmaBody;
	private final String why3Sexpr;

	public Why3RawSexprLemma(String why3LemmaName, String why3LemmaBody, String why3Sexpr) {
		this.why3LemmaBody = why3LemmaBody;
		this.why3LemmaName = why3LemmaName;
		this.why3Sexpr = why3Sexpr;
	}

	public String getWhy3LemmaBody() {
		return why3LemmaBody;
	}

	public String getWhy3LemmaName() {
		return why3LemmaName;
	}

	public String getWhy3Sexpr() {
		return why3Sexpr;
	}

	@Override
	public String toString() {
		return why3LemmaName + " ::= " + why3Sexpr + " ([" + why3LemmaBody + "])";
	}

}
