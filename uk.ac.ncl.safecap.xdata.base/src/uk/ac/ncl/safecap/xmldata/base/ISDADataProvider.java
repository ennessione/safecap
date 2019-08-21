package uk.ac.ncl.safecap.xmldata.base;

import java.util.Collection;
import java.util.Set;

import uk.ac.ncl.safecap.xmldata.IConceptMap;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;

public interface ISDADataProvider {
	String getName();

	Set<String> getEnumMembers(XEnumType type);

	Set<String> getEnumMembers(String type);

	XEnumType getEnum(String id);

	Collection<String> getEnums();

	Collection<String> getFunctionIds();

	IXFunction getFunction(String name);

	String getConceptMapSource();

	IConceptMap getConceptMap();

	Collection<String> getExternalMap(String element);

	Collection<String> getExternalFileLocations();

}
