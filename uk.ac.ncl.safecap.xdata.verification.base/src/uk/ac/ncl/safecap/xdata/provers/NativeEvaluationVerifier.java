package uk.ac.ncl.safecap.xdata.provers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class NativeEvaluationVerifier implements IPropertyVerifier {
	public static final NativeEvaluationVerifier INSTANCE = new NativeEvaluationVerifier();

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

			ExecutorService exec = Executors.newSingleThreadExecutor();

			final CLExpression _predicate = predicate;

			final Object v = exec.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return _predicate.getValue(model);
				}

			}).get(10, TimeUnit.SECONDS);

			exec.shutdown();
			exec = null;
			
			// Object v = exec.submit(job).get(timeout, timeUnit);
			// Object v = predicate.getValue(model);

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
			//e.printStackTrace();
			final VerificationResult result = new VerificationResult(VerificationResult.RESULT.FAILED, e);
			result.setTime(System.currentTimeMillis() - time);
			result.setProver("native");
			return result;
		}
	}
}
