package uk.ac.ncl.prime.sim.lang.model;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeBool;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLAssertStatement extends CLStatement implements ICLGuardedStatement {

	private final CLExpression condition;

	public CLAssertStatement(CLExpression condition, SourceLocation location) {
		super(alphabet.ASSERTION, location);
		this.condition = condition;
	}

	public CLExpression getCondition() {
		return condition;
	}

	@Override
	public void type(TypingContext ctx) {
		super.type(ctx);
		condition.type(ctx, CLTypeBool.INSTANCE);
		CLUtils.checkNoPrimedIdentifiers(this, condition, ctx);
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		// do nothing
	}

	@Override
	public String asString(int tabs) {
		return getTabs(tabs) + "[" + condition.asString() + "]";
	}

	@Override
	public void doExecuteSymbolically(SymbolicExecutionContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildUpdatedVariables() {
		updatedvariables = CLStatement.NO_UPDATED_VARIABLES;
	}

	@Override
	public CLExpression getGuardingCondition() {
		return condition;
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		condition.visit(visitor, userobject);
	}

	@Override
	public void visitStatements(ICLStatementVisitor visitor, Object userobject) {
		visitor.visit(this, userobject);
	}

	@Override
	public CLStatement deepCopy() {
		return new CLAssertStatement(condition.deepCopy(), getLocation());
	}

}
