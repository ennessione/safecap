package uk.ac.ncl.prime.sim.lang.mb;

import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public class NativeModelSolverUniversal extends ModelSolverNative {

	public NativeModelSolverUniversal(TypingContext typingContext, CLExpression predicate, List<CLParameter> variables) {
		super(typingContext, CLUtils.negate(predicate), variables);
	}

	public NativeModelSolverUniversal(TypingContext typingContext, CLExpression predicate, Collection<String> variables) {
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
		return true;
	}

}
