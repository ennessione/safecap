package uk.ac.ncl.safecap.misc;

import java.util.List;

public class ResourceContainer extends Container {
	private final String type;

	public ResourceContainer(String label, String type, List<?> children) {
		super(label, children);
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
