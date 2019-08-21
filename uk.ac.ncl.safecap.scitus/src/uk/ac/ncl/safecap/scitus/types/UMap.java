package uk.ac.ncl.safecap.scitus.types;

import java.util.HashMap;

public class UMap<T, U> extends HashMap<T, U> {
	private static final long serialVersionUID = -9138557620346588251L;

	public UMap() {
		super();
	}

	public UMap<T, U> _add(T first, U second) {
		put(first, second);
		return this;
	}

	public UMap<T, U> _add(UPair<T, U> pair) {
		put(pair.getFirst(), pair.getSecond());
		return this;
	}

}
