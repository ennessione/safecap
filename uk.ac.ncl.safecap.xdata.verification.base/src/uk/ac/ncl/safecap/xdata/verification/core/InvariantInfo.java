package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sapphire.Element;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.verification.ITag;
import uk.ac.ncl.safecap.xdata.verification.PredicateCategory;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;

public class InvariantInfo {
	private Map<String, List<String>> attributes;
	public CLExpression invariant;
	public String name;
	public String key;
	public PredicateDefinition source;

	public InvariantInfo(CLExpression invariant, String name, PredicateDefinition source) {
		this.invariant = invariant;
		this.name = name;
		key = source.getKey().content();
		this.source = source;
		buildAttributes();
	}

	public InvariantInfo(PredicateDefinition pr) {
		this(pr.getParsed().content(), pr.getId().content(), pr);
	}

	public boolean hasAttribute(String attribute) {
		if (attributes != null) {
			return attributes.containsKey(attribute);
		} else {
			return false;
		}
	}

	public List<String> getAttribute(String attribute) {
		if (attributes != null) {
			return attributes.get(attribute);
		} else {
			return null;
		}
	}

	private void buildAttributes() {
		attributes = new HashMap<>();
		for (final ITag tag : source.getTags()) {
			addAttribute(tag.getName().content(), tag.getValue().content());
		}

		Element parent = source.parent().element();
		while (parent instanceof PredicateCategory) {
			final PredicateCategory cat = (PredicateCategory) parent;
			for (final ITag tag : cat.getTags()) {
				addAttribute(tag.getName().content(), tag.getValue().content());
			}
			parent = cat.parent().element();
		}
	}

	private void addAttribute(String key, String value) {
		List<String> l = attributes.get(key);
		if (l == null) {
			l = new ArrayList<>();
			attributes.put(key, l);
		}

		l.add(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (key == null ? 0 : key.hashCode());
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
		final InvariantInfo other = (InvariantInfo) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
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