package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTKilogram extends PhysicalUnit {
	public static final PTKilogram INSTANCE = new PTKilogram();

	private PTKilogram() {
	};

	public static PTKilogram create() {
		return INSTANCE;
	}

	@Override
	public String getTag() {
		return "s";
	}

	@Override
	public String getName() {
		return "second";
	}

}
