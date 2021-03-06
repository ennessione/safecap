package uk.ac.ncl.prime.sim.lang.model;

import java.util.HashSet;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLNonExecutableException;
import uk.ac.ncl.prime.sim.lang.CLParameter;
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
 * A non-deterministic assignment of an auxiliary variable
 */
public class CLNonDetAssignmentStatement extends CLSubstitution {
	private final CLCollection<CLParameter> ids;
	private final CLExpression predicate;
	private final SYMBOL_CLASS sclass;

	public CLNonDetAssignmentStatement(CLCollection<CLParameter> ids, CLExpression predicate, SYMBOL_CLASS sclass,
			SourceLocation location) {
		super(alphabet.BCMSUCH, location);
		this.ids = ids;
		this.predicate = predicate;
		this.sclass = sclass;
	}

	public CLExpression getPredicate() {
		return predicate;
	}

	@Override
	public void type(TypingContext ctx) {
		final TypingContext ctx1 = new TypingContext(ctx);

		for (final CLParameter pp : ids) {
			final String id = pp.getName();
			final CLType type = pp.getTypeSafely(ctx);
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

			if (t == null) {
				ctx.getValidation().addError(this, "Unknown identifier " + id + " in assigment");
			} else if (ctx.isImmutable(id)) {
				ctx.getValidation().addError(this, "Cannot assign immutable identifier " + id);
			} else {
				ctx1.addPrimed(id);
			}

//			if (!ctx.getSymbolClass(id).isAuxiliary())
//				ctx.getValidation().addError(this, "Non-deterministic assignment of a concrete variable");

			final String primed_id = CLIdentifier.makeIdentifierName(id, null, true);
			if (!predicate.getFreeIdentifiers().contains(primed_id)) {
				ctx.getValidation().addError(predicate, "Predicate must bind identifier '" + primed_id + "'");
			}

		}

		predicate.type(ctx1, CLTypeBool.INSTANCE);

		super.type(ctx);
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		throw new CLNonExecutableException(this);
	}

	@Override
	public void buildUpdatedVariables() {
		updatedvariables = new HashSet<>(ids.size());

		for (final CLParameter pp : ids) {
			updatedvariables.add(pp.getName());
		}
	}

	@Override
	public String asString(int tabs) {
		return getTabs(tabs) + ids.toString() + " :| " + predicate.asString() + "; " + ppLocation();
	}

	@Override
	public CLExpression getSubstitutionPredicate() {
		return predicate;
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		predicate.visit(visitor, userobject);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (ids == null ? 0 : ids.hashCode());
		result = prime * result + (predicate == null ? 0 : predicate.hashCode());
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
		final CLNonDetAssignmentStatement other = (CLNonDetAssignmentStatement) obj;
		if (ids == null) {
			if (other.ids != null) {
				return false;
			}
		} else if (!ids.equals(other.ids)) {
			return false;
		}
		if (predicate == null) {
			if (other.predicate != null) {
				return false;
			}
		} else if (!predicate.equals(other.predicate)) {
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
		return new CLNonDetAssignmentStatement(ids.deepCopy(), predicate.deepCopy(), sclass, getLocation());
	}
}
