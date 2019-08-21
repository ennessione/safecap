package uk.ac.ncl.prime.sim.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLSeqType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLMultiExpression extends CLExpression {
	private final CLCollection<CLExpression> parts;

	public CLMultiExpression(int tag, CLCollection<CLExpression> parts, SourceLocation location) {
		super(tag, location);
		assert tag == alphabet.SETC || tag == alphabet.SEQC || parts.size() > 1;
		this.parts = parts;
		flatten();
		sort();
	}

//	public CLMultiExpression(int tag, CLCollection<CLExpression> parts) {
//		super(tag);
//		assert parts.size() > 0;
//		this.parts = parts;
//		flatten();
//		sort();
//	}
//
//	public CLMultiExpression(int tag, Collection<CLExpression> parts, SourceLocation location) {
//		super(tag);
//		assert parts.size() > 0;
//		this.parts = new CLCollection<CLExpression>(parts);
//		flatten();
//		sort();
//	}

	public CLMultiExpression(int tag, Collection<CLExpression> parts, SourceLocation location) {
		super(tag, location);
		assert tag == alphabet.SETC || tag == alphabet.SEQC || parts.size() > 1;
		this.parts = new CLCollection<>(parts);
		flatten();
		sort();
	}

//	public CLMultiExpression(int tag, CLExpression part1, CLExpression part2) {
//		super(tag);
//		parts = new CLCollection<CLExpression>(2);
//		parts.addPart(part1);
//		parts.addPart(part2);
//		flatten();
//		sort();
//	}

	public CLMultiExpression(int tag, CLExpression part1, CLExpression part2, SourceLocation location) {
		super(tag, location);
		parts = new CLCollection<>(2);
		parts.addPart(part1);
		parts.addPart(part2);
		flatten();
		sort();
	}

	public CLMultiExpression(int tag, SourceLocation location, CLExpression... meparts) {
		super(tag, location);

		assert tag == alphabet.SETC || tag == alphabet.SEQC || meparts.length > 1;

		parts = new CLCollection<>(meparts.length);
		for (final CLExpression e : meparts) {
			parts.addPart(e);
		}
		flatten();
		sort();
	}

	private void flatten() {
		if (isDistributive()) {
			final List<CLExpression> newList = new ArrayList<>();
			for (final CLExpression e : parts) {
				if (e == null) {
					return;
				} else if (e.getTag() == getTag()) {
					for (final CLExpression e2 : ((CLMultiExpression) e).parts.getParts()) {
						newList.add(e2);
					}
				} else {
					newList.add(e);
				}

			}

			parts.getParts().clear();
			parts.getParts().addAll(newList);
		}
	}

	private void sort() {
		if (isCommutative() && !isSensitive()) {
			Collections.sort(parts.getParts(), new Comparator<CLExpression>() {
				@Override
				public int compare(CLExpression arg0, CLExpression arg1) {
					if (arg0.getTag() != arg1.getTag()) {
						return arg0.getTag() - arg1.getTag();
					}
					final String l = arg0.asString();
					final String r = arg1.asString();
					if (l.length() == r.length()) {
						return arg0.asString().compareTo(arg1.asString());
					} else {
						return l.length() - r.length();
					}
				}

			});
		}
	}

	private boolean isDistributive() {
		switch (getTag()) {
		case alphabet.SEQC:
		case alphabet.SETC:
//		case alphabet.OP_SETMINUS:
			return false;
		default:
			return true;
		}
	}

	public CLCollection<CLExpression> getParts() {
		return parts;
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		switch (getTag()) {
		case alphabet.OP_PLUS:
		case alphabet.OP_MINUS:
		case alphabet.OP_MUL:
		case alphabet.OP_DIV: {
			assert parts.size() >= 2;
			for (int i = 0; i < parts.size(); i++) {
				parts.byIndex(i).type(ctx, CLTypeInteger.INSTANCE);
			}

			return CLTypeInteger.INSTANCE;
		}
		case alphabet.OP_AND:
		case alphabet.OP_OR: {
			assert parts.size() >= 2;
			for (int i = 0; i < parts.size(); i++) {
				parts.byIndex(i).type(ctx, CLTypeBool.INSTANCE);
			}

			return CLTypeBool.INSTANCE;
		}
		case alphabet.OP_UNION:
		case alphabet.OP_SETMINUS:
		case alphabet.OP_INTER: {
			assert parts.size() >= 2;
			final CLType f = parts.byIndex(0).type(ctx);

			if (f == null || !ctx.unify(f, CLPowerType.TEMPLATE)) {
				ctx.getValidation().addError(this, "Set type expected");
				return null;
			}

			for (int i = 1; i < parts.size(); i++) {
				parts.byIndex(i).type(ctx, f);
			}

			return f;
		}
		case alphabet.OP_OVR: {
			assert parts.size() >= 2;
			final CLType f = parts.byIndex(0).type(ctx);

			if (!ctx.unify(f, CLPowerType.RELATION)) {
				ctx.getValidation().addError(this, "Relation type expected");
				return null;
			}

			for (int i = 1; i < parts.size(); i++) {
				parts.byIndex(i).type(ctx, f);
			}

			return f;
		}
		case alphabet.SETC: {
			if (parts.size() == 0) {
				ctx.getValidation().addError(this, "Cannot type an empty set constructor");
				return null;
			}

			final CLType f = parts.byIndex(0).type(ctx);
			if (f != null) {
				for (int i = 1; i < parts.size(); i++) {
					parts.byIndex(i).type(ctx, f);
				}
				return new CLPowerType(f);
			} else {
				return null;
			}
		}
		case alphabet.SEQC: {
			if (parts.size() == 0) {
				ctx.getValidation().addError(this, "Cannot type an empty sequence constructor");
				return null;
			}

			final CLType f = parts.byIndex(0).type(ctx);
			if (f != null) {
				for (int i = 1; i < parts.size(); i++) {
					parts.byIndex(i).type(ctx, f);
				}
				return new CLSeqType(f);
			} else {
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
		switch (getTag()) {
		case alphabet.OP_PLUS: {
			int x = (Integer) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				x = x + (Integer) parts.byIndex(i).getValueInterpreted(context);
			}

			return new Integer(x);
		}
		case alphabet.OP_MINUS: {
			int x = (Integer) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				x = x - (Integer) parts.byIndex(i).getValueInterpreted(context);
			}

			return new Integer(x);
		}
		case alphabet.OP_MUL: {
			int x = (Integer) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				x = x * (Integer) parts.byIndex(i).getValueInterpreted(context);
			}

			return new Integer(x);
		}
		case alphabet.OP_DIV: {
			int x = (Integer) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				x = x / (Integer) parts.byIndex(i).getValueInterpreted(context);
			}

			return new Integer(x);
		}
		case alphabet.OP_AND: {
			boolean x = (Boolean) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				if (!x) {
					return false;
				}
				x = x && (Boolean) parts.byIndex(i).getValueInterpreted(context);
			}

			return new Boolean(x);
		}
		case alphabet.OP_OR: {
			boolean x = (Boolean) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				if (x) {
					return true;
				}
				x = x || (Boolean) parts.byIndex(i).getValueInterpreted(context);
			}

			return new Boolean(x);
		}
		case alphabet.OP_UNION: {
			BSet x = (BSet) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				x = x.union((BSet) parts.byIndex(i).getValueInterpreted(context));
			}

			return x;
		}
		case alphabet.OP_INTER: {
			BSet x = (BSet) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				if (x.isEmpty()) {
					return x;
				}
				x = x.intersect((BSet) parts.byIndex(i).getValueInterpreted(context));
			}

			return x;
		}
		case alphabet.OP_SETMINUS: {
			BSet x = (BSet) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				if (x.isEmpty()) {
					return x;
				}
				x = x.setminus((BSet) parts.byIndex(i).getValueInterpreted(context));
			}

			return x;
		}
		case alphabet.OP_OVR: {
			BRel x = (BRel) parts.byIndex(0).getValueInterpreted(context);
			for (int i = 1; i < parts.size(); i++) {
				x = x.ovr((BRel) parts.byIndex(i).getValueInterpreted(context));
			}

			return x;
		}
		case alphabet.SETC: {
			final List<Object> set = new ArrayList<>();
			for (int i = 0; i < parts.size(); i++) {
				set.add(parts.byIndex(i).getValueInterpreted(context));
			}

			return new BSet(set);
		}
		case alphabet.SEQC:
			final List<Object> seq = new ArrayList<>();

			for (int i = 0; i < parts.size(); i++) {
				seq.add(parts.byIndex(i).getValueInterpreted(context));
			}

			return BSeq.make(seq);
		}

		throw new CLExecutionException("Feature not implemented: " + getTag());
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		final Set<String> zz = new HashSet<>(parts.size());
		for (final CLExpression p : parts) {
			zz.addAll(p.getFreeIdentifiers());
		}

		if (zz.isEmpty()) {
			return noFreeIdentifiers; // forget zz
		} else {
			return zz;
		}
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		final Set<CLIdentifier> zz = new HashSet<>(parts.size());
		for (final CLExpression p : parts) {
			zz.addAll(p.getBoundIdentifiers());
		}

		if (zz.isEmpty()) {
			return noBoundIdentifiers;
		} else {
			return zz;
		}
	}

	@Override
	public String asString() {

		switch (getTag()) {
		case alphabet.OP_PLUS:
		case alphabet.OP_MINUS:
		case alphabet.OP_MUL:
		case alphabet.OP_DIV:
		case alphabet.OP_AND:
		case alphabet.OP_OR:
		case alphabet.OP_UNION:
		case alphabet.OP_OVR:
		case alphabet.OP_INTER: {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(" " + _opsymbol() + " ");
				}
				sb.append(ppBrackets(parts.byIndex(i), true));
			}
			return sb.toString();
		}
		case alphabet.OP_SETMINUS: {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(" " + _opsymbol() + " ");
				}
				sb.append(ppBrackets(parts.byIndex(i), false));
			}
			return sb.toString();
		}
		case alphabet.SETC: {
			final StringBuilder sb = new StringBuilder();
			sb.append("{");
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(parts.byIndex(i).asString());
			}
			sb.append("}");
			return sb.toString();
		}
		case alphabet.SEQC: {
			final StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(parts.byIndex(i).asString());
			}
			sb.append("]");
			return sb.toString();
		}
		default:
			return "?";
		}
	}

	public boolean isCommutative() {

		switch (getTag()) {
		case alphabet.OP_PLUS:
		case alphabet.OP_MUL:
		case alphabet.OP_AND:
		case alphabet.OP_OR:
		case alphabet.OP_UNION:
		case alphabet.OP_INTER:
			return true;
		}

		return false;
	}

	public boolean isSensitive() {

		switch (getTag()) {
		case alphabet.OP_AND:
			return true;
		}

		return false;
	}

	public String _opsymbol() {
		switch (getTag()) {
		case alphabet.OP_PLUS:
			return "+";
		case alphabet.OP_MINUS:
			return "-";
		case alphabet.OP_MUL:
			return "*";
		case alphabet.OP_DIV:
			return "/";
		case alphabet.OP_AND:
			return "and";
		case alphabet.OP_OR:
			return "or";
		case alphabet.OP_UNION:
			return "\\/";
		case alphabet.OP_INTER:
			return "/\\";
		case alphabet.OP_OVR:
			return "<+";
		case alphabet.OP_SETMINUS:
			return "\\";
		default:
			return "?(Multi " + getTag() + ")";
		}
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		final CLCollection<CLExpression> r_parts = new CLCollection<>(getTag());

		boolean isNew = false;
		for (final CLExpression a : parts) {
			final CLExpression a_r = a.rewrite(map);
			r_parts.addPart(a_r);
			isNew |= a != a_r;
		}

		if (isNew) {
			final CLMultiExpression me = new CLMultiExpression(getTag(), r_parts, getLocation());
			return me;
		} else {
			return this;
		}
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		final CLCollection<CLExpression> r_parts = new CLCollection<>(getTag());

		boolean isNew = false;
		boolean isBoolLiteral = true;
		boolean isIntLiteral = true;

		// simplify arguments
		for (final CLExpression a : parts) {
			final CLExpression a_r = a.simplify(ctx);
			r_parts.addPart(a_r);
			isNew |= a != a_r;

			final boolean isbool = CLUtils.isBoolLiteral(a_r);

			if (isbool) {
				final boolean x = CLUtils.getBooleanValue(a_r);
				switch (getTag()) {
				case alphabet.OP_AND: {
					if (!x) {
						return CLAtomicExpression.FALSE;
					}
					break;
				}
				case alphabet.OP_OR: {
					if (x) {
						return CLAtomicExpression.TRUE;
					}
					break;
				}
				}
			}

			isBoolLiteral &= isbool;
			isIntLiteral &= CLUtils.isNumberLiteral(a_r);
		}

		// simplify constant expressions
		if (isBoolLiteral) {
			switch (getTag()) {
			case alphabet.OP_AND: {
				boolean x = CLUtils.getBooleanValue(r_parts.byIndex(0));
				for (int i = 1; i < parts.size(); i++) {
					x = x && CLUtils.getBooleanValue(r_parts.byIndex(i));
				}

				return CLUtils.makeBoolLiteral(x);
			}
			case alphabet.OP_OR: {
				boolean x = CLUtils.getBooleanValue(r_parts.byIndex(0));
				for (int i = 1; i < parts.size(); i++) {
					x = x || CLUtils.getBooleanValue(r_parts.byIndex(i));
				}

				return CLUtils.makeBoolLiteral(x);
			}
			}
		} else if (isIntLiteral) {
			switch (getTag()) {
			case alphabet.OP_PLUS: {
				int x = CLUtils.getIntegerValue(r_parts.byIndex(0));
				for (int i = 1; i < parts.size(); i++) {
					x = x + CLUtils.getIntegerValue(r_parts.byIndex(i));
				}

				return new CLIntegerExpression(x);
			}
			case alphabet.OP_MINUS: {
				int x = CLUtils.getIntegerValue(r_parts.byIndex(0));
				for (int i = 1; i < parts.size(); i++) {
					x = x - CLUtils.getIntegerValue(r_parts.byIndex(i));
				}

				return new CLIntegerExpression(x);
			}
			case alphabet.OP_MUL: {
				int x = CLUtils.getIntegerValue(r_parts.byIndex(0));
				for (int i = 1; i < parts.size(); i++) {
					x = x * CLUtils.getIntegerValue(r_parts.byIndex(i));
				}

				return new CLIntegerExpression(x);
			}
			case alphabet.OP_DIV: {
				int x = CLUtils.getIntegerValue(r_parts.byIndex(0));
				boolean isPrecise = true;
				for (int i = 1; i < parts.size(); i++) {
					final int z = CLUtils.getIntegerValue(r_parts.byIndex(i));
					isPrecise &= isPreciseDivision(x, z);
					x = x / z;
				}

				if (isPrecise) {
					return new CLIntegerExpression(x);
				}
			}
			}
		}

		// general simplifications
		CLExpression sr = null;
		switch (getTag()) {
		case alphabet.OP_INTER:
			sr = simplify_INTER(r_parts, ctx);
			break;
		case alphabet.OP_OR:
			sr = simplify_OR(r_parts, ctx);
			break;
		case alphabet.OP_AND:
			sr = simplify_AND(r_parts, ctx);
			break;
		case alphabet.OP_SETMINUS:
			sr = simplify_SETMINUS(r_parts, ctx);
			break;
		}
		if (sr != null) {
			return sr;
		}

		if (isNew) {
			final CLMultiExpression me = new CLMultiExpression(getTag(), r_parts, getLocation());
			if (ctx != null) {
				me.type(ctx, super.getType());
			}
			return me;
		} else {
			return this;
		}
	}

	private CLExpression simplify_SETMINUS(CLCollection<CLExpression> r_parts, TypingContext ctx) {
		final CLExpression first = r_parts.byIndex(0);
		for (int i = 1; i < r_parts.size(); i++) {
			if (r_parts.byIndex(i).equals(first)) {
				return CLAtomicExpression.EMPTYSET;
			}
		}

		if (r_parts.size() == 2) {
			if (r_parts.byIndex(0).equals(r_parts.byIndex(1))) { // a / a
				return CLAtomicExpression.EMPTYSET;
			} else if (r_parts.byIndex(1).getTag() == alphabet.OP_UNION) {
				final CLMultiExpression me = (CLMultiExpression) r_parts.byIndex(1);
				if (me.getParts().contains(r_parts.byIndex(0))) { // a \ (a \/ ... )
					return CLAtomicExpression.EMPTYSET;
				}
			} else if (r_parts.byIndex(1).getTag() == alphabet.OP_SETMINUS) {
				final CLExpression template = setminus(var("a"), var("b"));
				final Map<String, CLExpression> map = r_parts.byIndex(1).match(template, ctx);
				if (map != null) {
					final CLExpression a = map.get("a");
					final CLExpression b = map.get("b");
					if (a.equals(r_parts.byIndex(0))) { // case of x \ (x \ z)
						return new CLMultiExpression(alphabet.OP_INTER, a, b, getLocation()).simplify(ctx);
					} else {
						final CLExpression x = r_parts.byIndex(0);
						return union(inter(x, b), setminus(x, a)).simplify(ctx);
					}
				}
			}
		}

		if (r_parts.byIndex(0).getTag() == alphabet.OP_UNION) {
			final CLMultiExpression me = (CLMultiExpression) r_parts.byIndex(0);

			final Collection<CLExpression> toRemove = new ArrayList<>();
			for (final CLExpression p : me.getParts()) {
				for (int i = 1; i < r_parts.size(); i++) {
					if (p.equals(r_parts.byIndex(i))) {
						toRemove.add(p);
					}
				}
			}

			// nothing removed, leave unchanged
			if (toRemove.isEmpty()) {
				return null;
			}

			// everything removed, empty set
			if (toRemove.size() == me.parts.size()) {
				return CLAtomicExpression.EMPTYSET;
			}

			final List<CLExpression> elements = new ArrayList<>(me.parts.getParts());
			elements.removeAll(toRemove);

			final List<CLExpression> ne = new ArrayList<>(r_parts.getParts());
			ne.remove(0);
			ne.removeAll(toRemove);
			// one left
			if (elements.size() == 1) {
				ne.add(0, elements.get(0));
				return CLUtils.buildMultiExpr(alphabet.OP_SETMINUS, ne, elements.get(0).getLocation());
			} else { // several left
				ne.add(CLUtils.buildMultiExpr(alphabet.OP_UNION, elements, elements.get(0).getLocation()));
				return CLUtils.buildMultiExpr(alphabet.OP_SETMINUS, ne, elements.get(0).getLocation());
			}

		}

		return null;
	}

	private CLExpression simplify_AND(CLCollection<CLExpression> r_parts, TypingContext ctx) {
		final Set<CLExpression> toRemove = new HashSet<>();
		int orig = r_parts.getParts().hashCode();
		for (final CLExpression e : r_parts) {
			if (e.getTag() == alphabet.OP_FALSE) {
				return CLAtomicExpression.FALSE;
			}
			if (e.getTag() == alphabet.OP_TRUE) {
				toRemove.add(e);
			}
		}
		r_parts.getParts().removeAll(toRemove);

		
		// detect redundant clauses
		r_parts.setParts(r_parts.getParts().stream().distinct().collect(Collectors.toList()));
		
		// detect conflicting clauses
		for (int i = 0; i < r_parts.size(); i++) {
			for (int j = i + 1; j < r_parts.size(); j++) {
				if (r_parts.getParts().get(i).equals(CLUtils.negate(r_parts.getParts().get(j)))) {
					return CLAtomicExpression.FALSE;
				}
			}
		}
		
		if (r_parts.size() == 0) {
			return CLAtomicExpression.TRUE;
		} else if (r_parts.size() == 1) {
			return r_parts.byIndex(0);
		} else if (orig != r_parts.getParts().hashCode()) {
			return CLUtils.buildConjunct(r_parts, getLocation());
		}

		return null;
	}

	private CLExpression simplify_OR(CLCollection<CLExpression> r_parts, TypingContext ctx) {
		final Set<CLExpression> toRemove = new HashSet<>();
		for (final CLExpression e : r_parts) {
			if (e.getTag() == alphabet.OP_TRUE) {
				return CLAtomicExpression.TRUE;
			}
			if (e.getTag() == alphabet.OP_FALSE) {
				toRemove.add(e);
			}
		}

		// detect redundant and conflicting clauses
		for (int i = 0; i < r_parts.size(); i++) {
			for (int j = i + 1; j < r_parts.size(); j++) {
				if (r_parts.getParts().get(i).equals(r_parts.getParts().get(j))) {
					toRemove.add(r_parts.getParts().get(i));
				} else if (r_parts.getParts().get(i).equals(CLUtils.negate(r_parts.getParts().get(j)))) {
					return CLAtomicExpression.TRUE;
				}
			}
		}

		r_parts.getParts().removeAll(toRemove);
		if (r_parts.size() == 0) {
			return CLAtomicExpression.FALSE;
		} else if (r_parts.size() == 1) {
			return r_parts.byIndex(0);
		} else if (!toRemove.isEmpty()) {
			return CLUtils.buildDisjunct(r_parts.getParts(), getLocation());
		}

		return null;
	}

	private CLExpression simplify_INTER(CLCollection<CLExpression> r_parts, TypingContext ctx) {
		for (final CLExpression e : parts) {
			if (e.getTag() == alphabet.OP_EMPTYSET) {
				return CLAtomicExpression.EMPTYSET;
			}
		}

		return null;
	}

	private boolean isPreciseDivision(int a, int b) {
		final double x = a / b;
		final double y = (double) a / (double) b;
		return x == y;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		for (final CLExpression a : parts) {
			final CLIdentifier x = a.resolveIdentifiers(name);
			if (x != null) {
				return x;
			}
		}
		return null;
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			for (final CLExpression a : parts) {
				a.visit(visitor, userobject);
			}
		}
	}

	@Override
	public boolean isImmutable() {
		return parts.isImmutable();
	}

	private int hashcode = 0;

	@Override
	public int hashCode() {
		int result = hashcode;
		if (result == 0) {
			final int prime = 31;
			result = super.hashCode();
			result = prime * result + (parts == null ? 0 : parts.hashCode());
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
		final CLMultiExpression other = (CLMultiExpression) obj;
		if (parts == null) {
			if (other.parts != null) {
				return false;
			}
		} else if (!parts.equals(other.parts)) {
			return false;
		}
		return true;
	}

	@Override
	public int getPriority() {
		switch (getTag()) {
		case alphabet.OP_PLUS:
		case alphabet.OP_MINUS:
			return 12;
		case alphabet.OP_MUL:
		case alphabet.OP_DIV:
			return 11;
		case alphabet.OP_AND:
			return 11;
		case alphabet.OP_OR:
			return 11;
		case alphabet.OP_UNION:
		case alphabet.OP_OVR:
		case alphabet.OP_SETMINUS:
		case alphabet.OP_INTER:
			return 5;
		case alphabet.SETC:
		case alphabet.SEQC:
			return 0;
		default:
			return 20;
		}
	}

	@Override
	public CLExpression deepCopy() {
		return new CLMultiExpression(getTag(), parts.deepCopy(), getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLMultiExpression(getTag(), parts, getLocation());
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
		final CLMultiExpression other = (CLMultiExpression) template;
		if (other.parts.size() != parts.size()) {
			return null;
		}

		Map<String, CLExpression> result = null;

		for (int i = 0; i < parts.size(); i++) {
			final Map<String, CLExpression> m = parts.byIndex(i).match(other.parts.byIndex(i), context);
			if (m == null) {
				return null;
			} else if (!m.isEmpty()) {
				if (result == null) {
					result = new HashMap<>(m);
				} else {
					if (!match_compatible_results(result, m)) {
						return null;
					}
					result.putAll(m);
				}
			}
		}

		if (result == null) {
			return Collections.emptyMap();
		}

		return result;
	}

	private Map<String, CLExpression> _match_commutative(CLExpression template, TypingContext context) {
		final CLMultiExpression other = (CLMultiExpression) template;
		if (other.parts.size() > parts.size()) {
			return null;
		}

		// TODO: match specific nodes first (starting with $)
		Collections.reverse(other.parts.getParts()); // quick and dirty fix

		Map<String, CLExpression> result = null;

		final List<CLExpression> used = new ArrayList<>(parts.size());
		for (final CLExpression z : other.parts) {
			boolean matched = false;
			for (final CLExpression x : parts) {
				if (!used.contains(x)) {
					final Map<String, CLExpression> m = x.match(z, context);
					if (m != null) {
						used.add(x);
						if (!m.isEmpty()) {
							if (result == null) {
								result = new HashMap<>(m);
							} else {
								result.putAll(m);
							}
						}
						matched = true;
						break;
					}
				}
			}
			if (!matched) {
				return null;
			}
		}

		if (result == null) {
			return Collections.emptyMap();
		}

		return result;
	}

	@Override
	public String compile(SDARuntimeExecutionContext typingContext) throws CLNonExecutableException {

		switch (getTag()) {
		case alphabet.OP_AND:
		case alphabet.OP_OR: {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(" " + _native_opsymbol() + " ");
				}
				sb.append("(" + parts.byIndex(i).compile(typingContext) + ")");
			}
			return sb.toString();
		}
		case alphabet.OP_UNION:
		case alphabet.OP_OVR:
		case alphabet.OP_SETMINUS:
		case alphabet.OP_INTER: {
			final StringBuilder sb = new StringBuilder();
			sb.append("(" + parts.byIndex(0).compile(typingContext) + ")." + _native_opsymbol() + "(");
			for (int i = 1; i < parts.size(); i++) {
				if (i > 1) {
					sb.append(", ");
				}
				sb.append(parts.byIndex(i).compile(typingContext));
			}
			sb.append(")");
			return sb.toString();
		}
		case alphabet.OP_PLUS:
		case alphabet.OP_MINUS:
		case alphabet.OP_MUL:
		case alphabet.OP_DIV: {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(" " + _native_opsymbol() + " ");
				}
				sb.append("(" + parts.byIndex(i).compile(typingContext) + ")");
			}
			return sb.toString();
		}
		case alphabet.SETC:
		case alphabet.SEQC: {
			final StringBuilder sb = new StringBuilder();
			sb.append("BSet_SETC(");
			for (int i = 0; i < parts.size(); i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(parts.byIndex(i).compile(typingContext));
			}
			sb.append(")");
			return sb.toString();
		}
		}
		throw new CLNonExecutableException(this);
	}

	public String _native_opsymbol() {
		switch (getTag()) {
		case alphabet.OP_PLUS:
			return "+";
		case alphabet.OP_MINUS:
			return "-";
		case alphabet.OP_MUL:
			return "*";
		case alphabet.OP_DIV:
			return "/";
		case alphabet.OP_AND:
			return "&&";
		case alphabet.OP_OR:
			return "||";
		case alphabet.OP_UNION:
			return "union";
		case alphabet.OP_INTER:
			return "inter";
		case alphabet.OP_OVR:
			return "ovr";
		case alphabet.OP_SETMINUS:
			return "setminus";
		default:
			return null;
		}
	}

}
