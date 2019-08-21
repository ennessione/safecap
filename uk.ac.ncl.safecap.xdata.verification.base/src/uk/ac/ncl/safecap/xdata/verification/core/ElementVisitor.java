package uk.ac.ncl.safecap.xdata.verification.core;

public interface ElementVisitor<T> {
	Object visit(T element, Object userData);
}
