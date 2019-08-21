package uk.ac.ncl.safecap.analytics.ctextract.core;

public interface IParallelTokenVisitor {
	boolean visit(CTEToken token1, CTEToken token2, Object data);
}
