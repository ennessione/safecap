package uk.ac.ncl.safecap.common.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class SRPart {
	public static final String ID = "id";
	public static final String INDEX = "index";
	public static final String ONINPUT = "oninput";
	public static final String ONFOCUS = "onfocus";
	public static final String ONBLUR = "onblur";
	public static final String TITLE = "title";
	public static final String BORDER = "border";
	public static final String BACKGROUND = "background";
	public static final String COLOR = "color";
	public static final String PADDING = "padding";
	public static final String NOWRAP = "nowrap";
	public static final String ALIGN = "align";
	public static final String WIDTH = "width";
	public static final String NOESCAPE = "noescape";
	public static final String HEIGHT = "height";
	public static final String MINHEIGHT = "minheight";
	public static final String MAXHEIGHT = "maxheight";	
	public static final String MARGIN = "margin";
	public static final String RAWSTYLE = "rawstyle";	
	public static final String EDITABLE = "editable";	
	public static final String TOOLTIP = "tooltip";	
	public static final String SPELLCHECK = "spellcheck";	
	public static final String LINK = "link";		
	
	private String tag;
	private Map<String, Object> data;

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

	public SRPart set(String key, Object value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}
	
	/**
	 * sets a value if not set already
	 * @param key key
	 * @param value value
	 * @return
	 */
	public SRPart set2(String key, Object value) {
		if (!hasKey(key))
			set(key, value);
		return this;
	}	

	public SRPart set(String key, int value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}

	public SRPart set(String key, double value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
		return this;
	}

	public String getTag() {
		return tag;
	}

	public SRPart setTag(String tag) {
		this.tag = tag;
		return this;
	}

}
