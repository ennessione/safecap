package uk.ac.ncl.safecap.cteparser;

import java.util.*;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;

public class syntree {
	private int operation;
	private List<syntree> siblings = null;
	private synvalue value = null;
	private int start = -1;
	private int end = -1;
	private int startLine = -1;
	private int startCol = -1;
	private int endLine = -1;
	private int endCol = -1;

	public static final syntree empty = null;

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getStartLine() {
		return startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getStartCol() {
		return startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	// process multi-expression in a step-by-step manner
	public static syntree multipack(int opcode, syntree l, syntree r) {
		if (l.operation == opcode) {
			return l.add(r);
		} else {
			return new syntree(opcode, l, r);
		}
	}

	public void setAbsolutePosition(int s, int e) {
		if (start == -1 || start > s)
			start = s;

		if (end == -1 || end < e)
			end = e;
	}

	public void setLineColPosition(int line, int col) {
		if (startLine == -1) {
			startLine = line;
			startCol = col;
		} else if (line < startLine) {
			startLine = line;
			startCol = col;
		} else if (line == startLine && col < startCol) {
			startCol = col;
		}

		if (endLine == -1) {
			endLine = line;
			endCol = col;
		} else if (line > endLine) {
			endLine = line;
			endCol = col;
		} else if (line == endLine && col > endCol) {
			endCol = col;
		}
	}

	public syntree loc(Symbol symbol) {
		if (symbol instanceof ComplexSymbolFactory.ComplexSymbol) {
			ComplexSymbolFactory.ComplexSymbol sym = (ComplexSymbolFactory.ComplexSymbol) symbol;
		}

		return this;
	}

	public syntree loc(int xstart, int xend) {
		// do nothing
		return this;
	}

	/*
	 * public syntree oldloc(int line, int col) { if (startLine == -1) { startLine =
	 * line; startCol = col; } else if (line < startLine) { startLine = line;
	 * startCol = col; } else if (line == startLine && col < startCol) { startCol =
	 * col; }
	 * 
	 * if (stopLine == -1) { stopLine = line; stopCol = col; } else if (line >
	 * stopLine) { stopLine = line; stopCol = col; } else if (line == stopLine &&
	 * col > stopCol) { stopCol = col; }
	 * 
	 * return this; }
	 */
	public syntree(int opcode) {
		operation = opcode;
		siblings = new ArrayList<syntree>();
	}

	public syntree(int opcode, synvalue v) {
		operation = opcode;
		value = v;
	}

	public syntree(int opcode, syntree l) {
		operation = opcode;
		siblings = new ArrayList<syntree>(1);
		add(l);
	}

	public syntree(int opcode, syntree l, syntree r) {
		operation = opcode;
		siblings = new ArrayList<syntree>(2);
		add(l);
		add(r);
	}

	public syntree(int opcode, synvalue val, syntree r) {
		operation = opcode;
		siblings = new ArrayList<syntree>(1);
		add(r);
		value = val;
	}

	public syntree(int opcode, synvalue val, syntree r, syntree s) {
		operation = opcode;
		siblings = new ArrayList<syntree>(2);
		add(r);
		add(s);
		value = val;
	}	
	
	public syntree(int opcode, syntree f, syntree s, syntree t) {
		operation = opcode;
		siblings = new ArrayList<syntree>(3);
		add(f);
		add(s);
		add(t);
	}

	public syntree(int opcode, syntree f, syntree s, syntree t, syntree a4) {
		operation = opcode;
		siblings = new ArrayList<syntree>(4);
		add(f);
		add(s);
		add(t);
		add(a4);
	}

	public syntree(int opcode, syntree f, syntree s, syntree t, syntree a4, syntree a5) {
		operation = opcode;
		siblings = new ArrayList<syntree>(5);
		add(f);
		add(s);
		add(t);
		add(a4);
		add(a5);
	}

	public syntree(int opcode, syntree f, syntree s, syntree t, syntree a4, syntree a5, syntree a6) {
		operation = opcode;
		siblings = new ArrayList<syntree>(5);
		add(f);
		add(s);
		add(t);
		add(a4);
		add(a5);
		add(a6);
	}

	public syntree(int opcode, syntree f, syntree s, syntree t, syntree a4, syntree a5, syntree a6, syntree a7) {
		operation = opcode;
		siblings = new ArrayList<syntree>(5);
		add(f);
		add(s);
		add(t);
		add(a4);
		add(a5);
		add(a6);
		add(a7);
	}

	public synvalue value() {
		return value;
	}

	public syntree add(syntree s) {
		if (s != null && siblings == null)
			siblings = new ArrayList<syntree>(2);
		
		if (s != null) {
			siblings.add(s);
			setAbsolutePosition(s.start, s.end);
			setLineColPosition(s.startLine, s.startCol);
			setLineColPosition(s.endLine, s.endCol);
		}
		return this;
	}

	public syntree join(syntree s) {
		// siblings.addAll(s.siblings);
		for (syntree z : s.siblings)
			add(z);

		return this;
	}

	public void remove(syntree s) {
		siblings.remove(s);
	}

	public void remove(int i) {
		siblings.remove(i);
	}

	public void replace(syntree oldtree, syntree newtree) {
		for (int i = 0; i < siblings.size(); i++)
			if (siblings.get(i) == oldtree)
				siblings.set(i, newtree);
	}

	public int op() {
		return operation;
	}

	public int siblings() {
		if (siblings == null)
			return 0;
		else
			return siblings.size();
	}

	public syntree sibling(int i) {
		return (syntree) siblings.get(i);
	}

	public List<syntree> getSiblings() {
		return siblings;
	}

}
