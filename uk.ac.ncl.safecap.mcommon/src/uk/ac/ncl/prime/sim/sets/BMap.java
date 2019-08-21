package uk.ac.ncl.prime.sim.sets;

import java.io.Serializable;

public class BMap<U, V> implements Serializable {
	private static final long serialVersionUID = -1479093730556138411L;
	private U first;
	private V second;

	public BMap(U first, V second) {
		this.first = first;
		this.second = second;
	}

	public BMap<V, U> swap() {
		return new BMap<>(second, first);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BMap BMap_make(Object a, Object b) {
		return new BMap(a, b);
	}

	public U prj1() {
		return first;
	}

	public V prj2() {
		return second;
	}

	public void setFirst(U first) {
		this.first = first;
	}

	public void setSecond(V second) {
		this.second = second;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		if (first instanceof BMap) {
			sb.append("(" + first.toString() + ")");
		} else {
			sb.append(first.toString());
		}
		sb.append(" -> ");
		if (second instanceof BMap) {
			sb.append("(" + second.toString() + ")");
		} else {
			sb.append(second.toString());
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BMap<?, ?>) {
			final BMap<?, ?> obj_map = (BMap<?, ?>) obj;
			if (first == null || second == null) {
				return false;
			}
			return first.equals(obj_map.first) && second.equals(obj_map.second);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return first.hashCode() ^ second.hashCode();
	}
}
