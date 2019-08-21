package uk.ac.ncl.safecap.gui.verification;

import java.util.List;

public interface IVerificationProfile {
	String getName();

	List<IVerificationTool> getTools();
}
