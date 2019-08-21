package uk.ac.ncl.prime.sim.lang.parser;

public class synvalue {
    private Object value;
    
    public synvalue(Object v)
    {
	value = v;
    }
    
    public Object getValue()
    {
	return value;
    }
    
    public Class getType()
    {
	return value.getClass();
    }
    
    public String toString()
    {
	return value.toString();
    }
}