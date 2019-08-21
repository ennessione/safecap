package uk.ac.ncl.safecap.xmldata.parsing;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;

public class RawDataParser {
	private final DataContext context;

	public RawDataParser(DataContext context) {
		this.context = context;
	}

	public void parse(String file) {
		try {
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			final DataNamespace r = getNamespace(doc.getDocumentElement().getNodeName(), doc.getDocumentElement().getNodeName());

			final NodeList nList = doc.getElementsByTagName("Item");
			for (int i = 0; i < nList.getLength(); i++) {
				final Node node = nList.item(i);
				final Element element = (Element) node;
				final String id = element.getElementsByTagName("Identity").item(0).getTextContent();
				final Element valueList = (Element) element.getElementsByTagName("ValueList").item(0);
				final NodeList values = valueList.getElementsByTagName("Value");
//				Block block = new Block("raw");
//				block.put("Identity", id);
				for (int j = 0; j < values.getLength(); j++) {
					final Element vnode = (Element) values.item(j);
					final String first = vnode.getAttribute("ObjIdentity");
					final String second = vnode.getTextContent();
					final Block block = r.getBlockByNameOrNew("raw", first);
					block.put(id, second);
				}

			}

		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private DataNamespace getNamespace(String canonical, String namespace) {
		DataNamespace r = context.getNamespaceByCanonicalId(namespace);
		if (r == null) {
			r = new DataNamespace(canonical, namespace);
			context.addNamespace(r);
		}

		return r;
	}

}
