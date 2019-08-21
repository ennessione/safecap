package uk.ac.ncl.safecap.xdata.verification.registry;

import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public interface ISymbolicTransition {
	String getId();

	List<CLExpression> getPreconditions();

	List<CLExpression> getPostconditions();
}
