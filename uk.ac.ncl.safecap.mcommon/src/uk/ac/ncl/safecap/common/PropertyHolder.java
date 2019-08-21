package uk.ac.ncl.safecap.common;

import java.util.HashMap;
import java.util.Map;

public abstract class PropertyHolder {
	private Map<String, Object> properties;

	public void setProperty(String name, Object value) {
		if (properties == null) {
			properties = new HashMap<>();
		}
		properties.put(name, value);
	}

	public Object getProperty(String name) {
		if (properties == null) {
			return null;
		}
		return properties.get(name);
	}

	public Object getProperty(String name, Object def) {
		if (properties == null) {
			return def;
		}
		if (properties.containsKey(name)) {
			return properties.get(name);
		} else {
			return def;
		}
	}

	public boolean hasProperty(String name) {
		return getProperty(name) != null;
	}	
	
	public boolean isProperty(String name) {
		return Boolean.TRUE.equals(getProperty(name, Boolean.FALSE));
	}	
}
