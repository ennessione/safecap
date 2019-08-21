package uk.ac.ncl.safecap.analytics.ctextract.core;

public interface ITokenVisitor {
	void visit(CTEToken token, Object data);
}
