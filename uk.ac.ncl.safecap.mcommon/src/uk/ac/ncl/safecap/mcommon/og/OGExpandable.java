package uk.ac.ncl.safecap.mcommon.og;

import java.util.HashMap;
import java.util.Map;

import safecap.schema.SubSchemaNode;
import safecap.schema.SubSchemaPath;

public class OGExpandable extends OGLabeled {
	private Map<String, Object> attributes;
	private OrderGraph subgraph;

	public OGExpandable(String label) {
		super(label);
	}

	public void setAttribute(String key, Object value) {
		if (attributes == null) {
			attributes = new HashMap<>();
		}
		attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		if (attributes == null) {
			return null;
		}
		return attributes.get(key);
	}

	public boolean hasAttribute(String key) {
		if (attributes == null) {
			return false;
		}
		return attributes.containsKey(key);
	}

	public Object getAttribute(String key, Object defvalue) {
		if (attributes == null || !attributes.containsKey(key) || attributes.get(key).toString().length() == 0) {
			return defvalue;
		} else {
			return attributes.get(key);
		}
	}

	public OrderGraph getSubgraph() {
		return subgraph;
	}

	public void setSubgraph(OrderGraph subgraph) {
		this.subgraph = subgraph;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (subgraph == null ? 0 : subgraph.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OGExpandable other = (OGExpandable) obj;
		if (subgraph == null) {
			if (other.subgraph != null) {
				return false;
			}
		} else if (!subgraph.equals(other.subgraph)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"label\": " + getLabel());
		if (subgraph != null) {
			sb.append(", subgraph: " + subgraph.toString());
		}

		annotateExtra(sb);

		sb.append("}");

		return sb.toString();
	}

	private void annotateExtra(StringBuilder sb) {
		if (getAttribute("ssn") instanceof SubSchemaNode) {
			final SubSchemaNode ssn = (SubSchemaNode) getAttribute("ssn");
			int i = 0;
			sb.append(", \"children\": [");
			for (final String c : ssn.getChildren()) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append("\"" + c + "\"");
				i++;
			}
			sb.append("], ");
			sb.append("\"paths\": [");
			i = 0;
			for (final SubSchemaPath p : ssn.getPaths()) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append("{");
				sb.append("\"in\": \"" + p.getFrom() + "\",");
				sb.append(" \"out\": \"" + p.getTo() + "\",");
				sb.append(" \"length\": \"" + p.getLength() + "\",");
				sb.append("}");
				i++;
			}
			sb.append("]");
		}

	}

}
