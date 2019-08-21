package uk.ac.ncl.safecap.common.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SRContainer extends SRPart {
	private List<SRPart> children;

	public SRContainer add(SRPart child) {
		if (child == null) {
			return this;
		}
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);

		return this;
	}

	public SRContainer add(String child) {
		if (child == null) {
			return this;
		}
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(new SFPlain(child));

		return this;
	}	
	
	public List<SRPart> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		}

		return children;
	}
}
