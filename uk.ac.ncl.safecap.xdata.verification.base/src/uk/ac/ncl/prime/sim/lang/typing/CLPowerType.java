package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class CLPowerType extends CLType {
	public static final CLPowerType TEMPLATE = new CLPowerType(null);
	public static final CLPowerType RELATION = new CLPowerType(new CLProductType(null, null)); // relation of the form A <-> B
	public static final CLPowerType POW = new CLPowerType(new CLPowerType(null));
	public static final CLPowerType RELATION_UNIFORM; // relation of the form A <-> A

	static {
		final CLTypeAny tvar = new CLTypeAny(null);
		RELATION_UNIFORM = new CLPowerType(new CLProductType(tvar, tvar));
	}

	private final CLType base;

	public CLPowerType(CLType base) {
		super(alphabet.SET, "POW");
		this.base = base;
	}

	public CLType getBase() {
		return base;
	}

	@Override
	public boolean equals(Object _o) {
		if (_o instanceof CLPowerType) {
			final CLPowerType pt = (CLPowerType) _o;
			if (pt.base == null || base == null) {
				return true;
			} else {
				return pt.base.equals(base);
			}
		} else if (_o instanceof CLTypeAny) {
			return _o.equals(this);
		}

		return false;
	}

	@Override
	public String toString() {
		if (base != null) {
			return "set(" + base.toString() + ")";
		} else {
			return "set(?)";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (base == null ? 0 : base.hashCode());
		return result;
	}

	@Override
	public boolean isPolymorphic() {
		return base != null && base.isPolymorphic();
	}

	@Override
	public boolean isTemplate() {
		return base == null || base.isTemplate();
	}

	@Override
	public CLType makePolymorphicType() {
		assert isTemplate();
		return new CLPowerType(makePolymorphicType(base));
	}

	@Override
	public boolean unify(TypeSolution solution, CLType other) {
		if (other instanceof CLPowerType) {
			final CLPowerType seqt = (CLPowerType) other;
			if (base == null) {
				return true;
			}
			return base.unify(solution, seqt.base);
		} else {
			return super.unify(solution, other);
		}
	}

	@Override
	public boolean isExtended() {
		return base.isExtended();
	}

	@Override
	protected Set<String> buildTypeVariables() {
		return base.getTypeVariables();
	}
}
