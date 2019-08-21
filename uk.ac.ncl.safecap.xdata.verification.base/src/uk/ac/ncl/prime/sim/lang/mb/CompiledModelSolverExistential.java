package uk.ac.ncl.prime.sim.lang.mb;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CompiledModelSolverExistential extends ModelSolverCompiled {

	public CompiledModelSolverExistential(SDARuntimeExecutionContext ctx, CLExpression predicate, List<CLParameter> variables)
			throws CLExecutionException, InvalidSetOpException {
		super(ctx, predicate, variables);
	}

	@Override
	protected boolean solutionFound(int... index) {
		setSolution(SOLUTION.TRUE);
		return true;
	}

	@Override
	public Object execute() throws CLException {
		try {
			solve();
		} catch (CLExecutionException | InvalidSetOpException e) {
			throw new CLException(e.getMessage());
		}
		if (getSolution() == SOLUTION.UNKNOWN || !definate || !solvable) {
			throw new CLException("Unable to solve existential quantifier");
		}
		return getSolution() == SOLUTION.TRUE ? Boolean.TRUE : Boolean.FALSE;
	}
}
