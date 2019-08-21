package uk.ac.ncl.safecap.textentry.actions;

import uk.ac.ncl.safecap.textentry.core.TEPart;

public interface ITEAction {
	String getLabel();

	boolean isEnabledFor(TEPart part);

	void execute(TEPart part) throws Exception;
}
