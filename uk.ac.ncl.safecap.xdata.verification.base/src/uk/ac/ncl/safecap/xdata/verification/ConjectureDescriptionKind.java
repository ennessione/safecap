package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "conjecturedescriptionkind")
public enum ConjectureDescriptionKind {
	@Label(standard = "None")
	NONE,

	@Label(standard = "Failure")
	FAILURE,

	@Label(standard = "Pending")
	PENDING,

	@Label(standard = "Success")
	SUCCESS

}