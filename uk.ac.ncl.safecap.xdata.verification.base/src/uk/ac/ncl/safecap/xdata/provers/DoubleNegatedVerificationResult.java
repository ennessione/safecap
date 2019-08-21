package uk.ac.ncl.safecap.xdata.provers;

public class DoubleNegatedVerificationResult extends VerificationResult {
	private final VerificationResult a;
	private final VerificationResult b;

	public DoubleNegatedVerificationResult(VerificationResult a, VerificationResult b) {
		super(arbitrate(a, b));
		this.a = a;
		this.b = b;
		setProperty(a.getProperty());
	}

	private static VerificationResult.RESULT arbitrate(VerificationResult a, VerificationResult b) {
		if (!a.isDefinite() || !b.isDefinite()) {
			return VerificationResult.RESULT.FAILED;
		}

		if (a.getProperty() != b.getProperty()) {
			return RESULT.FAILED;
		}

		if (a.kind == RESULT.VALID && b.kind == RESULT.INVALID) {
			return RESULT.VALID;
		} else {
			return RESULT.INVALID;
		}
	}

	@Override
	public String getProver() {
		return a.getProver();
	}

	@Override
	public String toString() {
		if (isDefinite()) {
			return getKind().name() + " DBL(" + a.getProver() + ": " + a.getTimeString() + "+" + b.getTimeString() + ")";
		}

		final StringBuilder builder = new StringBuilder();

		builder.append(getKind().name());
		builder.append(" [");
		builder.append(a.toString());
		builder.append(" ~ ");
		builder.append(b.toString());
		builder.append("]");

		return builder.toString();
	}

}
