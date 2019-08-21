package uk.ac.ncl.prime.sim.lang.bif;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class BIFShiftLeft extends BuiltinFunction {
	public static BIFShiftLeft INSTANCE = new BIFShiftLeft();

	private BIFShiftLeft() {
	}

	@Override
	public String compile(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments) throws CLNonExecutableException {
		return "(" + arguments.byIndex(0).compile(context) + ").toRel().shiftLeft()";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object evaluate(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws InvalidSetOpException, CLExecutionException {
		final BSet _relation = (BSet) arguments.byIndex(0).getValue(context);
		final BRel relation = _relation.toRel();
		return relation.shiftLeft();
	}

	@Override
	public CLExpression simplify(CLBuiltinFunction self, TypingContext ctx, CLCollection<CLExpression> arguments) {
		return self;
	}

	@Override
	public String getName() {
		return "shl";
	}

	@Override
	public CLType getType(CLElement element, TypingContext ctx, CLCollection<CLExpression> arguments) {
		if (arguments.size() != 1) {
			ctx.getValidation().addError(element, getName() + " expects one argument");
			return null;
		}

		final CLType type = arguments.byIndex(0).type(ctx);

		if (!type.isRelation()) {
			ctx.getValidation().addError(element, getName() + " expects a relation argument");
		}

		// A * (B * C) to (A * B) * C

		if (!type.ranType().isCartesian()) {
			ctx.getValidation().addError(element, getName() + " expects Cartesian type in range");
		}

		final CLType A = type.domType();
		final CLProductType r = (CLProductType) type.ranType();
		final CLType B = r.getLeft();
		final CLType C = r.getRight();

		return new CLPowerType(new CLProductType(new CLProductType(A, B), C));
	}

	@Override
	public String getAttribute(String attribute) {
		if ("prob.translation".equals(attribute)) {
			return null;
		}
		return null;
	}

}
