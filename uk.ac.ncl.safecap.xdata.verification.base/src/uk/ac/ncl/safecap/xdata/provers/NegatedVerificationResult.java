package uk.ac.ncl.safecap.xdata.provers;

public class NegatedVerificationResult extends VerificationResult {
	private final VerificationResult a;

	public NegatedVerificationResult(VerificationResult a) {
		super(negate(a));
		this.a = a;
		setProperty(a.getProperty());
	}

	@Override
	public String getProver() {
		return a.getProver();
	}

	@Override
	public String toString() {
		return getKind().name() + " NEG(" + a.getProver() + ": " + a.getTimeString() + ")";
	}

}
