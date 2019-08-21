package uk.ac.ncl.safecap.common.report;

import java.util.Collection;

public class SRList extends SRContainer {
	public SRList() {
		super();
	}

	public SRList(SRContainer parent) {
		super();
		parent.add(this);
	}
	
	public SRList(SRContainer parent, String caption) {
		super();
		parent.add(this);
		this.set(TITLE, caption);
	}

	public SRList(Collection<?> collection) {
		super();
		for (final Object s : collection) {
			add(new SFPlain(s.toString()));
		}
	}

}
