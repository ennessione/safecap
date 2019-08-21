package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "conjectureclass")
public enum ConjectureClass {
	@Label(standard = "none")
	NONE,

	@Label(standard = "consistency")
	CONSISTENCY,

	@Label(standard = "correctness")
	CORRECTNESS
}