package uk.ac.ncl.safecap.xmldata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class Block implements IDescribed, Cloneable {
	public static final String C_TRUE = "true";
	public static final String C_FALSE = "false";
	public static final List<String> C_ID = Arrays.asList("Id", "Identity");

	private String kind;
	private String description;
	private String blockidkey;
	private Object blockid;
	private List<Pair> values;
	private transient Object attr;
	private transient int valueCount;

	private Block() {
	}

	public void setBlockid(Object blockid) {
		this.blockid = blockid;
	}

	public Block(String kind) {
		this.kind = kind;
		values = new ArrayList<>();
		valueCount = 0;
	}

	@Override
	public Object clone() {
		final Block c = new Block();
		c.kind = kind;
		c.values = new ArrayList<>();

		for (final Pair p : values) {
			c.values.add((Pair) p.clone());
		}

		return c;
	}

	@Override
	@XmlElement
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public void buildFunctions(String canonical_namespaceId, String namespaceId, IFunctionBuilder builder) {
		if (values == null) {
			System.err.println("Block has no values id:\n" + toString());
			return;
		}
		final Object id = getId();
		if (id != null) {
			for (final Pair p : values) {
				if (!p.getKey().equals(blockidkey)) {
					builder.addMapping(canonical_namespaceId, namespaceId, p.getKey().toString(), id, p.getR());
				}
			}
		} else {
			System.err.println("Block has no id:\n" + toString());
		}
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setValues(List<Pair> values) {
		this.values = values;
	}

	@XmlElement
	public List<Pair> getValues() {
		return values;
	}

	@XmlElement
	public String getKind() {
		return kind;
	}

	public void put(Object key, Object value) {
		values.add(new Pair(key, value));
		assert key != null && value != null;
		// System.out.println("block put " + key + " = " + value + " ::: " +
		// value.getClass().getSimpleName());
	}

	public void enterAttribute(Object attr) {
		this.attr = attr;
		valueCount = 0;
	}

	public void enterValue(Object value) {
		// glue strings
		if (valueCount > 0) {
			final Pair last = values.get(values.size() - 1);
			final String newValue = last.getR().toString() + value.toString();
			last.setR(newValue);
			return;
		}

		if (value instanceof String) {
			final String svalue = (String) value;
			if (svalue.toLowerCase().equals(C_TRUE)) {
				put(attr, Boolean.TRUE);
			} else if (svalue.toLowerCase().equals(C_FALSE)) {
				put(attr, Boolean.FALSE);
			} else {
				try {
					final Integer ivalue = Integer.parseInt(svalue);
					put(attr, ivalue);
				} catch (final NumberFormatException e) {
					put(attr, value);
				}
			}
		} else {
			put(attr, value);
		}

		valueCount++;
	}

	// public void cookTypes(TypeRegistry typeRegistry) {
	// if (values == null)
	// return;
	// for(Pair<String, Object> p: values) {
	// Object v = p.getValue();
	// if (blockidkey == null || !p.getKey().equals(blockidkey)) {
	// XType type = XType.resolve(typeRegistry, v);
	// if (type != null && v instanceof String) {
	// assert(type instanceof XEnumType);
	// p.setR(new TypedId(v.toString(), ((XEnumType) type).getName()));
	// } else if (v instanceof ValueList) {
	// ValueList vlist = (ValueList) v;
	// vlist.cookTypes(typeRegistry);
	// }
	// }
	// }
	// }

	public boolean isUnique(Object key) {
		int c = 0;
		for (final Pair p : values) {
			if (p.getKey() != null && p.getKey().equals(key)) {
				if (c == 1) {
					return false;
				} else {
					c++;
				}
			}
		}

		return true;
	}

	public int getCount(Object key) {
		int c = 0;
		for (final Pair p : values) {
			if (p.getKey().equals(key)) {
				c++;
			}
		}

		return c;
	}

	@SuppressWarnings("rawtypes")
	public boolean isUniformType(String key) {
		Class z = null;
		for (final Pair p : values) {
			if (p.getKey().equals(key)) {
				if (z == null) {
					z = p.getR().getClass();
				} else {
					if (!z.isInstance(p.getR())) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public Object getAnyValue(Object key) {
		for (final Pair p : values) {
			if (p.getKey().equals(key)) {
				return p.getR();
			}
		}

		return null;
	}

	public Pair getAnyUniqueValue(Collection<String> keyset) {
		for (final Pair p : values) {
			if (keyset.contains(p.getKey()) && getCount(p.getKey()) == 1) {
				return p;
			}
		}

		return null;
	}

	public Set<Object> getValueSet(Object key) {
		final Set<Object> result = new HashSet<>();
		for (final Pair p : values) {
			if (p.getKey().equals(key)) {
				result.add(p.getR());
			}
		}

		return result;
	}

	public Set<Object> getKeySet() {
		final Set<Object> result = new HashSet<>();
		for (final Pair p : values) {
			result.add(p.getKey());
		}

		return result;
	}

	private void computeId() {
		Pair _blockid = getAnyUniqueValue(C_ID);
		if (_blockid == null) {
			_blockid = inferBlockId();
		}
		if (_blockid != null && _blockid.getR() != null) {
			blockidkey = _blockid.getKey().toString();
			blockid = _blockid.getR();
		} else {
			blockid = null;
			blockidkey = null;
			return;
		}
	}

	private Pair inferBlockId() {
		for (final Pair v : values) {
			if (v.getR() instanceof String && isUnique(v.getKey())) {
				return v;
			}
		}

		return null;
	}

	public Object getId() {
		if (values == null) {
			return "?";
		}

		if (blockid != null) {
			return blockid;
		} else {
			computeId();
			return blockid;
		}
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Block ");
		sb.append(kind);

		boolean excludeId = false;
		if (blockid != null) {
			sb.append("/");
			sb.append(blockid);
			excludeId = true;
		}
		sb.append("\n");

		final Set<Object> keySet = getKeySet();
		for (final Object key : keySet) {
			if (!excludeId || blockidkey == null || !key.equals(blockidkey)) {
				sb.append("\t");
				sb.append(key);
				sb.append(": ");
				final Set<Object> values = getValueSet(key);

				// sb.append("[");
				// sb.append(isUniformType(key) ? getType(key) : "*error*");
				// sb.append("]");
				// sb.append(": ");

				if (values.size() > 1) {
					sb.append("{");
				}

				int c = 0;
				for (final Object o : values) {
					if (c > 0) {
						sb.append(", ");
					}
					if (o != null) {
						sb.append(o.toString());
					} else {
						sb.append("null");
					}
					c++;
				}

				if (values.size() > 1) {
					sb.append("}");
				}

				sb.append("\n");
			}
		}

		return sb.toString();
	}

}
