package uk.ac.ncl.safecap.scitus.types;

public class UTDPLib {

	// emulates 'functional' sequential composition:
	// e.g., seq<Double>(s ^ 1, f(3), x + 2)
	public static final <T> T seq(Object... functions) {
		return (T) functions[functions.length - 1];
	}

	public static final void fnwrap(Object _body) {
	}
}
