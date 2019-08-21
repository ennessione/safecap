package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Collections;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class CLTypeAny extends CLType {
	private final SourceLocation location;
	private CLType bakedType;

	public CLTypeAny(SourceLocation location) {
		super(alphabet.SKIP, "ANY");
		this.location = location;
	}

	/**
	 * Type baking fixates a type variable to a monomorphic type
	 * 
	 * @param type a monomorphic type
	 */
	public void bake(CLType type) {
		assert type != null && !type.isPolymorphic() && !type.isTemplate();
		bakedType = type;
	}

	public SourceLocation getLocation() {
		return location;
	}

	public CLType getBakedType() {
		return bakedType;
	}

	@Override
	public boolean isPolymorphic() {
		return true;
	}

	@Override
	public boolean isTemplate() {
		return false;
	}

	@Override
	public boolean equals(Object other) {
		if (bakedType != null) {
			return bakedType.equals(other);
		} else {
			return this == other;
		}

	}

	@Override
	public boolean isSet() {
		if (bakedType != null) {
			return bakedType.isSet();
		} else {
			return false;
		}
	}

	@Override
	public boolean isSequence() {
		if (bakedType != null) {
			return bakedType.isSequence();
		} else {
			return false;
		}
	}

	@Override
	public boolean isRelation() {
		if (bakedType != null) {
			return bakedType.isRelation();
		} else {
			return false;
		}
	}

	@Override
	public CLType domType() {
		if (bakedType != null) {
			return bakedType.domType();
		} else {
			return null;
		}
	}

	@Override
	public CLType ranType() {
		if (bakedType != null) {
			return bakedType.ranType();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		if (bakedType != null) {
			return bakedType.toString();
		} else {
			return "?" + super.hashCode();
		}
	}

	@Override
	public boolean isExtended() {
		return false;
	}

	@Override
	protected Set<String> buildTypeVariables() {
		return Collections.emptySet();
	}
}
