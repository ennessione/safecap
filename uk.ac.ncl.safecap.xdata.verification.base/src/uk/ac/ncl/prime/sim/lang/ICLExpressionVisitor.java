package uk.ac.ncl.prime.sim.lang;

public interface ICLExpressionVisitor {
	/**
	 * Visitor invocation
	 * 
	 * @param element    the element being visited now
	 * @param userobject optional user data
	 * @return true if traversal should continue with children nodes; false
	 *         otherwise
	 * @throws CLException
	 */
	boolean visit(CLExpression element, Object userobject) throws CLException;
}
