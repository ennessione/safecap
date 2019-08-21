package uk.ac.ncl.safecap.xdata.provers;

public interface INameMapper {
	public enum KIND {
		FREE, BOUND, TYPE, TYPE_LITERAL, UNKNOWN
	};

	String insertLocallyBoundName(String name);

	String mapLocallyBoundName(String name);

	void removeLocallyBoundName(String name);

	String mapName(String name);

	String reverseName(String name);

	String mapType(String identifier) throws TranslationException;

	String mapTypeLiteral(String identifier);

	KIND getKind(String name);
}
