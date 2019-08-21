package uk.ac.ncl.prime.sim.lang.model;

import java.util.HashSet;

import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeAny;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

/**
 * A deterministic assignment of a concrete or an auxiliary variable
 */
public class CLAssignmentStatement extends CLSubstitution {
	private final String id;
	private final CLType type;
	private final SYMBOL_CLASS sclass;
	private final CLExpression value;

	private CLExpression predicate; // assignment predicate

	public CLAssignmentStatement(String id, CLType type, CLExpression value, SYMBOL_CLASS sclass, SourceLocation location) {
		super(alphabet.BCMEQ, location);
		this.id = id;
		this.type = type;
		this.value = value;
		this.sclass = sclass;
	}

	public String getVariableName() {
		return id;
	}

	public CLExpression getValue() {
		return value;
	}

	@Override
	public void type(TypingContext ctx) {
		CLUtils.identifierNotPrimed(this, id, ctx);

		CLType t = ctx.getType(id);

		if (!(type instanceof CLTypeAny)) {

			if (t == null) {
				ctx.addSymbol(id, type, sclass);
				t = type;
			} else if (!t.equals(type)) {
				ctx.getValidation().addError(this, "Redefinition of identifier " + id);
				return;
			} else {
				ctx.getValidation().addWarning(this, "Superfluous type annotation for " + id);
			}
		}

		if (sclass.isConstant() && t != null) {
			if (!ctx.getSymbolClass(id).isConstant()) {
				ctx.getValidation().addError(this, "Mutable identifier " + id + " cannot be redeclared as a constant");
			} else {
				ctx.getValidation().addWarning(this, "Superfluous const annotation for " + id);
			}
		}

		if (t == null && type instanceof CLTypeAny) {
			t = type;
			ctx.addSymbol(id, type, sclass);
		}

		if (t == null) {
			ctx.getValidation().addError(this, "Unknown identifier " + id + " in assigment");
		} else if (ctx.isImmutable(id)) {
			ctx.getValidation().addError(this, "Cannot assign immutable identifier " + id);
		} else {
			value.type(ctx, t);
			CLUtils.checkNoPrimedIdentifiers(this, value, ctx);
		}

//		if (!ctx.getSymbolClass(id).isAuxiliary())
//			CLUtils.checkNoAuxIdentifiers(this, value, ctx);

		final String primed = CLIdentifier.makeIdentifierName(id, null, true);
		final TypingContext ctx1 = new TypingContext(ctx);
		ctx1.addPrimed(id);

		predicate = new CLBinaryExpression(alphabet.OP_EQL, new CLIdentifier(primed), value, getLocation());
		predicate.type(ctx1, CLTypeBool.INSTANCE);

		super.type(ctx);
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		context.setValue(id, value.getValue(context));
	}

	@Override
	public void buildUpdatedVariables() {
		updatedvariables = new HashSet<>(1);
		updatedvariables.add(id);
	}

	@Override
	public String asString(int tabs) {
		return getTabs(tabs) + id + " = " + value.asString() + "; " + ppLocation();
	}

	@Override
	public CLExpression getSubstitutionPredicate() {
		return predicate;
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		value.visit(visitor, userobject);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
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
		final CLAssignmentStatement other = (CLAssignmentStatement) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
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
		return new CLAssignmentStatement(id, type, value.deepCopy(), sclass, getLocation());
	}
}
