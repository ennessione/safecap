package uk.ac.ncl.safecap.misc;

import java.util.List;

public class Container {
	private final String label;
	private final List<?> children;

	public Container(String label, List<?> children) {
		this.label = label;
		this.children = children;
	}

	public String getLabel() {
		return label;
	}

	public List<?> getChildren() {
		return children;
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	@Override
	public int hashCode() {
		return label.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Container == false) {
			return false;
		}

		return label.equals(((Container) obj).label);
	}

}
