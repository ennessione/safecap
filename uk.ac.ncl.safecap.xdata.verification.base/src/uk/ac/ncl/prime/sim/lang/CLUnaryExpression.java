package uk.ac.ncl.prime.sim.lang;

import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLUnaryExpression extends CLExpression {
	private final CLExpression argument;

	protected CLUnaryExpression(int tag, CLExpression arg, SourceLocation location) {
		super(tag, location);
		argument = arg;
	}

	public CLUnaryExpression(int tag, CLExpression arg) {
		super(tag);
		argument = arg;
	}

	public CLExpression getArgument() {
		return argument;
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		final CLType r = argument.type(ctx);
		if (r == null) {
			return null;
		}

		switch (getTag()) {
		case alphabet.OP_FINITE: {
			if (ctx.unify(r, CLPowerType.TEMPLATE)) {
				return CLTypeBool.INSTANCE;
			}
			ctx.getValidation().addError(argument, "Expect a set expression; supplied " + r);
			return null;
		}
		case alphabet.OP_SET: {
			if (ctx.unify(r, CLPowerType.TEMPLATE)) {
				return new CLPowerType(r);
			}
			ctx.getValidation().addError(argument, "Expect a set expression; supplied " + r);
			return null;
		}
		case alphabet.OP_SEQ: {
			if (ctx.unify(r, CLSeqType.TEMPLATE)) {
				return new CLSeqType(r);
			}
			ctx.getValidation().addError(argument, "Expect a sequence expression; supplied " + r);
			return null;
		}
		case alphabet.OP_NOT: {
			if (!ctx.unify(r, CLTypeBool.INSTANCE)) {
				ctx.getValidation().addError(argument, "Boolean type expected");
				return null;
			}

			return CLTypeBool.INSTANCE;
		}
		case alphabet.OP_DOM:
			if (r.isRelation()) {
				return new CLPowerType(r.domType());
			} else if (r instanceof CLSeqType) {
				return new CLPowerType(CLTypeInteger.INSTANCE);
			} else {
				ctx.getValidation().addError(argument, "A product or sequence type expected");
				return null;
			}
		case alphabet.OP_RAN:
			if (r.isRelation()) {
				return new CLPowerType(r.ranType());
			} else if (r instanceof CLSeqType) {
				final CLSeqType st = (CLSeqType) r;
				return new CLPowerType(st.getBase());
			} else {
				ctx.getValidation().addError(argument, "A product or sequence type expected");
				return null;
			}
		case alphabet.OP_CARD:
			if (r instanceof CLPowerType) {
				return CLTypeInteger.INSTANCE;
			} else if (r instanceof CLSeqType) {
				return CLTypeInteger.INSTANCE;
			} else {
				ctx.getValidation().addError(argument, "A product or sequence type expected");
				return null;
			}
		case alphabet.OP_UNARY_MINUS:
			argument.type(ctx, CLTypeInteger.INSTANCE);
			return CLTypeInteger.INSTANCE;
		case alphabet.OP_GEN_UNION:
		case alphabet.OP_GEN_INTER: {
			if (ctx.unify(r, CLPowerType.POW)) {
				final CLPowerType pt = (CLPowerType) r;
				if (pt.getBase() instanceof CLPowerType || pt.getBase().isRelation()) {
					return pt.getBase();
				}
			}
			ctx.getValidation().addError(argument, "Expect a set of sets expression");
			return null;
		}
		case alphabet.OP_MIN:
		case alphabet.OP_MAX:
		case alphabet.OP_SUM: {
			final CLType t = argument.type(ctx);
			if (t == null) {
				return null;
			}

			if (t.isRelation() && t.ranType().equals(CLTypeInteger.INSTANCE)) {
				return CLTypeInteger.INSTANCE;
			} else if (t instanceof CLPowerType) {
				final CLPowerType pt = (CLPowerType) t;
				if (pt.getBase() == null) {
					ctx.getValidation().addError(argument, "Invalid type");
					return null;
				}
				if (pt.getBase().equals(CLTypeInteger.INSTANCE)) {
					return CLTypeInteger.INSTANCE;
				}
			}
			ctx.getValidation().addError(argument, "Expect a set of integers; supplied " + t);
			return null;
		}
		case alphabet.OP_CONVERSE: {
			// if (!ctx.unify(r, CLPowerType.RELATION)) {// (t.isRelation()) {
			if (r.isRelation()) {// (t.isRelation()) {
				return new CLPowerType(new CLProductType(r.ranType(), r.domType()));
			}
			ctx.getValidation().addError(argument, "Expect a relation type; supplied " + r);
			return null;
		}
		case alphabet.OP_PRJ1: {
			if (r.isRelation()) {
				return new CLPowerType(r.domType());
			} else if (r instanceof CLProductType) {
				final CLProductType pt = (CLProductType) r;
				return pt.getLeft();
			} else {
				ctx.getValidation().addError(argument, "Expect a relation or product type; supplied " + r);
				return null;
			}
		}
		case alphabet.OP_PRJ2: {
			if (r.isRelation()) {
				return new CLPowerType(r.ranType());
			} else if (r instanceof CLProductType) {
				final CLProductType pt = (CLProductType) r;
				return pt.getRight();
			} else {
				ctx.getValidation().addError(argument, "Expect a relation or product type; supplied " + r);
				return null;
			}
		}
		case alphabet.OP_SOME: {
			if (ctx.unify(r, CLPowerType.TEMPLATE)) {
				final CLPowerType pt = (CLPowerType) r;
				return pt.getBase();
			} else {
				ctx.getValidation().addError(argument, "Expect a set expression; supplied " + r);
				return null;
			}
		}

		default:
			ctx.getValidation().addError(this, "Don't know how to type this");
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		Object val = argument.getValueInterpreted(context);
		if (val == null) {
			return null;
		}
		switch (getTag()) {
		case alphabet.OP_FINITE:
			throw new CLNonExecutableException(this);
		case alphabet.OP_SET:
		case alphabet.OP_SEQ:
			throw new CLNonExecutableException(this);
		case alphabet.OP_NOT: {
			if (val instanceof Boolean) {
				final Boolean x = (Boolean) val;
				return new Boolean(!x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_DOM: {
			val = toRelation(val);
			if (val instanceof BRel) {
				return ((BRel) val).dom();
			}
			throw new CLExecutionException("Invalid operator execution context: expect relation but " + argument.asString() + "("
					+ val.toString() + ") is of kind " + val.getClass().getSimpleName());
		}
		case alphabet.OP_RAN: {
			val = toRelation(val);
			if (val instanceof BRel) {
				return ((BRel) val).ran();
			}
			throw new CLExecutionException("Invalid operator execution context: expect relation but " + argument.asString() + "("
					+ val.toString() + ") is of kind " + val.getClass().getSimpleName());
		}
		case alphabet.OP_CARD: {
			if (val instanceof BSet) {
				final BSet<?> set = (BSet<?>) val;
				return set.card();
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_UNARY_MINUS: {
			if (val instanceof Integer) {
				final Integer integer = (Integer) val;
				return new Integer(-integer);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_GEN_UNION: {
			if (val instanceof BSet) {
				final BSet<BSet<Object>> set = (BSet<BSet<Object>>) val;
				return BSet.Union(set);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_GEN_INTER: {
			if (val instanceof BSet) {
				final BSet<BSet<Object>> set = (BSet<BSet<Object>>) val;
				return BSet.Inter(set);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_MIN: {
			if (val instanceof BSet) {
				final BSet<Integer> set = (BSet<Integer>) val;
				return BSet.min(set);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_MAX: {
			if (val instanceof BSet) {
				final BSet<Integer> set = (BSet<Integer>) val;
				return BSet.max(set);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_SUM: {
			if (val instanceof BSet) {
				final BSet<Integer> set = (BSet<Integer>) val;
				return BSet.summation(set);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_CONVERSE: {
			val = toRelation(val);
			if (val instanceof BRel) {
				return ((BRel) val).inverse();
			}
			throw new CLExecutionException("Invalid operator execution context: expect relation but " + val.toString() + " is of kind "
					+ val.getClass().getSimpleName());
		}
		case alphabet.OP_PRJ1: {
			if (val instanceof BRel) {
				return ((BRel) val).dom();
			} else if (val instanceof BMap) {
				final BMap<?, ?> map = (BMap<?, ?>) val;
				return map.prj1();
			} else {
				throw new CLExecutionException("Invalid operator execution context");
			}
		}
		case alphabet.OP_PRJ2: {
			if (val instanceof BRel) {
				return ((BRel) val).ran();
			} else if (val instanceof BMap) {
				final BMap<?, ?> map = (BMap<?, ?>) val;
				return map.prj2();
			} else {
				throw new CLExecutionException("Invalid operator execution context");
			}
		}
		case alphabet.OP_SOME: {
			if (val instanceof BSet) {
				final BSet<?> set = (BSet<?>) val;
				return set.some();
			} else {
				throw new CLExecutionException("Invalid operator execution context");
			}
		}

		}

		throw new CLExecutionException("Feature not implemented: " + getTag());
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		return argument.getFreeIdentifiers();
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		return argument.getBoundIdentifiers();
	}

	@Override
	public String asString() {
		switch (getTag()) {
		case alphabet.OP_FINITE:
			return "finite(" + argument.asString() + ")";
		case alphabet.OP_SET:
			return "set(" + argument.asString() + ")";
		case alphabet.OP_SEQ:
			return "seq(" + argument.asString() + ")";
		case alphabet.OP_NOT:
			return "not " + argument.asString();
		case alphabet.OP_DOM:
			return "dom(" + argument.asString() + ")";
		case alphabet.OP_RAN:
			return "ran(" + argument.asString() + ")";
		case alphabet.OP_CARD:
			return "card(" + argument.asString() + ")";
		case alphabet.OP_UNARY_MINUS:
			return "-" + ppBrackets(argument, false);
		case alphabet.OP_GEN_UNION:
			return "union(" + argument.asString() + ")";
		case alphabet.OP_GEN_INTER:
			return "inter(" + argument.asString() + ")";
		case alphabet.OP_MIN:
			return "min(" + argument.asString() + ")";
		case alphabet.OP_MAX:
			return "max(" + argument.asString() + ")";
		case alphabet.OP_SUM:
			return "sum(" + argument.asString() + ")";
		case alphabet.OP_CONVERSE:
			return ppBrackets(argument, false) + "~";
		case alphabet.OP_PRJ1:
			return "prj1(" + argument.asString() + ")";
		case alphabet.OP_PRJ2:
			return "prj2(" + argument.asString() + ")";
		case alphabet.OP_SOME:
			return "some(" + argument.asString() + ")";
		}
		return "?(Unary " + getTag() + ")";

	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		final CLExpression r_arg = argument.rewrite(map);

		if (r_arg != argument) {
			final CLUnaryExpression r = new CLUnaryExpression(getTag(), r_arg, getLocation());
			return r;
		}
		return this;
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		final CLExpression r_arg = argument.simplify(ctx);

		if (CLUtils.isBoolLiteral(r_arg)) {
			final boolean a = CLUtils.getBooleanValue(r_arg);

			switch (getTag()) {
			case alphabet.OP_NOT:
				return CLUtils.makeBoolLiteral(!a);
			}
		}

		if (CLUtils.isNumberLiteral(r_arg)) {
			final int a = CLUtils.getIntegerValue(r_arg);

			switch (getTag()) {
			case alphabet.OP_UNARY_MINUS:
				return new CLIntegerExpression(-a);
			}
		}

		// inverse reduction
		if (r_arg instanceof CLUnaryExpression && r_arg.getTag() == getTag()) {
			final CLUnaryExpression u_r = (CLUnaryExpression) r_arg;
			switch (getTag()) {
			case alphabet.OP_UNARY_MINUS:
				return u_r.getArgument();
			case alphabet.OP_NOT:
				return u_r.getArgument();
			}
		}

		if (r_arg != argument) {
			final CLUnaryExpression r = new CLUnaryExpression(getTag(), r_arg, getLocation());
			if (ctx != null) {
				r.type(ctx, getType());
			}

			return r;
		}
		return this;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		return argument.resolveIdentifiers(name);
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			argument.visit(visitor, userobject);
		}
	}

	private int hashcode = 0;

	@Override
	public int hashCode() {
		int result = hashcode;
		if (result == 0) {
			final int prime = 31;
			result = super.hashCode();
			result = prime * result + (argument == null ? 0 : argument.hashCode());
			hashcode = result;
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CLUnaryExpression other = (CLUnaryExpression) obj;
		if (argument == null) {
			if (other.argument != null) {
				return false;
			}
		} else if (!argument.equals(other.argument)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isImmutable() {
		return getTag() != alphabet.OP_SOME && argument.isImmutable();
	}

	@Override
	public int getPriority() {
		switch (getTag()) {
		case alphabet.OP_NOT:
		case alphabet.OP_CONVERSE:
			return 1;
		case alphabet.OP_FINITE:
		case alphabet.OP_SET:
		case alphabet.OP_SEQ:
		case alphabet.OP_DOM:
		case alphabet.OP_RAN:
		case alphabet.OP_CARD:
		case alphabet.OP_UNARY_MINUS:
		case alphabet.OP_GEN_UNION:
		case alphabet.OP_GEN_INTER:
		case alphabet.OP_MIN:
		case alphabet.OP_MAX:
		case alphabet.OP_SUM:
		case alphabet.OP_PRJ1:
		case alphabet.OP_PRJ2:
		case alphabet.OP_SOME:
			return 0;
		default:
			return 20;
		}
	}

	@Override
	public CLExpression deepCopy() {
		return new CLUnaryExpression(getTag(), argument.deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLUnaryExpression(getTag(), argument, getLocation());
	}

	@Override
	public Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		final CLUnaryExpression other = (CLUnaryExpression) template;
		return argument.match(other.argument, context);
	}

	@Override
	public String compile(SDARuntimeExecutionContext typing) throws CLNonExecutableException {

		final Object val = argument.compile(typing);

		switch (getTag()) {
		case alphabet.OP_FINITE:
		case alphabet.OP_SET:
		case alphabet.OP_SEQ:
			throw new CLNonExecutableException(this);
		case alphabet.OP_NOT:
			return "!(" + val + ")";
		case alphabet.OP_DOM:
			return val + ".toRel().dom()";
		case alphabet.OP_RAN:
			return val + ".toRel().ran()";
		case alphabet.OP_CARD:
			return val + ".card()";
		case alphabet.OP_UNARY_MINUS:
			return "-(" + val + ")";
		case alphabet.OP_GEN_UNION:
			return "Union(" + val + ")";
		case alphabet.OP_GEN_INTER:
			return "Inter(" + val + ")";
		case alphabet.OP_MIN:
			return "min(" + val + ")";
		case alphabet.OP_MAX:
			return "max(" + val + ")";
		case alphabet.OP_SUM:
			return "summation(" + val + ")";
		case alphabet.OP_CONVERSE:
			return "(" + val + ").toRel().inverse()";
		case alphabet.OP_PRJ1:
			if (argument.getType() instanceof CLProductType) {
				return CLUtils.compilationTypeCoercion("(" + val + ").prj1()", getType());
			} else {
				return "(" + val + ").toRel().dom()";
			}
		case alphabet.OP_PRJ2:
			if (argument.getType() instanceof CLProductType) {
				return CLUtils.compilationTypeCoercion("(" + val + ").prj2()", getType());
			} else {
				return "(" + val + ").toRel().ran()";
			}
		case alphabet.OP_SOME:
			return "(" + val + ").some()";
		}

		throw new CLNonExecutableException(this);
	}
}
