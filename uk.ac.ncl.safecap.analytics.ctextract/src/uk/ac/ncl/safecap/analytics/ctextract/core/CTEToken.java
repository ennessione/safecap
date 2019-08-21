package uk.ac.ncl.safecap.analytics.ctextract.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

public class CTEToken extends CTEPartBase {
	public static final CTEToken Start = new CTEToken(KIND.START);
	public static final CTEToken End = new CTEToken(KIND.END);

	private KIND kind = KIND.ELEMENT;
	private String token;
	private String bin;
	private int index;

	private transient Map<String, Object> flags;

	public CTEToken() {
	}

	public CTEToken(String token) {
		this.token = token;
	}

	private CTEToken(KIND kind) {
		this.kind = kind;
	}

	@SuppressWarnings("unchecked")
	public <T> T flagValue(String key, T def) {
		if (flags != null && flags.containsKey(key) && def.getClass().isAssignableFrom(flags.get(key).getClass())) {
			return (T) flags.get(key);
		}
		return def;
	}

	public int flagInt(String key, int def) {
		return flagValue(key, def);
	}

	public String flagString(String key, String def) {
		return flagValue(key, def);
	}

	public Boolean flagBoolean(String key, Boolean def) {
		return flagValue(key, def);
	}

	public void flagSet(String key, Object value) {
		if (flags == null) {
			flags = new HashMap<>();
		}

		flags.put(key, value);
	}

	public KIND getKind() {
		return kind;
	}

	public void setKind(KIND kind) {
		this.kind = kind;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@XmlTransient
	public int getCardinality() {
		return flagInt("card", 0);
	}

	public void setCardinality(int cardinality) {
		flagSet("card", cardinality);
	}

	@XmlTransient
	public int getCount() {
		return flagInt("count", 0);
	}

	public void setCount(int cardinality) {
		flagSet("count", cardinality);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		final String type = flagString("rwe", "none");

		final boolean hasToken = token != null && token.length() > 0;

		if (type.equals("none")) {
			if (token != null && getCardinality() == getCount() && kind != KIND.NOISE || kind == KIND.OPERATOR) {
				sb.append(kind.toString() + "[" + token + "]");
			} else {
				sb.append(kind.toString());
			}

			if (kind == KIND.ELEMENT) {
				sb.append(":" + index);
			}

		} else if (type.equals("left")) {
			if (index == 0 && kind == KIND.ELEMENT) {
				sb.append(token);
			} else if (kind == KIND.NOISE) {
				sb.append(KIND.NOISE.name());
			} else {
				sb.append(kind.toString());
				if (index > 0) {
					sb.append(":" + index);
				}
				if (hasToken) {
					sb.append("[" + token + "]");
				}
			}
		} else if (type.equals("right")) {
			// sb.append("R");
			if (kind == KIND.NOISE) {
				sb.append(kind.name());
			} else {
				sb.append(kind.toString());
				if (hasToken) {
					sb.append("[" + token + "]");
				}
			}
		} else if (type.equals("production")) {
			if (kind == KIND.ELEMENT) {
				sb.append("{" + (bin == null ? "?" : bin) + "}");
			} else {
				sb.append(kind.toString());
				if (hasToken) {
					sb.append("[" + token + "]");
				}
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (kind == null ? 0 : kind.hashCode());
		if (kind == KIND.OPERATOR) {
			result = prime * result + (token == null ? 0 : token.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CTEToken other = (CTEToken) obj;
		if (kind != other.kind) {
			return false;
		}
		if (kind == KIND.OPERATOR && !token.equals(other.token)) {
			return false;
		}
		return true;
	}

	@Override
	public CTEPartBase transform(ITokenTransfomer transform) {
		final CTEPartBase transformed = transform.transform(this);
		if (transformed != this) {
			return transformed;
		}
		return this;
	}

	@Override
	public void merge(CTEPartBase other) {
		if (other instanceof CTEToken) {
			final CTEToken tother = (CTEToken) other;
			if (tother.kind == kind) {
				setCount(getCount() + 1);
				if (tother.token != null && tother.token.equals(token)) {
					setCardinality(getCardinality() + 1);
				}
			}
		}
	}

	@Override
	public int index(int start) {
		if (kind == KIND.ELEMENT) {
			index = start;
			return 1;
		}
		return 0;
	}

	@Override
	public void visit(ITokenVisitor visitor, Object data) {
		visitor.visit(this, data);
	}

	@Override
	public void visit(IVisitor visitor, Object data) {
		visitor.visit(this);
	}

	@Override
	public Map<String, String> match(CTEPartBase data) {
		if (data instanceof CTEToken) {
			final CTEToken other = (CTEToken) data;
			switch (kind) {
			case END:
			case START:
			case NOISE: {
				if (other.kind != kind) {
					return null;
				}

				return Collections.emptyMap();
			}
			case OPERATOR: {
				if (other.kind != KIND.OPERATOR || !token.equals(other.token)) {
					return null;
				}

				return Collections.emptyMap();
			}
			case ELEMENT: {
				if (other.kind != KIND.ELEMENT || other.token == null) {
					return null;
				}

				if (bin == null) {
					return Collections.emptyMap();
				}

				return Collections.singletonMap(bin, other.token);
			}
			default:
				break;
			}
		}

		return null;
	}

	@Override
	public boolean visit(IParallelTokenVisitor visitor, CTEPartBase target, Object data) {
		if (target instanceof CTEToken) {
			final CTEToken token = (CTEToken) target;
			return visitor.visit(this, token, data);
		}

		return false;
	}

	@Override
	public CTEPartBase copy() {
		final CTEToken copy = new CTEToken(token);
		copy.kind = kind;
		copy.bin = bin;
		copy.index = index;
		copy.flags = new HashMap<>(flags);
		return copy;
	}

	@Override
	public CTEPartBase car() {
		return this;
	}

	@Override
	public CTEPartBase cdr() {
		return this;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
