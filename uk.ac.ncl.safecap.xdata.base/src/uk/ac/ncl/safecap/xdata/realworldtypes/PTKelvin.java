package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTKelvin extends PhysicalUnit {
	public static final PTKelvin INSTANCE = new PTKelvin();

	private PTKelvin() {
	};

	public static PTKelvin create() {
		return INSTANCE;
	}

	@Override
	public String getTag() {
		return "K";
	}

	@Override
	public String getName() {
		return "kelvin";
	}

}
