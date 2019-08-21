package uk.ac.ncl.safecap.textentry.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TEFlags extends TEBase {
	private final Map<Object, Map<String, Object>> flags;

	public TEFlags() {
		flags = new HashMap<>();
	}

	private void _setFlag(Object key, String flag, Object value) {
		Map<String, Object> fmap = flags.get(key);
		if (fmap == null) {
			fmap = new HashMap<>();
			flags.put(key, fmap);
		}

		fmap.put(flag, value);
	}

	public void setFlagRaw(Object key, Map<String, Object> value) {
		flags.put(key, value);
	}

	@SuppressWarnings("unchecked")
	private <T> T _testFlag(Object key, String flag, T defaultValue) {
		final Map<String, Object> fmap = flags.get(key);
		if (fmap == null) {
			return defaultValue;
		}

		final Object r = fmap.getOrDefault(flag, defaultValue);
		return (T) r;
	}

	public Object getFlag(Object key, String flag) {
		final Map<String, Object> fmap = flags.get(key);
		if (fmap == null) {
			return null;
		}

		return fmap.get(flag);
	}

	public void setFlag(Object key, String flag) {
		_setFlag(key, flag, true);
	}

	public void setFlag(Object key, String flag, int value) {
		_setFlag(key, flag, value);
	}

	public void setFlag(Object key, String flag, double value) {
		_setFlag(key, flag, value);
	}

	public void setFlag(Object key, String flag, boolean value) {
		_setFlag(key, flag, value);
	}

	public void setFlag(Object key, String flag, String value) {
		_setFlag(key, flag, value);
	}

	public Collection<String> getFlags(Object key) {
		if (flags.get(key) != null) {
			return flags.get(key).keySet();
		}

		return Collections.emptyList();
	}

	public boolean isFlag(Object key, String flag) {
		return _testFlag(key, flag, Boolean.FALSE);
	}

	public String getFlag(Object key, String flag, String def) {
		return _testFlag(key, flag, def);
	}

	public Integer getFlag(Object key, String flag, Integer def) {
		return _testFlag(key, flag, def);
	}

	public Double getFlag(Object key, String flag, Double def) {
		return _testFlag(key, flag, def);
	}

}
