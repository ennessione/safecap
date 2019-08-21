package uk.ac.ncl.prime.sim.lang.model;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLIfPart extends CLContainerStatement {
	private final CLExpression condition;
	private final CLStatement statement;

	public CLIfPart(CLExpression condition, CLStatement statement, SourceLocation location) {
		super(-1, location);
		this.condition = condition;
		this.statement = statement;
	}

	public CLIfPart(CLExpression condition, CLStatement statement) {
		super(-1, condition.getLocation().copyAndExtend(statement.getLocation()));
		this.condition = condition;
		this.statement = statement;
	}

	public CLExpression getCondition() {
		return condition;
	}

	public CLStatement getStatement() {
		return statement;
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		visitor.visit(condition, userobject);
		statement.visitExpressions(visitor, userobject);
	}

	@Override
	public void visitStatements(ICLStatementVisitor visitor, Object userobject) {
		statement.visitStatements(visitor, userobject);
	}

	@Override
	public void buildUpdatedVariables() {
		statement.buildUpdatedVariables();
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
	}

	@Override
	public CLStatement deepCopy() {
		return new CLIfPart(condition.deepCopy(), statement.deepCopy(), getLocation());
	}

	@Override
	public String asString(int tabs) {
		final StringBuilder sb = new StringBuilder();
		sb.append(getTabs(tabs));
		sb.append("if (" + condition.asString() + ")");
		if (statement instanceof CLBlockStatement && ((CLBlockStatement) statement).getStatements().size() > 1) {
			final CLBlockStatement block = (CLBlockStatement) statement;
			sb.append(" { \n");
			for (final CLStatement statement : block.getStatements()) {
				sb.append(statement.asString(tabs + 1));
				sb.append("\n");
			}
			sb.append(getTabs(tabs));
			sb.append("} ");
		} else {
			sb.append("\n");
			sb.append(statement.asString(tabs + 1));
		}
		return sb.toString();
	}

}
