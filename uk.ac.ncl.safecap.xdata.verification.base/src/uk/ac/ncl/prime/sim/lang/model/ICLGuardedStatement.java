package uk.ac.ncl.prime.sim.lang.model;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;

/**
 * Interface of guarded statements
 */
public interface ICLGuardedStatement {

	/**
	 * Returns a symbolic context qualifying the state just before the execution of
	 * the current statement
	 */
	SymbolicExecutionContext getSymbolicContextBefore();

	/**
	 * Returns the guarding condition for conditional statements
	 */
	CLExpression getGuardingCondition();
}
