package uk.ac.ncl.safecap.textentry.actions;

import uk.ac.ncl.safecap.textentry.core.TEPart;

public class TestAction implements ITEAction {

	@Override
	public String getLabel() {
		return "test";
	}

	@Override
	public boolean isEnabledFor(TEPart part) {
		return true;
	}

	@Override
	public void execute(TEPart part) throws Exception {
	}

}
