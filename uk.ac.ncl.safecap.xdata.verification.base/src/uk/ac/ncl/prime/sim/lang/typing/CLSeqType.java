package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class CLSeqType extends CLType {
	public static final CLPowerType TEMPLATE = new CLPowerType(null);
	private final CLType base;

	public CLSeqType(CLType base) {
		super(alphabet.SEQ, "SEQ");
		this.base = base;
	}

	public CLType getBase() {
		return base;
	}

	@Override
	public boolean equals(Object _o) {
		if (_o instanceof CLSeqType) {
			final CLSeqType pt = (CLSeqType) _o;
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
			return "seq(" + base.toString() + ")";
		} else {
			return "seq(?)";
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
	public CLType domType() {
		return CLTypeInteger.INSTANCE;
	}

	@Override
	public CLType ranType() {
		return base;
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
		return new CLSeqType(makePolymorphicType(base));
	}

	@Override
	public boolean unify(TypeSolution solution, CLType other) {
		if (other instanceof CLSeqType) {
			final CLSeqType seqt = (CLSeqType) other;
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
