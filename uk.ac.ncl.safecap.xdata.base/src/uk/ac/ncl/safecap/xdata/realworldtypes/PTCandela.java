package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlType;

@XmlType(factoryMethod = "create")
public class PTCandela extends PhysicalUnit {
	public static final PTCandela INSTANCE = new PTCandela();

	private PTCandela() {
	};

	public static PTCandela create() {
		return INSTANCE;
	}

	@Override
	public String getTag() {
		return "cd";
	}

	@Override
	public String getName() {
		return "candela";
	}

}
