package uk.ac.ncl.prime.sim.lang;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.CLTypeInteger;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLIntegerExpression extends CLExpression {
	private final Integer value;

	protected CLIntegerExpression(Integer value, SourceLocation location) {
		super(alphabet.NUMBER, location);
		this.value = value;
	}

	public CLIntegerExpression(Integer value) {
		super(alphabet.NUMBER);
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	protected CLType getType(TypingContext ctx) {
		return CLTypeInteger.INSTANCE;
	}

	@Override
	public Object getValueInterpreted(SDARuntimeExecutionContext context) {
		return value;
	}

	@Override
	protected Set<String> buildFreeIdentifiers() {
		return CLExpression.noFreeIdentifiers;
	}

	@Override
	protected Set<CLIdentifier> buildBoundIdentifiers() {
		return CLExpression.noBoundIdentifiers;
	}

	@Override
	public String asString() {
		return value.toString();
	}

	@Override
	public CLExpression rewrite(Map<String, CLExpression> map) {
		return this;
	}

	@Override
	public CLExpression simplify(TypingContext ctx) {
		return this;
	}

	@Override
	public CLIdentifier resolveIdentifiers(String name) {
		return null;
	}

	@Override
	public void visit(ICLExpressionVisitor visitor, Object userobject) {
		visitor.visit(this, userobject);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		final CLIntegerExpression other = (CLIntegerExpression) obj;
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
	public boolean isImmutable() {
		return true;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	@Override
	public CLExpression deepCopy() {
		return new CLIntegerExpression(value, getLocation());
	}

	@Override
	public CLExpression shallowCopy() {
		return new CLIntegerExpression(value, getLocation());
	}

	@Override
	protected Map<String, CLExpression> _match(CLExpression template, TypingContext context) {
		final CLIntegerExpression other = (CLIntegerExpression) template;
		return other.value.equals(value) ? Collections.emptyMap() : null;
	}

	@Override
	public String compile(SDARuntimeExecutionContext typingContext) throws CLNonExecutableException {
		return "mkint(" + value.toString() + ")";
	}
}
