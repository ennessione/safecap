package uk.ac.ncl.safecap.xmldata;

import java.util.List;

public class DataContractSplit {
	private final String path;
	private final String base;
	private List<String> parts;

	public DataContractSplit(String path, String base, List<String> parts) {
		this.path = path;
		this.base = base;
		this.parts = parts;
	}

	public String getPath() {
		return path;
	}

	public String getBase() {
		return base;
	}

	public List<String> getParts() {
		return parts;
	}

	public void setParts(List<String> parts) {
		this.parts = parts;
	}
}
