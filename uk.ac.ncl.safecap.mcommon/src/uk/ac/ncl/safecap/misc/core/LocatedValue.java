package uk.ac.ncl.safecap.misc.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class LocatedValue extends Location {
	private Object value;

	private int offset1;
	private int offset2;

	private transient String file;

	public LocatedValue() {
	}

	public LocatedValue(Object value, int line, int column, int offset, int length) {
		super(line, column, offset, length);
		assert value != null;
		this.value = value;
	}

	public int getOffset1() {
		return offset1;
	}

	public void setOffset1(int offset1) {
		this.offset1 = offset1;
	}

	public int getOffset2() {
		return offset2;
	}

	public void setOffset2(int offset2) {
		this.offset2 = offset2;
	}

	@XmlTransient
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@XmlElement(namespace = "http://www.w3.org/2001/XMLSchema")
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		assert value != null;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof LocatedValue) {
			final LocatedValue lv = (LocatedValue) obj;
			return lv.value.equals(value);
		} else {
			return obj.equals(value);
		}
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
