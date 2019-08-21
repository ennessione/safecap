package uk.ac.ncl.safecap.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashBag<T extends Comparable<T>> implements Collection<T> {
	private Map<T, Integer> map;

	public HashBag() {
		map = new HashMap<T, Integer>();
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	@Override
	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}

	@Override
	public Object[] toArray() {
		throw new IllegalArgumentException();
	}

	@Override
	public <Z> Z[] toArray(Z[] a) {
		throw new IllegalArgumentException();
	}
	
	public int get(T e) {
		if (map.containsKey(e)) 
			return map.get(e);
		else
			return 0;
	}	

	@Override
	public boolean add(T e) {
		map.put(e, get(e) + 1);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (map.containsKey(o)) {
			T e = (T) o;
			if (get(e) > 0)
				map.put(e, get(e) - 1);
			else
				map.remove(e);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new IllegalArgumentException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for(T t: c)
			add(t);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for(Object t: c)
			remove(t);
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new IllegalArgumentException();
	}

	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		List<T> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		
		for(int i = 0; i < keys.size(); i++) {
			T t = keys.get(i);
			if (i > 0)
				sb.append(", ");
			sb.append(t);
			sb.append(": ");
			sb.append(map.get(t));
		}
		
		return sb.toString();
	}
	
}
