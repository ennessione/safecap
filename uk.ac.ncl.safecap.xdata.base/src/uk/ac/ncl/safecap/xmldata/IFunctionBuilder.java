package uk.ac.ncl.safecap.xmldata;

public interface IFunctionBuilder {
	void addMapping(String canonical_namespace, String namespace, String attribute, Object block, Object value);
}
