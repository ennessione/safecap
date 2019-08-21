package uk.ac.ncl.safecap.xmldata.parsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StreamParserBase {
	private final IParserHandler handler;

	public StreamParserBase(IParserHandler handler) {
		this.handler = handler;
	}

	public void parse(String file) throws XMLStreamException, FileNotFoundException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_COALESCING, true);
		factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

		BufferedReader fr;
		try {
			fr = new BufferedReader(new FileReader(file));
			boolean marked = false;
			while (fr.read() != '<') {
				fr.mark(256);
				marked = true;
			}

			if (marked) {
				fr.reset();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new XMLStreamException(e.getMessage());
		}

		XMLStreamReader reader = factory.createXMLStreamReader(fr);
		reader = factory.createFilteredReader(reader, new StreamFilter() {
			@Override
			public boolean accept(XMLStreamReader r) {
				return !r.isWhiteSpace();
			}
		});

		while (reader.hasNext()) {
			final int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				handler.start(reader.getLocalName(), reader.getLocation().getLineNumber(), reader.getLocation().getColumnNumber(),
						reader.getLocation().getCharacterOffset());

				for (int i = 0; i < reader.getAttributeCount(); i++) {
					handler.addAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
				}

				break;

			case XMLStreamConstants.CHARACTERS:
				handler.character(reader.getText(), reader.getLocation().getLineNumber(), reader.getLocation().getColumnNumber(),
						reader.getLocation().getCharacterOffset());

				break;
			case XMLStreamConstants.SPACE:
				// ignore
				break;

			case XMLStreamConstants.END_ELEMENT:
				handler.end(reader.getLocalName(), reader.getLocation().getLineNumber(), reader.getLocation().getColumnNumber(),
						reader.getLocation().getCharacterOffset());
				break;
			}

		}

	}
}