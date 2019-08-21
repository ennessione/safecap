package uk.ac.ncl.safecap.textentry.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TEMeta {
	protected Map<String, Object> data;

	public TEMeta() {
		data = new HashMap<>();
	}

	public Collection<String> getDataKeys() {
		return data.keySet();
	}

	public boolean hasKey(String key) {
		return data != null && data.containsKey(key);
	}

	public Object get(String key) {
		if (data != null) {
			return data.get(key);
		} else {
			return null;
		}
	}

	public String get(String key, String value) {
		if (data != null && data.containsKey(key)) {
			return data.get(key).toString();
		} else {
			return value;
		}
	}

	public Boolean get(String key, boolean value) {
		if (data != null) {
			final Object v = data.get(key);
			if (v instanceof Boolean) {
				return (Boolean) v;
			} else {
				return value;
			}
		} else {
			return value;
		}
	}

	public int get(String key, int value) {
		if (data != null) {
			final Object v = data.get(key);
			if (v instanceof Integer) {
				return (Integer) v;
			} else {
				return value;
			}
		} else {
			return value;
		}
	}

	public double get(String key, double value) {
		if (data != null) {
			final Object v = data.get(key);
			if (v instanceof Double) {
				return (Double) v;
			} else {
				return value;
			}
		} else {
			return value;
		}
	}

	public TEMeta set(String key, Object value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}

	public TEMeta set(String key, int value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}

	public TEMeta set(String key, double value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}
}
