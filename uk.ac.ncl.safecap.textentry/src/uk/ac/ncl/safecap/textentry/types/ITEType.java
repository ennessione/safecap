package uk.ac.ncl.safecap.textentry.types;

import uk.ac.ncl.safecap.textentry.core.TEPart;

public interface ITEType {
	Object[] EMPTY_ARRAY = new Object[0];

	String bestGuess(TEPart element, String entry);

	String checkMember(TEPart element, Object value);

	Object[] suggest(TEPart element, String prefix);
}
