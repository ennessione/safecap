package uk.ac.ncl.prime.sim.lang.semantics;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public interface SymbolicExecutionConditional {
	SymbolicExecutionContext enterBranch(CLExpression condition);
}
