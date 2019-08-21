package uk.ac.ncl.prime.sim.lang.bif;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class BIFIteration extends BuiltinFunction {
	public static BIFIteration INSTANCE = new BIFIteration();

	private BIFIteration() {
	}

	@Override
	public String compile(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments) throws CLNonExecutableException {
		final StringBuilder sb = new StringBuilder();
		sb.append("(" + arguments.byIndex(0).compile(context) + ").toRel().iteration(");
		sb.append(arguments.byIndex(1).compile(context));
		sb.append(")");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object evaluate(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws InvalidSetOpException, CLExecutionException {
		final BRel relation = CLExpression.toRelation(arguments.byIndex(0).getValue(context));
		final int iterations = (Integer) arguments.byIndex(1).getValue(context);

		if (iterations <= 0) {
			throw new InvalidSetOpException("Invalid number of relation iterations - " + iterations);
		}

		return relation.iteration(iterations);
	}

	@Override
	public CLExpression simplify(CLBuiltinFunction self, TypingContext ctx, CLCollection<CLExpression> arguments) {
		return self;
	}

	@Override
	public String getName() {
		return "iteration";
	}

	@Override
	public CLType getType(CLElement element, TypingContext ctx, CLCollection<CLExpression> arguments) {
		if (arguments.size() != 2) {
			ctx.getValidation().addError(element, getName() + " expects two arguments");
			return null;
		}

		arguments.byIndex(0).type(ctx, CLPowerType.RELATION); // any uniform relation

		final CLType reltype = arguments.byIndex(0).getType();
		if (reltype == null || !reltype.isRelation()) {
			return null;
		}

		if (!reltype.domType().equals(reltype.ranType())) {
			ctx.getValidation().addError(element, getName() + " expects relation of the form A <-> A as its first argument");
			return null;
		}

		arguments.byIndex(1).type(ctx, CLTypeInteger.INSTANCE); // any integer

		return reltype;
	}

	@Override
	public String getAttribute(String attribute) {
		if ("prob.translation".equals(attribute)) {
			return "iterate";
		}
		return null;
	}

}
