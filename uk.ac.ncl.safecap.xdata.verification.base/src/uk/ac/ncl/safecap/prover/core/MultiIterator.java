package uk.ac.ncl.safecap.prover.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
class MultiIterator implements Iterator {
	private Iterator current;
	private final Collection[] collections;
	private int index = 0;

	MultiIterator(Collection... collections) {
		this.collections = collections;
		current = nextIterator();
	}

	@Override
	public boolean hasNext() {
		while (current != null) {
			if (current.hasNext()) {
				return true;
			}
			current = nextIterator();
		}

		return false;
	}

	@Override
	public Object next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		return current.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private Iterator nextIterator() {
		Iterator result = null;

		while (result == null && index < collections.length) {
			if (collections[index] != null) {
				result = collections[index].iterator();
				collections[index] = null; // free memory
			}

			index++;
		}

		return result;
	}
}
