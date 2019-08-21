package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.safecap.xmldata.ValueList;

public abstract class BaseGenerator implements IInjectionGenerator {

	protected abstract String getKind();

	protected static <E> E pickRandomElement(Collection<? extends E> coll, Random rangen) {
		if (coll.size() == 0) {
			return null; // or throw IAE, if you prefer
		}

		final int index = rangen.nextInt(coll.size());
		if (coll instanceof List) {
			return ((List<? extends E>) coll).get(index);
		} else {
			final Iterator<? extends E> iter = coll.iterator();
			for (int i = 0; i < index; i++) {
				iter.next();
			}

			return iter.next();
		}
	}

	protected Object pickReplacementValue(Collection<?> base, List<Object> data, Random rangen) {
		final List<Object> candidates = new ArrayList<>();
		candidates.addAll(base);
		if (data != null) {
			for (final Object dx : data) {
				if (dx instanceof ValueList) {
					final ValueList vl = (ValueList) dx;
					candidates.remove(vl.get(1));
				} else {
					candidates.remove(dx);
				}

			}
		}

		if (candidates.size() == 0) {
			return null;
		}

		final int index = rangen.nextInt(candidates.size());
		return candidates.get(index);
	}
}
