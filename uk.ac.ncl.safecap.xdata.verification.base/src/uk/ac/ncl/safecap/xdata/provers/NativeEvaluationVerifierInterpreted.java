package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class NativeEvaluationVerifierInterpreted implements IPropertyVerifier {
	public static final NativeEvaluationVerifierInterpreted INSTANCE = new NativeEvaluationVerifierInterpreted();

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		final long time = System.currentTimeMillis();
		try {
			final SDAContext context = property.getContext();
			final SDARuntimeExecutionContext model = context.getRootRuntimeContext();
			if (property.getGoal() == null) {
				return VerificationResult.FAILED;
			}

			CLExpression predicate = property.getGoal();
			if (negated) {
				predicate = CLUtils.negate(predicate);
			}
			final Object v = predicate.getValueInterpreted(model);

			VerificationResult.RESULT code;
			if (v instanceof Boolean) {
				final Boolean vb = (Boolean) v;
				if (vb) {
					code = VerificationResult.RESULT.VALID;
				} else {
					code = VerificationResult.RESULT.INVALID;
				}
			} else {
				code = VerificationResult.RESULT.UNKNOWN;
			}
			final VerificationResult result = new VerificationResult(code);
			result.setTime(System.currentTimeMillis() - time);
			result.setProver("native");
			return result;
		} catch (final Throwable e) {
			e.printStackTrace();
			final VerificationResult result = new VerificationResult(VerificationResult.RESULT.FAILED, e);
			result.setTime(System.currentTimeMillis() - time);
			result.setProver("native");
			return result;
		}
	}
}
