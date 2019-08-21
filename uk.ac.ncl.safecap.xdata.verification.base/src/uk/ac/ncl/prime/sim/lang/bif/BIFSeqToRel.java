package uk.ac.ncl.prime.sim.lang.bif;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class BIFSeqToRel extends BuiltinFunction {
	public static BIFSeqToRel INSTANCE = new BIFSeqToRel();

	private BIFSeqToRel() {
	}

	@Override
	public String compile(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments) throws CLNonExecutableException {
		return "(" + arguments.byIndex(0).compile(context) + ").toRel().seqrel()";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object evaluate(SDARuntimeExecutionContext context, CLCollection<CLExpression> arguments)
			throws InvalidSetOpException, CLExecutionException {
		final BSet _relation = (BSet) arguments.byIndex(0).getValue(context);
		final BRel relation = _relation.toRel();
		return relation.seqrel();
	}

	@Override
	public CLExpression simplify(CLBuiltinFunction self, TypingContext ctx, CLCollection<CLExpression> arguments) {
		return self;
	}

	@Override
	public String getName() {
		return "seqrel";
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

		if (!type.domType().equals(CLTypeInteger.INSTANCE)) {
			ctx.getValidation().addError(element, getName() + " expects a relation argument of from int <-> A");
		}

		return new CLPowerType(new CLProductType(type.ranType(), type.ranType()));
	}

	@Override
	public String getAttribute(String attribute) {
		if ("prob.translation".equals(attribute)) {
			return null;
		}
		return null;
	}

}
