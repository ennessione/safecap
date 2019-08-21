package uk.ac.ncl.prime.sim.lang.typing;

import java.util.Collections;
import java.util.Set;

import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.parser.alphabet;

public abstract class CLUserType extends CLType {
	private final String name;
	private String ppId;

	public CLUserType(String id) {
		super(alphabet.ID, id);
		name = id;
		if (CLUtils.needsQuotes(id)) {
			ppId = "\"" + id + "\"";
		} else {
			ppId = id;
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object _o) {
		if (_o instanceof CLUserType) {
			final CLUserType gt = (CLUserType) _o;
			return name.equals(gt.name);
		} else if (_o instanceof CLTypeAny) {
			return _o.equals(this);
		}

		return false;
	}

	@Override
	public String toString() {
		return ppId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
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
		return Collections.singleton(name);
	}
}
