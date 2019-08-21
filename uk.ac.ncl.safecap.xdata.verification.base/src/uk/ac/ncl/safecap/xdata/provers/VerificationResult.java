package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.common.PropertyHolder;
import uk.ac.ncl.safecap.common.resources.CommonPlugin;

public class VerificationResult extends PropertyHolder {
	public static enum RESULT {
		VALID, INVALID, UNKNOWN, FAILED, CANCELLED, PANIC;

		public boolean isDefinite() {
			return this == VALID || this == INVALID;
		}

		public boolean isValid() {
			return this == VALID;
		}
	}

	public static final VerificationResult FAILED = new VerificationResult(VerificationResult.RESULT.FAILED);
	public static final VerificationResult UNKNOWN = new VerificationResult(VerificationResult.RESULT.UNKNOWN);

	
	public static VerificationResult.RESULT negate(VerificationResult a) {
		if (!a.isDefinite()) {
			return VerificationResult.RESULT.UNKNOWN;
		}

		if (a.kind == RESULT.VALID) {
			return RESULT.INVALID;
		} else {
			return RESULT.VALID;
		}
	}

	public static VerificationResult.RESULT combine(VerificationResult.RESULT... results) {
		VerificationResult.RESULT r = results[0];
		if (r == VerificationResult.RESULT.INVALID)
			return VerificationResult.RESULT.INVALID;
		
		for (int i = 1; i < results.length; i++) {
			switch (results[i]) {
			case UNKNOWN:
			case FAILED:
			case PANIC:
			case CANCELLED:
				r = VerificationResult.RESULT.UNKNOWN;
				break;
			case VALID:
				if (r != VerificationResult.RESULT.VALID)
					r = VerificationResult.RESULT.UNKNOWN;
				break;
			case INVALID:
				return VerificationResult.RESULT.INVALID;
			}
		}
		return r;
	}

	public static VerificationResult.RESULT arbitrate(VerificationResult... results) {
		VerificationResult.RESULT r = VerificationResult.RESULT.UNKNOWN;

		for (final VerificationResult x : results) {
			if (x != null) {
				switch (x.kind) {
				case UNKNOWN:
				case FAILED:
				case CANCELLED:
					break;
				case VALID:
					if (r == VerificationResult.RESULT.INVALID) {
						return VerificationResult.RESULT.PANIC;
					} else {
						r = x.kind;
					}
					break;
				case INVALID:
					if (r == VerificationResult.RESULT.VALID) {
						return VerificationResult.RESULT.PANIC;
					} else {
						r = x.kind;
					}
					break;
				default:
					r = x.kind;
				}
			}
		}

		return r;
	}

	VerificationResult.RESULT kind;
	String prover;
	long time;
	Throwable exception;
	ICondition property;

	public VerificationResult(VerificationResult.RESULT kind) {
		this.kind = kind;
	}

	public VerificationResult(VerificationResult.RESULT kind, String prover) {
		this.kind = kind;
		this.prover = prover;
	}

	public static VerificationResult getFailed(ICondition property) {
		return getFailed(property, null);
	}

	public static VerificationResult getFailed(ICondition property, String prover) {
		final VerificationResult result = new VerificationResult(RESULT.FAILED);
		result.setProperty(property);
		result.setProver(prover);
		return result;
	}

	public VerificationResult(VerificationResult.RESULT kind, Throwable exception) {
		this.kind = kind;
		this.exception = exception;
	}

	public ICondition getProperty() {
		return property;
	}

	public void setProperty(ICondition property) {
		this.property = property;
	}

	public long getTime() {
		return time;
	}

	public String getTimeString() {
		return CommonPlugin.formatter.format(time / 1000.0) + "s";
	}

	public static String getTimeString(int time) {
		return CommonPlugin.formatter.format(time / 1000.0) + "s";
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isDefinite() {
		return kind == VerificationResult.RESULT.VALID || kind == VerificationResult.RESULT.INVALID;
	}

	public boolean isValid() {
		return kind == VerificationResult.RESULT.VALID;
	}

	public VerificationResult.RESULT getKind() {
		return kind;
	}

	public String getProver() {
		return prover;
	}

	public void setProver(String prover) {
		this.prover = prover;
	}

	public Throwable getException() {
		return exception;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append(kind.name());

		if (prover != null) {
			builder.append(" " + prover);
		}

		if (time > 0) {
			builder.append(" " + getTimeString());
		}

		if (exception != null) {
			builder.append(" cause: " + exception.getMessage());
		}

		return builder.toString();
	}
}