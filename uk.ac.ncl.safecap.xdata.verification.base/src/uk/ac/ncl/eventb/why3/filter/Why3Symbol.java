package uk.ac.ncl.eventb.why3.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Why3Symbol {
	private final String name;
	private Set<String> dependentTheories = Collections.emptySet();

	public Why3Symbol(String name) {
		this.name = name;
	}

	public Why3Symbol(String name, String... theories) {
		this.name = name;
		dependentTheories = new HashSet<>(theories.length);
		for (final String s : theories) {
			dependentTheories.add(s);
		}
	}

	public String getName() {
		return name;
	}

	public Set<String> getDependentTheories() {
		return dependentTheories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Why3Symbol other = (Why3Symbol) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
