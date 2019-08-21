package uk.ac.ncl.safecap.common;

import java.util.Map.Entry;

public class MyEntry<T, S> implements Entry<T, S> {
	private final T key;
	private S value;

	public MyEntry(T key, S value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public T getKey() {
		return key;
	}

	@Override
	public S getValue() {
		return value;
	}

	@Override
	public S setValue(S value) {
		this.value = value;
		return value;
	}

}