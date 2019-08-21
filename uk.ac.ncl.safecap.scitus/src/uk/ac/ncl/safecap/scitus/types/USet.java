package uk.ac.ncl.safecap.scitus.types;

import java.util.HashSet;

public class USet<T> extends HashSet<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9039179296411998230L;

	public USet() {
		super();
	}

	public USet<T> _add(T e) {
		super.add(e);
		return this;
	}
}
