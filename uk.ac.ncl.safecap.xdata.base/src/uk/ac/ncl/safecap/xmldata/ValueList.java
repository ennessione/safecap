package uk.ac.ncl.safecap.xmldata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import uk.ac.ncl.safecap.xmldata.types.XInvalidType;
import uk.ac.ncl.safecap.xmldata.types.XPowType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XSeqType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class ValueList {
	private List<Object> values;
	private XType type;
	private boolean set = false;

	public ValueList() {
		values = new ArrayList<>();
	}

	public ValueList(int size, boolean set) {
		values = new ArrayList<>(size);
		this.set = set;
	}

	public ValueList(Object a, Object b) {
		values = new ArrayList<>(2);
		values.add(a);
		values.add(b);
		set = false;
	}

	public ValueList(Collection<Object> data, boolean set) {
		values = new ArrayList<>(data.size());
		values.addAll(data);
		this.set = set;
	}

	// public void cookTypes(TypeRegistry typeRegistry) {
	//
	// for(int i = 0; i < values.size(); i++) {
	// Object v = values.get(i);
	// XType type = XType.resolve(typeRegistry, v);
	// if (type != null && v instanceof String) {
	// assert(type instanceof XEnumType);
	// values.set(i, new TypedId(v.toString(), ((XEnumType) type).getName()));
	// } else if (v instanceof ValueList) {
	// ValueList vlist = (ValueList) v;
	// vlist.cookTypes(typeRegistry);
	// }
	// }
	//
	// if (type == null)
	// type = resolve(typeRegistry);
	// }

	public void setType(XType type) {
		this.type = type;
	}

	public XType resolve(TypeRegistry typeRegistry) {
		if (type != null) {
			return type;
		}
		final List<XType> types = new ArrayList<>(values.size());
		XType unified = null;
		for (final Object o : values) {
			XType t = XType.resolve(typeRegistry, o);
			if (t == null) {
				return null;
			}

			if (unified == null) {
				unified = t;
			} else {
				unified = XType.unify(unified, t);
			}

			if (unified.equals(t)) {
				t = unified;
			}

			types.add(t);
		}

		if (unified == null) {
			return null;
		}

		if (unified != XInvalidType.INSTANCE) {
			if (set) {
				type = new XPowType(unified);
				return type;
			} else {
				type = new XSeqType(unified);
				return type;
			}
		} else {
			final XType ptype = new XProductType(types);
			// if (set) {
			// type = new XPowType(ptype);
			// return type;
			// } else {
			// type = new XSeqType(ptype);
			// return type;
			// }
			return ptype;
		}
	}

	public int size() {
		return values.size();
	}

	public XType getType() {
		return type;
	}

	public void add(Object value) {
		values.add(value);
	}

	public Object get(int index) {
		return values.get(index);
	}

	@XmlElement
	public boolean isSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}

	// @XmlElement(namespace = "http://www.w3.org/2001/XMLSchema")
	@XmlElement
	public List<Object> getValues() {
		return values;
	}

	@SuppressWarnings("unchecked")
	public void setValues(List values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return values.toString();
	}
}
