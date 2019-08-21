package uk.ac.ncl.prime.sim.lang.mb;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

public class NativeModelSolverExistential extends ModelSolverNative {
	private final Map<String, Object> solutions;
	private int findNumber = 1;
	private boolean foundAll = false;
	private ISolverCallback callback;

	private int foundSolutions = 0;
	private boolean triedAll = true;

	public NativeModelSolverExistential(TypingContext typingContext, CLExpression predicate, List<CLParameter> vars) {
		super(typingContext, predicate, vars);
		solutions = new HashMap<>();
	}

	public NativeModelSolverExistential(TypingContext typingContext, CLExpression predicate, Collection<String> vars) {
		super(typingContext, predicate, vars);
		solutions = new HashMap<>();
	}

	public ISolverCallback getCallback() {
		return callback;
	}

	public int getFindNumber() {
		return findNumber;
	}

	public int getFoundSolutions() {
		return foundSolutions;
	}

	public void setFindNumber(int findNumber) {
		if (findNumber > 100) {
			this.findNumber = 100;
		} else if (findNumber < 1) {
			this.findNumber = 1;
		} else {
			this.findNumber = findNumber;
		}
	}

	public boolean hasFoundAll() {
		return foundAll;
	}

	private void setFoundAll(boolean foundAll) {
		this.foundAll = foundAll;
	}

	public void setCallback(ISolverCallback callback) {
		this.callback = callback;
	}

	protected void addSolution(String name, Object value) {
		solutions.put(name, value);
	}

	public boolean isSolved() {
		return getVariables().size() == solutions.size();
	}

	public Map<String, Object> getSolutions() {
		return solutions;
	}

	@Override
	public void solve() throws CLExecutionException, InvalidSetOpException {
		super.solve();
		setFindNumber(foundSolutions);
		setFoundAll(triedAll);
	}

	@Override
	public boolean solutionFound(int[] index) {
		for (int k = 0; k < index.length; k++) {
			addSolution(getVariables().get(k).getName(), members[k][index[k]]);
		}

		foundSolutions++;
		setSolution(SOLUTION.TRUE);

		if (getCallback() != null) {
			getCallback().solved(getSolutions(), foundSolutions);
		}

		if (foundSolutions >= getFindNumber()) {
			triedAll = false;
			return true;
		}

		return false;
	}

}
