package uk.ac.ncl.prime.sim.lang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BFun;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.common.RELATION_KIND;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLFunAppExpression extends CLExpression {
	protected CLExpression left;
	protected CLCollection<CLExpression> right;

	public CLFunAppExpression(CLExpression left, CLCollection<CLExpression> right, SourceLocation location) {
		super(alphabet.FAPP, location);
		this.left = left;
		this.right = right;
	}

	public CLFunAppExpression(CLExpression left, CLCollection<CLExpression> right) {
		super(alphabet.FAPP, null);
		this.left = left;
		this.right = right;
	}

	public CLExpression getLeft() {
		return left;
	}

	public CLCollection<CLExpression> getRight() {
		return right;
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		final CLType l = left.type(ctx);
		if (l instanceof CLPowerType) {
			final CLPowerType pp = (CLPowerType) l;
			if (pp.getBase() instanceof CLProductType) {
				final CLProductType pr = (CLProductType) pp.getBase();

				if (right.size() != 1) {
					ctx.getValidation().addError(this, "Functional application expects one argument");
				}
				final CLExpression right1 = right.byIndex(0);

				right1.type(ctx, pr.getLeft());

				// cover primitive case of constant function given by name
				if (left.isConstant(ctx) && left instanceof CLIdentifier) {
					final CLIdentifier idt = (CLIdentifier) left;
					final String name = idt.getName();
					final RELATION_KIND kind = ctx.getRelationKind(name);
					if (kind == null) {
						ctx.getValidation().addWarning(this, "Unknown relation kind for constant relation " + name);
					} else if (!kind.isFunction()) {
						ctx.getValidation().addError(this, "Constant relation " + name + " is not functional");
					} else if (!kind.isTotal()) {
						ctx.getValidation().addWarning(this, "Constant relation " + name + " is not total");
					}
				}

				return pr.getRight();
			} else {
				ctx.getValidation().addError(this, "Invalid function application context");
				return null;
			}
		} else if (l instanceof CLSeqType) {
			final CLSeqType st = (CLSeqType) l;

			if (right.size() != 1) {
				ctx.getValidation().addError(this, "Functional application expects one argument");
			}
			final CLExpression right1 = right.byIndex(0);

			right1.type(ctx, CLTypeInteger.INSTANCE);

			return st.getBase();
		} else {
			ctx.getValidation().addError(this, "Invalid function application context");
			return null;
		}
	}

	public boolean hasSideEffects() {
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		Object l = left.getValueInterpreted(context);

		if (!(l instanceof BFun)) {
			l = toFunction(l);
		}

		Object value = null;
		if (l instanceof BFun && right.size() == 1) {
			try {
				final BFun x = (BFun) l;
				value = right.getParts().get(0).getValueInterpreted(context);
				return x.fapp(value);
			} catch (final InvalidSetOpException e) {
				if (value == null) {
					throw new InvalidSetOpException(e.getMessage() + " for " + left.asString() + "(" + right.toString() + ")");
				} else {
					throw new CLExecutionException(
							"Invalid operator execution context:" + value.toString() + " not in domain of " + l.toString());
				}
			}
		} else {
			throw new CLExecutionException("Invalid operator execution context: " + l.toString() + " is not a function (but "
					+ l.getClass().getSimpleName() + ")");
		}

	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		final Set<String> freeIdentifiers = new HashSet<>(10);
		freeIdentifiers.addAll(left.getFreeIdentifiers());

		for (final CLExpression p : right) {
			freeIdentifiers.addAll(p.getFreeIdentifiers());
		}

		return freeIdentifiers;
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		final Set<CLIdentifier> boundIdentifiers = new HashSet<>(10);
		boundIdentifiers.addAll(left.getBoundIdentifiers());

		for (final CLExpression p : right) {
			boundIdentifiers.addAll(p.getBoundIdentifiers());
		}

		return boundIdentifiers;
	}

	@Override
	public String asString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(ppBrackets(left, false));
		sb.append("(");

		for (int i = 0; i < right.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(right.byIndex(i).asString());
		}

		sb.append(")");
		return sb.toString();
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		final CLExpression r_left = left.rewrite(map);

		final CLCollection<CLExpression> r_right = new CLCollection<>(getTag());

		boolean isNew = r_left != left;
		for (final CLExpression a : right) {
			final CLExpression a_r = a.rewrite(map);
			r_right.addPart(a_r);
			isNew |= a != a_r;
		}

		if (isNew) {
			final CLFunAppExpression fa = new CLFunAppExpression(r_left, r_right, getLocation());
			return fa;
		} else {
			return this;
		}
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {

		if (left.getTag() == alphabet.OP_OVR && right.size() == 1) {
			// match left with a <+ b where b is a constant set comprehension
			final CLExpression templateLeft = ovr(var("a"), tem("b", alphabet.SETC, true));

			// match right with a constant identifier
			final CLExpression templateRight = tem("a", alphabet.ID, true);

			final Map<String, CLExpression> mapr = right.getParts().get(0).match(templateRight, ctx);
			if (mapr != null) {
				final Map<String, CLExpression> mapl = left.match(templateLeft, ctx);
				if (mapl != null) {
					final CLExpression repl = mapr.get("a");
					final CLExpression ff = mapl.get("a");
					final CLMultiExpression setc = (CLMultiExpression) mapl.get("b");
					for (final CLExpression _p : setc.getParts()) {
						final CLBinaryExpression p = (CLBinaryExpression) _p;
						if (p.getLeft().equals(repl)) {
							return p.getRight();
						}
					}

					final CLFunAppExpression fa = new CLFunAppExpression(ff, new CLCollection<>(repl), getLocation());
					fa.type(ctx);
					return fa;
				}
			}
		}

		final CLExpression r_left = left.simplify(ctx);

		final CLCollection<CLExpression> r_right = new CLCollection<>(getTag());

		boolean isNew = r_left != left;
		for (final CLExpression a : right) {
			final CLExpression a_r = a.simplify(ctx);
			r_right.addPart(a_r);
			isNew = a != a_r;
		}

		if (isNew) {
			final CLFunAppExpression fa = new CLFunAppExpression(r_left, r_right, getLocation());
			if (ctx != null) {
				fa.type(ctx, super.getType());
			}
			return fa;
		} else {
			return this;
		}
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		CLIdentifier x = left.resolveIdentifiers(name);
		if (x != null) {
			return x;
		}

		for (final CLExpression a : right) {
			x = a.resolveIdentifiers(name);
			if (x != null) {
				return x;
			}
		}
		return null;
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			left.visit(visitor, userobject);
			for (final CLExpression a : right) {
				a.visit(visitor, userobject);
			}
		}
	}

	@Override
	public boolean isImmutable() {
		return !hasSideEffects() && left.isImmutable() && right.isImmutable();
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
		final CLFunAppExpression other = (CLFunAppExpression) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public CLExpression deepCopy() {
		return new CLFunAppExpression(left.deepCopy(), right.deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLFunAppExpression(left, right, getLocation());

	}

	@Override
	protected Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		final CLFunAppExpression other = (CLFunAppExpression) template;
		if (other.right.size() != right.size()) {
			return null;
		}

		final Map<String, CLExpression> main = left.match(other.left, context);
		if (main == null) {
			return null;
		}

		Map<String, CLExpression> result = null;
		for (int i = 0; i < right.size(); i++) {
			final Map<String, CLExpression> m = right.byIndex(i).match(other.right.byIndex(i), context);
			if (m == null) {
				return null;
			} else if (!m.isEmpty()) {
				if (result == null) {
					result = new HashMap<>(m);
				} else {
					result.putAll(m);
				}
			}
		}

		if (result == null) {
			return main;
		}

		result.putAll(main);

		return result;
	}

	@Override
	public String compile(SDARuntimeExecutionContext typingContext) throws CLNonExecutableException {
		final StringBuilder sb = new StringBuilder();
		sb.append("(" + left.compile(typingContext) + ").toFun().fapp(");
		for (int i = 0; i < right.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(right.byIndex(i).compile(typingContext));
		}
		sb.append(")");
		return CLUtils.compilationTypeCoercion(sb.toString(), getType());
	}

}
