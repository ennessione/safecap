package uk.ac.ncl.prime.sim.lang;

import uk.ac.ncl.prime.sim.parser.SourceLocation;

public class CLDummyElement extends CLElement {
	public static CLDummyElement INSTANCE = new CLDummyElement(null);

	public CLDummyElement(SourceLocation location) {
		super(-1, location);
	}

}
