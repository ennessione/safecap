package uk.ac.ncl.safecap.xmldata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
import uk.ac.ncl.safecap.xmldata.types.XBoolType;
import uk.ac.ncl.safecap.xmldata.types.XChoiceType;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRealType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class TypeCasting {

	public static void castFunctionRange(XFunctionBasic ff, String rangeType, TypeRegistry registry) throws DataConfigurationException {
		try {
			final XType range = XType.parse(rangeType);
			castFunctionRange(ff, range, registry);
		} catch (final Throwable e) {
			System.err.println("Range casting failed for " + ff.getName() + ": " + e.getMessage());
		}
	}

	public static void castFunctionDomain(XFunctionBasic ff, String domainType, TypeRegistry registry) throws DataConfigurationException {
		try {
			final XType domain = XType.parse(domainType);
			castFunctionDomain(ff, domain, registry);
		} catch (final Throwable e) {
			System.err.println("Domain casting failed for " + ff.getName() + ": " + e.getMessage());
		}
	}

	public static void castFunctionRange(XFunctionBasic ff, XType range, TypeRegistry registry) throws DataConfigurationException {
		try {
			_castFunction(ff, null, range, registry, true);
		} catch (final TypeCastingAbort e) {
			throw new DataConfigurationException(e);
		}
	}

	public static void castFunctionDomain(XFunctionBasic ff, XType domain, TypeRegistry registry) throws DataConfigurationException {
		try {
			_castFunction(ff, domain, null, registry, true);
		} catch (final TypeCastingAbort e) {
			throw new DataConfigurationException(e);
		}
	}

	public static boolean canCastFunction(XFunctionBasic ff, XType domain, XType range, TypeRegistry registry) {
		try {
			_castFunction(ff, domain, range, registry, false);
		} catch (final TypeCastingAbort e) {
			return false;
		}

		return true;
	}

	public static boolean castFunction(XFunctionBasic ff, XType domain, XType range, TypeRegistry registry) {
		try {
			_castFunction(ff, domain, range, registry, true);
		} catch (final TypeCastingAbort e) {
			return false;
		}

		return true;
	}

	private static void _castFunction(XFunctionBasic ff, XType domain, XType range, TypeRegistry registry, boolean setType)
			throws TypeCastingAbort {
		if (ff.getMaps().isEmpty()) {
			return;
		}

		final Map<Object, List<Object>> data = ff.getMaps();
		final Map<Object, List<Object>> result = new HashMap<>(data.size());
		for (final Object d : data.keySet()) {
			try {
				// boolean contains = data.containsKey(d);
				final List<Object> oldValue = data.get(d);
				final Object d_new = domain == null ? d : enforce(d, domain, registry, setType);

				final List<Object> r_new = range == null ? oldValue : enforceCollection(oldValue, range, registry, setType);

				assert r_new != null;

				result.put(d_new, r_new);

			} catch (final TypeCastingAbort e) {
				e.setDomainType(domain);
				e.setRangeType(range);
				e.setFunction(ff);
				e.setDomain(d);
				throw e;
			}
		}
		if (setType) {
			ff.setMaps(result);
		}
	}

	private static List<Object> enforceCollection(Collection<Object> value, XType type, TypeRegistry registry, boolean setType)
			throws TypeCastingAbort {
		if (type instanceof XPowType) {
			final XPowType t = (XPowType) type;
			try {
				return _wrap(enforceSetType(value, t, registry, setType));
			} catch (final TypeCastingAbort e) {
				return _wrap(enforceSetType(_wrapVL(value, true), t, registry, setType));
			}
		} else if (type instanceof XSeqType) {
			final XSeqType t = (XSeqType) type;
			try {
				return _wrap(enforceSeqType(value, t, registry, setType));
			} catch (final TypeCastingAbort e) {
				return _wrap(enforceSeqType(_wrapVL(value, false), t, registry, setType));
			}
		} else if (type instanceof XChoiceType) {
			final XChoiceType choice = (XChoiceType) type;
			for (final XType c : choice.getMembers()) {
				try {
					return enforceCollection(value, c, registry, setType);
				} catch (final TypeCastingAbort e) {
					// empty
				}
			}
			throw new TypeCastingAbort(value, type);
		} else if (type instanceof XProductType) {
			final XProductType t = (XProductType) type;

			if (value instanceof Collection) {
				final Collection<Object> ls = value;
				final List<Object> result = new ArrayList<>(ls.size());
				for (final Object o : ls) {
					result.add(enforceProductType(o, t, registry, setType));
				}
				return result;
			} else {
				try {
					return _wrap(enforceProductType(value, t, registry, setType));
				} catch (final TypeCastingAbort e) {
					return _wrap(enforceProductType(_wrapVL(value, true), t, registry, setType));
				}
			}
		} else {
			final List<Object> result = new ArrayList<>(value.size());
			try {
				for (final Object v : value) {
					result.add(enforce(v, type, registry, setType));
				}
			} catch (final TypeCastingAbort e) {
				if (value.size() == 1) {
					final Object _value = value.iterator().next();
					if (_value instanceof ValueList) {
						final ValueList vl = (ValueList) _value;
						return enforceCollection(vl.getValues(), type, registry, setType);
					}
				} else if (value.size() == 2) {
					result.add(enforce(value.toArray()[1], type, registry, setType));
				}
			}

			return result;
		}
	}

	private static Object _wrapVL(Collection<Object> value, boolean b) {
		if (value.size() == 1) {
			final Object z = value.iterator().next();
			if (z instanceof ValueList) {
				return z;
			}
		}

		return new ValueList(value, b);
	}

	private static List<Object> _wrap(ValueList vl) {
		final List<Object> c = new ArrayList<>(1);
		c.add(vl);
		return c;
	}

	public static Object castToType(Object value, XType type, TypeRegistry registry) {
		try {
			return enforce(value, type, registry, true);
		} catch (final TypeCastingAbort e) {
			return null;
		}
	}

	public static Object canCastToType(Object value, XType type, TypeRegistry registry) {
		try {
			enforce(value, type, registry, false);
			return true;
		} catch (final TypeCastingAbort e) {
			return null;
		}
	}

	private static Object enforce(Object value, XType type, TypeRegistry registry, boolean setTypes) throws TypeCastingAbort {
		if (type instanceof XPowType) {
			final XPowType t = (XPowType) type;
			return enforceSetType(value, t, registry, setTypes);
		} else if (type instanceof XSeqType) {
			final XSeqType t = (XSeqType) type;
			return enforceSeqType(value, t, registry, setTypes);
		} else if (type instanceof XProductType) {
			final XProductType t = (XProductType) type;
			return enforceProductType(value, t, registry, setTypes);
		} else if (type instanceof XChoiceType) {
			final XChoiceType t = (XChoiceType) type;
			return enforceChoiceType(value, t, registry, setTypes);
		} else if (type instanceof XBoolType) {
			return enforceBoolType(value);
		} else if (type instanceof XIntegerType) {
			return enforceIntegerType(value);
		} else if (type instanceof XRealType) {
			return enforceRealType(value);
		} else if (type instanceof XEnumType) {
			return enforceEnumType(value, (XEnumType) type, registry, setTypes);
		} else {
			throw new TypeCastingAbort(value, type);
		}
	}

	private static Object enforceChoiceType(Object value, XChoiceType t, TypeRegistry registry, boolean setTypes) throws TypeCastingAbort {
		for (final XType c : t.getMembers()) {
			try {
				return enforce(value, c, registry, setTypes);
			} catch (final TypeCastingAbort e) {
				// empty
			}
		}
		throw new TypeCastingAbort(value, t);
	}

	private static Object valueWrapping(Object original, Object created) {
		if (original != created) {
			if (original instanceof LocatedValue) {
				((LocatedValue) original).setValue(created);
				return original;
			} else {
				return created;
			}
		}
		return original;
	}

	private static Object enforceEnumType(Object value, XEnumType enumType, TypeRegistry registry, boolean setTypes)
			throws TypeCastingAbort {
		if (isLiteralValue(value)) {
			final Object unwrapped = getLiteralValue(value);
			String literal = unwrapped.toString();
			final XType type = XType.getLiteralType(registry, literal);
			if (type == null) {
				literal = registry.normalise(literal, enumType.getName());
				if (setTypes) {
					if (registry.getEnum(enumType.getName()) == null) {
						registry.createNewEnum(enumType.getName());
					}
					registry.setType(literal, enumType.getName());
				}
				return valueWrapping(value, literal);
			} else if (enumType.equals(type)) {
				return valueWrapping(value, literal);
			} else {
				// unequal types, try normalisation
				final String alternative = registry.normalise(literal, enumType.getName());
				if (alternative != literal) { // there is normalisation
					return enforceEnumType(valueWrapping(value, alternative), enumType, registry, setTypes);
				}
				throw new TypeCastingAbort(value, enumType, type);
			}
		}
		throw new TypeCastingAbort(value, enumType);
	}

	private static Object enforceIntegerType(Object _value) throws TypeCastingAbort {
		final Object value = valueUnwrapping(_value);
		if (value instanceof Integer) {
			return _value;
		}
		if (value instanceof ValueList) {
			throw new TypeCastingAbort(_value, XIntegerType.INSTANCE);
		} else {
			try {
				return valueWrapping(_value, Integer.parseInt(value.toString()));
			} catch (final NumberFormatException e) {
				throw new TypeCastingAbort(_value, XIntegerType.INSTANCE);
			}
		}
	}

	private static Object enforceRealType(Object _value) throws TypeCastingAbort {
		final Object value = valueUnwrapping(_value);
		if (value instanceof Double) {
			return _value;
		}
		if (value instanceof ValueList) {
			throw new TypeCastingAbort(_value, XRealType.INSTANCE);
		} else {
			try {
				return valueWrapping(_value, Double.parseDouble(value.toString()));
			} catch (final NumberFormatException e) {
				throw new TypeCastingAbort(_value, XRealType.INSTANCE);
			}
		}
	}

	private static Object enforceBoolType(Object _value) throws TypeCastingAbort {
		final Object value = valueUnwrapping(_value);
		if (value instanceof Boolean) {
			return _value;
		}
		if (value instanceof ValueList) {
			final ValueList vl = (ValueList) value;
			if (vl.size() == 2) {
				return enforceBoolType(vl.get(1));
			}
			throw new TypeCastingAbort(_value, XBoolType.INSTANCE);
		} else {
			if (value.toString().toLowerCase().equals("true")) {
				return valueWrapping(_value, Boolean.TRUE);
			} else if (value.toString().toLowerCase().equals("false")) {
				return valueWrapping(_value, Boolean.FALSE);
			} else {
				throw new TypeCastingAbort(value, XBoolType.INSTANCE);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static ValueList enforceProductType(Object value, XProductType type, TypeRegistry registry, boolean setTypes)
			throws TypeCastingAbort {
		if (value instanceof ValueList) {
			final ValueList vl = (ValueList) value;
			// vl = normalize(vl);

			if (vl.size() != type.getMembers().size()) {
				if (vl.size() == 1 && vl.get(0) instanceof ValueList) {
					final ValueList _vl = (ValueList) vl.get(0);
					if (_vl.size() == type.getSize()) {
						return enforceProductType(_vl, type, registry, setTypes);
					}
				}
				throw new TypeCastingAbort(value, type);
			}

			final ValueList result = new ValueList(vl.size(), false);
			for (int i = 0; i < vl.size(); i++) {
				result.add(enforce(vl.get(i), type.getMembers().get(i), registry, setTypes));
			}

			result.setType(type);
			return result;
		} else if (value instanceof List) {
			final List<Object> ls = (List<Object>) value;

		} else if (type.getSize() == 2 && type.get(0) == XIntegerType.INSTANCE
				&& !(value instanceof Collection || value instanceof ValueList)) {
			final Object left = new Integer(0);
			final Object right = enforce(value, type.get(1), registry, setTypes);
			final ValueList r = new ValueList(left, right);
			r.setSet(true);
			r.setType(type);
			return r;
		} else if (value instanceof List) {
			final List<Object> col = (List<Object>) value;
			if (col.size() != type.getMembers().size()) {
				if (col.size() == 1 && col.get(0) instanceof ValueList) {
					final ValueList _vl = (ValueList) col.get(0);
					if (_vl.size() == type.getSize()) {
						return enforceProductType(_vl, type, registry, setTypes);
					}
				}
				throw new TypeCastingAbort(value, type);
			}

			final ValueList result = new ValueList(col.size(), false);
			for (int i = 0; i < col.size(); i++) {
				result.add(enforce(col.get(i), type.getMembers().get(i), registry, setTypes));
			}

			result.setType(type);
			return result;
		}
		throw new TypeCastingAbort(value, type);
	}

	@SuppressWarnings("unchecked")
	private static ValueList enforceSeqType(Object value, XSeqType type, TypeRegistry registry, boolean setTypes) throws TypeCastingAbort {
		if (value instanceof ValueList) {
			final ValueList vl = (ValueList) value;
			// vl = normalize(vl);

			final ValueList result = new ValueList(vl.size(), false);

			try {
				for (int i = 0; i < vl.size(); i++) {
					result.add(enforce(vl.get(i), type.getBase(), registry, setTypes));
				}
			} catch (final TypeCastingAbort e) {
				result.add(enforce(vl, type.getBase(), registry, setTypes));
			}

			result.setType(type);
			return result;
		} else if (value instanceof List) {
			final List<Object> col = (List<Object>) value;
			final ValueList result = new ValueList(col.size(), false);

			try {
				for (int i = 0; i < col.size(); i++) {
					result.add(enforce(col.get(i), type.getBase(), registry, setTypes));
				}
			} catch (final TypeCastingAbort e) {
				if (col.size() == 1 && col.get(0) instanceof ValueList) {
					return enforceSeqType(col.get(0), type, registry, setTypes);
				}
			}

			result.setType(type);
			return result;
		}
		throw new TypeCastingAbort(value, type);
	}

	@SuppressWarnings("unchecked")
	private static ValueList enforceSetType(Object value, XPowType type, TypeRegistry registry, boolean setTypes) throws TypeCastingAbort {
		if (value instanceof ValueList) {
			final ValueList vl = (ValueList) value;
			// vl = normalize(vl);

			final ValueList result = new ValueList(vl.size(), true);
			for (int i = 0; i < vl.size(); i++) {
				result.add(enforce(vl.get(i), type.getBase(), registry, setTypes));
			}

			result.setType(type);
			return result;
		} else if (value instanceof List) {
			final List<Object> col = (List<Object>) value;
			final ValueList result = new ValueList(col.size(), true);
			for (int i = 0; i < col.size(); i++) {
				result.add(enforce(col.get(i), type.getBase(), registry, setTypes));
			}

			result.setType(type);
			return result;
		}
		throw new TypeCastingAbort(value, type);
	}

	// private static ValueList normalize(ValueList vl) {
	// if (vl.size() == 1 && vl.get(0) instanceof ValueList)
	// return normalize((ValueList) vl.get(0));
	// return vl;
	// }

	private static boolean isLiteralValue(Object object) {
		if (object instanceof ValueList) {
			final ValueList vl = (ValueList) object;
			return vl.size() == 1;
		} else {
			return true;
		}
	}

	private static Object getLiteralValue(Object object) {
		if (object instanceof ValueList) {
			final ValueList vl = (ValueList) object;
			assert vl.size() == 1;
			return valueUnwrapping(vl.get(0));
		} else {
			return valueUnwrapping(object);
		}
	}

	private static Object valueUnwrapping(Object value) {
		if (value instanceof LocatedValue) {
			return ((LocatedValue) value).getValue();
		} else {
			return value;
		}
	}

}
