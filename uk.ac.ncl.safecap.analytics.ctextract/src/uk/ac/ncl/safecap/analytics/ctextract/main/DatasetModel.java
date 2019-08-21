package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DatasetModel {
	public Map<String, List<DatasetBlock>> blocks;

	public DatasetModel() {
		blocks = new HashMap<>();
	}

	public void addEntry(String concept, String id, String key, String value) {
		final DatasetBlock block = getBlock(concept, id);
		block.addEntry(key, value);
	}

	public DatasetBlock getBlock(String concept, String id) {
		List<DatasetBlock> l = blocks.get(concept);
		if (l == null) {
			l = new ArrayList<>();
			blocks.put(concept, l);
		}

		DatasetBlock r = null;
		for (final DatasetBlock x : l) {
			if (x.identity.equals(id)) {
				r = x;
				break;
			}
		}

		if (r == null) {
			r = new DatasetBlock(id);
			l.add(r);
		}

		return r;
	}

	public void toXML(Document document, Element parent) {
		for (final String key : blocks.keySet()) {
			final Element bpart = document.createElement(key + "List");
			parent.appendChild(bpart);
			for (final DatasetBlock block : blocks.get(key)) {
				block.toXML(document, bpart, key);
			}
		}
	}
}

class DatasetBlock {
	public String identity;
	public Map<String, List<String>> values;

	public DatasetBlock(String identity) {
		this.identity = identity;
		values = new HashMap<>();
	}

	public void addEntry(String key, String value) {
		List<String> list = values.get(key);
		if (list == null) {
			list = new ArrayList<>();
			values.put(key, list);
		}

		list.add(value);
	}

	public void toXML(Document document, Element parent, String concept) {
		final Element overall = document.createElement(concept);
		parent.appendChild(overall);

		final Element idpart = document.createElement("Identity");
		idpart.appendChild(document.createTextNode(identity));
		overall.appendChild(idpart);

		for (final String key : values.keySet()) {
			for (final String value : values.get(key)) {
				final Element vpart = document.createElement(key);
				vpart.appendChild(document.createTextNode(value));
				overall.appendChild(vpart);
			}
		}
	}

}
