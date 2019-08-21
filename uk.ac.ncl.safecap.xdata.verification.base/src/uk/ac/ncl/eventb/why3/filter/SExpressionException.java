package uk.ac.ncl.eventb.why3.filter;

import uk.ac.ncl.pparser.syntree;

public class SExpressionException extends Exception {
	private static final long serialVersionUID = -6120278415462995690L;
	private final syntree ast;
	private final String message;

	public SExpressionException(syntree ast, String message) {
		super();
		this.ast = ast;
		this.message = message;
	}

	public syntree getAst() {
		return ast;
	}

	public String getMessageString() {
		return message;
	}
}
