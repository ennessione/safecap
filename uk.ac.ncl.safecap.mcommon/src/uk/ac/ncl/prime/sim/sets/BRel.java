package uk.ac.ncl.prime.sim.sets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of immutable relations. It caches dom and ran sets
 */
public class BRel<U, V> extends BSet<BMap<U, V>> {
	public static int LIMIT = 20;

	private static final long serialVersionUID = 6653582166357515531L;

	@SuppressWarnings("rawtypes")
	public static final BRel BREL_EMPTY = new BRel();

	private BSet<U> dom;
	private BSet<V> ran;

	private final Map<U, BSet<V>> singletonCache;

	private BRel<V, U> converse;

	@SuppressWarnings("unchecked")
	protected BRel() {
		super();
		dom = (BSet<U>) BSet.BSET_EMPTY;
		ran = (BSet<V>) BSet.BSET_EMPTY;
		singletonCache = new HashMap<>();
	}

	public BRel(List<BMap<U, V>> raw) {
		super(raw);
		singletonCache = new HashMap<>();
		buildRanDom(raw);
	}

	private void buildRanDom(List<BMap<U, V>> raw) {
		final List<U> ldom = new ArrayList<>();
		final List<V> lran = new ArrayList<>();
		for (final BMap<U, V> m : raw) {
			if (!ldom.contains(m.prj1())) {
				ldom.add(m.prj1());
			}
			if (!lran.contains(m.prj2())) {
				lran.add(m.prj2());
			}
		}

		dom = new BSet<>(ldom);
		ran = new BSet<>(lran);
	}

	public BSet<U> dom() throws InvalidSetOpException {
		return dom;
	}

	public BSet<V> ran() throws InvalidSetOpException {
		return ran;
	}

	public BRel<V, U> inverse() throws InvalidSetOpException {
		if (converse == null) {
			final List<BMap<V, U>> r = new ArrayList<>(el.size());

			for (final BMap<U, V> m : el) {
				r.add(new BMap<>(m.prj2(), m.prj1()));
			}

			converse = new BRel<>(r);
		}
		return converse;
	}

	public BRel<U, V> domRestr(BSet<U> set) throws InvalidSetOpException {
		final List<BMap<U, V>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> m : el) {
			if (set.el.contains(m.prj1())) {
				r.add(m);
			}
		}

		return new BRel<>(r);
	}

	public BRel<U, V> domSubtr(BSet<U> set) throws InvalidSetOpException {
		final List<BMap<U, V>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> m : el) {
			if (!set.el.contains(m.prj1())) {
				r.add(m);
			}
		}

		return new BRel<>(r);
	}

	public BRel<U, V> ranRestr(BSet<V> set) throws InvalidSetOpException {
		final List<BMap<U, V>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> m : el) {
			if (set.el.contains(m.prj2())) {
				r.add(m);
			}
		}

		return new BRel<>(r);
	}

	public BRel<U, V> ranSubtr(BSet<V> set) throws InvalidSetOpException {
		final List<BMap<U, V>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> m : el) {
			if (!set.el.contains(m.prj2())) {
				r.add(m);
			}
		}

		return new BRel<>(r);
	}

	public BSet<V> image(BSet<U> set) throws InvalidSetOpException {
		final List<V> r = new ArrayList<>();

		for (final BMap<U, V> m : el) {
			if (set.contains(m.prj1()) && !r.contains(m.prj2())) {
				r.add(m.prj2());
			}
		}

		return new BSet<>(r);
	}

	public BSet<V> imageSingleton(U element) {

		final BSet<V> cached = singletonCache.get(element);
		if (cached != null) {
			return cached;
		}

		final List<V> r = new ArrayList<>();
		for (final BMap<U, V> m : el) {
			if (m.prj1().equals(element) && !r.contains(m.prj2())) {
				r.add(m.prj2());
			}
		}

		final BSet<V> rr = new BSet<>(r);
		singletonCache.put(element, rr);

		return rr;
	}

	public <T> BRel<U, T> forwardcomp(BRel<V, T> other) throws InvalidSetOpException {
		final List<BMap<U, T>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> a : el) {
			for (final BMap<V, T> b : other.el) {
				if (a.prj2().equals(b.prj1())) {
					r.add(new BMap<>(a.prj1(), b.prj2()));
				}
			}
		}

		return new BRel<>(r);
	}

	public BRel<U, V> ovr(BRel<U, V> other) throws InvalidSetOpException {
		final List<BMap<U, V>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> m : el) {
			if (other.dom.contains(m.prj1())) {
				r.add(m);
			}
		}

		r.addAll(other.el);

		return new BRel<>(r);
	}

	// ---

	public BRel<U, V> union(BRel<U, V> set) {
		final List<BMap<U, V>> r = new ArrayList<>(el);
		for (final BMap<U, V> x : set) {
			if (!r.contains(x)) {
				r.add(x);
			}
		}
		return new BRel<>(r);
	}

	public BRel<U, V> intersect(BRel<U, V> set) {
		final List<BMap<U, V>> r = new ArrayList<>();
		if (el.size() < set.el.size()) {
			for (final BMap<U, V> e : el) {
				if (set.el.contains(e)) {
					r.add(e);
				}
			}
		} else {
			for (final BMap<U, V> e : set.el) {
				if (el.contains(e)) {
					r.add(e);
				}
			}

		}
		return new BRel<>(r);
	}

	@SuppressWarnings("unchecked")
	public BRel<U, V> union(BRel<U, V>... sets) throws InvalidSetOpException {
		final List<BMap<U, V>> r = new ArrayList<>(el);
		for (final BRel<U, V> set : sets) {
			for (final BMap<U, V> x : set) {
				if (!r.contains(x)) {
					r.add(x);
				}
			}
		}
		return new BRel<>(r);
	}

	@SuppressWarnings("unchecked")
	public BRel<U, V> inter(BRel<U, V>... sets) throws InvalidSetOpException {
		final List<BRel<U, V>> ss = new ArrayList<>();
		ss.add(this);
		for (final BRel<U, V> s : sets) {
			ss.add(s);
		}
		ss.sort(new Comparator<BRel<U, V>>() {
			@Override
			public int compare(BRel<U, V> o1, BRel<U, V> o2) {
				return o1.el.size() - o2.el.size();
			}
		});

		final List<BMap<U, V>> r = new ArrayList<>();

		outer: for (final BMap<U, V> e : ss.get(0)) {
			for (int i = 1; i < ss.size(); i++) {
				final BRel<U, V> other = ss.get(i);
				if (!other.el.contains(e)) {
					break outer;
				}
				r.add(e);
			}
		}

		return new BRel<>(r);
	}

	public BRel<U, V> setminus(BRel<U, V> set) {
		final List<BMap<U, V>> r = new ArrayList<>(el);
		r.removeAll(set.el);
		return new BRel<>(r);
	}

	@SuppressWarnings("unchecked")
	public BRel<U, U> iteration(int n) throws InvalidSetOpException {
		BRel<U, U> r = (BRel<U, U>) this;

		for (int i = 0; i < n; i++) {
			r = r.forwardcomp((BRel<U, U>) this);
		}

		return r;
	}

	public BRel<U, U> closure1() throws InvalidSetOpException {
		return closure1(LIMIT);
	}

	@SuppressWarnings("unchecked")
	public BRel<U, U> closure1(int limit) throws InvalidSetOpException {
		BRel<U, U> r = (BRel<U, U>) this;

		for (int i = 0; i < limit; i++) {
			final BRel<U, U> prev = r;
			r = r.union(r.forwardcomp((BRel<U, U>) this));
			if (prev.card() == r.card()) {
				return r;
			}
		}

		throw new InvalidSetOpException("Failed computing closure1");
	}

	public BRel<U, U> closure() throws InvalidSetOpException {
		return closure(LIMIT);
	}

	@SuppressWarnings("unchecked")
	public BRel<U, U> closure(int limit) throws InvalidSetOpException {
		BRel<U, U> r = (BRel<U, U>) this;
		r = r.union(this.dom().id());

		for (int i = 0; i < limit; i++) {
			final BRel<U, U> prev = r;
			r = r.union(r.forwardcomp((BRel<U, U>) this));
			if (prev.card() == r.card()) {
				return r;
			}
		}

		throw new InvalidSetOpException("Failed computing closure1");
	}

	@SuppressWarnings("unchecked")
	public BRel<V, V> seqrel() {
		final BRel<Integer, V> s = (BRel<Integer, V>) this;
		s.el.sort(new IntMapComparator());
		final List<BMap<V, V>> r = new ArrayList<>();

		BMap<Integer, V> prev = s.el.get(0);

		for (int i = 1; i < s.el.size(); i++) {
			final BMap<Integer, V> mapi = s.el.get(i);
			r.add(new BMap<>(prev.prj2(), mapi.prj2()));
			prev = mapi;
		}

		return new BRel<>(r);
	}

	public BFun<U, BSet<V>> reltofun() {
		final List<BMap<U, BSet<V>>> r = new ArrayList<>();

		for (final U d : dom) {
			r.add(new BMap<>(d, imageSingleton(d)));
		}

		return new BFun<>(r);
	}

	@SuppressWarnings("unchecked")
	public BRel<U, V> funtorel() {
		final List<BMap<U, V>> r = new ArrayList<>();

		for (final BMap<U, V> m : el) {
			final BSet<V> set = (BSet<V>) m.prj2();
			for (final V z : set.el) {
				r.add(new BMap<>(m.prj1(), z));
			}
		}

		return new BRel<>(r);
	}

	@SuppressWarnings("unchecked")
	public BRel<BMap<U, Object>, Object> shiftLeft() {
		final List<BMap<BMap<U, Object>, Object>> r = new ArrayList<>();

		for (final BMap<U, V> m : el) {
			final BMap<Object, Object> compound = (BMap<Object, Object>) m.prj2();
			final BMap<U, Object> left = new BMap<>(m.prj1(), compound.prj1());
			r.add(new BMap<>(left, compound.prj2()));
		}

		return new BRel<>(r);
	}

	private class IntMapComparator implements Comparator<BMap<Integer, V>> {
		@Override
		public int compare(BMap<Integer, V> o1, BMap<Integer, V> o2) {
			return o1.prj1().compareTo(o2.prj1());
		}

	}
}
