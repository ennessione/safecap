package uk.ac.ncl.prime.sim.lang;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext.SYMBOL_CLASS;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLIdentifier extends CLExpression {
	private String prefixName;
	private String fullName;
	private int index = -1;
	private boolean isprime = false;
	private String qual = null;
	private String ppId;

	// compilation extensions
	private boolean compileVerbatim = false;

	// template extensions
	private CLType typeConstraint;
	private String typeDescriptor;
	private int tagConstraint = -1;
	private boolean constantConstraint = false;
	private boolean functionalConstraint = false;
	
	public CLIdentifier(String name, SourceLocation location) {
		super(alphabet.ID, location);
		prefixName = name;
		fullName = name;
		if (CLUtils.needsQuotes(fullName)) {
			ppId = "\"" + fullName + "\"";
		} else {
			ppId = fullName;
		}
	}

	public CLIdentifier(String name, String qual, SourceLocation location) {
		super(alphabet.ID, location);
		prefixName = name;
		this.qual = qual;
		isprime = true;
		fullName = makeIdentifierName(name, qual, true);
		if (CLUtils.needsQuotes(fullName)) {
			ppId = "\"" + fullName + "\"";
		} else {
			ppId = fullName;
		}
	}

	public CLIdentifier mkCopy() {
		final CLIdentifier id = new CLIdentifier(prefixName, getLocation());
		id.fullName = fullName;
		id.isprime = isprime;
		id.ppId = ppId;
		id.prefixName = prefixName;
		id.qual = qual;
		id.tagConstraint = tagConstraint;
		id.typeConstraint = typeConstraint;
		id.typeDescriptor = typeDescriptor;
		return id;
	}

	public CLIdentifier(String name) {
		super(alphabet.ID);
		prefixName = name;
		qual = null;
		isprime = CLUtils.isPrimed(name);
		fullName = makeIdentifierName(name, null, false);
		if (CLUtils.needsQuotes(fullName)) {
			ppId = "\"" + fullName + "\"";
		} else {
			ppId = fullName;
		}
	}

	public boolean isCompileVerbatim() {
		return compileVerbatim;
	}

	public void setCompileVerbatim(boolean compileVerbatim) {
		this.compileVerbatim = compileVerbatim;
	}

	public static final String makeIdentifierName(String prefix, String qual, boolean isprime) {

		if (isprime) {
			if (qual != null) {
				return prefix + "'" + qual;
			} else {
				return prefix + "'";
			}
		} else {
			return prefix;
		}
	}

	public void setConstantConstraint(boolean constantConstraint) {
		this.constantConstraint = constantConstraint;
	}

	protected CLType getTypeConstraint() {
		return typeConstraint;
	}

	protected void setTypeConstraint(CLType typeConstraint) {
		this.typeConstraint = typeConstraint;
	}

	public boolean isConstantConstraint() {
		return constantConstraint;
	}

	public boolean isFunctionalConstraint() {
		return functionalConstraint;
	}

	protected void setTypeDescriptor(String typeDescriptor) {
		this.typeDescriptor = typeDescriptor;
		for (final char c : typeDescriptor.toCharArray()) {
			if (c == 'C') {
				constantConstraint = true;
			} else if (c == 'F') {
				functionalConstraint = true;
			}
		}
	}

	public int getTagConstraint() {
		return tagConstraint;
	}

	public void setTagConstraint(int tagConstraint) {
		this.tagConstraint = tagConstraint;
	}

	public String getName() {
		return fullName;
	}

	public boolean isBound() {
		return index != -1;
	}

	/**
	 * Clears the bound index of this identifier. It is likley to break formula -
	 * use with extreme caution!
	 * 
	 * @return
	 */
	@Deprecated
	public void removeBound() {
		index = -1;
	}

	public boolean isPrime() {
		return isprime && qual == null;
	}

	public boolean isQualified() {
		return isprime && qual != null;
	}

	public int getIndex() {
		return index;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public String getQualifier() {
		return qual;
	}

	@Override
	protected CLType getType(TypingContext ctx) {

		if (fullName.indexOf('$') > -1) {
			ctx.getValidation().addError(this, "Template identifier " + fullName + " should not be typed");
		}

		final CLType t = ctx.getType(fullName);
		if (t == null) {
			ctx.getValidation().addError(this, "Unknown identifier " + fullName);
		}

		if (ctx.getSymbolClass(fullName) == SYMBOL_CLASS.BOUND) {
			index = ctx.getBoundIndex(prefixName);

			if (isprime) {
				ctx.getValidation().addError(this, "Bound identifier may not be primed");
			}
		}

		return t;
	}

	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) throws CLExecutionException, InvalidSetOpException {
		final Object r = context.getValue(prefixName);
		if (r == null) {
			throw new CLExecutionException("Undefined identifier " + fullName);
		} else {
			return context.getValue(prefixName);
		}
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		if (isBound()) {
			return CLExpression.noFreeIdentifiers;
		} else {
			return java.util.Collections.singleton(fullName);
		}
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		if (isBound()) {
			return java.util.Collections.singleton(this);
		} else {
			return CLExpression.noBoundIdentifiers;
		}
	}

	@Override
	public String asString() {
		return ppId;
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		if (isBound()) {
			final String my_map = String.valueOf(index);
			if (map.containsKey(my_map)) {
				final CLExpression r = map.get(my_map);
				return r;
			}
			return this;
		} else {
			if (map.containsKey(fullName)) {
				final CLExpression r = map.get(fullName);
				return r;
			}
			return this;
		}
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		return this;
	}

	private int hashcode = 0;

	@Override
	public int hashCode() {
		int result = hashcode;
		if (result == 0) {
			final int prime = 31;
			result = super.hashCode();
			result = prime * result + (fullName == null ? 0 : fullName.hashCode());
			hashcode = result;
		}

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
		final CLIdentifier other = (CLIdentifier) obj;
		if (fullName == null) {
			if (other.fullName != null) {
				return false;
			}
		} else if (!fullName.equals(other.fullName)) {
			return false;
		}
		return true;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		if (fullName.equals(name)) {
			return this;
		} else {
			return null;
		}
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		visitor.visit(this, userobject);
	}

	@Override
	public boolean isImmutable() {
		return true;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	@Override
	public CLExpression deepCopy() {
		return mkCopy();
	}

	@Override
	public CLExpression shallowCopy() {
		return mkCopy();
	}

	@Override
	public Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		final CLIdentifier other = (CLIdentifier) template;
		if (other.fullName.equals("$" + fullName) || other.fullName.startsWith("$$")) {
			return Collections.emptyMap();
		}
		return null;
	}

	@Override
	public String compile(SDARuntimeExecutionContext context) throws CLNonExecutableException {
		if (isCompileVerbatim()) {
			return fullName;
		}

		try {
			String name;
			if (!isBound()) {
				if (this.getType(context.getRootContext()) == null) {
					throw new CLNonExecutableException(this, "Undefined identifier " + fullName);
				}

				name = context.getFastRuntimeContext(getFreeIdentifiers()).getCompiledName(fullName);
			} else {
				name = "set" + index + "[x" + index + "]";
			}

			if (getType() != null && getType().equals(CLTypeInteger.INSTANCE)) {
				return "toInt(" + name + ")";
			} else {
				return name;
			}
		} catch (final CLExecutionException e) {
			throw new CLNonExecutableException(this, e.toString());
		}
	}
}
