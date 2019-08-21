package uk.ac.ncl.prime.sim.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CLValuationContext {
	private final Map<String, Object> state;
	private final CLValuationContext parent;

	public CLValuationContext(CLValuationContext parent) {
		this.parent = parent;
		state = new HashMap<>();
	}

	public CLValuationContext getParent() {
		return parent;
	}

	public void defineNew(String id, Object value) {
		state.put(id, value);
	}

	public Collection<String> getIdentifiers() {
		return state.keySet();
	}

	public Object getValue(String id) {
		final Object value = state.get(id);
		if (value == null && parent != null) {
			return parent.getValue(id);
		}

		return value;
	}

	public void setValue(String id, Object newvalue) {
		final Object value = state.get(id);
		if (value == null && parent != null) {
			parent.setValue(id, newvalue);
			return;
		}

		state.put(id, newvalue);
	}

	@Override
	public String toString() {
		return state.toString();
	}
}
