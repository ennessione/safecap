package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.modeling.annotations.Label;

@Label(standard = "predicatekind")

public enum PredicateKind {
	@Label(standard = "axiom")
	AXIOM,

	@Label(standard = "invariant")
	INVARIANT,

	@Label(standard = "lemma")
	LEMMA
}