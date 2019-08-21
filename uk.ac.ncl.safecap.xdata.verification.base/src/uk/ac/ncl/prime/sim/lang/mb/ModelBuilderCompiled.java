package uk.ac.ncl.prime.sim.lang.mb;

import java.util.ArrayList;
import java.util.List;

import gnu.jel.CompilationException;
import gnu.jel.CompiledExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class ModelBuilderCompiled extends ModelSolverCompiled {
	private final CLExpression expression;
	private CompiledExpression expressionCompiled;
	private final List<Object> result;

	public ModelBuilderCompiled(SDARuntimeExecutionContext ctx, CLExpression predicate, CLExpression expression,
			List<CLParameter> variables) throws CLExecutionException, InvalidSetOpException {
		super(ctx, predicate, variables);
		this.expression = expression;
		result = new ArrayList<>();
	}

	@Override
	public void prepare() throws CLNonExecutableException {
		try {
			super.prepare();
			expressionCompiled = expression.prepareCompiled(getTypingContext().getRuntimeExecutionContext());
		} catch (final CompilationException e) {
			expressionCompiled = null;
			throw new CLNonExecutableException(expression, e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BSet<Object> getResult() {
		return new BSet(result);
	}

	@Override
	protected boolean solutionFound(int... index) throws CLExecutionException {
		try {
			final Object value = expressionCompiled.evaluate(context);
			if (!result.contains(value)) {
				result.add(value);
			}
		} catch (final Throwable e) {
			// System.err.println("[MBC] failed evaluating expression (compiled) " +
			// expression + ": " + e.toString());
			// e.printStackTrace();
			throw new CLExecutionException(e.toString());
		}
		return false;
	}

	@Override
	public Object execute() throws CLExecutionException {
		try {
			result.clear();
			// long start = System.currentTimeMillis();
			super.solve();
			// long done = System.currentTimeMillis();
			// System.out.println("Solve time: " + (done - start));

			return getResult();
		} catch (final InvalidSetOpException e) {
			throw new CLExecutionException(e.getMessage());
		}
	}

}
