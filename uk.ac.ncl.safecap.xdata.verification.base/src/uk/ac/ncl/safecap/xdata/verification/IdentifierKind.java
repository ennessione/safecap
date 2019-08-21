package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "predicatekind")

public enum IdentifierKind {
	@Label(standard = "model variable")
	MODEL,

	@Label(standard = "given type")
	TYPE,

	@Label(standard = "constant")
	CONSTANT,

	@Label(standard = "enumerated set")
	ENUM
}