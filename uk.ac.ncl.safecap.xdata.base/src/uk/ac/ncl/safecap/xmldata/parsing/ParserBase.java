package uk.ac.ncl.safecap.xmldata.parsing;

//import java.io.File;
import java.io.IOException;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;

//import uk.ac.ncl.safecap.xmldata.DataNamespace;

public class ParserBase extends DefaultHandler {
	protected SAXParser saxParser;
	protected DataContext dataContext;
	protected Locator locator;

	public ParserBase(DataContext dataContext) throws ParserConfigurationException, SAXException {
		final SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		saxParser = spf.newSAXParser();
		this.dataContext = dataContext;
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		super.setDocumentLocator(locator);
		this.locator = locator;
	}

	public void parse(String file) throws SAXException, IOException {
		dataContext.setSourceFile(file);
		saxParser.parse(file, this);
	}

	// public static void parseTest() {
	// try {
	// String file = Utils.openXmlFileDialog();
	// if (file != null) {
	// DataContext dataContext = new DataContext();
	// ParserBase parser = new ParserBase(dataContext);
	// parser.parse(file);
	//
	// System.out.println("\nType registry:");
	// System.out.println(dataContext.getTypeRegistry().toString());
	//
	// System.out.println("\nBlocks:");
	// for(DataNamespace nmsp: dataContext.getNamespaces()) {
	// System.out.println("\nNamespace " + nmsp.getId());
	// for(Block block: nmsp.getBlocks()) {
	// System.out.println(block.toString());
	// }
	// }
	//
	// serTest(dataContext);
	// }
	// } catch (SAXException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (ParserConfigurationException e) {
	// e.printStackTrace();
	// } catch (JAXBException e) {
	// e.printStackTrace();
	// }
	// }

	// private static void serTest(DataContext dataContext) throws
	// JAXBException, IOException {
	// JAXBContext jaxbContext = JAXBContext.newInstance(DataContext.class);
	// Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	//
	// File file = File.createTempFile("dataContextTest", ".xml");
	//
	// // output pretty printed
	// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	//
	// jaxbMarshaller.marshal(dataContext, System.out);
	// jaxbMarshaller.marshal(dataContext, file);
	//
	// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	// DataContext test = (DataContext) jaxbUnmarshaller.unmarshal(file);
	//
	// System.out.println("\nUnmarshalled version:");
	// for(DataNamespace nmsp: test.getNamespaces()) {
	// System.out.println("\nNamespace " + nmsp.getId());
	// for(Block block: nmsp.getBlocks()) {
	// System.out.println(block.toString());
	// }
	// }
	// }

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
