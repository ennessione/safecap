package uk.ac.ncl.safecap.mcommon.og;

public class OGEdge extends OGExpandable {
	private final OGNode from;
	private final OGNode to;

	public OGEdge(String label, OGNode from, OGNode to) {
		super(label);
		this.from = from;
		this.to = to;
	}

	public OGNode getFrom() {
		return from;
	}

	public OGNode getTo() {
		return to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (from == null ? 0 : from.hashCode());
		result = prime * result + (to == null ? 0 : to.hashCode());
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
		final OGEdge other = (OGEdge) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"label\": " + getLabel() + ",");
		sb.append(" \"from\": {\"node\": " + from.getLabel());
		if (hasAttribute("port.from")) {
			sb.append(", \"port\": \"" + getAttribute("port.from", "_") + "\"");
		}
		sb.append("}, ");
		sb.append(" \"to\": {\"node\": " + to.getLabel());
		if (hasAttribute("port.to")) {
			sb.append(", \"port\": \"" + getAttribute("port.to", "_") + "\"");
		}
		sb.append("}");
		if (hasAttribute("length")) {
			sb.append(", \"length\": \"" + getAttribute("length", "_") + "\"");
			try {
				final int value = Integer.valueOf(getAttribute("length").toString());
				sb.append(", \"delay\": \"" + value / 40.0 + "\"");
			} catch (final Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (hasAttribute("gradient")) {
			sb.append(", \"gradient\": \"" + getAttribute("gradient", "_") + "\"");
		}
		if (hasAttribute("speedlimit")) {
			sb.append(", \"speedlimit\": \"" + getAttribute("speedlimit", "_") + "\"");
		}
		sb.append("}");

		return sb.toString();
	}

}
