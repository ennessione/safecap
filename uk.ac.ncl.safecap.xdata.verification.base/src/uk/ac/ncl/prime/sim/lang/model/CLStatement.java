package uk.ac.ncl.prime.sim.lang.model;

import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLTerminationException;
import uk.ac.ncl.prime.sim.lang.ICLExpressionVisitor;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public abstract class CLStatement extends CLElement implements ICLLocatable {
	public static final Set<String> NO_UPDATED_VARIABLES = new HashSet<>();

	protected Set<String> updatedvariables = null;
	protected TypingContext typingContext;

	/**
	 * Symbolic state before the execution of the current statement
	 */
	protected SymbolicExecutionContext symbolicContextBefore;

	/**
	 * Symbolic state after the execution of the current statement
	 */
	protected SymbolicExecutionContext symbolicContextAfter;

	protected CLStatement(int tag, SourceLocation location) {
		super(tag, location);
	}

	protected CLStatement(int tag) {
		super(tag);
	}

	/**
	 * Returns the typing context used in this statement. All the identifiers
	 * ocurring within the AST of the statement must be typeable with this context
	 */
	public TypingContext getTypingContext() {
		return typingContext;
	}

	/**
	 * Type checks the statement with a given parent typing context
	 * 
	 * @param ctx parent typing context
	 */
	public void type(TypingContext ctx) {
		typingContext = ctx;
	}

	/**
	 * Returns the set of variables assigned in this statement
	 */
	public Set<String> getUpdatedVariables() {
		if (updatedvariables == null) {
			buildUpdatedVariables();
		}

		assert updatedvariables != null;

		return updatedvariables;
	}

	/**
	 * Returns the symbolic state before the execution of the current statement
	 */
	public SymbolicExecutionContext getSymbolicContextAfter() {
		return symbolicContextAfter;
	}

	/**
	 * Returns the symbolic state after the execution of the current statement
	 */
	public SymbolicExecutionContext getSymbolicContextBefore() {
		return symbolicContextBefore;
	}

	/**
	 * Walks an statement tree visiting every expression leaf with a given visitor
	 * class
	 * 
	 * @param visitor    visitor instance
	 * @param userobject an option user object to be passed on to the visitor
	 */
	public abstract void visitExpressions(ICLExpressionVisitor visitor, Object userobject);

	/**
	 * Walks a statement tree with a given visitor class
	 * 
	 * @param visitor    visitor instance
	 * @param userobject an option user object to be passed on to the visitor
	 */
	public abstract void visitStatements(ICLStatementVisitor visitor, Object userobject);

	/**
	 * This method must build the set of variables assigned in this statement; it is
	 * called only once within type-checking cycle
	 */
	public abstract void buildUpdatedVariables();

	/**
	 * Concrete execution of the statement
	 * 
	 * @param context runtime execution context holding the current execution state
	 *                including variable states
	 * @throws CLExecutionException
	 * @throws InvalidSetOpException
	 * @throws CLTerminationException
	 */
	public abstract void execute(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException;

	/**
	 * Symbolic execution of the statement
	 * 
	 * @param context symbolic execution context containing accumulated properties
	 */
	public abstract void doExecuteSymbolically(SymbolicExecutionContext context);

	/**
	 * Make a deep copy of a given expression
	 * 
	 * @return a copy of the expression
	 */
	public abstract CLStatement deepCopy();

	/**
	 * The entry method for symbolic execution.
	 * 
	 * @param context symbolic execution context containing accumulated properties
	 */
	public final void executeSymbolically(SymbolicExecutionContext context) {
		doExecuteSymbolically(context);
		// context.end();
	}

	@Override
	public CLStatement locateChildStatement(int offset) {
		if (getLocation() != null && getLocation().contains(offset)) {
			return this;
		} else {
			return null;
		}
	}

	public abstract String asString(int tabs);

	protected String getTabs(int tabs) {
		final StringBuilder sb = new StringBuilder();
		while (tabs > 0) {
			sb.append("    ");
			tabs--;
		}
		return sb.toString();
	}

	/**
	 * Verbose pretty-printing of the statement
	 */
	public String asString() {
		return asString(0);
	}

	@Override
	public String toString() {
		return asString(0);
	}

}
