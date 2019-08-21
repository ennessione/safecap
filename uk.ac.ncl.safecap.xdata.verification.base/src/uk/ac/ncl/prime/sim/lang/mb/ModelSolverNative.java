package uk.ac.ncl.prime.sim.lang.mb;

import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public abstract class ModelSolverNative extends ModelBuilderNative {

	public ModelSolverNative(TypingContext typingContext, CLExpression predicate, List<CLParameter> variables) {
		super(typingContext, predicate, variables);
	}

	public ModelSolverNative(TypingContext typingContext, CLExpression predicate, Collection<String> variables) {
		super(typingContext, predicate, variables);
	}

	@Override
	public void solve() throws CLExecutionException, InvalidSetOpException {

		buildIndices();

		if (!solvable) {
			setSolution(SOLUTION.UNKNOWN);
			return;
		}

		final int[] index = new int[getVariables().size()];

		if (getVariables().size() == 0) {
			if (test(index)) {
				setSolution(SOLUTION.TRUE);
				solutionFound(index);
			} else {
				setSolution(SOLUTION.FALSE);
			}
			return;
		}

		itest(index, 0);

		if (!definate && getSolution() == SOLUTION.FALSE) {
			setSolution(SOLUTION.UNKNOWN);
		}
	}

	abstract public boolean solutionFound(int[] index) throws InvalidSetOpException, CLExecutionException;

	private boolean itest(int[] index, int i) throws CLExecutionException, InvalidSetOpException {
		for (int j = 0; j < members[i].length; j++) {
			index[i] = j;

			if (i == members.length - 1) {
				if (test(index)) {
					if (solutionFound(index)) {
						return true;
					}
				}
			} else {
				if (itest(index, i + 1)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean test(int[] index) throws CLExecutionException, InvalidSetOpException {
		final Object value = super.eval(index);
		if (value instanceof Boolean) {
			final Boolean bv = (Boolean) value;
			return bv.booleanValue();
		} else {
			return false;
		}
	}
}
