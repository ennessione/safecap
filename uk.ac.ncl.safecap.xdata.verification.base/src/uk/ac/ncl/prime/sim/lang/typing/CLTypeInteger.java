package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Collections;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.safecap.xdata.realworldtypes.PhysicalType;

public class CLTypeInteger extends CLType {
	public static final CLType INSTANCE = new CLTypeInteger();
	public static final CLType POWER_INSTANCE = new CLPowerType(INSTANCE);
	private PhysicalType ptype;

	public CLTypeInteger(PhysicalType ptype) {
		super(alphabet.INT, "int");
		this.ptype = ptype;
	}

	private CLTypeInteger() {
		super(alphabet.INT, "int");
	}

	public PhysicalType getPhysicalType() {
		return ptype;
	}

	@Override
	public boolean isPolymorphic() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (ptype == null ? 0 : ptype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof CLTypeAny) {
			return obj.equals(this);
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CLTypeInteger other = (CLTypeInteger) obj;
		if (ptype == null) {
			if (other.ptype != null) {
				return false;
			}
		} else if (!ptype.equals(other.ptype)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTemplate() {
		return false;
	}

	@Override
	public boolean unify(TypeSolution solution, CLType other) {
		if (other instanceof CLTypeInteger) {
			final CLTypeInteger otheri = (CLTypeInteger) other;
			if (ptype != null && otheri.ptype != null) {
				final boolean comp = ptype.equals(otheri.ptype);
				return comp;
			} else {
				return true;
			}
		} else {
			return super.unify(solution, other);
		}
	}

	@Override
	public String toString() {
		if (ptype == null) {
			return super.toString();
		} else {
			return super.toString() + ":" + ptype.toString();
		}
	}

	@Override
	public boolean isExtended() {
		return ptype != null;
	}

	@Override
	protected Set<String> buildTypeVariables() {
		return Collections.emptySet();
	}
}
