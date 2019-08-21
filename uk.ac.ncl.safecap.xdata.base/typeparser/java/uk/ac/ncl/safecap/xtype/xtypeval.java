package uk.ac.ncl.safecap.xtype;

public class xtypeval {
    private Object value;
    
    public xtypeval(Object v)
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