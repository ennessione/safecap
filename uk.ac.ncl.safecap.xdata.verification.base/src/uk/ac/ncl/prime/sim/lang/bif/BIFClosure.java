package uk.ac.ncl.prime.sim.lang.bif;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class BIFClosure extends BuiltinFunction {
	public static BIFClosure INSTANCE = new BIFClosure();

	private BIFClosure() {
	}

	@Override
	public String compile(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments) throws CLNonExecutableException {
		return "(" + arguments.byIndex(0).compile(context) + ").toRel().closure()";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object evaluate(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws InvalidSetOpException, CLExecutionException {
		final BRel relation = CLExpression.toRelation(arguments.byIndex(0).getValue(context));

		return relation.closure(20);
	}

	@Override
	public CLExpression simplify(CLBuiltinFunction self, TypingContext ctx, CLCollection<CLExpression> arguments) {
		return self;
	}

	@Override
	public String getName() {
		return "closure";
	}

	@Override
	public CLType getType(CLElement element, TypingContext ctx, CLCollection<CLExpression> arguments) {
		if (arguments.size() != 1) {
			ctx.getValidation().addError(element, getName() + " expects one argument");
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

		return reltype;
	}

	@Override
	public String getAttribute(String attribute) {
		if ("prob.translation".equals(attribute)) {
			return "closure";
		}
		return null;
	}

}
