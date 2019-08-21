package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "conjecturekind")

public enum ConjectureKind {
	@Label(standard = "error")
	ERROR,

	@Label(standard = "warning")
	WARNING,

	@Label(standard = "notice")
	NOTICE
}