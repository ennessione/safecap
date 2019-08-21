package uk.ac.ncl.prime.sim.lang.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.ICLContainer;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLBlockStatement extends CLStatement implements ICLContainer {
	private final CLCollection<CLStatement> statements;

	public CLBlockStatement(CLCollection<CLStatement> _statements, SourceLocation location) {
		super(alphabet.BLOCK, location);
		final List<CLStatement> _children = new ArrayList<>();
		for (final CLStatement s : _statements.getParts()) {
			assert s != null;
			if (s instanceof CLBlockStatement) {
				_children.addAll(((CLBlockStatement) s).statements.getParts());
			} else {
				_children.add(s);
			}
		}
		statements = new CLCollection<>(_children);
	}

	@Override
	public void type(TypingContext ctx) {
		final TypingContext ctx1 = new TypingContext(ctx);
		super.type(ctx1);

		for (final CLStatement stm : statements) {
			stm.type(ctx1);
		}
	}

	public CLCollection<CLStatement> getStatements() {
		return statements;
	}

	@Override
	public void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		context = new SDARuntimeExecutionContext(context);

		for (final CLStatement s : statements) {
			s.execute(context);
		}

	}

	@Override
	public void doExecuteSymbolically(SymbolicExecutionContext context) {
		for (final CLElement element : statements) {
			final CLStatement s = (CLStatement) element;
			s.doExecuteSymbolically(context);
		}
	}

	@Override
	public void buildUpdatedVariables() {
		updatedvariables = new HashSet<>(1);
		for (final CLStatement statement : statements) {
			updatedvariables.addAll(statement.getUpdatedVariables());
		}
	}

	@Override
	public String asString(int tabs) {
		final StringBuilder sb = new StringBuilder();

		if (statements.size() > 1 && tabs > 0) {
			sb.append(getTabs(tabs));
			sb.append("{\n");
		}

		for (int i = 0; i < statements.size(); i++) {
			final CLStatement statement = statements.byIndex(i);
			if (i > 0) {
				sb.append("\n");
			}
			sb.append(statement.asString(tabs + 1));
		}
		if (statements.size() > 1 && tabs > 0) {
			sb.append(getTabs(tabs));
			sb.append("}");
		}

		return sb.toString();
	}

	@Override
	public Collection<?> getChildren() {
		return statements.getParts();
	}

	@Override
	public CLStatement locateChildStatement(int offset) {
		if (getLocation().contains(offset)) {

			if (statements != null) {
				for (final CLStatement st : statements) {
					final CLStatement s = st.locateChildStatement(offset);
					if (s != null) {
						return s;
					}
				}
			}

			return this;
		} else {
			return null;
		}
	}

	@Override
	public void visitExpressions(ICLExpressionVisitor visitor, Object userobject) {
		for (final CLStatement st : statements) {
			st.visitExpressions(visitor, userobject);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (statements == null ? 0 : statements.hashCode());
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
		final CLBlockStatement other = (CLBlockStatement) obj;
		if (statements == null) {
			if (other.statements != null) {
				return false;
			}
		} else if (!statements.equals(other.statements)) {
			return false;
		}
		return true;
	}

	@Override
	public void visitStatements(ICLStatementVisitor visitor, Object userobject) {
		if (visitor.visit(this, userobject)) {
			for (final CLStatement st : statements) {
				st.visitStatements(visitor, userobject);
			}
		}
	}

	@Override
	public CLStatement deepCopy() {
		return new CLBlockStatement(statements.deepCopy(), getLocation());
	}

}
