package uk.ac.ncl.prime.sim.parser;

public interface ICLSourceMarker {
	public enum SYMBOL_CLASS {
		GIVEN_TYPE, IDENTIFIER, BOUND_IDENTIFIER, OPERATOR, BUILTIN_TYPE, COMMENT, TERM, OTHER
	}

	String getText();

	void markStart(int from, int length);

	void markError(int from, int length, String message);

	void markWarning(int from, int length, String message);

	void markInfo(int from, int length, String message);

	void colour(int from, int length, SYMBOL_CLASS kind);

}
