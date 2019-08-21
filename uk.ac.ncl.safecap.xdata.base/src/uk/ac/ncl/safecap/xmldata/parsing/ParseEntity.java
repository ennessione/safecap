package uk.ac.ncl.safecap.xmldata.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.Pair;
import uk.ac.ncl.safecap.xmldata.ValueList;

public class ParseEntity {
	private static class ParseEntityData {
		public List<ParseEntity> children;
		public List<Pair> values;
		public Object rawData;

		public ParseEntityData() {
			children = new ArrayList<>();
			values = new ArrayList<>();
		}
	}

	private final String name;
	private final ParseEntity parent;
	private boolean isRaw;

	private List<ParseEntityData> indexedData;
	private ParseEntityData data = new ParseEntityData();

	// detected properties
	private String id;
	private String prefix;
	private String canonical_prefix;

	public ParseEntity(String name, ParseEntity parent) {
		this.name = name;
		this.parent = parent;
		assert name != null;
		if (parent != null && parent.isRaw || name.equals("Raw")) {
			isRaw = true;
		}
	}

	public boolean isRaw() {
		return isRaw;
	}

	// switch to next index an a sequence of blocks
	public void next() {

		if (indexedData == null) {
			indexedData = new ArrayList<>();
			indexedData.add(data);
		}
		data = new ParseEntityData();
		indexedData.add(data);

	}

	public void addChild(ParseEntity entity) {
		if (data.children.contains(entity)) {
			return;
		}

		if (entity.data.values.isEmpty() && entity.data.children.isEmpty()) {
			if (entity.data.rawData != null) {
				data.values.add(new Pair(entity.name, entity.data.rawData));
			}
		} else {
			data.children.add(entity);
		}
	}

	public void addValue(Object key, Object value) {
		data.values.add(new Pair(key, value));
	}

	public void addIndexedValue(Object key, Object value, int index) {
		addValue(key, new ValueList(new Integer(index), value));
	}

	public void setRawData(Object rawData) {
		if (data.rawData == null) {
			data.rawData = rawData;
		} else if (data.rawData instanceof LocatedValue) {
			final LocatedValue first = (LocatedValue) data.rawData;
			if (rawData instanceof LocatedValue) {
				final LocatedValue second = (LocatedValue) rawData;
				first.setValue(first.getValue().toString() + second.getValue().toString());
				first.setLength(first.getLength() + second.getLength());
			} else {
				first.setValue(first.getValue().toString() + rawData.toString());
				first.setLength(first.getLength() + rawData.toString().length());
			}
		} else {
			data.rawData = data.rawData.toString() + rawData.toString();
		}
	}

	public Object getRawData() {
		return data.rawData;
	}

	public boolean isBlock() {
		for (final ParseEntity pe : getChildren()) {
			if (isKnownBlock(pe)) {
				return false;
			}
		}

		final Pair bid = getAnyUniqueValue(Block.C_ID);
		if (bid != null) {
			// idkey = bid.getKey();
			id = bid.getR().toString();
			return true;
		}
		return false;
	}

	private boolean isKnownBlock(ParseEntity entity) {
		for (final ParseEntity pe : entity.getChildren()) {
			if (isKnownBlock(pe)) {
				return true;
			}
		}

		return entity.id != null;
	}

	public void normalise(Set<String> functions) {
		// TODO: convert children into values
		// 1) simple keys/values into prefixed keys/values (recursive)
		if (indexedData == null) {
			normaliseValues(functions);
		} else {
			next();
			for (int i = 0; i < indexedData.size() - 1; i++) {
				normaliseValues(functions, indexedData.get(i), i);
			}
		}
	}

	private void normaliseValues(Set<String> functions) {
		for (final ParseEntity pe : data.children) {
			pe.normalise(functions);
			for (final Pair pp : pe.data.values) {
				addValue(pe.name + "." + pp.getKey(), pp.getR());
			}

			if (data.rawData != null) {
				addValue(pe.name, data.rawData);
			}
		}

		if (data.children.isEmpty() && data.rawData == null) {
			functions.add(getCanonicalPrefix() + "." + getName());
		}

	}

	private void normaliseValues(Set<String> functions, ParseEntityData d, int index) {
		for (final Pair pp : d.values) {
			addIndexedValue(pp.getKey(), pp.getR(), index);
		}

		for (final ParseEntity pe : d.children) {
			pe.normalise(functions);
			for (final Pair pp : pe.data.values) {
				addIndexedValue(pe.name + "." + pp.getKey(), pp.getR(), index);
			}

			if (d.rawData != null) {
				addIndexedValue(pe.name, d.rawData, index);
			}
		}

		if (d.children.isEmpty() && d.rawData == null) {
			functions.add(getCanonicalPrefix() + "." + getName());
		}

	}

	private int getCount(Object key) {
		int c = 0;
		for (final Pair p : data.values) {
			if (p.getKey().equals(key)) {
				c++;
			}
		}

		return c;
	}

	private Pair getAnyUniqueValue(Collection<String> keyset) {
		for (final Pair p : data.values) {
			if (keyset.contains(p.getKey()) && getCount(p.getKey()) == 1) {
				return p;
			}
		}

		return null;
	}

	public String getCanonicalPrefix() {
		if (canonical_prefix == null) {
			canonical_prefix = _getCanonicalPrefix();
		}

		return canonical_prefix;
	}

	private String _getCanonicalPrefix() {
		final StringBuilder prefix = new StringBuilder();

		ParseEntity _p = getParent();
		while (_p != null && _p.getParent() != null) {
			assert _p.name != null;
			if (prefix.length() > 0) {
				prefix.insert(0, '.');
			}
			prefix.insert(0, _p.name);
			_p = _p.getParent();
		}

		if (prefix.length() == 0) {
			return "default";
		}

		return prefix.toString();
	}

	public String getCompressedPrefix() {
		if (prefix == null) {
			prefix = _getCompressedPrefix();
		}

		return prefix;
	}

	private String _getCompressedPrefix() {
		final StringBuilder prefix = new StringBuilder();

		ParseEntity _p = closureParent(this);
		while (_p != null && _p.getParent() != null) {
			if (prefix.length() > 0) {
				prefix.insert(0, '.');
			}
			prefix.insert(0, _p.name);
			_p = closureParent(_p);
		}

		if (prefix.length() == 0) {
			return "default";
		}

		return prefix.toString();
	}

	private ParseEntity closureParent(ParseEntity c) {
		while (c.getParent() != null && (c.getParent().getChildren().size() == 1
				|| c.getParent().getParent() != null && c.getParent().getParent().getChildren().size() == 1)) {
			c = c.getParent();
		}

		return c.getParent();
	}

	public String getName() {
		return name;
	}

	public ParseEntity getParent() {
		return parent;
	}

	public List<ParseEntity> getChildren() {
		return data.children;
	}

	public List<Pair> getValues() {
		return data.values;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "<" + name + "[" + data.children.size() + "]" + ">";
	}

}
