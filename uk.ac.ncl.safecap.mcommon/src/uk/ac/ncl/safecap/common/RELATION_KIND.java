package uk.ac.ncl.safecap.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum RELATION_KIND {
	RELATION,
	PARTIAL_FUNCTION,

	TOTAL_FUNCTION,
	PARTIAL_SURJECTION,
	TOTAL_SURJECTION,

	TOTAL_RELATION,
	SURJECTIVE_RELATION,
	TOTAL_SURJECTIVE_RELATION;

	public boolean isFunction() {
		switch (this) {
		case PARTIAL_FUNCTION:
		case TOTAL_FUNCTION:
		case TOTAL_SURJECTION:
		case PARTIAL_SURJECTION:
			return true;
		default:
			return false;
		}
	}

	public boolean isTotal() {
		switch (this) {
		case TOTAL_FUNCTION:
		case TOTAL_SURJECTION:
		case TOTAL_RELATION:
		case TOTAL_SURJECTIVE_RELATION:
			return true;
		default:
			return false;
		}
	}

	public RELATION_KIND unify(RELATION_KIND other) {
		if (equals(other)) {
			return this;
		} else {
			return RELATION;
		}
	}

	public static RELATION_KIND getKind(boolean isFunction, boolean domTotal, boolean ranTotal) {
		if (isFunction) {
			if (domTotal) {
				if (ranTotal) {
					return TOTAL_SURJECTION;
				} else {
					return TOTAL_FUNCTION;
				}
			} else {
				if (ranTotal) {
					return PARTIAL_SURJECTION;
				} else {
					return PARTIAL_FUNCTION;
				}
			}
		} else {
			if (domTotal) {
				if (ranTotal) {
					return TOTAL_SURJECTIVE_RELATION;
				} else {
					return TOTAL_RELATION;
				}
			} else {
				if (ranTotal) {
					return SURJECTIVE_RELATION;
				} else {
					return RELATION;
				}
			}
		}
	}

	public String value() {
		return name();
	}

	public static RELATION_KIND fromValue(String v) {
		if (v.equals("relation")) {
			return RELATION;
		} else if (v.equals("partial function")) {
			return PARTIAL_FUNCTION;
		} else if (v.equals("total function")) {
			return TOTAL_FUNCTION;
		} else if (v.equals("total surjection")) {
			return TOTAL_SURJECTION;
		} else if (v.equals("total surjective relation")) {
			return TOTAL_SURJECTIVE_RELATION;
		} else if (v.equals("total relation")) {
			return TOTAL_RELATION;
		} else if (v.equals("partial surjection")) {
			return PARTIAL_SURJECTION;
		} else if (v.equals("surjective relation")) {
			return SURJECTIVE_RELATION;
		} else {
			return RELATION;
		}
	}

	@Override
	public String toString() {
		switch (this) {
		case RELATION:
			return "<->"; // 21f9 21AE
		case PARTIAL_FUNCTION:
			return "+->"; // or 219B
		case TOTAL_FUNCTION:
			return "-->";
		case TOTAL_SURJECTION:
			return "->>";
		case TOTAL_SURJECTIVE_RELATION:
			// return "\u21C6";
			return "<<->>";
		case TOTAL_RELATION:
			return "<<->";
		case PARTIAL_SURJECTION:
			return "+->>";
		case SURJECTIVE_RELATION:
			return "<->>";
		// return "\u2904";
		}
		return "";
	}

	/*
	 * @Override public String toString() { switch (this) { case RELATION: return
	 * "\u21F9"; // 21f9 21AE case PARTIAL_FUNCTION: return "\u21F8"; // or 219B
	 * case TOTAL_FUNCTION: return "\u2192"; case TOTAL_SURJECTION: return "\u21A0";
	 * case TOTAL_SURJECTIVE_RELATION: // return "\u21C6"; return "\u21F9"; case
	 * TOTAL_RELATION: return "\u2194"; case PARTIAL_SURJECTION: return "\u2900";
	 * case SURJECTIVE_RELATION: return "\u21F9"; // return "\u2904"; } return ""; }
	 */

	public String getLongName() {
		switch (this) {
		case RELATION:
			return "relation"; // 21f9 21AE
		case PARTIAL_FUNCTION:
			return "partial function"; // or 219B
		case TOTAL_FUNCTION:
			return "total function";
		case TOTAL_SURJECTION:
			return "total surjection";
		case TOTAL_SURJECTIVE_RELATION:
			return "total surjective relation";
		case TOTAL_RELATION:
			return "total relation";
		case PARTIAL_SURJECTION:
			return "partial surjection";
		case SURJECTIVE_RELATION:
			return "surjective relation";
		}
		return "";
	}

}