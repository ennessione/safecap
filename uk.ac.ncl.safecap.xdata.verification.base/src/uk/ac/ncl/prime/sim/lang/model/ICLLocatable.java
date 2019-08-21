package uk.ac.ncl.prime.sim.lang.model;

public interface ICLLocatable {

	/**
	 * Returns a child element that syntactically occupies a given offset
	 * 
	 * @param offset offset from the start of text
	 * @return a child element if found, null otherwise
	 */
	CLStatement locateChildStatement(int offset);
}
