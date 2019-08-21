package uk.ac.ncl.prime.sim.lang.mb;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLException;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CompiledModelSolverUniversal extends ModelSolverCompiled {

	public CompiledModelSolverUniversal(SDARuntimeExecutionContext ctx, CLExpression predicate, List<CLParameter> variables)
			throws CLExecutionException, InvalidSetOpException {
		super(ctx, CLUtils.negate(predicate), variables);
	}

	@Override
	protected boolean solutionFound(int... index) {
		setSolution(SOLUTION.FALSE);
		return true;
	}

	@Override
	public Object execute() throws CLException {
		setSolution(SOLUTION.TRUE);
		try {
			solve();
		} catch (CLExecutionException | InvalidSetOpException e) {
			throw new CLException(e.getMessage());
		}
		if (!definate || !solvable) {
			throw new CLException("Unable to solve universal quantifier");
		}
		return getSolution() == SOLUTION.FALSE ? Boolean.FALSE : Boolean.TRUE;
	}

}
