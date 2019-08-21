package uk.ac.ncl.safecap.analytics.ctextract.core;

public interface ITokenFilter {
	boolean accept(CTEToken token, Object data);
}
