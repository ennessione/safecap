package uk.ac.ncl.safecap.xdata.verification.core;

import uk.ac.ncl.prime.sim.lang.CLExpression;

public interface ITransition {
	CLExpression getPrecondition();

	CLExpression getPostcondition();

	String getId();
}
