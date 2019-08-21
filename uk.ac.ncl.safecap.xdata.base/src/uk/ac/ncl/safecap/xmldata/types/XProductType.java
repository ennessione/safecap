package uk.ac.ncl.safecap.xmldata.types;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XProductType extends XType {
	private List<XType> members;

	public XProductType() {
		members = null;
	}

	public XProductType(List<XType> members) {
		this.members = members;
	}

	public XType get(int index) {
		return members.get(index);
	}

	public int getSize() {
		return members.size();
	}

	@XmlElement
	public List<XType> getMembers() {
		return members;
	}

	public void setMembers(List<XType> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < members.size(); i++) {
			if (i > 0) {
				sb.append(" * ");
			}
			sb.append(members.get(i));
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String toStringNoBrackets() {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < members.size(); i++) {
			if (i > 0) {
				sb.append(" * ");
			}
			sb.append(members.get(i));
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (members == null ? 0 : members.hashCode());
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
		final XProductType other = (XProductType) obj;
		if (members == null) {
			if (other.members != null) {
				return false;
			}
		} else if (!members.equals(other.members)) {
			return false;
		}
		return true;
	}

}
