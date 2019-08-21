package uk.ac.ncl.prime.sim.sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of immutable functions. It creates a hash map to speed up function
 * valuation
 */
public class BFun<U, V> extends BRel<U, V> {
	private static final long serialVersionUID = 828192901111041245L;
	private final Map<U, V> function;

	@SuppressWarnings("rawtypes")
	public static final BFun BFUN_EMPTY = new BFun();

	@SuppressWarnings("unchecked")
	protected BFun() {
		super();
		function = Collections.EMPTY_MAP;
	}

	public BFun(List<BMap<U, V>> raw) {
		super(raw);
		function = new HashMap<>();
		for (final BMap<U, V> m : raw) {
			function.put(m.prj1(), m.prj2());
		}
	}

	public static <U> BFun<Integer, U> makeFromSequence(List<U> raw) {
		final List<BMap<Integer, U>> seq = new ArrayList<>(raw.size());
		for (int i = 0; i < raw.size(); i++) {
			seq.add(new BMap<>(i, raw.get(i)));
		}

		return new BFun<>(seq);
	}

	public V fapp(U el) throws InvalidSetOpException {

		final V r = function.get(el);

		if (r == null) {
			throw new InvalidSetOpException("fapp(..) outside function domain");
		}

		return r;
	}

	public <T> BFun<U, BMap<V, T>> directproduct(BFun<U, T> f) throws InvalidSetOpException {
		final List<BMap<U, BMap<V, T>>> r = new ArrayList<>(el.size());

		for (final BMap<U, V> m : el) {
			if (f.function.containsKey(m.prj1())) {
				r.add(new BMap<>(m.prj1(), new BMap<>(m.prj2(), f.function.get(m.prj1()))));
			}
		}

		return new BFun<>(r);
	}

}
