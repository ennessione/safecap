package uk.ac.ncl.safecap.xmldata.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XSeqType extends XType {
	private XType base;

	public XSeqType() {
	}

	public XSeqType(XType base) {
		this.base = base;
	}

	@XmlElement
	public XType getBase() {
		return base;
	}

	public void setBase(XType base) {
		this.base = base;
	}

	@Override
	public String toString() {
		return "seq(" + base.toStringNoBrackets() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (base == null ? 0 : base.hashCode());
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
		final XSeqType other = (XSeqType) obj;
		if (base == null) {
			if (other.base != null) {
				return false;
			}
		} else if (!base.equals(other.base)) {
			return false;
		}
		return true;
	}

}
