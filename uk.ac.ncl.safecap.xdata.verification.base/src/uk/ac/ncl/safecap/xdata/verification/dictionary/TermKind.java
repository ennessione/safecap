package uk.ac.ncl.safecap.xdata.verification.dictionary;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "termkind")
public enum TermKind {
	@Label(standard = "macro")
	LITERAL,

	@Label(standard = "constant")
	CACHED
}