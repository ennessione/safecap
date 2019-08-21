package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTSecond extends PhysicalUnit {
	public static final PTSecond INSTANCE = new PTSecond();

	private PTSecond() {
	};

	public static PTSecond create() {
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
