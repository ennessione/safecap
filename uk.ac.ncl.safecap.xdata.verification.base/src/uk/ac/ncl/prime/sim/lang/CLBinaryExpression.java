package uk.ac.ncl.prime.sim.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import uk.ac.ncl.prime.sim.sets.BFun;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLBinaryExpression extends CLExpression {
	private CLExpression left;
	private CLExpression right;

	public CLBinaryExpression(int tag, CLExpression left, CLExpression right, SourceLocation location) {
		super(tag, location);
		this.left = left;
		this.right = right;
		sort();
	}

	public CLBinaryExpression(int tag, CLExpression left, CLExpression right) {
		super(tag);
		this.left = left;
		this.right = right;
		sort();
	}

	private void sort() {

		if (isCommutative()) {
			if (comp(left, right) > 0) {
				final CLExpression t = left;
				left = right;
				right = t;
			}
		}
	}

	private int comp(CLExpression l, CLExpression r) {
		final boolean lf = isQualIdentifier(l);
		final boolean rf = isQualIdentifier(r);
		if (lf) {
			if (!rf) {
				return -1;
			} else {
				return getQual(l).compareTo(getQual(r));
			}
		} else if (rf) {
			return 1;
		} else if (l instanceof CLIdentifier && !(r instanceof CLIdentifier)) {
			return -1;
		} else if (r instanceof CLIdentifier && !(l instanceof CLIdentifier)) {
			return 1;
		} else {
			final String ls = left.asString();
			final String rs = right.asString();
			if (ls.length() != rs.length()) {
				return ls.length() - rs.length();
			}
			return ls.compareTo(rs);
		}
	}

	private boolean isQualIdentifier(CLExpression e) {
		if (e instanceof CLIdentifier) {
			final CLIdentifier ident = (CLIdentifier) e;
			return ident.isPrime() || ident.isQualified();
		}

		return false;
	}

	private String getQual(CLExpression e) {
		if (e instanceof CLIdentifier) {
			final CLIdentifier ident = (CLIdentifier) e;
			if (ident.isQualified()) {
				return ident.getQualifier();
			}
		}

		return "";
	}

	public CLExpression getLeft() {
		return left;
	}

	public CLExpression getRight() {
		return right;
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		switch (getTag()) {
		case alphabet.OP_PINJ:
		case alphabet.OP_REL:
		case alphabet.OP_PFUN: {
			final CLType l = left.type(ctx);
			final CLType r = right.type(ctx);

			if (ctx.unify(l, CLPowerType.TEMPLATE) && ctx.unify(r, CLPowerType.TEMPLATE)) {
				final CLPowerType lp = (CLPowerType) l;
				final CLPowerType rp = (CLPowerType) r;
				return new CLPowerType(new CLPowerType(new CLProductType(lp.getBase(), rp.getBase())));
			} else if (!(l instanceof CLPowerType)) {
				ctx.getValidation().addError(left, "Expect a set expression value");
			} else {
				ctx.getValidation().addError(right, "Expect a set expression value");
			}

			return null;
		}
		case alphabet.OP_CART: {
			final CLType l = left.type(ctx);
			final CLType r = right.type(ctx);

			if (ctx.unify(l, CLPowerType.TEMPLATE) && ctx.unify(r, CLPowerType.TEMPLATE)) {
				final CLPowerType lp = (CLPowerType) l;
				final CLPowerType rp = (CLPowerType) r;
				return new CLPowerType(new CLProductType(lp.getBase(), rp.getBase()));
			} else if (!(l instanceof CLPowerType)) {
				ctx.getValidation().addError(left, "Expect a set expression value");
			} else {
				ctx.getValidation().addError(right, "Expect a set expression value");
			}

			return null;
		}
		case alphabet.IMAGE: {
			final CLType l = left.type(ctx);
			if (ctx.unify(l, CLPowerType.TEMPLATE) && l instanceof CLPowerType) {
				final CLPowerType pp = (CLPowerType) l;
				if (ctx.unify(pp.getBase(), CLProductType.TEMPLATE)) {
					final CLProductType pr = (CLProductType) pp.getBase();
					right.type(ctx, new CLPowerType(pr.getLeft()));
					return new CLPowerType(pr.getRight());
				} else {
					ctx.getValidation().addError(this, "Invalid image context");
					return null;
				}
			} else if (l instanceof CLSeqType) { // bug!
				final CLSeqType st = (CLSeqType) l;
				right.type(ctx, new CLPowerType(CLTypeInteger.INSTANCE));
				return new CLPowerType(st.getBase());
			} else if (l != null) {
				ctx.getValidation().addError(this, "Invalid image context");
				return null;
			}
		}

		case alphabet.OP_MAP: {
			final CLType tl = left.type(ctx);
			final CLType tr = right.type(ctx);
			return new CLProductType(tl, tr);
		}

		case alphabet.OP_IMPLIES:
			left.type(ctx, CLTypeBool.INSTANCE);
			right.type(ctx, CLTypeBool.INSTANCE);
			return CLTypeBool.INSTANCE;

		case alphabet.OP_LEQ:
		case alphabet.OP_LSS:
		case alphabet.OP_GEQ:
		case alphabet.OP_GRT:
			left.type(ctx, CLTypeInteger.INSTANCE);
			right.type(ctx, CLTypeInteger.INSTANCE);
			return CLTypeBool.INSTANCE;

		case alphabet.OP_IN:
		case alphabet.OP_NOTIN:
			CLType r = right.type(ctx);
			if (r != null) {
				if (ctx.unify(r, new CLPowerType(left.type(ctx)))) {
					return CLTypeBool.INSTANCE;
				} else {
					ctx.getValidation().addError(right,
							"Expect compatible element and set types (actual " + left.type(ctx) + " : " + r.toString() + ")");
				}
			}
			return null;
		case alphabet.OP_SUBSET:
		case alphabet.OP_SUBSETEQ: {
			final CLType tl = left.type(ctx);
			if (ctx.unify(tl, CLPowerType.TEMPLATE)) {
				right.type(ctx, tl);
				return CLTypeBool.INSTANCE;
			} else {
				ctx.getValidation().addError(this, "Set type expected");
				return null;
			}
		}

		case alphabet.OP_MOD:
			left.type(ctx, CLTypeInteger.INSTANCE);
			right.type(ctx, CLTypeInteger.INSTANCE);
			return CLTypeInteger.INSTANCE;

		case alphabet.OP_NEQ:
		case alphabet.OP_EQL:
			left.type(ctx, right.type(ctx));
			return CLTypeBool.INSTANCE;

		case alphabet.OP_FCOMP: {
			final CLType tl = left.type(ctx);
			final CLType tr = right.type(ctx);
			if (tl != null && tr != null) {
				if (!tl.isRelation()) {
					ctx.getValidation().addError(left, "A relational expression is expected here");
				}
				if (!tr.isRelation()) {
					ctx.getValidation().addError(left, "A relational expression is expected here");
				}
				if (!ctx.unify(tl.ranType(), tr.domType())) {
					ctx.getValidation().addError(this, "Incompatible domain join types " + tl.ranType() + " and " + tr.domType());
				}

				return new CLPowerType(new CLProductType(tl.domType(), tr.ranType()));
			} else {
				ctx.getValidation().addError(this, "Invalid join types");
				return null;
			}
		}
		case alphabet.OP_DIRECT_PRODUCT: {
			final CLType tl = left.type(ctx);
			final CLType tr = right.type(ctx);
			if (!tl.isRelation()) {
				ctx.getValidation().addError(left, "A relational expression is expected here");
			}
			if (!tr.isRelation()) {
				ctx.getValidation().addError(right, "A relational expression is expected here");
			}
			if (!ctx.unify(tl.domType(), tr.domType())) {
				ctx.getValidation().addError(this, "Incompatible domain types " + tl.domType() + " and " + tr.domType());
			}

			return new CLPowerType(new CLProductType(tl.domType(), new CLProductType(tl.ranType(), tr.ranType())));
		}

		case alphabet.OP_RANRES:
		case alphabet.OP_RANSUB:
			r = left.type(ctx);
			if (r != null && r.isRelation()) {
				right.type(ctx, new CLPowerType(r.ranType()));
				return r;
			} else if (r instanceof CLSeqType) {
				final CLSeqType st = (CLSeqType) r;
				right.type(ctx, new CLPowerType(st.getBase()));
				return r;
			}

			ctx.getValidation().addError(this, "Incompatible types for domain restriction operator");
			return null;

		case alphabet.OP_DOMRES:
		case alphabet.OP_DOMSUB:
			r = right.type(ctx);
			if (r != null && r.isRelation()) {
				left.type(ctx, new CLPowerType(r.domType()));
				return r;
			} else if (r instanceof CLSeqType) {
				left.type(ctx, new CLPowerType(CLTypeInteger.INSTANCE));
				return r;
			}

			ctx.getValidation().addError(this, "Incompatible types for domain restriction operator");
			return null;
		case alphabet.OP_RANGE: {
			left.type(ctx, CLTypeInteger.INSTANCE);
			right.type(ctx, CLTypeInteger.INSTANCE);
			return new CLPowerType(CLTypeInteger.INSTANCE);
		}
		// case alphabet.OP_DOT: {
		// if (left.getTag() != alphabet.ID || right.getTag() != alphabet.ID) {
		// ctx.getValidation().addError(this,
		// "Invalid context of namespace resolution");
		// return null;
		// }
		// CLType tl = left.type(ctx);
		// if (tl instanceof CLPowerType) {
		// CLPowerType pt = (CLPowerType) tl;
		// if (pt.getBase() instanceof CLGivenType) {
		// CLGivenType gt = (CLGivenType) pt.getBase();
		// CLIdentifier rid = (CLIdentifier) right;
		// }
		// }
		//
		// right.type(ctx, CLTypeInteger.INSTANCE);
		// }
		default:
			ctx.getValidation().addError(this, "Don't know how to type this");
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		Object l = left.getValueInterpreted(context);
		if (l == null) {
			return null;
		}
		switch (getTag()) {
		case alphabet.OP_PINJ:
		case alphabet.OP_REL:
		case alphabet.OP_PFUN:
			throw new CLNonExecutableException(this);
		case alphabet.OP_CART: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof BSet && r instanceof BSet) {
				final BSet x = (BSet) l;
				final BSet<Object> y = (BSet<Object>) r;
				return x.times(y);
			}
		}
		case alphabet.IMAGE: {
			final Object r = right.getValueInterpreted(context);
			l = toRelation(l);
			if (l instanceof BRel && r instanceof BSet) {
				final BRel x = (BRel) l;
				final BSet y = (BSet) r;
				return x.image(y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_MAP: {
			final Object r = right.getValueInterpreted(context);
			return new BMap<>(l, r);
		}
		case alphabet.OP_IMPLIES: {
			if (l instanceof Boolean) {
				final Boolean x = (Boolean) l;
				if (x) {
					final Object r = right.getValueInterpreted(context);
					return r;
				} else {
					return true;
				}
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_LEQ: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof Integer && r instanceof Integer) {
				final Integer x = (Integer) l;
				final Integer y = (Integer) r;
				return new Boolean(x <= y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_LSS: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof Integer && r instanceof Integer) {
				final Integer x = (Integer) l;
				final Integer y = (Integer) r;
				return new Boolean(x < y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_GEQ: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof Integer && r instanceof Integer) {
				final Integer x = (Integer) l;
				final Integer y = (Integer) r;
				return new Boolean(x >= y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_GRT: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof Integer && r instanceof Integer) {
				final Integer x = (Integer) l;
				final Integer y = (Integer) r;
				return new Boolean(x > y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_IN: {
			final Object r = right.getValueInterpreted(context);
			if (r instanceof BSet) {
				final BSet<Object> x = (BSet<Object>) r;
				return x.contains(l);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_NOTIN: {
			final Object r = right.getValueInterpreted(context);
			if (r instanceof BSet) {
				final BSet<Object> x = (BSet<Object>) r;
				return !x.contains(l);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_MOD: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof Integer && r instanceof Integer) {
				final Integer x = (Integer) l;
				final Integer y = (Integer) r;
				return new Integer(x % y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_NEQ: {
			final Object r = right.getValueInterpreted(context);
			return new Boolean(!l.equals(r));
		}
		case alphabet.OP_EQL: {
			final Object r = right.getValueInterpreted(context);
			return new Boolean(l.equals(r));
		}
		case alphabet.OP_DOMRES: {
			final Object r = toRelation(right.getValueInterpreted(context));
			if (l instanceof BSet && r instanceof BRel) {
				final BSet<Object> x = (BSet<Object>) l;
				final BRel y = (BRel) r;
				return y.domRestr(x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_DOMSUB: {
			final Object r = toRelation(right.getValueInterpreted(context));
			if (l instanceof BSet && r instanceof BRel) {
				final BSet<Object> x = (BSet<Object>) l;
				final BRel y = (BRel) r;
				return y.domSubtr(x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_FCOMP: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof BRel && r instanceof BRel) {
				final BRel x = (BRel) l;
				final BRel y = (BRel) r;
				return x.forwardcomp(y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_RANRES: {
			final Object r = right.getValueInterpreted(context);
			l = toRelation(l);
			if (r instanceof BSet && l instanceof BRel) {
				final BSet x = (BSet) r;
				final BRel y = (BRel) l;
				return y.ranRestr(x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_RANSUB: {
			final Object r = right.getValueInterpreted(context);
			l = toRelation(l);
			if (r instanceof BSet && l instanceof BRel) {
				final BSet x = (BSet) r;
				final BRel y = (BRel) l;
				return y.ranSubtr(x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_RANGE: {
			final Object r = right.getValueInterpreted(context);
			if (l instanceof Integer && r instanceof Integer) {
				final Integer x = (Integer) l;
				final Integer y = (Integer) r;
				return BSet.mkRange(x, y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_SUBSET: {
			final Object r = right.getValueInterpreted(context);
			if (r instanceof BSet && l instanceof BSet) {
				final BSet<Object> x = (BSet<Object>) l;
				final BSet<Object> y = (BSet<Object>) r;
				return y.containsStrictly(x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_SUBSETEQ: {
			final Object r = right.getValueInterpreted(context);
			if (r instanceof BSet && l instanceof BSet) {
				final BSet<Object> x = (BSet<Object>) l;
				final BSet<Object> y = (BSet<Object>) r;
				return y.contains(x);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		case alphabet.OP_DIRECT_PRODUCT: {
			final Object r = right.getValueInterpreted(context);
			if (r instanceof BFun && l instanceof BFun) {
				final BFun x = (BFun) l;
				final BFun y = (BFun) r;
				return x.directproduct(y);
			}
			throw new CLExecutionException("Invalid operator execution context");
		}
		}

		throw new CLExecutionException("Feature not implemented: " + getTag());
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		final Set<String> s1 = left.getFreeIdentifiers();
		final Set<String> s2 = right.getFreeIdentifiers();

		if (s1.size() > 0 || s2.size() > 0) {
			final Set<String> freeIdentifiers = new HashSet<>(s1.size() + s2.size());
			freeIdentifiers.addAll(s1);
			freeIdentifiers.addAll(s2);
			return freeIdentifiers;
		} else {
			return CLExpression.noFreeIdentifiers;
		}
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		final Set<CLIdentifier> s1 = left.getBoundIdentifiers();
		final Set<CLIdentifier> s2 = right.getBoundIdentifiers();

		if (s1.size() > 0 || s2.size() > 0) {
			final Set<CLIdentifier> boundIdentifiers = new HashSet<>(s1.size() + s2.size());
			boundIdentifiers.addAll(s1);
			boundIdentifiers.addAll(s2);
			return boundIdentifiers;
		} else {
			return CLExpression.noBoundIdentifiers;
		}
	}

	public String _opsymbol() {
		switch (getTag()) {
		case alphabet.OP_PINJ:
			return ">+>";
		case alphabet.OP_REL:
			return "<->";
		case alphabet.OP_PFUN:
			return "+->";
		case alphabet.OP_CART:
			return "**";
		case alphabet.IMAGE:
			return "[..]";
		case alphabet.OP_MAP:
			return "->";
		case alphabet.OP_IMPLIES:
			return "=>" + ppBrackets(right, false);
		case alphabet.OP_LEQ:
			return "<=" + ppBrackets(right, false);
		case alphabet.OP_LSS:
			return "<" + ppBrackets(right, false);
		case alphabet.OP_GEQ:
			return ">=" + ppBrackets(right, false);
		case alphabet.OP_GRT:
			return ">" + ppBrackets(right, false);
		case alphabet.OP_IN:
			return ":" + ppBrackets(right, false);
		case alphabet.OP_NOTIN:
			return "/:" + ppBrackets(right, false);
		case alphabet.OP_MOD:
			return "mod" + ppBrackets(right, false);
		case alphabet.OP_NEQ:
			return "!=" + ppBrackets(right, false);
		case alphabet.OP_EQL:
			return "==" + ppBrackets(right, false);
		case alphabet.OP_DOMRES:
			return "<|" + ppBrackets(right, false);
		case alphabet.OP_DOMSUB:
			return "<<|" + ppBrackets(right, false);
		case alphabet.OP_FCOMP:
			return "@" + ppBrackets(right, false);
		case alphabet.OP_DIRECT_PRODUCT:
			return "><" + ppBrackets(right, false);
		case alphabet.OP_RANRES:
			return "|>" + ppBrackets(right, false);
		case alphabet.OP_RANSUB:
			return "|>>" + ppBrackets(right, false);
		case alphabet.OP_RANGE:
			return ".." + ppBrackets(right, false);
		case alphabet.OP_SUBSET:
			return "<<:" + ppBrackets(right, false);
		case alphabet.OP_SUBSETEQ:
			return "<:" + ppBrackets(right, false);
		}

		return "?(Binary " + getTag() + ")";
	}

	@Override
	public String asString() {
		switch (getTag()) {
		case alphabet.OP_PINJ:
			return ppBrackets(left, false) + ">+>" + ppBrackets(right, false);
		case alphabet.OP_REL:
			return ppBrackets(left, false) + "<->" + ppBrackets(right, false);
		case alphabet.OP_PFUN:
			return ppBrackets(left, false) + "+->" + ppBrackets(right, false);
		case alphabet.OP_CART:
			return ppBrackets(left, false) + "**" + ppBrackets(right, false);
		case alphabet.IMAGE:
			return ppBrackets(left, false) + "[" + right.asString() + "]";
		case alphabet.OP_MAP:
			return ppBrackets(left, false) + "->" + ppBrackets(right, false);
		case alphabet.OP_IMPLIES:
			return ppBrackets(left, false) + " => " + ppBrackets(right, false);
		case alphabet.OP_LEQ:
			return ppBrackets(left, false) + " <= " + ppBrackets(right, false);
		case alphabet.OP_LSS:
			return ppBrackets(left, false) + " < " + ppBrackets(right, false);
		case alphabet.OP_GEQ:
			return ppBrackets(left, false) + " >= " + ppBrackets(right, false);
		case alphabet.OP_GRT:
			return ppBrackets(left, false) + " > " + ppBrackets(right, false);
		case alphabet.OP_IN:
			return ppBrackets(left, false) + " : " + ppBrackets(right, false);
		case alphabet.OP_NOTIN:
			return ppBrackets(left, false) + " /: " + ppBrackets(right, false);
		case alphabet.OP_MOD:
			return ppBrackets(left, false) + " mod " + ppBrackets(right, false);
		case alphabet.OP_NEQ:
			return ppBrackets(left, false) + " != " + ppBrackets(right, false);
		case alphabet.OP_EQL:
			return ppBrackets(left, false) + " == " + ppBrackets(right, false);
		case alphabet.OP_DOMRES:
			return ppBrackets(left, false) + " <| " + ppBrackets(right, false);
		case alphabet.OP_DOMSUB:
			return ppBrackets(left, false) + " <<| " + ppBrackets(right, false);
		case alphabet.OP_FCOMP:
			return ppBrackets(left, false) + " @ " + ppBrackets(right, false);
		case alphabet.OP_DIRECT_PRODUCT:
			return ppBrackets(left, false) + " >< " + ppBrackets(right, false);
		case alphabet.OP_RANRES:
			return left.asString() + " |> " + ppBrackets(right, false);
		case alphabet.OP_RANSUB:
			return left.asString() + " |>> " + ppBrackets(right, false);
		case alphabet.OP_RANGE:
			return left.asString() + " .. " + ppBrackets(right, false);
		case alphabet.OP_SUBSET:
			return left.asString() + " <<: " + ppBrackets(right, false);
		case alphabet.OP_SUBSETEQ:
			return left.asString() + " <: " + ppBrackets(right, false);

		}

		return "?(Binary " + getTag() + ")";
	}

	public boolean isCommutative() {
		switch (getTag()) {
		case alphabet.OP_NEQ:
		case alphabet.OP_EQL:
			return true;
		}
		return false;
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		final CLExpression r_left = left.rewrite(map);
		final CLExpression r_right = right.rewrite(map);

		if (r_left != left || r_right != right) {
			final CLBinaryExpression r = new CLBinaryExpression(getTag(), r_left, r_right, getLocation());

			return r;
		}
		return this;
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		final CLExpression r_left = left.simplify(ctx);
		final CLExpression r_right = right.simplify(ctx);

		if (CLUtils.isBoolLiteral(r_left) && CLUtils.isBoolLiteral(r_right)) {
			final boolean a = CLUtils.getBooleanValue(r_left);
			final boolean b = CLUtils.getBooleanValue(r_right);

			switch (getTag()) {
			case alphabet.OP_IMPLIES:
				return CLUtils.makeBoolLiteral(!a || a && b);
			case alphabet.OP_NEQ:
				return CLUtils.makeBoolLiteral(a != b);
			case alphabet.OP_EQL:
				return CLUtils.makeBoolLiteral(a == b);
			}
		} else if (CLUtils.isEnumLiteral(r_left, ctx) && CLUtils.isEnumLiteral(r_right, ctx)) {
			final String a = ((CLIdentifier) r_left).getName();
			final String b = ((CLIdentifier) r_right).getName();

			switch (getTag()) {
			case alphabet.OP_NEQ:
				return CLUtils.makeBoolLiteral(!a.equals(b));
			case alphabet.OP_EQL:
				return CLUtils.makeBoolLiteral(a.equals(b));
			}
		} else if (CLUtils.isNumberLiteral(r_left) && CLUtils.isNumberLiteral(r_right)) {
			final int a = CLUtils.getIntegerValue(r_left);
			final int b = CLUtils.getIntegerValue(r_right);

			switch (getTag()) {
			case alphabet.OP_LEQ:
				return CLUtils.makeBoolLiteral(a <= b);
			case alphabet.OP_LSS:
				return CLUtils.makeBoolLiteral(a < b);
			case alphabet.OP_GEQ:
				return CLUtils.makeBoolLiteral(a >= b);
			case alphabet.OP_GRT:
				return CLUtils.makeBoolLiteral(a > b);
			case alphabet.OP_NEQ:
				return CLUtils.makeBoolLiteral(a != b);
			case alphabet.OP_EQL:
				return CLUtils.makeBoolLiteral(a == b);
			case alphabet.OP_MOD:
				return new CLIntegerExpression(a % b);
			}
		}

		// some congruence properties
		if (r_left.equals(r_right)) {
			switch (getTag()) {
			case alphabet.OP_IMPLIES:
			case alphabet.OP_GEQ:
			case alphabet.OP_LEQ:
			case alphabet.OP_EQL:
			case alphabet.OP_SUBSETEQ:
				return CLAtomicExpression.TRUE;
			case alphabet.OP_GRT:
			case alphabet.OP_NEQ:
			case alphabet.OP_LSS:
			case alphabet.OP_SUBSET:
				return CLAtomicExpression.FALSE;
			}
		}

		// constant boolean left-hand
		if (CLUtils.isBoolLiteral(r_left)) {
			final boolean a = CLUtils.getBooleanValue(r_left);
			switch (getTag()) {
			case alphabet.OP_IMPLIES:
				if (a) {
					return r_right;
				} else {
					return CLAtomicExpression.TRUE;
				}
			}
		}

		// constant boolean right-hand
//		if (CLUtils.isBoolLiteral(r_right)) {
//			boolean a = CLUtils.getBooleanValue(r_right);
//			switch (getTag()) {
//			case alphabet.OP_IMPLIES:
//				if (!a)
//					return (new CLUnaryExpression(alphabet.OP_NOT, r_left)).simplify(ctx);
//				else
//					return CLAtomicExpression.TRUE;
//			}
//		}

		// membership properties
		if (getTag() == alphabet.OP_IN) {
			if (r_right.getTag() == alphabet.OP_EMPTYSET) {
				return CLAtomicExpression.FALSE;
			} else if (r_right.getTag() == alphabet.SETC) { // singleton
				final CLMultiExpression multi = (CLMultiExpression) r_right;
				final List<CLExpression> parts = new ArrayList<>();
				for (final CLExpression p : multi.getParts()) {
					final CLBinaryExpression r = new CLBinaryExpression(alphabet.OP_EQL, r_left, p);
					if (ctx != null) {
						r.type(ctx, getType());
					}
					parts.add(r);
				}

				final CLExpression result = CLUtils.buildDisjunct(parts, getLocation());
				if (ctx != null) {
					result.type(ctx, getType());
				}
				return result.simplify(ctx);
			}
		} else if (getTag() == alphabet.OP_NOTIN) {
			if (r_right.getTag() == alphabet.OP_EMPTYSET) {
				return CLAtomicExpression.TRUE;
			} else if (r_right.getTag() == alphabet.SETC) { // singleton
				final CLMultiExpression multi = (CLMultiExpression) r_right;
				final List<CLExpression> parts = new ArrayList<>();
				for (final CLExpression p : multi.getParts()) {
					final CLBinaryExpression r = new CLBinaryExpression(alphabet.OP_NEQ, r_left, p);
					if (ctx != null) {
						r.type(ctx, getType());
					}
					parts.add(r);
				}

				final CLExpression result = CLUtils.buildConjunct(parts, null);
				if (ctx != null) {
					result.type(ctx, getType());
				}
				return result;
			}
		}

		// equality with empty set
		if (getTag() == alphabet.OP_EQL) {
			if (r_left.getTag() == alphabet.OP_EMPTYSET && nonEmpty(r_right)) {
				return CLAtomicExpression.FALSE;
			} else if (r_right.getTag() == alphabet.OP_EMPTYSET && nonEmpty(r_left)) {
				return CLAtomicExpression.FALSE;
			}
		} else if (getTag() == alphabet.OP_NEQ) {
			if (r_left.getTag() == alphabet.OP_EMPTYSET && nonEmpty(r_right)) {
				return CLAtomicExpression.TRUE;
			} else if (r_right.getTag() == alphabet.OP_EMPTYSET && nonEmpty(r_left)) {
				return CLAtomicExpression.TRUE;
			}
		}

		// subset with empty set
		if (getTag() == alphabet.OP_SUBSET) {
			if (r_right.getTag() == alphabet.OP_EMPTYSET) {
				return CLAtomicExpression.FALSE;
			} else if (r_left.getTag() == alphabet.OP_EMPTYSET && nonEmpty(r_right)) {
				return CLAtomicExpression.TRUE;
			}
		} else if (getTag() == alphabet.OP_SUBSETEQ) {
			if (r_left.getTag() == alphabet.OP_EMPTYSET) {
				return CLAtomicExpression.TRUE;
			} else if (r_right.getTag() == alphabet.OP_EMPTYSET && nonEmpty(r_left)) {
				return CLAtomicExpression.FALSE;
			}
		}

		// image properties
		if (getTag() == alphabet.IMAGE) {
			if (r_left.getTag() == alphabet.OP_EMPTYSET || r_left.getTag() == alphabet.OP_EMPTYSEQ) {
				return CLAtomicExpression.EMPTYSET;
			}
			if (r_right.getTag() == alphabet.OP_EMPTYSET || r_right.getTag() == alphabet.OP_EMPTYSEQ) {
				return CLAtomicExpression.EMPTYSET;
			}

			// image of an override with constant function
			if (ctx != null) {
				final Map<String, CLExpression> map_right = r_right.match(tem("x", alphabet.SETC, true), ctx);
				if (map_right != null) {
					final Map<String, CLExpression> map_left = r_left.match(ovr(var("f"), tem("y", alphabet.SETC, true)), ctx);
					if (map_left != null) {
						final CLExpression func = map_left.get("f");
						final CLMultiExpression setc_image = (CLMultiExpression) map_right.get("x");
						final CLMultiExpression ovr_func = (CLMultiExpression) map_left.get("y");
						if (matches_all(ovr_func, setc_image)) {
							final CLExpression expr = image(ovr_func, setc_image);
							expr.type(ctx, getType());
							return expr;
						} else if (matches_none(ovr_func, setc_image)) {
							final CLExpression expr = image(func, setc_image);
							expr.type(ctx, getType());
							return expr;
						}
					}
				}
			}
		}

		if (r_left != left || r_right != right) {
			final CLBinaryExpression r = new CLBinaryExpression(getTag(), r_left, r_right, getLocation());
			if (ctx != null) {
				r.type(ctx, getType());
			}

			return r;
		}
		return this;
	}

	private boolean matches_all(CLMultiExpression ovr_func, CLMultiExpression set) {
		final List<CLExpression> domain = new ArrayList<>(ovr_func.getParts().size());

		for (final CLExpression e : ovr_func.getParts()) {
			if (e.getTag() == alphabet.OP_MAP) {
				final CLBinaryExpression map = (CLBinaryExpression) e;
				domain.add(map.left);
			} else {
				return false;
			}
		}

		return domain.containsAll(set.getParts().getParts());
	}

	private boolean matches_none(CLMultiExpression ovr_func, CLMultiExpression set) {
		final List<CLExpression> domain = new ArrayList<>(ovr_func.getParts().size());

		for (final CLExpression e : ovr_func.getParts()) {
			if (e.getTag() == alphabet.OP_MAP) {
				final CLBinaryExpression map = (CLBinaryExpression) e;
				domain.add(map.left);
			} else {
				return false;
			}
		}

		for (final CLExpression e : set.getParts().getParts()) {
			if (domain.contains(e)) {
				return false;
			}
		}

		return true;
	}

	private boolean nonEmpty(CLExpression exp) {
		if (exp.getTag() == alphabet.OP_OVR || exp.getTag() == alphabet.OP_UNION) {
			final CLMultiExpression multi = (CLMultiExpression) exp;
			for (final CLExpression e : multi.getParts()) {
				if (nonEmpty(e)) {
					return true;
				}
			}
		} else if (exp.getTag() == alphabet.SETC || exp.getTag() == alphabet.NAT || exp.getTag() == alphabet.NAT1
				|| exp.getTag() == alphabet.BOOL || exp.getTag() == alphabet.OP_MAP) {
			return true;
		} else if (exp.getTag() == alphabet.OP_SET || exp.getTag() == alphabet.OP_SEQ || exp.getTag() == alphabet.OP_DOM
				|| exp.getTag() == alphabet.OP_RAN || exp.getTag() == alphabet.OP_CONVERSE) {
			final CLUnaryExpression unary = (CLUnaryExpression) exp;
			return nonEmpty(unary.getArgument());
		} else if (exp.getTag() == alphabet.OP_PINJ || exp.getTag() == alphabet.OP_PFUN || exp.getTag() == alphabet.OP_REL
				|| exp.getTag() == alphabet.OP_CART) {
			final CLBinaryExpression binary = (CLBinaryExpression) exp;
			return nonEmpty(binary.getLeft()) && nonEmpty(binary.getRight());
		}

		return false;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		final CLIdentifier x = left.resolveIdentifiers(name);
		if (x != null) {
			return x;
		} else {
			return right.resolveIdentifiers(name);
		}
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			left.visit(visitor, userobject);
			right.visit(visitor, userobject);
		}
	}

	private int hashcode = 0;

	@Override
	public int hashCode() {
		int result = hashcode;
		if (result == 0) {
			final int prime = 31;
			result = super.hashCode();
			result = prime * result + (left == null ? 0 : left.hashCode());
			result = prime * result + (right == null ? 0 : right.hashCode());
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
		final CLBinaryExpression other = (CLBinaryExpression) obj;
		if (!left.equals(other.left)) {
			return false;
		}
		if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isImmutable() {
		return left.isImmutable() && right.isImmutable();
	}

	@Override
	public int getPriority() {
		switch (getTag()) {
		case alphabet.OP_PINJ:
		case alphabet.OP_REL:
		case alphabet.OP_PFUN:
			return 1;
		case alphabet.OP_CART:
			return 0;
		case alphabet.IMAGE:
			return 1;
		case alphabet.OP_MAP:
			return 15;
		case alphabet.OP_IMPLIES:
			return 30;
		case alphabet.OP_LEQ:
		case alphabet.OP_LSS:
		case alphabet.OP_GEQ:
		case alphabet.OP_GRT:
			return 10;
		case alphabet.OP_IN:
		case alphabet.OP_NOTIN:
			return 9;
		case alphabet.OP_MOD:
			return 8;
		case alphabet.OP_NEQ:
		case alphabet.OP_EQL:
			return 1;
		case alphabet.OP_DOMRES:
		case alphabet.OP_DOMSUB:
		case alphabet.OP_FCOMP:
		case alphabet.OP_DIRECT_PRODUCT:
		case alphabet.OP_RANRES:
		case alphabet.OP_RANSUB:
		case alphabet.OP_RANGE:
			return 5;
		case alphabet.OP_SUBSET:
		case alphabet.OP_SUBSETEQ:
			return 9;

		}
		return 20;
	}

	@Override
	public CLExpression deepCopy() {
		return new CLBinaryExpression(getTag(), left.deepCopy(), right.deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLBinaryExpression(getTag(), left, right, getLocation());
	}

	@Override
	public Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		if (isCommutative()) {
			return _match_commutative(template, context);
		} else {
			return _match_non_commutative(template, context);
		}
	}

	private Map<String, CLExpression> _match_non_commutative(CLExpression template, TypingContext context) {
		final CLBinaryExpression other = (CLBinaryExpression) template;
		final Map<String, CLExpression> rleft = left.match(other.left, context);
		if (rleft == null) {
			return null;
		}
		final Map<String, CLExpression> rright = right.match(other.right, context);
		if (rright == null) {
			return null;
		}
		if (rleft.isEmpty() && rright.isEmpty()) {
			return Collections.emptyMap();
		} else {
			if (match_compatible_results(rleft, rright)) {
				final Map<String, CLExpression> map = new HashMap<>();
				map.putAll(rleft);
				map.putAll(rright);
				return map;
			}
			return null;
		}
	}

	private Map<String, CLExpression> _match_commutative(CLExpression template, TypingContext context) {
		final CLBinaryExpression other = (CLBinaryExpression) template;

		final Map<String, CLExpression> m1L = left.match(other.left, context);
		final Map<String, CLExpression> m1R = right.match(other.right, context);
		if (m1L != null && m1R != null) {
			final Map<String, CLExpression> map = new HashMap<>(m1L.size() + m1R.size());
			map.putAll(m1L);
			map.putAll(m1R);
			return map;
		}

		final Map<String, CLExpression> m2L = right.match(other.left, context);
		final Map<String, CLExpression> m2R = left.match(other.right, context);
		if (m2L != null && m2R != null) {
			final Map<String, CLExpression> map = new HashMap<>(m2L.size() + m2R.size());
			map.putAll(m2L);
			map.putAll(m2R);
			return map;
		}

		return null;
	}

	@Override
	public String compile(SDARuntimeExecutionContext typingContext) throws CLNonExecutableException {
		final String cl = left.compile(typingContext);
		final String cr = right.compile(typingContext);

		switch (getTag()) {
		case alphabet.OP_PINJ:
		case alphabet.OP_REL:
		case alphabet.OP_PFUN:
			throw new CLNonExecutableException(this);
		case alphabet.OP_CART:
			return "(" + cl + ").times(" + cr + ")";
		case alphabet.IMAGE:
			if (right.isSingletonSet()) {
				final String crs = right.asSingletonSet().compile(typingContext);
				return CLUtils.compilationTypeCoercion("(" + cl + ").toRel().imageSingleton(" + crs + ")", getType());
			} else {
				return CLUtils.compilationTypeCoercion("(" + cl + ").toRel().image(" + cr + ")", getType());
			}
		case alphabet.OP_MAP:
			return "BMap_make(" + cl + ", " + cr + ")";
		case alphabet.OP_IMPLIES:
			return "!(" + cl + ") || (" + cl + " && " + cr + ")";
		case alphabet.OP_LEQ:
			return "(" + cl + ") <= (" + cr + ")";
		case alphabet.OP_LSS:
			return "(" + cl + ") < (" + cr + ")";
		case alphabet.OP_GEQ:
			return "(" + cl + ") >= (" + cr + ")";
		case alphabet.OP_GRT:
			return "(" + cl + ") > (" + cr + ")";
		case alphabet.OP_IN:
			return "(" + cr + ").contains(" + cl + ")";
		case alphabet.OP_NOTIN:
			return "!(" + cr + ").contains(" + cl + ")";
		case alphabet.OP_MOD:
			return "(" + cl + ") % (" + cr + ")";
		case alphabet.OP_NEQ:
			return "inequality(" + cl + ", " + cr + ")";
		case alphabet.OP_EQL:
			return "equality(" + cl + ", " + cr + ")";
		case alphabet.OP_DOMRES:
			return "(" + cr + ").toRel().domRestr(" + cl + ")";
		case alphabet.OP_DOMSUB:
			return "(" + cr + ").toRel().domSubtr(" + cl + ")";
		case alphabet.OP_FCOMP:
			return "(" + cl + ").toRel().forwardcomp(" + cr + ".toRel())";
		case alphabet.OP_RANRES:
			return "(" + cl + ").toRel().ranRestr(" + cr + ")";
		case alphabet.OP_RANSUB:
			return "(" + cl + ").toRel().ranSubtr(" + cr + ")";
		case alphabet.OP_RANGE:
			return " mkRange(" + cl + ", " + cr + ")";
		case alphabet.OP_SUBSET:
			return "(" + cr + ").containsStrictly(" + cl + ")";
		case alphabet.OP_SUBSETEQ:
			return "(" + cr + ").contains(" + cl + ")";
		case alphabet.OP_DIRECT_PRODUCT:
			return "(" + cr + ").toFun().directproduct(" + cl + ".toFun())";
		}
		throw new CLNonExecutableException(this);
	}

}
