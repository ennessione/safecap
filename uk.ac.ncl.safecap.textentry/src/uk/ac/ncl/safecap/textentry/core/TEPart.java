package uk.ac.ncl.safecap.textentry.core;

import java.util.ArrayList;
import java.util.List;

public class TEPart extends TERecord {
	private final String type;
	private final String name;
	private final List<TEPart> children;
	private TEPart parent;
	private TEFlags meta;

	public TEPart(String type, String name) {
		super();
		this.type = type;
		this.name = name;
		children = new ArrayList<>();
	}

	public void setMeta(TEFlags meta) {
		this.meta = meta;
	}

	public TEFlags getMeta() {
		return meta;
	}

	public void addChild(TEPart part) {
		children.add(part);
		part.setParent(this);
	}

	private void setParent(TEPart tePart) {
		parent = tePart;
	}

	public TEPart getParent() {
		return parent;
	}

	public TEPart getRoot() {
		TEPart root = this;
		while (root.parent != null) {
			root = root.parent;
		}
		return root;
	}

	public List<TEPart> getChildren() {
		return children;
	}

	public List<TEPart> getChildrenOfType(String type) {
		final List<TEPart> result = new ArrayList<>();
		for (final TEPart p : children) {
			if (type.equals(p.type)) {
				result.add(p);
			}
		}
		return result;
	}

	public List<String> getChildrenOfTypeNames(String type) {
		final List<String> result = new ArrayList<>();
		for (final TEPart p : getChildrenOfType(type)) {
			result.add(p.name);
		}
		return result;
	}

	public TEPart getChildOfTypeAndName(String type, String name) {
		for (final TEPart p : children) {
			if (type.equals(p.type) && name.equals(p.name)) {
				return p;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String pp(int depth) {
		final StringBuilder sb = new StringBuilder();

		ppSpaces(sb, depth);
		sb.append(quote(type));
		sb.append(' ');
		sb.append(quote(name));
		sb.append(" {\n");

		for (final Object key : getRecordKeys()) {
			for (final Object value : getValues(key)) {
				ppSpaces(sb, depth + 1);
				sb.append(quote(key.toString()));
				sb.append(": ");
				sb.append(quote(value.toString()));

				if (!getFlags(key.toString()).isEmpty()) {
					sb.append(" ` {");
					for (final String f : getFlags(key.toString())) {
						sb.append(f);
						sb.append(": ");
						sb.append(getFlag(key, f));
					}
					sb.append("}");
				}

				sb.append("\n");
			}
		}

		for (final TEPart part : children) {
			sb.append(part.pp(depth + 1));
			sb.append("\n");
		}

		ppSpaces(sb, depth);
		sb.append("}");

		if (!getPropertyKeys().isEmpty()) {
			sb.append(" :: {\n");

			for (final String key : getPropertyKeys()) {
				ppSpaces(sb, depth + 1);
				sb.append(quote(key.toString()));
				sb.append(": ");
				sb.append(quote(getProperty(key).toString()));
				sb.append("\n");
			}

			ppSpaces(sb, depth);
			sb.append("}");
		}

		return sb.toString();
	}

	private void ppSpaces(StringBuilder sb, int depth) {
		for (int i = 0; i < depth; i++) {
			sb.append("    ");
		}
	}

	@Override
	public String toString() {
		return name;
	}

	public String pp() {
		return pp(0);
	}

	public static String quote(String rm) {
		if (needQuotes(rm)) {
			return "\"" + rm.replace("\"", "'") + "\"";
		} else {
			return rm;
		}
	}

	public static String unquote(String rm) {
		if (rm == null) {
			return null;
		}
		if (rm.charAt(0) == '"' && rm.charAt(rm.length() - 1) == '"') {
			return rm.substring(1, rm.length() - 1);
		} else {
			return rm;
		}
	}

	public static boolean needQuotes(String rm) {
		if (rm == null || rm.length() == 0 || rm.charAt(0) == '"') {
			return false;
		}

		char c = rm.charAt(0);
		if (!Character.isLetter(c) && c != '?') {
			return true;
		}

		for (int i = 1; i < rm.length(); i++) {
			c = rm.charAt(i);
			if (!Character.isLetterOrDigit(c)) {
				return true;
			}
		}
		return false;
	}
}
