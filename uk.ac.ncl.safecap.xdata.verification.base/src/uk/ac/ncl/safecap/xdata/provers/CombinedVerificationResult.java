package uk.ac.ncl.safecap.xdata.provers;

import java.util.Arrays;
import java.util.List;

public class CombinedVerificationResult extends VerificationResult {
	private final List<VerificationResult> reports;

	public CombinedVerificationResult(VerificationResult... results) {
		super(arbitrate(results));
		reports = Arrays.asList(results);
		long time = 0;
		for (final VerificationResult r : results) {
			if (r != null) {
				time += r.getTime();
			}
		}
		setTime(time);

		final StringBuilder prover = new StringBuilder();
		for (int i = 0; i < results.length; i++) {
			final VerificationResult r = results[i];
			if (r != null) {
				if (i > 0) {
					prover.append(" and ");
				}
				prover.append(r.getProver());
				prover.append("/");
				prover.append(r.getKind().toString());
				prover.append("/");
			}
		}

		setProver(prover.toString());
		setProperty(reports.get(0).getProperty());
	}

	@Override
	public String toString() {
		if (isDefinite()) {
			return getKind().name() + " (" + getProver() + ": " + getTimeString() + ")";
		}

		final StringBuilder builder = new StringBuilder();

		builder.append(getKind().name());
		for (final VerificationResult vr : reports) {
			builder.append(" ");
			builder.append(vr.toString());
		}

		return builder.toString();
	}
}
