package uk.ac.ncl.prime.sim.lang.semantics;

import uk.ac.ncl.prime.sim.lang.model.CLSubstitution;

public interface SymbolicExecutionContext {
	void addSubstitution(CLSubstitution substitution);

	SymbolicExecutionConditional addConditional();
}
