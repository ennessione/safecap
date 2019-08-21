package uk.ac.ncl.safecap.mcommon.datareport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DRReport {
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	private final String title;
	private final String description;
	private Map<String, Object> data;

	public DRReport(String title, String description) {
		this.title = title;
		this.description = description;
		data = new LinkedHashMap<>();
	}

	public Collection<String> getDataKeys() {
		return data.keySet();
	}

	public void insertDate() {
		set("date", df.format(new Date()));
	}

	public Object get(String key) {
		if (data != null) {
			return data.get(key);
		} else {
			return null;
		}
	}

	public void set(String key, Object value) {
		if (data == null) {
			data = new HashMap<>();
		}

		data.put(key, value);
	}

	public String toCSV() {
		final StringBuilder sb = new StringBuilder();
		for (final String key : data.keySet()) {
			sb.append("\"" + key + "\"");
			sb.append(",");
			sb.append("\"" + wrapNull(key) + "\"");
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	public String toJSON() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		boolean first = true;
		for (final String key : data.keySet()) {
			if (!first) {
				sb.append(", \n");
			}
			sb.append("\t\"" + key + "\"");
			sb.append(": ");
			sb.append("\"" + wrapNull(key) + "\"");
			first = false;
		}
		sb.append("\n}\n");
		return sb.toString();
	}

	public String toXML() {
		final StringBuilder sb = new StringBuilder();
		sb.append("<safecap-report>\n");
		for (final String key : data.keySet()) {
			sb.append("\t<entry>");
			sb.append("<id>");
			sb.append(escapeXML(key));
			sb.append("</id>");
			sb.append("<value>");
			sb.append(escapeXML(wrapNull(key)));
			sb.append("</value>");
			sb.append("</entry>\n");
		}
		sb.append("</safecap-report>");
		return sb.toString();
	}

	private static String escapeXML(String s) {
		final StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
				out.append("&#");
				out.append((int) c);
				out.append(';');
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(title.toUpperCase());
		sb.append("\n");
		for (int i = 0; i < title.length(); i++) {
			sb.append('-');
		}
		sb.append("\n\n");
		sb.append(description);
		sb.append("\n\n");
		for (final String key : data.keySet()) {
			sb.append(key);
			sb.append(": ");
			sb.append(wrapNull(key));
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	private String wrapNull(String key) {
		return data.get(key) == null ? "?" : data.get(key).toString();
	}
}
