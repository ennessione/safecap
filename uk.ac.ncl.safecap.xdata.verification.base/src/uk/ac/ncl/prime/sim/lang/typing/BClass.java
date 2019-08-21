package uk.ac.ncl.prime.sim.lang.typing;

import uk.ac.ncl.prime.sim.sets.BSet;

public class BClass<T extends BProto<T>> extends BSet<T> {

	private static final long serialVersionUID = -4676837510601144531L;

	public BClass() {
		super();
	}

	public BClass(BSet<T> set) {
		super(set);
	}

	public void remove(T element) {
		throw new RuntimeException("remove(..) failed: cannot modify class");
	}

	public void add(BSet<T> set) {
		throw new RuntimeException("add(..) failed: cannot modify class");
	}

	public void remove(BSet<T> set) {
		throw new RuntimeException("remove(..) failed: cannot modify class");
	}

	public T getByName(String name) {
		for (final T e : el) {
			if (e.toString().equals(name)) {
				return e;
			}
		}

		return null;
	}

}
