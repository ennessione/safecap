package uk.ac.ncl.prime.sim.lang.bif;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public abstract class BuiltinFunction {

	public abstract String compile(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws CLNonExecutableException;

	public abstract Object evaluate(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws CLExecutionException, InvalidSetOpException;

	public abstract CLExpression simplify(CLBuiltinFunction self, TypingContext ctx, CLCollection<CLExpression> arguments);

	public abstract String getName();

	public abstract CLType getType(CLElement element, TypingContext ctx, CLCollection<CLExpression> arguments);

	public abstract String getAttribute(String attribute);

}