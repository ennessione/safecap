package uk.ac.ncl.safecap.scitus.types;

public class UPair<T, U> {
	private final T first;
	private final U second;

	public UPair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public U getSecond() {
		return second;
	}
}
