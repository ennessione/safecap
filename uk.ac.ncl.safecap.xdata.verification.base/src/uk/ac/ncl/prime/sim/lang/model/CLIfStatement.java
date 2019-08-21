package uk.ac.ncl.prime.sim.lang.model;

import java.util.HashSet;

import uk.ac.ncl.prime.sim.lang.CLBase;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionConditional;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLIfStatement extends CLStatement {
	private final CLCollection<CLIfPart> parts;
	private final CLStatement body_else;

	public CLIfStatement(CLExpression condition, CLStatement body_if, CLStatement body_else, SourceLocation location) {
		super(alphabet.IF, location);
		parts = new CLCollection<>(1);
		parts.addPart(new CLIfPart(condition, body_if));
		this.body_else = body_else;
	}

	public CLIfStatement(CLCollection<CLIfPart> parts, CLStatement body_else, SourceLocation location) {
		super(alphabet.IF, location);
		this.parts = parts;
		this.body_else = body_else;
	}

	public CLCollection<CLIfPart> getParts() {
		return parts;
	}

	public CLStatement getBodyElse() {
		return body_else;
	}

	@Override
	public void type(TypingContext ctx) {
		super.type(ctx);

		for (final CLIfPart part : parts) {
			part.type(ctx);
		}

		if (body_else != null) {
			body_else.type(ctx);
		}
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {

		for (final CLIfPart part : parts) {
			if (part.getCondition().isTrue(context)) {
				part.getStatement().execute(context);
				return;
			}
		}

		if (body_else != null) {
			body_else.execute(context);
		}
	}

	@Override
	public void doExecuteSymbolically(SymbolicExecutionContext context) {

		final SymbolicExecutionConditional conditional = context.addConditional();

		CLExpression accum = null;

		for (final CLIfPart part : parts) {
			CLExpression cond = part.getCondition();
			if (accum != null) {
				cond = CLBase.and(accum, cond);
			}

			if (getTypingContext() != null) {
				cond = cond.simplify(getTypingContext());
			}

			final SymbolicExecutionContext sub_context = conditional.enterBranch(cond);
			part.getStatement().doExecuteSymbolically(sub_context);

			if (accum == null) {
				accum = CLUtils.negate(cond);
			} else {
				accum = CLBase.and(accum, CLUtils.negate(cond));
			}
		}

		final SymbolicExecutionContext else_context = conditional.enterBranch(accum);
		if (body_else != null) {
			body_else.doExecuteSymbolically(else_context);
		}
	}

	@Override
	public void buildUpdatedVariables() {
		for (final CLIfPart part : parts) {
			part.buildUpdatedVariables();
		}

		if (body_else != null) {
			body_else.buildUpdatedVariables();
		}

		if (body_else == null && parts.size() == 1) {
			updatedvariables = parts.byIndex(0).getUpdatedVariables();
		} else {
			updatedvariables = new HashSet<>();
			for (final CLIfPart part : parts) {
				updatedvariables.addAll(part.getUpdatedVariables());
			}
			if (body_else != null) {
				updatedvariables.addAll(body_else.getUpdatedVariables());
			}
		}
	}

	@Override
	public String asString(int tabs) {
		final StringBuilder sb = new StringBuilder();
		boolean body_if_block = false;
		boolean first = true;
		for (final CLIfPart part : parts) {
			if (first) {
				sb.append(getTabs(tabs));
			} else {
				if (!body_if_block) {
					sb.append(getTabs(tabs));
				}
				sb.append("else ");
			}
			sb.append("if (" + part.getCondition().asString() + ")");
			if (part.getStatement() instanceof CLBlockStatement && ((CLBlockStatement) part.getStatement()).getStatements().size() > 1) {
				final CLBlockStatement block = (CLBlockStatement) part.getStatement();
				sb.append(" {\n");
				for (final CLStatement statement : block.getStatements()) {
					sb.append(statement.asString(tabs + 1));
					sb.append("\n");
				}
				sb.append(getTabs(tabs));
				sb.append("} ");
				body_if_block = true;
			} else {
				sb.append("\n");
				sb.append(part.getStatement().asString(tabs + 1));
				body_if_block = false;
			}
			first = false;
		}
		if (body_else != null) {
			if (!body_if_block) {
				sb.append(getTabs(tabs));
			}
			sb.append("else");
			if (body_else instanceof CLBlockStatement && ((CLBlockStatement) body_else).getStatements().size() > 1) {
				final CLBlockStatement block = (CLBlockStatement) body_else;
				sb.append(" { \n");
				for (final CLStatement statement : block.getStatements()) {
					sb.append(statement.asString(tabs + 1));
					sb.append("\n");
				}
				sb.append(getTabs(tabs));
				sb.append("} ");
			} else {
				sb.append("\n");
				sb.append(body_else.asString(tabs + 1));
			}
		}
		return sb.toString();
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		for (final CLIfPart part : parts) {
			part.visitExpressions(visitor, userobject);
		}
	}

	@Override
	public void visitStatements(ICLStatementVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			for (final CLIfPart part : parts) {
				part.visitStatements(visitor, userobject);
			}
			if (body_else != null) {
				body_else.visitStatements(visitor, userobject);
			}
		}
	}

	@Override
	public CLStatement deepCopy() {
		return new CLIfStatement(parts.deepCopy(), body_else == null ? null : body_else.deepCopy(), getLocation());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (body_else == null ? 0 : body_else.hashCode());
		result = prime * result + (parts == null ? 0 : parts.hashCode());
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
		final CLIfStatement other = (CLIfStatement) obj;
		if (body_else == null) {
			if (other.body_else != null) {
				return false;
			}
		} else if (!body_else.equals(other.body_else)) {
			return false;
		}
		if (parts == null) {
			if (other.parts != null) {
				return false;
			}
		} else if (!parts.equals(other.parts)) {
			return false;
		}
		return true;
	}

}
