package uk.ac.ncl.prime.sim.lang.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import uk.ac.ncl.prime.sim.lang.CLAtomicExpression;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLPowerType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLSetAssignmentStatement extends CLSubstitution {
	private final String id;
	private CLExpression setAdd = CLAtomicExpression.EMPTYSET;
	private CLExpression setRemove = CLAtomicExpression.EMPTYSET;

	private CLExpression ovr_expr = null;
	private CLExpression predicate; // assignment predicate

	protected CLSetAssignmentStatement(CLExpression setAdd, CLExpression setRemove, SourceLocation location, String id) {
		super(alphabet.BCMEQ, location);
		this.id = id;
		this.setAdd = setAdd;
		this.setRemove = setRemove;
		buildOvrExpression(setAdd, setRemove, id);
	}

	public CLSetAssignmentStatement(String id, CLExpression index, CLExpression value, SourceLocation location) {
		super(alphabet.BCMEQ, location);
		this.id = id;
		if (value == CLAtomicExpression.TRUE) {
			setAdd = new CLMultiExpression(alphabet.SETC, getLocation(), index);
		} else {
			setRemove = new CLMultiExpression(alphabet.SETC, getLocation(), index);
		}

		buildOvrExpression(setAdd, setRemove, id);
	}

	private void buildOvrExpression(CLExpression setAdd, CLExpression setRemove, String id) {
		if (setAdd != CLAtomicExpression.EMPTYSET && setRemove == CLAtomicExpression.EMPTYSET) {
			ovr_expr = new CLMultiExpression(alphabet.OP_UNION, new CLIdentifier(id), setAdd, getLocation());
		} else if (setAdd == CLAtomicExpression.EMPTYSET && setRemove != CLAtomicExpression.EMPTYSET) {
			ovr_expr = new CLMultiExpression(alphabet.OP_SETMINUS, new CLIdentifier(id), setRemove, getLocation());
		} else {
			ovr_expr = new CLMultiExpression(alphabet.OP_UNION, new CLIdentifier(id), setAdd, getLocation());
			ovr_expr = new CLMultiExpression(alphabet.OP_SETMINUS, ovr_expr, setRemove, getLocation());
		}
	}

	public String getId() {
		return id;
	}

	public Collection<CLExpression> getAddedElements() {
		if (setAdd.getTag() == alphabet.OP_EMPTYSET) {
			return Collections.emptySet();
		} else {
			return ((CLMultiExpression) setAdd).getParts().getParts();
		}
	}

	public Collection<CLExpression> getRemovedElements() {
		if (setRemove.getTag() == alphabet.OP_EMPTYSET) {
			return Collections.emptySet();
		} else {
			return ((CLMultiExpression) setRemove).getParts().getParts();
		}
	}

	@Override
	public void type(TypingContext ctx) {
		final CLType t = ctx.getType(id);

		if (t == null) {
			ctx.getValidation().addError(this, "Unknown identifier in assigment");
			return;
		}

		if (ctx.isImmutable(id)) {
			ctx.getValidation().addError(this, "Cannot assign immutable identifier");
			return;
		}

		if (!t.isSet()) {
			ctx.getValidation().addError(this, "Subscribed identifier is not of set type");
			return;
		}

		setAdd.type(ctx, new CLPowerType(t.baseType()));
		setRemove.type(ctx, new CLPowerType(t.baseType()));

		// if (ctx.getSymbolClass(id).isAuxiliary())
		// CLUtils.checkNoAuxIdentifiers(this, value, ctx);

		ovr_expr.type(ctx, t);

		final String primed = CLIdentifier.makeIdentifierName(id, null, true);
		final TypingContext ctx1 = new TypingContext(ctx);
		ctx1.addPrimed(id);

		predicate = new CLBinaryExpression(alphabet.OP_EQL, new CLIdentifier(primed), ovr_expr, getLocation());
		predicate.type(ctx1, CLTypeBool.INSTANCE);

		super.type(ctx);
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		throw new CLExecutionException("Not implemented");
	}

	@Override
	public void buildUpdatedVariables() {
		updatedvariables = new HashSet<>(1);
		updatedvariables.add(id);
	}

	@Override
	public String asString(int tabs) {
		return getTabs(tabs) + id + " = " + ovr_expr.asString() + "; " + ppLocation();
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		setAdd.visit(visitor, userobject);
		setRemove.visit(visitor, userobject);
	}

	@Override
	public CLExpression getSubstitutionPredicate() {
		return predicate;
	}

	private static CLExpression makeSet(Collection<CLExpression> list, SourceLocation loc) {
		if (list.isEmpty()) {
			return CLAtomicExpression.EMPTYSET;
		} else {
			return new CLMultiExpression(alphabet.SETC, list, loc);
		}
	}

	private static Collection<CLExpression> negative(CLSetAssignmentStatement f) {
		if (f.setAdd.getTag() == alphabet.SETC) {
			final CLMultiExpression r = (CLMultiExpression) f.setAdd;
			return r.getParts().getParts();
		} else {
			return Collections.emptySet();
		}
	}

	private static Collection<CLExpression> positive(CLSetAssignmentStatement f) {
		if (f.setRemove.getTag() == alphabet.SETC) {
			final CLMultiExpression r = (CLMultiExpression) f.setRemove;
			return r.getParts().getParts();
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (setAdd == null ? 0 : setAdd.hashCode());
		result = prime * result + (setRemove == null ? 0 : setRemove.hashCode());
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
		final CLSetAssignmentStatement other = (CLSetAssignmentStatement) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (setAdd == null) {
			if (other.setAdd != null) {
				return false;
			}
		} else if (!setAdd.equals(other.setAdd)) {
			return false;
		}
		if (setRemove == null) {
			if (other.setRemove != null) {
				return false;
			}
		} else if (!setRemove.equals(other.setRemove)) {
			return false;
		}
		return true;
	}

	@Override
	public void visitStatements(ICLStatementVisitor visitor, Object userobject) {
		visitor.visit(this, userobject);
	}

	@Override
	public CLStatement deepCopy() {
		return new CLSetAssignmentStatement(setAdd.deepCopy(), setRemove.deepCopy(), getLocation(), id);
	}
}
