package uk.ac.ncl.prime.sim.lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gnu.jel.CompilationException;
import gnu.jel.CompiledExpression;
import gnu.jel.Evaluator;
import gnu.jel.Library;
import uk.ac.ncl.prime.sim.lang.mb.ModelSolverCompiled;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.BProto;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.BFun;
import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.prime.sim.sets.BRel;
import uk.ac.ncl.prime.sim.sets.BSeq;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.prime.sim.sets.Builtin;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.FastRuntimeContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public abstract class CLExpression extends CLElement {

	public static final Set<String> noFreeIdentifiers = new HashSet<>();
	public static final Set<CLIdentifier> noBoundIdentifiers = new HashSet<>();
	public static final Set<String> noPrimedIdentifiers = noFreeIdentifiers;
	public static final int MAX_MATCHING_CACHE = 100;

	private CLType type;
	private Set<String> freeIdentifiers;
	private Set<CLIdentifier> boundIdentifiers;
	private int isConstant = 0; // 0 - unknown, 1 - not constant, 2 - constant

	private transient Map<CLExpression, Map<String, CLExpression>> lruMatchingCache;

	@SuppressWarnings("rawtypes")
	public static BRel toRelation(Object val) throws InvalidSetOpException {
		if (val instanceof BRel) {
			return (BRel) val;
		} else if (val instanceof BSet) {
			return ((BSet) val).toRel();
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static BFun toFunction(Object val) throws InvalidSetOpException {
		if (val instanceof BFun) {
			return (BFun) val;
		} else if (val instanceof BSet) {
			return ((BSet) val).toFun();
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public CompiledExpression prepareCompiled(SDARuntimeExecutionContext context) throws CompilationException, CLNonExecutableException {
		context.getRootContext().bakeTypes();
		final String compiledString = compile(context);
		if (compiledString != null) {
			try {
				final Class[] staticAccess = new Class[] { BSet.class, BMap.class, BSeq.class, BProto.class, ModelSolverCompiled.class,
						Builtin.class };
				final Class[] dynamicAccess = new Class[] { FastRuntimeContext.class, ModelSolverCompiled.class };
				final Library lib = new Library(staticAccess, dynamicAccess, new Class[0], null, null);
				return Evaluator.compile(compiledString, lib);
			} catch (final Throwable e) {
				System.out.println("Failed compiling  " + compiledString + ": " + e.getMessage());
				throw e;
			}
		} else {
			throw new CLNonExecutableException(this);
		}
	}

	public Object getValueCompiled(SDARuntimeExecutionContext context) throws CLExecutionException, Throwable {
		CompiledExpression compiled = context.getFastRuntimeContext().getCompiledExpression(this);
		if (compiled == null) {
			compiled = prepareCompiled(context);
			if (compiled != null) {
				context.getFastRuntimeContext().storeCompiledExpression(this, compiled);
			}
		}

		if (compiled != null) {
			return compiled.evaluate(context.getFastRuntimeContext(getFreeIdentifiers()).getContext());
		} else {
			return null;
		}

	}

	protected CLExpression(int tag) {
		super(tag);
		freeIdentifiers = null;
		boundIdentifiers = null;
	}

	protected CLExpression(int tag, SourceLocation location) {
		super(tag, location);
	}

	public CLType getType() {
		return type;
	}

	public CLType type(TypingContext ctx) {
		isConstant = 0;
		final CLType t = getType(ctx);
		type = t;
		return t;
	}

	public void type(TypingContext ctx, CLType expected) {
		isConstant = 0;
		if (expected == null) {
			return;
		}

		final CLType t = getType(ctx);
		if (t == null) {
			return;
		}

		if (!ctx.unify(t, expected)) {
			ctx.getValidation().addError(this, "Expected type " + expected.toString() + "; actual: " + t.toString());
			return;
		}

		if (!expected.isPolymorphic() && !expected.isTemplate() && expected.isExtended()) {
			type = expected;
		} else {
			type = t;
		}
	}

	public boolean isConstant(TypingContext ctx) {
		if (isConstant == 0) {
			if (getBoundIdentifiers().size() != 0) {
				isConstant = 2;
			} else {
				final Set<String> expression_identifiers = getFreeIdentifiers();
				for (final String s : expression_identifiers) {
					if (!ctx.getSymbolClass(s).isConstant()) {
						// final CLType type = ctx.getType(s);
						// assert type != null;
						isConstant = 2;
						return false;
					}
				}
				isConstant = 1;
			}
		}

		return isConstant == 1;
	}

	/**
	 * Walks an expression tree with a given visitor class
	 * 
	 * @param visitor    visitor instance
	 * @param userobject an option user object to be passed on to the visitor
	 */
	public abstract void visit(ICLExpressionVisitor visitor, Object userobject);

	/**
	 * An immutable expression may be evaluated any number of timed yielding the
	 * same result.
	 * 
	 * @return true if the expression is immutable
	 */
	public abstract boolean isImmutable();

	protected abstract CLType getType(TypingContext ctx);

	protected abstract Set<String> buildFreeIdentifiers();

	protected abstract Set<CLIdentifier> buildBoundIdentifiers();

	/**
	 * Used in extreme cases with in-place formula rewrites, normally should be
	 * avoided
	 */
	public void clearIdentifierCaches() {
		freeIdentifiers = null;
		boundIdentifiers = null;
	}

	/**
	 * Returns the set of identifiers occurring free in the expression
	 * 
	 * @return
	 */
	public Set<String> getFreeIdentifiers() {
		if (freeIdentifiers == null) {
			freeIdentifiers = buildFreeIdentifiers();
		}
		return freeIdentifiers;
	}

	/**
	 * Returns the set of identifiers bound by a containing quantifier
	 * 
	 * @return
	 */
	public Set<CLIdentifier> getBoundIdentifiers() {
		if (boundIdentifiers == null) {
			boundIdentifiers = buildBoundIdentifiers();
		}
		return boundIdentifiers;
	}

	/**
	 * Resolve a given free identifier name to a any identifier element occurring in
	 * the formula
	 */
	public abstract CLIdentifier resolveIdentifiers(String name);

	/**
	 * Expression evaluation
	 * 
	 * @param context runtime context holding variable values
	 * @return expression value evaluated in the given context
	 * @throws CLExecutionException  if there is a non-executable construct
	 * @throws InvalidSetOpException on failed set operation
	 */
	public abstract Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException;

	/**
	 * Debug version arbitrating between compiled and interpreted results
	 * 
	 * @param typing
	 * @param context
	 * @return
	 * @throws Throwable
	 */
	final public Object getValue(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		// return getValueInterpreted(context);

		try {
			// return null;
			return getValueCompiled(context);
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new CLExecutionException(e.getMessage());
		}

//		try {
//			long start = System.currentTimeMillis();
//			Object resultCompiled = getValueCompiled(context);
//			long compiled = System.currentTimeMillis();
//			Object resultInterpreted = getValueInterpreted(context);
//			long interpreted = System.currentTimeMillis();
//
//
//			if (resultCompiled != null && resultCompiled != resultInterpreted
//					&& !resultCompiled.equals(resultInterpreted)) {
//				System.err.println("Evaluation mismatch for " + this.asString());
//				System.err.println("\t\t(C): " + resultCompiled);
//				System.err.println("\t\t(I): " + resultInterpreted);
//				if (resultCompiled instanceof BSet && resultInterpreted instanceof BSet) {
//					BSet setc = (BSet) resultCompiled;
//					BSet seti = (BSet) resultInterpreted;
//					BSet diff0 = setc.setminus(seti);
//					BSet diff1 = seti.setminus(setc);
//					System.err.println("\t\t C extra: " + diff0);
//					System.err.println("\t\t I extra: " + diff1);
//				}
//				assert false;
//				return null;
//			} else if (resultCompiled == null) {
//				System.err.println("Compilation failed for " + this.asString());
//				assert false;
//			}
//
//			System.out.println("\t\tCompiled: " + (compiled - start));
//			System.out.println("\t\tInterpreted: "+ (interpreted - start));
//
//			return resultInterpreted;
//		} catch (Throwable e) {
//			//e.printStackTrace();
//			throw new CLExecutionException(e.getMessage());
//		}

	}

	public boolean isTrue(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		final Object val = getValue(context);
		if (val instanceof Boolean) {
			final Boolean bv = (Boolean) val;
			return bv.booleanValue();
		}

		return false;
	}

	/**
	 * Pretty-prints an expression in a string
	 * 
	 * @return
	 */
	public abstract String asString();
	
	public String asStringWithLocation() {
		if (this.getLocation() != null)
			return asString() + "@" + getLocation();
		else
			return asString();
	}

	/**
	 * Rewrites an expression with a given map
	 * 
	 * @param map maps identifiers to expressions
	 * @return an original expression with all the identifiers from the map key set
	 *         replaced with corresponding expressions
	 */
	public abstract CLExpression rewrite(Map<String, CLExpression> map);

	/**
	 * Rewrites an expression with a given name/value pair (typed)
	 * 
	 * @param id    an identifier to rewrite
	 * @param value an expression to rewrite with
	 * @param ctx   a context used to type the result
	 * @return a rewritten and typed expression
	 */
	public CLExpression rewrite(String id, CLExpression value, TypingContext ctx) {
		final Map<String, CLExpression> map = new HashMap<>();
		map.put(id, value);
		return rewrite(map, ctx);
	}

	/**
	 * Rewrites an expression with a given name/value pair (untyped)
	 * 
	 * @param id    an identifier to rewrite
	 * @param value an expression to rewrite with
	 * @return a rewritten and typed expression
	 */
	public CLExpression rewrite(String id, CLExpression value) {
		final Map<String, CLExpression> map = new HashMap<>();
		map.put(id, value);
		return rewrite(map);
	}

	/**
	 * Rewrites an expression with a given map
	 * 
	 * @param map maps identifiers to expressions
	 * @param ctx a typing context
	 * @return a typed original expression with all the identifiers from the map key
	 *         set replaced with corresponding expressions
	 */
	public CLExpression rewrite(Map<String, CLExpression> map, TypingContext ctx) {
		final CLExpression rewritten = rewrite(map);

		if (ctx != null) {
			rewritten.type(ctx, getType());
		}

		return rewritten;
	}

	/**
	 * Applies all the built-in expression simplifications
	 * 
	 * @param ctx a typing context used to type a result
	 * @return the same or equivalent simplified expression
	 */
	public abstract CLExpression simplify(TypingContext ctx);

	/**
	 * Make a deep copy of a given expression
	 * 
	 * @return a copy of the expression
	 */
	public abstract CLExpression deepCopy();

	/**
	 * Make a shallow copy of a given expression: only the outer most construct is
	 * duplicated
	 * 
	 * @return a copy of the expression
	 */
	public abstract CLExpression shallowCopy();

	final private boolean isTemplateVariable() {
		if (this instanceof CLIdentifier) {
			final CLIdentifier idt = (CLIdentifier) this;
			return !idt.getName().startsWith("$");
		}
		return false;
	}

	protected abstract Map<String, CLExpression> _match(CLExpression template, TypingContext context);

	/**
	 * Caching version of pattern matching. Caching uses a small LRU buffer to save
	 * previous match results and relies on strict immutability of expressions.
	 * 
	 * @param template
	 * @param context
	 * @return
	 */
	final public Map<String, CLExpression> match(CLExpression template, TypingContext context) {
		if (lruMatchingCache != null && lruMatchingCache.containsKey(template)) {
			// System.out.println("cache hit");
			return lruMatchingCache.get(template);
		}

		// System.out.println("cache miss");
		final Map<String, CLExpression> map = directMatch(template, context);
		if (lruMatchingCache == null) {
			lruMatchingCache = CLUtils.lruCache(MAX_MATCHING_CACHE);
		}

		lruMatchingCache.put(template, map);

		return map;
	}

	final public CLExpression matchFor(CLExpression template, TypingContext context, String id) {
		final Map<String, CLExpression> map = match(template, context);
		return map.get(id);
	}

	final public CLIdentifier matchForIdentifier(CLExpression template, TypingContext context, String id) {
		final Map<String, CLExpression> map = match(template, context);
		final CLExpression z = map.get(id);
		if (z instanceof CLIdentifier) {
			return (CLIdentifier) z;
		}

		return null;
	}

	/**
	 * Non-caching version of template matching. Generally should be avoided.
	 * 
	 * @param template
	 * @param context
	 * @return
	 */
	final public Map<String, CLExpression> directMatch(CLExpression template, TypingContext context) {
		if (template.isTemplateVariable()) {
			final CLIdentifier other = (CLIdentifier) template;

			if (other.getTagConstraint() != -1 && getTag() != other.getTagConstraint()) {
				return null;
			}

			if (context != null && other.isConstantConstraint() && !isConstant(context)) {
				return null;
			}

			if (other.getTypeConstraint() != null) {
				final CLType constraint = other.getTypeConstraint();
				final CLType current = getType();
				if (!constraint.equals(current)) {
					return null;
				}

				if (other.isFunctionalConstraint()) {
					if (!current.isRelation()) {
						return null;
					}
					if (!isConstant(context)) {
						return null;
					}
					if (getTag() == alphabet.ID) {
						final CLIdentifier self = (CLIdentifier) this;
						if (!context.getRelationKind(self.getName()).isFunction()) {
							return null;
						}
					} else {
						return null;
					}
				}
			}

			return Collections.singletonMap(other.getName(), this);
		} else if (template.getTag() == getTag()) {
			return _match(template, context);
		} else {
			return null;
		}
	}

	final static public boolean match_compatible_results(Map<String, CLExpression> a, Map<String, CLExpression> b) {
		for (final String key : a.keySet()) {
			if (b.containsKey(key) && !b.get(key).equals(a.get(key))) {
				return false;
			}
		}

		return true;
	}

	/*
	 * Returns the relative binding strength of the expression operator; the higher
	 * the number the stronger is the binding
	 */
	public abstract int getPriority();

	protected String ppBrackets(CLExpression expression, boolean associative) {

		// if (expression.getPriority() > getPriority()) {
		// return expression.asString();
		// } else
		if (associative && expression.getTag() == getTag()) {
			return expression.asString();
		} else if (expression instanceof CLIdentifier || expression instanceof CLIntegerExpression
				|| expression instanceof CLAtomicExpression || expression.getTag() == alphabet.SETC) {
			return expression.asString();
		} else {
			return "(" + expression.asString() + ")";
		}

	}

	/**
	 * Returns the set of primed identifiers occurring in the expression
	 * 
	 * @return
	 */
	public Set<String> getPrimedIdentifiers() {
		final Set<String> result = new HashSet<>();
		for (final String s : getFreeIdentifiers()) {
			if (CLUtils.isPrimed(s)) {
				result.add(s);
			}
		}

		return result;
	}

	public Set<String> getPrimedIdentifiers(String qual) {
		final Set<String> result = new HashSet<>();
		for (final String s : getFreeIdentifiers()) {
			if (CLUtils.isQualified(s, qual)) {
				result.add(s);
			}
		}

		return result;
	}

	/**
	 * Returns the set of primed identifiers occurring in the expression but each
	 * identifier unprimed in the returned set
	 * 
	 * @return
	 */
	public Set<String> getUnprimedSetOfPrimedIdentifiers() {
		final Set<String> result = new HashSet<>();
		for (final String s : getFreeIdentifiers()) {
			if (CLUtils.isPrimed(s)) {
				result.add(CLUtils.unPrimed(s));
			}
		}

		return result;
	}

	/**
	 * Un-primes all primed identifiers
	 */
	public CLExpression unprime(TypingContext ctx) {
		final Set<String> primed = getPrimedIdentifiers();
		final Map<String, CLExpression> unprime = new HashMap<>(primed.size());

		for (final String s : primed) {
			unprime.put(s, new CLIdentifier(CLUtils.unPrimed(s)));
		}

		return rewrite(unprime, ctx);
	}

	/**
	 * Primes all identifiers from set toPrime
	 */
	public CLExpression prime(TypingContext ctx, Set<String> toPrime) {
		final Map<String, CLExpression> prime = new HashMap<>(toPrime.size());

		for (final String s : getFreeIdentifiers()) {
			if (toPrime.contains(s) && !CLUtils.isPrimed(s)) {
				prime.put(s, new CLIdentifier(s, null, null));
			}
		}

		return rewrite(prime, ctx);
	}

	/**
	 * Previous to unprimed: turns all x'p identifiers to x
	 */
	public CLExpression unprime(TypingContext ctx, String qualifier) {
		final Set<String> primed = getPrimedIdentifiers(qualifier);
		final Map<String, CLExpression> unprime = new HashMap<>(primed.size());

		for (final String s : primed) {
			unprime.put(s, new CLIdentifier(CLUtils.unPrimed(s)));
		}

		return rewrite(unprime, ctx);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getTag();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CLElement other = (CLElement) obj;
		if (getTag() != other.getTag()) {
			return false;
		}
		return true;
	}

	public abstract String compile(SDARuntimeExecutionContext context) throws CLNonExecutableException;

	public boolean isSingletonSet() {
		if (getTag() == alphabet.SETC) {
			final CLMultiExpression me = (CLMultiExpression) this;
			return me.getParts().size() == 1;
		}
		return false;
	}

	public CLExpression asSingletonSet() {
		if (getTag() == alphabet.SETC) {
			final CLMultiExpression me = (CLMultiExpression) this;
			if (me.getParts().size() == 1) {
				return me.getParts().byIndex(0);
			}
		}
		return null;
	}

	public boolean matches(CLExpression template, TypingContext typingContext) {

		return match(template, typingContext) != null;
	}
}
