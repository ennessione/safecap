package uk.ac.ncl.safecap.xmldata.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import uk.ac.ncl.safecap.xdata.realworldtypes.PhysicalType;

@XmlType // (factoryMethod="create")
public final class XIntegerType extends XType {
	public static final XIntegerType INSTANCE = new XIntegerType();
	private PhysicalType physicalType;

	public static XIntegerType create() {
		return INSTANCE;
	}

	public XIntegerType() {
	}

	public XIntegerType(PhysicalType physicalType) {
		assert physicalType != null;
		this.physicalType = physicalType;
	}

	@XmlElement
	public PhysicalType getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(PhysicalType physicalType) {
		this.physicalType = physicalType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (physicalType == null ? 0 : physicalType.hashCode());
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
		final XIntegerType other = (XIntegerType) obj;
		if (physicalType == null) {
			if (other.physicalType != null) {
				return false;
			}
		} else if (!physicalType.equals(other.physicalType)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if (physicalType == null) {
			return "int";
		} else {
			return physicalType.toStringLong();
		}

	}
}
