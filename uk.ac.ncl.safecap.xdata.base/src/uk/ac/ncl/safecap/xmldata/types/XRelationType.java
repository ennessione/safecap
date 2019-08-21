package uk.ac.ncl.safecap.xmldata.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import uk.ac.ncl.safecap.common.RELATION_KIND;

@XmlType
public class XRelationType extends XType {
	private XType domain;
	private XType range;
	private RELATION_KIND kind;

	public XRelationType() {
	}

	public XRelationType(XType domain, XType range, RELATION_KIND kind) {
		this.domain = domain;
		this.range = range;
		this.kind = kind;
	}

	@XmlElement
	public XType getDomain() {
		return domain;
	}

	@XmlElement
	public XType getRange() {
		return range;
	}

	@XmlElement
	public RELATION_KIND getKind() {
		return kind;
	}

	public void setDomain(XType domain) {
		this.domain = domain;
	}

	public void setRange(XType range) {
		this.range = range;
	}

	public void setKind(RELATION_KIND kind) {
		System.out.println("Set kind: " + kind + " isfun: " + kind.isFunction());
		this.kind = kind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (domain == null ? 0 : domain.hashCode());
		result = prime * result + (kind == null ? 0 : kind.hashCode());
		result = prime * result + (range == null ? 0 : range.hashCode());
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
		final XRelationType other = (XRelationType) obj;
		if (domain == null) {
			if (other.domain != null) {
				return false;
			}
		} else if (!domain.equals(other.domain)) {
			return false;
		}
		if (kind != other.kind) {
			return false;
		}
		if (range == null) {
			if (other.range != null) {
				return false;
			}
		} else if (!range.equals(other.range)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if (domain != null && kind != null && range != null) {
			return domain.toStringNoBrackets() + " " + kind.toString() + " " + range.toStringNoBrackets();
		} else {
			return "?";
		}
	}
}
