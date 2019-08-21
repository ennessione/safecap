package uk.ac.ncl.safecap.xmldata.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import uk.ac.ncl.safecap.common.MyEntry;
import uk.ac.ncl.safecap.common.RELATION_KIND;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataException;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.ValueList;

public class XFunctionBasic implements IXFunction {
	private String name;
	private String tag; // tag denotes function origin, like derived or signalling
	private String canonicalName;
	private Map<Object, List<Object>> maps;
	private transient XType myDomainType = null;
	private transient XType myRangeType = null;
	private transient XType myType = null;
	private final DataContext dataContext;
	private boolean derived;

	public XFunctionBasic(DataContext dataContext, String name) {
		this.name = name;
		this.dataContext = dataContext;
		maps = new HashMap<>();
	}

	public XFunctionBasic clone(String suffix) {
		final XFunctionBasic c = new XFunctionBasic(dataContext, name + suffix);
		c.tag = tag;
		c.canonicalName = canonicalName + suffix;
		c.maps = new HashMap<>(maps.size());
		for (final Object z : maps.keySet()) {
			c.maps.put(z, new ArrayList<>(maps.get(z)));
		}
		c.myDomainType = myDomainType;
		c.derived = derived;
		return c;
	}

	public XFunctionBasic cloneEmpty() {
		final XFunctionBasic c = new XFunctionBasic(dataContext, name);
		c.tag = tag;
		c.canonicalName = canonicalName;
		c.maps = new HashMap<>();
		c.myDomainType = myDomainType;
		c.derived = derived;
		c.myRangeType = myRangeType;
		c.myType = myType;
		return c;
	}	
	
	public XFunctionBasic(DataContext dataContext, String name, XType domain, XType range) {
		this.name = name;
		this.dataContext = dataContext;
		maps = new HashMap<>();
		myDomainType = domain;
		myRangeType = range;
	}

	public void setMapping(Object key, List<Object> values) {
		maps.put(key, values);
	}

	@Override
	public void add(Object from, Object _to) {
		final Object to = normalise(_to);
		// if (to != _to) {
		// System.out.println("Normalised " + _to + " to " + to);
		// }
		if (maps.containsKey(from)) {
			maps.get(from).add(to);
		} else {
			final List<Object> set = new ArrayList<>();
			set.add(to);
			maps.put(from, set);
		}
	}

	private Object normalise(Object data) {
		if (data instanceof String) {
			String s = (String) data;
			s = s.trim();
			if (s.startsWith("(") && s.endsWith(")")) {
				return processList(s);
			}
		}

		return data;
	}

	private Object processList(String s) {
		if (s.startsWith("(") && s.endsWith(")")) {
			s = s.substring(1, s.length() - 1);
			return nestedSplit(s);
		} else {
			return s;
		}
	}

	private ValueList nestedSplit(String data) {
		final ValueList tokens = new ValueList();
		final StringBuilder buffer = new StringBuilder();

		int parenthesesCounter = 0;
		int quotesCounter = 0;
		for (final char c : data.toCharArray()) {
			if (c == '(') {
				parenthesesCounter++;
			}
			if (c == ')') {
				parenthesesCounter--;
			}
			if (c == '"') {
				quotesCounter++;
			}
			if (c == ',' && parenthesesCounter == 0 && quotesCounter % 2 == 0) {
				// lets add token inside buffer to our tokens
				tokens.add(processList(buffer.toString()));
				// now we need to clear buffer
				buffer.delete(0, buffer.length());
			} else {
				buffer.append(c);
			}
		}

		tokens.add(processList(buffer.toString()));

		return tokens;
	}

	@Override
	public String getDescription() {
		return dataContext.getFunctionInfo().get(name);
	}

	@Override
	public void setDescription(String description) {
		dataContext.getFunctionInfo().put(name, description);
	}

	@Override
	public List<Object> get(Object from) {
		return maps.get(from);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<Object> domain() {
		return maps.keySet();
	}

	@Override
	public Object getAny(Object from) {
		final Collection<Object> s = maps.get(from);
		if (s != null && !s.isEmpty()) {
			return s.iterator().next();
		} else {
			return null;
		}
	}

	@Override
	public boolean isFunction() {
		try {
			for (final Object k : maps.keySet()) {
				if (maps.get(k).size() != 1) {
					return false;
				}
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Entry<Object, Object>[] getParts() {
		final List<Entry<Object, Object>> result = new ArrayList<>(maps.size() * 2);
		for (final Object k : maps.keySet()) {
			for (final Object v : maps.get(k)) {
				result.add(new MyEntry<>(k, v));
			}
		}
		return result.toArray(new Entry[result.size()]);
	}

	/**
	 * Returns raw data of the function. Unsafe!
	 */
	public Map<Object, List<Object>> getMaps() {
		return maps;
	}

	/**
	 * Saves raw data of the function. Unsafe!
	 */
	public void setMaps(Map<Object, List<Object>> maps) {
		this.maps = maps;
	}

	public DataContext getDataContext() {
		return dataContext;
	}

	// private XType getRangeTypeIncomplete() {
	// XType z = null;
	// for (Object d : maps.keySet()) {
	// for (Object v : maps.get(d)) {
	// if (z == null) {
	// z = XType.resolve(dataContext.getTypeRegistry(), v);
	// } else {
	// XType ff = XType.resolve(dataContext.getTypeRegistry(), v);
	// if (ff != null && !z.equals(ff))
	// return null;
	// }
	// }
	// }
	//
	// return z;
	// }

	private XType getDomainTypeIncomplete() {
		XType z = null;
		for (final Object d : maps.keySet()) {
			if (z == null) {
				z = XType.resolve(dataContext.getTypeRegistry(), d);
			} else {
				final XType ff = XType.resolve(dataContext.getTypeRegistry(), d);
				if (ff != null && !z.equals(ff)) {
					return null;
				}
			}
		}

		return z;
	}

	private XType getRangeTypeComplete() {

		XType z = null;
		for (final Object d : maps.keySet()) {
			for (final Object v : maps.get(d)) {
				if (z == null) {
					z = XType.resolve(dataContext.getTypeRegistry(), v);
					if (z == null) {
						return null;
					}
				} else {
					final XType ff = XType.resolve(dataContext.getTypeRegistry(), v);
					if (ff == null || !z.equals(ff)) {
						return null;
					}
				}
			}
		}

		return z;
	}

	private XType getDomainTypeComplete() {
		XType z = null;
		for (final Object d : maps.keySet()) {
			if (z == null) {
				z = XType.resolve(dataContext.getTypeRegistry(), d);
				if (z == null) {
					return null;
				}
			} else {
				final XType ff = XType.resolve(dataContext.getTypeRegistry(), d);
				if (ff == null || !z.equals(ff)) {
					return null;
				}
			}
		}

		return z;
	}

	public XType getType() {
		myDomainType = maps.isEmpty() ? myDomainType : getDomainTypeComplete();
		myRangeType = maps.isEmpty() ? myRangeType : getRangeTypeComplete();

		if (myDomainType != null && myRangeType != null) {
			final RELATION_KIND kind = RELATION_KIND.getKind(isFunction(), isDomainTotal(), isRangeTotal());
			return new XRelationType(myDomainType, myRangeType, kind);
		}

		return null;
	}

	public XType getDomainType() {
		return myDomainType;
	}

	public XType getRangeType() {
		return myRangeType;
	}

	public boolean isDomainTotal() {
		if (myDomainType instanceof XEnumType) {
			final XEnumType etype = (XEnumType) myDomainType;
			return dataContext.getEnumMembers(etype).equals(maps.keySet());
		} else if (myDomainType instanceof XBoolType) {
			boolean hasTrue = false;
			boolean hasFalse = false;

			outer: for (final Object k : maps.keySet()) {
				if (k instanceof Boolean) {
					final Boolean b_k = (Boolean) k;
					if (b_k == Boolean.TRUE) {
						hasTrue = true;
					} else if (b_k == Boolean.TRUE) {
						hasFalse = true;
					}

					if (hasTrue && hasFalse) {
						break outer;
					}
				}
			}

			return hasTrue && hasFalse;
		} else {
			return false;
		}
	}

	public boolean isRangeTotal() {
		if (myRangeType instanceof XEnumType) {
			final Set<Object> range_union = new HashSet<>();
			for (final Object k : maps.keySet()) {
				range_union.addAll(maps.get(k));
			}

			final XEnumType etype = (XEnumType) myRangeType;
			return dataContext.getEnumMembers(etype).equals(range_union);
		} else if (myRangeType instanceof XBoolType) {
			boolean hasTrue = false;
			boolean hasFalse = false;

			outer: for (final Object k : maps.keySet()) {
				if (k instanceof Boolean) {
					final Boolean b_k = (Boolean) k;
					if (b_k == Boolean.TRUE) {
						hasTrue = true;
					} else if (b_k == Boolean.TRUE) {
						hasFalse = true;
					}

					if (hasTrue && hasFalse) {
						break outer;
					}
				}
			}

			return hasTrue && hasFalse;
		} else {
			return false;
		}
	}

	private void doTypeInference() throws DataException {

		synchronized (dataContext) {
			final XType domType = getDomainTypeIncomplete();
			if (domType instanceof XEnumType) {
				final XEnumType etype = (XEnumType) domType;
				for (final Object _s : maps.keySet()) {
					if (_s instanceof String) {
						final String s = _s.toString();
						if (XType.resolve(dataContext.getTypeRegistry(), _s) == null) {
							dataContext.getTypeRegistry().setType(s, etype.getName());
						}
					}
				}
			}
		}
	}

	@Override
	public void computeType() {
		if (!maps.isEmpty()) {
			myType = getType();
		} else {
			myType = new XRelationType(myDomainType, myRangeType, RELATION_KIND.PARTIAL_FUNCTION);
		}
	}

	@Override
	public void inferType() {
		try {
			if (!maps.isEmpty()) {
				doTypeInference();
			}
		} catch (final DataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public XType getFunctionType() {
		return myType;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCanonicalName() {
		if (canonicalName == null) {
			return name;
		} else {
			return canonicalName;
		}
	}

	@Override
	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	@Override
	public boolean isDerived() {
		return derived;
	}

	@Override
	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
