package uk.ac.ncl.safecap.analytics.ctextract.core;

public interface IVisitor {
	void visit(CTEToken part);

	void next();

	/**
	 * Visits a token list
	 * 
	 * @param part list to visit
	 * @return true if the visitor should continue with the children of the list
	 */
	boolean enter(CTETokenList part);

	void leave(CTETokenList part);
}
