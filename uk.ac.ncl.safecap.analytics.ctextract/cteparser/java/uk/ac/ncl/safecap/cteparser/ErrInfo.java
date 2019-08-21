package uk.ac.ncl.safecap.cteparser;

import java_cup.runtime.Symbol;

public class ErrInfo extends TEMarker {
	
	public java_cup.runtime.Symbol symbol;
	public Object raw;

	public ErrInfo(int line, int column, String message) {
		super.line = line;
		super.column = column;
		super.message = message;
	}
	
	public ErrInfo(syntree s, String message) {
		this.line = s.getStartLine();
		this.column = s.getStartCol();
		this.start = s.getStart();
		this.end = s.getEnd();
		this.message = message;
	}	
}
