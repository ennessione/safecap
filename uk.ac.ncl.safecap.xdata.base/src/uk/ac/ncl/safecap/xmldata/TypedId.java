package uk.ac.ncl.safecap.xmldata;

import javax.xml.bind.annotation.XmlElement;

public class TypedId {
	private String id;
	private String type;

	public TypedId() {
	}

	public TypedId(String id, String type) {
		this.id = id;
		this.type = type;
	}

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return id + ":" + type.toString();
	}

	@Override
	public boolean equals(Object e) {
		if (e instanceof TypedId) {
			final TypedId ti = (TypedId) e;
			return id.equals(ti.id);
		} else if (e instanceof String) {
			return id.equals(e);
		} else {
			return false;
		}
	}

}
