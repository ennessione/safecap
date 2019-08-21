package uk.ac.ncl.prime.sim.lang.typing;

import java.util.HashSet;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class CLProductType extends CLType {
	public static final CLProductType TEMPLATE = new CLProductType(null, null);
	private final CLType left;
	private final CLType right;

	public CLProductType(CLType left, CLType right) {
		super(alphabet.CART, "CART");
		this.left = left;
		this.right = right;
	}

	public CLType getLeft() {
		return left;
	}

	public CLType getRight() {
		return right;
	}

	@Override
	public boolean equals(Object _o) {
		if (_o instanceof CLProductType) {
			final CLProductType pt = (CLProductType) _o;
			return (left == null || pt.left == null || pt.left.equals(left))
					&& (right == null || pt.right == null || pt.right.equals(right));
		} else if (_o instanceof CLTypeAny) {
			return _o.equals(this);
		}

		return false;
	}

	@Override
	public String toString() {
		return (left != null ? left.toString() : "?") + "**" + (right != null ? right.toString() : "?");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (left == null ? 0 : left.hashCode());
		result = prime * result + (right == null ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean isPolymorphic() {
		return left != null && left.isPolymorphic() || right != null && right.isPolymorphic();
	}

	@Override
	public boolean isTemplate() {
		return left == null || left.isTemplate() || right == null || right.isTemplate();
	}

	@Override
	public CLType makePolymorphicType() {
		assert isTemplate();
		return new CLProductType(makePolymorphicType(left), makePolymorphicType(right));
	}

	@Override
	public boolean unify(TypeSolution solution, CLType other) {
		if (other instanceof CLProductType) {
			final CLProductType prod = (CLProductType) other;
			final boolean a = left != null ? left.unify(solution, prod.left) : true;
			final boolean b = right != null ? right.unify(solution, prod.right) : true;
			return a && b;
		} else {
			return super.unify(solution, other);
		}
	}

	@Override
	public boolean isExtended() {
		return left.isExtended() || right.isExtended();
	}

	@Override
	protected Set<String> buildTypeVariables() {
		final Set<String> result = new HashSet<>();
		result.addAll(left.getTypeVariables());
		result.addAll(right.getTypeVariables());
		return result;
	}
}
