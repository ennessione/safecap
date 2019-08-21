package uk.ac.ncl.prime.sim.sets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BSet<T> implements Iterable<T>, Serializable {

	private static final long serialVersionUID = -5607641913079068986L;

	// random number generator is used to implement operator some()
	private static Random rand = new Random();

	public static final BSet<Object> BSET_EMPTY = new BSet<>();

	protected List<T> el;

	protected BSet() {
		el = Collections.EMPTY_LIST;
	}

	public BSet(BSet<T> set) {
		el = set.el;
		assert isWellFormed();
	}

	public BSet(List<T> list) {
		el = list;
		// assert isWellFormed();
	}

	public BSet(Set<T> set) {
		el = new ArrayList<>(set);
		assert isWellFormed();
	}

	private boolean isWellFormed() {
		if (el.isEmpty()) {
			return true;
		}
		final Class i0 = el.get(0).getClass();
		for (int i = 1; i < el.size(); i++) {
			if (!el.get(i).getClass().equals(i0)) {
				return false;
			}
		}

		return true;
	}

//	public BSet(T... elements) {
//		this(Arrays.asList(elements));
//	}

	public Collection<T> asCollection() {
		return el;
	}

	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

	public Object[] toArray() {
		return el.toArray();
	}

	public static BSet<Integer> mkRange(int from, int to) {
		final List<Integer> r = new ArrayList<>(to - from + 1);
		for (int i = from; i <= to; i++) {
			r.add(i);
		}

		return new BSet<>(r);
	}

	public static BSet BSet_SETC(Object... elements) {
		return new BSet(Arrays.asList(elements));
	}

	public Iterable<T> elements() {
		return el;
	}

	public T getByIndex(int index) throws InvalidSetOpException {
		if (index < 0 || index >= el.size()) {
			throw new InvalidSetOpException("invalid index");
		}

		return el.get(index);
	}

	public T getByIndexorNull(int index) throws InvalidSetOpException {
		if (index < 0 || index >= el.size()) {
			return null;
		}

		return el.get(index);
	}

	public boolean contains(Object element) {
		return el.contains(element);
	}

	public boolean contains(BSet<T> subset) throws InvalidSetOpException {
		for (final T t : subset.elements()) {
			if (!el.contains(t)) {
				return false;
			}
		}

		return true;
	}

	public boolean containsStrictly(BSet<T> subset) throws InvalidSetOpException {
		if (card() <= subset.card()) {
			return false;
		}

		return contains(subset);
	}

	public boolean _containsStrictly(BSet<?> subset) throws InvalidSetOpException {
		if (card() <= subset.card()) {
			return false;
		}

		return contains(subset);
	}

	public BSet<T> union(BSet<T> set) {
		final List<T> r = new ArrayList<>(this.el);
		for (final T x : set) {
			if (!r.contains(x)) {
				r.add(x);
			}
		}
		return new BSet<>(r);
	}

	public BSet<T> intersect(BSet<T> set) {
		final List<T> r = new ArrayList<>();
		if (el.size() < set.el.size()) {
			for (final T e : el) {
				if (set.el.contains(e)) {
					r.add(e);
				}
			}
		} else {
			for (final T e : set.el) {
				if (el.contains(e)) {
					r.add(e);
				}
			}

		}
		return new BSet<>(r);
	}

	public BSet<T> union(BSet<T>... sets) throws InvalidSetOpException {
		final List<T> r = new ArrayList<>(this.el);
		for (final BSet<T> set : sets) {
			for (final T x : set) {
				if (!r.contains(x)) {
					r.add(x);
				}
			}
		}
		return new BSet<>(r);
	}

	public BSet<T> inter(BSet<T>... sets) throws InvalidSetOpException {
		final List<BSet<T>> ss = new ArrayList<>();
		ss.add(this);
		for (final BSet<T> s : sets) {
			ss.add(s);
		}
		ss.sort(new Comparator<BSet<T>>() {
			@Override
			public int compare(BSet<T> o1, BSet<T> o2) {
				return o1.el.size() - o2.el.size();
			}
		});

		final List<T> r = new ArrayList<>();

		for (final T e : ss.get(0)) {
			boolean found = true;
			for (int i = 1; i < ss.size(); i++) {
				final BSet<T> other = ss.get(i);
				if (!other.el.contains(e)) {
					found = false;
					break;
				}
			}
			if (found) {
				r.add(e);
			}
		}

		return new BSet<>(r);
	}

	public BSet<T> setminus(BSet<T> set) {
		final List<T> r = new ArrayList<>(this.el);
		r.removeAll(set.el);
		return new BSet<>(r);
	}

	public BSet<T> setminus(BSet<T>... sets) {
		final List<T> r = new ArrayList<>(sets[0].el);

		for (int i = 1; i < sets.length; i++) {
			r.removeAll(sets[i].el);
		}

		return new BSet<>(r);
	}

	public <U> BSet<BMap<T, U>> times(BSet<U> set) throws InvalidSetOpException {
		final List<BMap<T, U>> r = new ArrayList<>();

		for (final T a : el) {
			for (final U b : set.el) {
				r.add(new BMap<>(a, b));
			}
		}

		return new BRel<>(r);
	}

	public T some() throws InvalidSetOpException {
		return el.get(randInt(0, el.size() - 1));
	}

	public int card() {
		return el.size();
	}

	public BRel<T, T> id() throws InvalidSetOpException {
		final List<BMap<T, T>> r = new ArrayList<>();

		for (final T t : el) {
			r.add(new BMap<>(t, t));
		}

		return new BRel<>(r);
	}

	/**
	 * Computes the union of sets
	 * 
	 * @param sets a set of sets
	 * @return union of sets
	 * @throws InvalidSetOpException
	 */
	public static <T> BSet<T> Union(BSet<BSet<T>> sets) throws InvalidSetOpException {
		if (sets.isEmpty()) {
			return (BSet<T>) BSET_EMPTY;
		}

		final List<T> r = new ArrayList<>(sets.el.get(0).el);

		for (final BSet<T> set : sets) {
			for (final T e : set) {
				if (!r.contains(e)) {
					r.add(e);
				}
			}
		}

		return new BSet<>(r);
	}

	/**
	 * Computes the intersection of passed sets; the value of 'this' object is
	 * ignored
	 * 
	 * @param sets a set of sets
	 * @return intersection of sets
	 * @throws InvalidSetOpException
	 */
	public static <T> BSet<T> Inter(BSet<BSet<T>> sets) throws InvalidSetOpException {
		if (sets.isEmpty()) {
			return (BSet<T>) BSET_EMPTY;
		}

		final BSet<T> base = sets.el.get(0);
		final BSet<T>[] arg = new BSet[sets.el.size() - 1];
		for (int i = 1; i < sets.el.size(); i++) {
			arg[i - 1] = sets.el.get(i);
		}

		return base.inter(arg);
	}

	public static Integer min(BSet<Integer> set) throws InvalidSetOpException {
		if (set.isEmpty()) {
			throw new InvalidSetOpException("min(..) expects a non-empty set");
		}

		int min_index = 0;
		for (int i = 1; i < set.el.size(); i++) {
			if (set.el.get(i) < set.el.get(min_index)) {
				min_index = i;
			}
		}

		return set.el.get(min_index);
	}

	public static Integer max(BSet<Integer> set) throws InvalidSetOpException {
		if (set.isEmpty()) {
			throw new InvalidSetOpException("max(..) expects a non-empty set");
		}

		int max_index = 0;
		for (int i = 1; i < set.el.size(); i++) {
			if (set.el.get(i) > set.el.get(max_index)) {
				max_index = i;
			}
		}

		return set.el.get(max_index);
	}

	public static int summation(BSet<Integer> set) {
		int r = 0;

		for (final Integer e : set.el) {
			r += e;
		}

		return r;
	}

	public boolean isEmpty() {
		return el.size() == 0;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		boolean f = true;
		sb.append("{");
		for (final T e : el) {
			if (!f) {
				sb.append(", ");
			}
			sb.append(e.toString());
			f = false;
		}
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return el.iterator();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BSet<?>) {
			final BSet<?> obj_set = (BSet<?>) obj;
			if (obj_set.el.size() != el.size()) {
				return false;
			}
			for (final Object o : obj_set.el) {
				if (!el.contains(o)) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return el.hashCode();
	}

	final public <V, U> BRel<V, U> toRel() throws InvalidSetOpException {
		if (this instanceof BRel) {
			return (BRel<V, U>) this;
		} else if (el.isEmpty()) {
			return BRel.BREL_EMPTY;
		} else if (el.get(0) instanceof BMap) {
			final BRel rel = new BRel(el);
			return rel;
		} else {
			System.err.println("Coercion to relation failed for " + this);
			return null;
		}
	}

	final public <V, U> BFun<V, U> toFun() throws InvalidSetOpException {
		if (this instanceof BFun) {
			return (BFun<V, U>) this;
		} else if (el.isEmpty()) {
			return BFun.BFUN_EMPTY;
		} else if (this instanceof BRel) {
			return new BFun(el);
		} else if (el.get(0) instanceof BMap) {
			return new BFun(el);
		} else {
			System.err.println("Coercion to function failed for " + this);
			return null;
		}
	}
}
