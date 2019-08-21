package uk.ac.ncl.prime.sim.sets;

import java.util.ArrayList;
import java.util.List;

public class BSeq<T> extends BRel<Integer, T> {
	private static final long serialVersionUID = 7944654143173946719L;
	public static final BSeq<Object> BSEQ_EMPTY = new BSeq<>();

	protected BSeq() {
		super();
	}

	public BSeq(List<BMap<Integer, T>> raw) {
		super(raw);
	}

	public T getSeqElement(int index) {
		try {
			return super.getByIndex(index).prj2();
		} catch (final InvalidSetOpException e) {
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> BSeq<T> make(List<T> raw) {
		return new BSeq(zip(raw));
	}

	private static <T> List<BMap<Integer, T>> zip(List<T> raw) {
		final List<BMap<Integer, T>> r = new ArrayList<>();

		int i = 0;
		for (final T t : raw) {
			r.add(new BMap<>(i++, t));
		}

		return r;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("[");
		for (int i = 0; i < el.size(); i++) {
			final BMap<Integer, T> map = el.get(i);
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(map.prj2().toString());
		}
		sb.append("]");

		return sb.toString();
	}
}
