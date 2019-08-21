package uk.ac.ncl.prime.sim.lang.model;

import uk.ac.ncl.prime.sim.lang.CLException;

public interface ICLStatementVisitor {
	/**
	 * Visitor invocation
	 * 
	 * @param element    the element being visited now
	 * @param userobject optional user data
	 * @return true if traversal should continue with children nodes; false
	 *         otherwise
	 * @throws CLException
	 */
	boolean visit(CLStatement element, Object userobject) throws CLException;
}
