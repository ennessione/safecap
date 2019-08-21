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

public class BIFFunToRel extends BuiltinFunction {
	public static BIFFunToRel INSTANCE = new BIFFunToRel();

	private BIFFunToRel() {
	}

	@Override
	public String compile(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments) throws CLNonExecutableException {
		return "(" + arguments.byIndex(0).compile(context) + ").toRel().funtorel()";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object evaluate(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws InvalidSetOpException, CLExecutionException {
		final BSet _relation = (BSet) arguments.byIndex(0).getValue(context);
		final BRel relation = _relation.toRel();
		return relation.funtorel();
	}

	@Override
	public CLExpression simplify(CLBuiltinFunction self, TypingContext ctx, CLCollection<CLExpression> arguments) {
		return self;
	}

	@Override
	public String getName() {
		return "funrel";
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

		// BRel<U, BSet<V>> to BRel<U, V>

		if (!type.ranType().isSet()) {
			ctx.getValidation().addError(element, getName() + " set type in range");
		}

		final CLType U = type.domType();
		final CLType V = type.ranType().baseType();

		return new CLPowerType(new CLProductType(U, V));
	}

	@Override
	public String getAttribute(String attribute) {
		if ("prob.translation".equals(attribute)) {
			return "rel";
		}
		return null;
	}

}
