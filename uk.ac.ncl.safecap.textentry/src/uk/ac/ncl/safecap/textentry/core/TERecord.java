package uk.ac.ncl.safecap.textentry.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TERecord extends TEFlags {
	private final Map<Object, List<Object>> values;

	public TERecord() {
		values = new HashMap<>();
	}

	public void addEntry(Object key, Object value) {
		List<Object> list = values.get(key);
		if (list == null) {
			list = new ArrayList<>();
			values.put(key, list);
		}
		list.add(value);
	}

	public void addEntry(Object value) {
		final int index = values.size();
		addEntry(new Integer(index), value);
	}

	public Collection<Object> getRecordKeys() {
		return values.keySet();
	}

	public List<Object> getValues(Object key) {
		if (values.containsKey(key)) {
			return values.get(key);
		}

		return Collections.emptyList();
	}

	public boolean getBoolean(String key, boolean def) {
		if (values.containsKey(key) && values.get(key).size() >= 1 && values.get(key).get(values.get(key).size() - 1) instanceof Boolean) {
			return (Boolean) values.get(key).get(values.get(key).size() - 1);
		}

		return def;
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public String getString(String key, String def) {
		if (values.containsKey(key) && values.get(key).size() >= 1 && values.get(key).get(values.get(key).size() - 1) instanceof String) {
			return TEPart.unquote((String) values.get(key).get(values.get(key).size() - 1));
		}

		return def;
	}

	public String getString(String key) {
		return TEPart.unquote(getString(key, null));
	}

	public int getInt(String key, int def) {
		if (values.containsKey(key) && values.get(key).size() >= 1 && values.get(key).get(values.get(key).size() - 1) instanceof Integer) {
			return (Integer) values.get(key).get(values.get(key).size() - 1);
		}

		return def;
	}

	public boolean isIndexBased() {
		if (!values.isEmpty()) {
			return values.keySet().iterator().next() instanceof Integer;
		}

		return false;
	}

}
