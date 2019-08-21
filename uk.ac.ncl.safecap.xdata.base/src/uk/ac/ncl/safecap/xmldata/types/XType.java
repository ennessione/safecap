package uk.ac.ncl.safecap.xmldata.types;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.safecap.misc.core.LocatedValue;
//import uk.ac.ncl.safecap.xdata.realworldtypes.PhysicalType;
import uk.ac.ncl.safecap.xmldata.DataConfigurationException;
import uk.ac.ncl.safecap.xmldata.TypeRegistry;
import uk.ac.ncl.safecap.xmldata.ValueList;
import uk.ac.ncl.safecap.xtype.ErrInfo;
import uk.ac.ncl.safecap.xtype.Parser;
import uk.ac.ncl.safecap.xtype.xtypesymb;
import uk.ac.ncl.safecap.xtype.xtypesyn;

@XmlTransient
@XmlSeeAlso({ XBoolType.class, XIntegerType.class, XRealType.class, XPowType.class, XSeqType.class, XProductType.class, XRelationType.class,
		XEnumType.class })
public abstract class XType {

	public String toStringNoBrackets() {
		return toString();
	}

	public static XType getLiteralType(TypeRegistry typeRegistry, String name) {
		final String type = typeRegistry.getType(name);
		if (type != null) {
			return typeRegistry.getEnum(type);
		} else {
			return null;
		}
	}

	public static XType parse(String typeString) throws DataConfigurationException {
		try {
			final List<ErrInfo> errInfo = new ArrayList<>();
			InputStream is = new ByteArrayInputStream(typeString.getBytes(StandardCharsets.UTF_8));
			final xtypesyn syn = Parser.parse(is, errInfo);
			if (syn == null) {
				throw new DataConfigurationException("Failed parsing type " + typeString);
			}
			return buildType(syn);
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new DataConfigurationException("Failed parsing type " + typeString + " : " + e.getMessage());
		}

	}

	private static XType buildType(xtypesyn syn) throws DataConfigurationException {
		switch (syn.op()) {
		case xtypesymb.BOOL:
			return XBoolType.INSTANCE;
		case xtypesymb.INTEGER:
			if (syn.value() != null) {
//				final String physType = syn.value().toString();
//				final PhysicalType pt = new PhysicalType(physType);
			}
			return XIntegerType.INSTANCE;
		case xtypesymb.REAL:
			if (syn.value() != null) {
//				final String physType = syn.value().toString();
//				final PhysicalType pt = new PhysicalType(physType);
			}
			return XRealType.INSTANCE;
		case xtypesymb.SEQ: {
			final XType t = buildType(syn.sibling(0));
			return new XSeqType(t);
		}
		case xtypesymb.POW: {
			final XType t = buildType(syn.sibling(0));
			return new XPowType(t);
		}
		case xtypesymb.ENUM: {
			final String id = syn.getSynId();
			return new XEnumType(id);
		}
		case xtypesymb.PRODUCT: {
			final List<XType> members = new ArrayList<>(syn.siblings());
			for (int i = 0; i < syn.siblings(); i++) {
				members.add(buildType(syn.sibling(i)));
			}
			return new XProductType(members);
		}
		}
		throw new DataConfigurationException("");
	}

	public static XType resolve(TypeRegistry typeRegistry, Object value) {
		if (value instanceof Boolean) {
			return XBoolType.INSTANCE;
		} else if (value instanceof Integer) {
			return XIntegerType.INSTANCE;
		} else if (value instanceof Double) {
			return XRealType.INSTANCE;
		} else if (value instanceof LocatedValue) {
			return resolve(typeRegistry, ((LocatedValue) value).getValue());
		} else if (value instanceof String) { // un-cooked id
			final String type = typeRegistry.getType(value.toString());
			if (type != null) {
				return typeRegistry.getEnum(type);
			} else {
				return typeRegistry.getEnum(value.toString());
			}
		} else if (value instanceof ValueList) {
			final ValueList vlist = (ValueList) value;
			if (vlist.getType() != null) { // cooked list
				return vlist.getType();
			} else { // un-cooked list
				return vlist.resolve(typeRegistry);
			}
		}
		return null;
	}

	public static XType unify(XType left, XType right) {
		if (left.equals(right)) {
			return left;
		}

		// invalid type cannot be unified
		if (left == XInvalidType.INSTANCE || right == XInvalidType.INSTANCE) {
			return XInvalidType.INSTANCE;
		}

		// unify real/integer into real
		if (left == XIntegerType.INSTANCE && right == XRealType.INSTANCE) {
			return XRealType.INSTANCE;
		} else if (right == XIntegerType.INSTANCE && left == XRealType.INSTANCE) {
			return XRealType.INSTANCE;
		}

		// unify non-tagged integers and tagged integers into tagged integers
		if (left == XIntegerType.INSTANCE && right instanceof XIntegerType) {
			return right;
		} else if (right == XIntegerType.INSTANCE && left instanceof XIntegerType) {
			return left;
		}

		// unify non-tagged reals and tagged reals into tagged reals
		if (left == XRealType.INSTANCE && right instanceof XRealType) {
			return right;
		} else if (right == XRealType.INSTANCE && left instanceof XRealType) {
			return left;
		}

		// unify sequences
		if (left instanceof XSeqType && right instanceof XSeqType) {
			final XSeqType s_left = (XSeqType) left;
			final XSeqType s_right = (XSeqType) right;
			final XType unified_based = unify(s_left.getBase(), s_right.getBase());
			if (unified_based == XInvalidType.INSTANCE) {
				return XInvalidType.INSTANCE;
			} else {
				return new XSeqType(unified_based);
			}
		}

		// unify sets
		if (left instanceof XPowType && right instanceof XPowType) {
			final XPowType s_left = (XPowType) left;
			final XPowType s_right = (XPowType) right;
			final XType unified_based = unify(s_left.getBase(), s_right.getBase());
			if (unified_based == XInvalidType.INSTANCE) {
				return XInvalidType.INSTANCE;
			} else {
				return new XPowType(unified_based);
			}
		}

		// unify products
		if (left instanceof XProductType && right instanceof XProductType) {
			final XProductType s_left = (XProductType) left;
			final XProductType s_right = (XProductType) right;
			if (s_left.getSize() != s_right.getSize()) {
				return XInvalidType.INSTANCE;
			}
			final List<XType> unified = new ArrayList<>(s_left.getSize());
			for (int i = 0; i < s_left.getSize(); i++) {
				final XType u_type = unify(s_left.get(i), s_right.get(i));
				if (u_type == XInvalidType.INSTANCE) {
					return XInvalidType.INSTANCE;
				} else {
					unified.add(u_type);
				}
			}
			return new XProductType(unified);
		}

		// unify relations
		if (left instanceof XRelationType && right instanceof XRelationType) {
			final XRelationType s_left = (XRelationType) left;
			final XRelationType s_right = (XRelationType) right;
			final XType u_dom = unify(s_left.getDomain(), s_right.getDomain());
			if (u_dom == XInvalidType.INSTANCE) {
				return XInvalidType.INSTANCE;
			}
			final XType u_ran = unify(s_left.getRange(), s_right.getRange());
			if (u_ran == XInvalidType.INSTANCE) {
				return XInvalidType.INSTANCE;
			}
			return new XRelationType(u_dom, u_ran, s_left.getKind().unify(s_right.getKind()));
		}

		return XInvalidType.INSTANCE;
	}

}
