package uk.ac.ncl.safecap.analytics.ctextract.main;

import javax.xml.bind.annotation.XmlTransient;

public class CTBin {
	private String name;
	private String path;
	private transient CTProjectPart parent;

	public CTBin() {
	}

	public CTBin(String name, String path) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@XmlTransient
	public void setParent(CTProjectPart parent) {
		this.parent = parent;
	}

	public CTProjectPart getParent() {
		return parent;
	}
}
