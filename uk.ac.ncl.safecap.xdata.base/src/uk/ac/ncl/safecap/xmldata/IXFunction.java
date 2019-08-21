package uk.ac.ncl.safecap.xmldata;

import java.util.Collection;
import java.util.List;

import uk.ac.ncl.safecap.xmldata.types.XType;

public interface IXFunction {

	void add(Object from, Object to);

	List<Object> get(Object from);

	Object getAny(Object from);

	String getName();

	String getTag();

	String getCanonicalName();

	Collection<Object> domain();

	boolean isFunction();

	// compute type on the basis of domain and range elements, does not type any
	// untyped elements
	void computeType();

	// compute type and do type unification on untyped elements
	void inferType();

	XType getFunctionType();

	String getDescription();

	void setDescription(String description);

	void setName(String name);

	void setCanonicalName(String name);

	boolean isDerived();

	void setDerived(boolean flag);

}
