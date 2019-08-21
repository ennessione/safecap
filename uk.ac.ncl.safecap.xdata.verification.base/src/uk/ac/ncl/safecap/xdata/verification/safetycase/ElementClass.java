package uk.ac.ncl.safecap.xdata.verification.safetycase;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "elementclass")
public enum ElementClass {
	@Label(standard = "Conceptual")
	CONCEPTUAL,

	@Label(standard = "Operational")
	OPERATIONAL,

	@Label(standard = "Implementation")
	IMPLEMENTATION

}