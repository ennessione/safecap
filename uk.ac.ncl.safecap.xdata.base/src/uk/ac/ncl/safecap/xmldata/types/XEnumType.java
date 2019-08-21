package uk.ac.ncl.safecap.xmldata.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XEnumType extends XType {
	private String name;
	// private Set<String> members;

	public XEnumType() {

	}

	public XEnumType(String name) {
		assert name.length() > 1;
		this.name = name;
		// members = new HashSet<String>();
	}

//	public void addMember(String member) {
//		if (!members.contains(member))
//			members.add(member);
//	}

	@XmlElement
	public String getName() {
		return name;
	}

//	public Set<String> getMembers() {
//		return members;
//	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
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
		final XEnumType other = (XEnumType) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
