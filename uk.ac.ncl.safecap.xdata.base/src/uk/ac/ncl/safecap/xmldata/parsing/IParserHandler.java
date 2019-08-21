package uk.ac.ncl.safecap.xmldata.parsing;

public interface IParserHandler {
	void start(String localName, int line, int column, int offset);

	void addAttribute(String name, String value);

	void end(String localName, int line, int column, int offset);

	void character(String text, int line, int column, int offset);
}
