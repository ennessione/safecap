package uk.ac.ncl.prime.sim.lang.mb;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.CLValuationContext;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class ModelSolverSetComprehensionNative extends ModelSolverNative {
	private final CLExpression expression;
	private final List<Object> result;

	public ModelSolverSetComprehensionNative(TypingContext typingContext, CLExpression predicate, CLExpression expression,
			List<CLParameter> variables) {
		super(typingContext, predicate, variables);
		this.expression = expression;
		result = new ArrayList<>();
	}

	public BSet<Object> getResult() {
		return new BSet<>(result);
	}

	@Override
	public void solve() throws CLExecutionException, InvalidSetOpException {
		setSolution(SOLUTION.TRUE);
		result.clear();
		super.solve();
	}

	@Override
	public boolean solutionFound(int[] index) throws InvalidSetOpException, CLExecutionException {
		final Object v = evalExpression(index);
		if (!result.contains(v)) {
			result.add(v);
		}
		return false;
	}

	private Object evalExpression(int[] index) throws CLExecutionException, InvalidSetOpException {
		final SDARuntimeExecutionContext runtimeContext = new SDARuntimeExecutionContext(getTypingContext().getRuntimeExecutionContext());
		final CLValuationContext state = runtimeContext.getStateContext();
		for (int i = 0; i < index.length; i++) {
			state.defineNew(getVariables().get(i).getName(), members[i][index[i]]);
		}

		return expression.getValueInterpreted(runtimeContext);
	}

}
