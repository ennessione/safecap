package uk.ac.ncl.safecap.xmldata.parsing;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import uk.ac.ncl.safecap.xmldata.DataContext;

public abstract class ParserHandlerBase implements IParserHandler {
	protected DataContext dataContext;

	protected ParserHandlerBase(DataContext context) {
		dataContext = context;
	}

	public void parse(String file) throws IOException, XMLStreamException {
		final StreamParserBase parser = new StreamParserBase(this);
		dataContext.setSourceFile(file);
		parser.parse(file);

	}

	protected String removeSpaces(String source) {
		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < source.length(); i++) {
			final char c = source.charAt(i);
			if (!Character.isWhitespace(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

}
