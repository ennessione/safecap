package uk.ac.ncl.prime.sim.lang.parser;

import java_cup.runtime.Symbol;


public class ErrInfo {
    public int line;
    public int column;
    public int start;
    public int end;
    public String message;
    public java_cup.runtime.Symbol symbol;
    public Object raw;
    
    public ErrInfo(int line, int column, String message) {
	this.line = line;
	this.column = column;
	this.message = message;
    }
}
