package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Collections;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public class CLTypeBool extends CLType {
	public static final CLType INSTANCE = new CLTypeBool();
	public static final CLType POWER_INSTANCE = new CLPowerType(INSTANCE);

	private CLTypeBool() {
		super(alphabet.BOOL, "bool");
	}

	@Override
	public boolean equals(Object _o) {
		if (_o == this) {
			return true;
		} else if (_o instanceof CLTypeAny) {
			return _o.equals(this);
		}
		return false;
	}

	@Override
	public boolean isPolymorphic() {
		return false;
	}

	@Override
	public boolean isTemplate() {
		return false;
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
