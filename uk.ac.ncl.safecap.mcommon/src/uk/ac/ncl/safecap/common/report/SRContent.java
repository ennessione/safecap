package uk.ac.ncl.safecap.common.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SRContent extends SRPart {
	private List<SRFormatted> children;

	public SRContent add(SRFormatted child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);

		return this;
	}

	public SRContent add(SRPart child) {
		return add((SRFormatted) child);
	}

	public SRContent add(String text) {
		return add(new SFPlain(text));
	}

	public List<SRFormatted> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		}

		return children;
	}
}
