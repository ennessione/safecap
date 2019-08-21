package uk.ac.ncl.eventb.why3.filter;

public class RankedLemma {
	private final double rank;
	private final Why3FilterableLemma lemma;

	public RankedLemma(double rank, Why3FilterableLemma lemma) {
		this.rank = rank;
		this.lemma = lemma;
	}

	public double getRank() {
		return rank;
	}

	public Why3FilterableLemma getLemma() {
		return lemma;
	}

	@Override
	public String toString() {
		return "RankedLemma [rank=" + rank + ", lemma=" + lemma + "]";
	}

}
