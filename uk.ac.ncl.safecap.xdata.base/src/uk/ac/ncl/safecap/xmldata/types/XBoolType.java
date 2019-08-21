package uk.ac.ncl.safecap.xmldata.types;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public final class XBoolType extends XType {
	public static final XBoolType INSTANCE = new XBoolType();

	private XBoolType() {
	}

	public static XBoolType create() {
		return INSTANCE;
	}

	@Override
	public String toString() {
		return "bool";
	}

}
