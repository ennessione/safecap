package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTMetre extends PhysicalUnit {
	public static final PTMetre INSTANCE = new PTMetre();

	private PTMetre() {
	};

	public static PTMetre create() {
		return INSTANCE;
	}

	@Override
	public String getTag() {
		return "m";
	}

	@Override
	public String getName() {
		return "metre";
	}

}
