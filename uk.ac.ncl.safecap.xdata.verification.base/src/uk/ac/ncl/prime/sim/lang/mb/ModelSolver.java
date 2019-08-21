package uk.ac.ncl.prime.sim.lang.mb;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLParameter;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.Builtin;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;

/*
 * Finds a solution to a predicate
 */
public abstract class ModelSolver {
	final static Integer[] INTEGER_SET;
	final static Boolean[] BOOLEAN_SET = new Boolean[] { Boolean.TRUE, Boolean.FALSE };

	static {
		INTEGER_SET = new Integer[Builtin.MAX_INT - Builtin.MIN_INT + 1];
		for (int i = Builtin.MIN_INT; i <= Builtin.MAX_INT; i++) {
			INTEGER_SET[i - Builtin.MIN_INT] = i;
		}

	}

	public enum SOLUTION {
		TRUE, FALSE, UNKNOWN
	};

	private final TypingContext typingContext;
	private final CLExpression predicate;
	private final List<CLParameter> variables;
	private SOLUTION solution;

	public ModelSolver(TypingContext typingContext, CLExpression predicate, List<CLParameter> variables) {
		this.typingContext = typingContext;
		this.predicate = predicate;
		this.variables = variables;
		solution = SOLUTION.UNKNOWN;
	}

	public abstract void solve() throws CLExecutionException, InvalidSetOpException;

	public SOLUTION getSolution() {
		return solution;
	}

	protected void setSolution(SOLUTION solution) {
		this.solution = solution;
	}

	public CLExpression getPredicate() {
		return predicate;
	}

	public List<CLParameter> getVariables() {
		return variables;
	}

	protected TypingContext getTypingContext() {
		return typingContext;
	}

}
