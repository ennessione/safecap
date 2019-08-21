package uk.ac.ncl.prime.sim.lang;

import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class CLForeignLocationExpression {
	private CLExpression expression;
	private SourceLocation location;

	public CLForeignLocationExpression(CLExpression expression, SourceLocation location) {
		super();
		this.expression = expression;
		this.location = location;
	}

	public CLExpression getExpression() {
		return expression;
	}

	public SourceLocation getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CLForeignLocationExpression other = (CLForeignLocationExpression) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return expression + "@" + location;
	}
}
