package uk.ac.ncl.safecap.xmldata.normalisation;

import java.util.Map;

public class Coercion extends Normalisation {
	private final String type;
	private final Map<String, String> map;

	public Coercion(String type, Map<String, String> map) {
		super();
		this.type = type;
		this.map = map;
	}

	@Override
	public String normalise(String literal, String type) {
		if (this.type.equals(type) && map.containsKey(literal)) {
			return map.get(literal);
		}

		return null;
	}

}
