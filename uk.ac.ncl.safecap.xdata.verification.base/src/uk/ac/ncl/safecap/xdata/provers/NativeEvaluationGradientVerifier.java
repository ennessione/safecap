package uk.ac.ncl.safecap.xdata.provers;

import java.util.Collection;
import java.util.HashSet;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLForallExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolver;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolverNative;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.IPropertyVerifier;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.RuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public class NativeEvaluationGradientVerifier implements IPropertyVerifier {
	public static final NativeEvaluationGradientVerifier INSTANCE = new NativeEvaluationGradientVerifier();

	@Override
	public VerificationResult verify(ICondition property, IVerificationProgressMonitor monitor, boolean negated) {
		final long time = System.currentTimeMillis();
		try {
			CLExpression predicate = property.getGoal();
			if (negated) {
				predicate = CLUtils.negate(predicate);
			}

			if (predicate instanceof CLForallExpression) {
				final SDAContext context = property.getContext();
				final TypingContext typing = context.getRootRuntimeContext().getRootContext();
				final RuntimeExecutionContext model = context.getRootRuntimeContext();
				final CLForallExpression forall = (CLForallExpression) predicate;
				final int value = getValue(forall, typing, model);
				final VerificationResult.RESULT code = value == 0 ? VerificationResult.RESULT.VALID : VerificationResult.RESULT.INVALID;
				final VerificationResult result = new VerificationResult(code);
				result.setTime(System.currentTimeMillis() - time);
				result.setProperty("gradient", value);
				result.setProver("native-gradient");
				return result;
			} else {
				return NativeEvaluationVerifier.INSTANCE.verify(property, monitor, negated);
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			final VerificationResult result = new VerificationResult(VerificationResult.RESULT.FAILED, e);
			result.setTime(System.currentTimeMillis() - time);
			result.setProver("native-gradient");
			return result;
		}
	}

	public int getValue(CLForallExpression forall, TypingContext typing, RuntimeExecutionContext context)
			throws CLExecutionException, InvalidSetOpException {

		final TypingContext ctx0 = new TypingContext(typing);

		final Collection<String> vars = new HashSet<>(forall.getBoundIdentifiers().size());
		for (final CLParameter id : forall.getBoundParameters()) {
			ctx0.addSymbol(id.getName(), id.getTypeSafely(typing), SYMBOL_CLASS.IDENTIFIER);
			vars.add(id.getName());
		}

		final GradientNativeModelSolverUniversal solver = new GradientNativeModelSolverUniversal(ctx0, forall.getBody(), vars);

		solver.solve();

		if (solver.getSolution() == ModelSolver.SOLUTION.UNKNOWN) {
			throw new CLNonExecutableException(forall);
		} else {
			return solver.violations;
		}
	}

	static class GradientNativeModelSolverUniversal extends ModelSolverNative {
		private int violations = 0;

		public GradientNativeModelSolverUniversal(TypingContext typingContext, CLExpression predicate, Collection<String> variables) {
			super(typingContext, CLUtils.negate(predicate), variables);
		}

		@Override
		public void solve() throws CLExecutionException, InvalidSetOpException {
			setSolution(SOLUTION.TRUE);
			super.solve();
		}

		@Override
		public boolean solutionFound(int[] index) {
			setSolution(SOLUTION.FALSE);
			violations++;
			return false;
		}

	}
}
