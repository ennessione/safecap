package uk.ac.ncl.prime.sim.lang.model;

import java.util.HashSet;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLBinaryExpression;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLIdentifier;
import uk.ac.ncl.prime.sim.lang.CLMultiExpression;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLProductType;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLIndexedAssignmentStatement extends CLSubstitution {
	private final String id;
	private final CLCollection<CLExpression> maps;

	private CLExpression ovr_expr = null;
	private CLExpression predicate; // assignment predicate

	public CLIndexedAssignmentStatement(String id, CLExpression index, CLExpression value, SourceLocation location) {
		super(alphabet.BCMEQ, location);
		this.id = id;
		maps = new CLCollection<>(1);
		maps.addPart(new CLBinaryExpression(alphabet.OP_MAP, index, value, getLocation()));
		ovr_expr = make_ovr();
	}

	private CLIndexedAssignmentStatement(String id, CLCollection<CLExpression> maps, CLExpression ovr_expr, CLExpression predicate,
			SourceLocation location) {
		super(alphabet.BCMEQ, location);

		assert maps.size() > 0;

		this.id = id;
		this.maps = maps;
		this.ovr_expr = ovr_expr;
		this.predicate = predicate;
	}

	public List<CLExpression> getMaps() {
		return maps.getParts();
	}

	public String getId() {
		return id;
	}

	public CLExpression getOvrExpr() {
		return ovr_expr;
	}

	private CLExpression make_ovr() {
		final CLExpression map = new CLMultiExpression(alphabet.SETC, maps, getLocation());
		return new CLMultiExpression(alphabet.OP_OVR, new CLIdentifier(id), map, getLocation());
	}

	public CLIndexedAssignmentStatement(String id, CLExpression index, CLExpression value) {
		super(alphabet.BCMEQ, null);
		this.id = id;
		maps = new CLCollection<>(1);
		CLBase.add(new CLBinaryExpression(alphabet.OP_MAP, index, value, getLocation()));
		ovr_expr = make_ovr();
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

		if (!t.isSequence() && !t.isRelation()) {
			ctx.getValidation().addError(this, "Subscribed identifier is not of a sequence or set type");
			return;
		}

		final CLType mtype = new CLProductType(t.domType(), t.ranType());

		for (final CLExpression m : maps) {
			m.type(ctx, mtype);
			//
			// if (ctx.getSymbolClass(id).isAuxiliary())
			// CLUtils.checkNoAuxIdentifiers(this, value, ctx);
		}

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

		throw new CLExecutionException("Non executable construct");
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
		for (final CLExpression me : maps) {
			me.visit(visitor, userobject);
		}
	}

	@Override
	public CLExpression getSubstitutionPredicate() {
		return predicate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (maps == null ? 0 : maps.hashCode());
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
		final CLIndexedAssignmentStatement other = (CLIndexedAssignmentStatement) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (maps == null) {
			if (other.maps != null) {
				return false;
			}
		} else if (!maps.equals(other.maps)) {
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
		return new CLIndexedAssignmentStatement(id, maps.deepCopy(), ovr_expr.deepCopy(), predicate == null ? null : predicate.deepCopy(),
				getLocation());
	}
}
