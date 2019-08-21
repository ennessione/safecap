package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTMole extends PhysicalUnit {
	public static final PTMole INSTANCE = new PTMole();

	private PTMole() {
	};

	public static PTMole create() {
		return INSTANCE;
	}

	@Override
	public String getTag() {
		return "mol";
	}

	@Override
	public String getName() {
		return "mole";
	}

}
