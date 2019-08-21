package uk.ac.ncl.safecap.xmldata.parsing;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;

public class TwoLevelParser extends ParserBase {
	private enum STATE {
		NAMESPACE, BLOCK, DATA, VALUE;

		STATE next() {
			switch (this) {
			case NAMESPACE:
				return BLOCK;
			case BLOCK:
				return DATA;
			case DATA:
				return VALUE;
			case VALUE:
				assert false;
			}

			assert false;
			return this;
		}

		STATE prev() {
			switch (this) {
			case NAMESPACE:
				assert false;
			case BLOCK:
				return NAMESPACE;
			case DATA:
				return BLOCK;
			case VALUE:
				return DATA;
			}

			assert false;
			return this;
		}
	}

	private DataNamespace namespace;
	private STATE state = STATE.NAMESPACE;
	private Block block = null;

	public TwoLevelParser(DataContext dataContext) throws ParserConfigurationException, SAXException {
		super(dataContext);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (state) {
		case NAMESPACE:
			namespace = new DataNamespace(localName, localName);
			dataContext.addNamespace(namespace);
			break;
		case BLOCK:
			assert namespace != null;
			block = new Block(localName);
			break;
		case DATA:
			block.enterAttribute(localName);
			break;
		case VALUE:
			throw new SAXException("*** error: enter value");
		}

		state = state.next();
		// System.out.println("Enter " + localName + " to state " +
		// state.name());
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		state = state.prev();

		if (state == STATE.BLOCK) {
			// System.out.println("\n***\n");
			// System.out.println(block.toString());

			// save block type
			if (block.getId() != null) {
				final String id = block.getId().toString();
				if (id != null) {
					// fishy!
					dataContext.getTypeRegistry().setType(id, block.getKind());
				}
			}

			namespace.addBlock(block);

			block = null;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		final String value = removeSpaces(new String(ch, start, length));
		if (value.length() > 0) {
			if (state == STATE.VALUE) {
				block.enterValue(value);
			} else {
				throw new SAXException("*** error: extra value in state " + state);
			}
		}
	}
}
