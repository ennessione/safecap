package uk.ac.ncl.safecap.textentry.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class TEBase {
	private Map<String, Object> data;

	public TEBase() {
		data = new HashMap<>();
	}

	public void setProperty(Map<String, Object> data) {
		this.data = data;
	}

	public Collection<String> getPropertyKeys() {
		return data.keySet();
	}

	public boolean hasPropertyKey(String key) {
		return data != null && data.containsKey(key);
	}

	public Object getProperty(String key) {
		if (data != null) {
			return data.get(key);
		} else {
			return null;
		}
	}

	public String getProperty(String key, String value) {
		if (data != null && data.containsKey(key)) {
			return data.get(key).toString();
		} else {
			return value;
		}
	}

	public Boolean getProperty(String key, boolean value) {
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

	public int getProperty(String key, int value) {
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

	public double getProperty(String key, double value) {
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

	public TEBase setProperty(String key, Object value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}

	public TEBase setProperty(String key, int value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}

	public TEBase setProperty(String key, double value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}
}
