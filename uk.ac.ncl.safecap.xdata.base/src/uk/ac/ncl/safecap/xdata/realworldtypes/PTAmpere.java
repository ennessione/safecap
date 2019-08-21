package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTAmpere extends PhysicalUnit {
	public static final PTAmpere INSTANCE = new PTAmpere();

	private PTAmpere() {
	};

	public static PTAmpere create() {
		return INSTANCE;
	}

	@Override
	public String getTag() {
		return "A";
	}

	@Override
	public String getName() {
		return "ampere";
	}

}
