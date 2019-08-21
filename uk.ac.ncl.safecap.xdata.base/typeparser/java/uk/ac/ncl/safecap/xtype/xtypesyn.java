package uk.ac.ncl.safecap.xtype;

import java.util.*;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;

public class xtypesyn {
    private int operation;
    private List<xtypesyn> siblings = null;
    private String synid = null;
    private xtypeval value = null;
    private int start = -1;
    private int end = -1;
    private int startLine = -1;
    private int startCol = -1;
    private int endLine = -1;
    private int endCol = -1;

    public static final xtypesyn empty = null;

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

    public String getSynId() {
	return synid;
    }

    // process multi-expression in a step-by-step manner
    public static xtypesyn multipack(int opcode, xtypesyn l, xtypesyn r) {
	if (l.operation == opcode) {
	    return l.add(r);
	} else {
	    return new xtypesyn(opcode, l, r);
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


   public xtypesyn loc(Symbol symbol) {
	if (symbol instanceof ComplexSymbolFactory.ComplexSymbol) {
		ComplexSymbolFactory.ComplexSymbol sym = (ComplexSymbolFactory.ComplexSymbol) symbol;
		System.out.println("** sym: " + sym);	
	}
	
	return this;
    }

    public xtypesyn loc(int xstart, int xend) {
	// do nothing	
	return this;
    }

/*
    public xtypesyn oldloc(int line, int col) {
	if (startLine == -1) {
	    startLine = line;
	    startCol = col;
	} else if (line < startLine) {
	    startLine = line;
	    startCol = col;
	} else if (line == startLine && col < startCol) {
	    startCol = col;
	}

	if (stopLine == -1) {
	    stopLine = line;
	    stopCol = col;
	} else if (line > stopLine) {
	    stopLine = line;
	    stopCol = col;
	} else if (line == stopLine && col > stopCol) {
	    stopCol = col;
	}
	
	return this;
    }
*/
    public xtypesyn(int opcode)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>();
    }

    public xtypesyn(int opcode, xtypeval v)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>();
	value = v;
    }

    public xtypesyn(int opcode, xtypesyn l)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(1);
	add(l);
    }

    public xtypesyn(int opcode, xtypesyn l, xtypesyn r)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(2);
	add(l);
	add(r);
    }

    public xtypesyn(int opcode, xtypeval val, xtypesyn r)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(1);
	add(r);
	value = val;
    }

    public xtypesyn(int opcode, xtypesyn r, xtypeval val)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(1);
	add(r);
	value = val;
    }

    public xtypesyn(int opcode, String synid, xtypesyn r, xtypeval val)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(1);
	add(r);
	value = val;
	this.synid = synid;
    }

    public xtypesyn(int opcode, String synid, xtypeval val)
    {
	operation = opcode;
	value = val;
	this.synid = synid;
    }

    public xtypesyn(int opcode, String synid, xtypesyn r)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(1);
	add(r);
	this.synid = synid;
    }


    public xtypesyn(int opcode, String synid)
    {
	operation = opcode;
	this.synid = synid;
    }


    public xtypesyn(int opcode, xtypesyn f, xtypesyn s, xtypesyn t)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(3);
	add(f);
	add(s);
	add(t);
    }

    public xtypesyn(int opcode, xtypesyn f, xtypesyn s, xtypesyn t, xtypesyn a4)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(4);
	add(f);
	add(s);
	add(t);
	add(a4);
    }

    public xtypesyn(int opcode, xtypesyn f, xtypesyn s, xtypesyn t, xtypesyn a4, xtypesyn a5)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(5);
	add(f);
	add(s);
	add(t);
	add(a4);
	add(a5);
    }


    public xtypesyn(int opcode, xtypesyn f, xtypesyn s, xtypesyn t, xtypesyn a4, xtypesyn a5, xtypesyn a6)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(5);
	add(f);
	add(s);
	add(t);
	add(a4);
	add(a5);
	add(a6);
    }

    public xtypesyn(int opcode, xtypesyn f, xtypesyn s, xtypesyn t, xtypesyn a4, xtypesyn a5, xtypesyn a6, xtypesyn a7)
    {
	operation = opcode;
	siblings = new ArrayList<xtypesyn>(5);
	add(f);
	add(s);
	add(t);
	add(a4);
	add(a5);
	add(a6);
	add(a7);
    }

    public xtypeval value()
    {
	return value;
    }

    public xtypesyn setId(String synid)
    {
	this.synid = synid;
	return this;
    }


    public xtypesyn add(xtypesyn s)
    {
	if (s != null) {
		siblings.add(s);
		setAbsolutePosition(s.start, s.end);
		setLineColPosition(s.startLine, s.startCol);
		setLineColPosition(s.endLine, s.endCol);
	}
	return this;
    }

    public xtypesyn join(xtypesyn s)
    {
	//siblings.addAll(s.siblings);
	for(xtypesyn z: s.siblings)
		add(z);

	return this;
    }
        
    public void remove(xtypesyn s)
    {
	siblings.remove(s);
    }
    
    public void remove(int i)
    {
	siblings.remove(i);
    }
        
    public void replace(xtypesyn oldtree, xtypesyn newtree)
    {
	for(int i = 0; i < siblings.size(); i++)
	    if (siblings.get(i) == oldtree) siblings.set(i, newtree);
    }
    
    public int op() 
    {
	return operation;
    }
    
    public int siblings()
    {
	if (siblings == null) return 0;
	else return siblings.size();
    }
    
    public xtypesyn sibling(int i)
    {
	return (xtypesyn) siblings.get(i);
    }	    

    public List<xtypesyn> getSiblings()
    {
	return siblings;
    }	    


}
