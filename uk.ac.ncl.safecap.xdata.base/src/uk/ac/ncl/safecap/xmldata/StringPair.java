package uk.ac.ncl.safecap.xmldata;

import javax.xml.bind.annotation.XmlElement;

public class StringPair {
	private String l;
	private String r;

	public StringPair() {
	}

	public StringPair(String l, String r) {
		this.l = l;
		this.r = r;
	}

	@Override
	public StringPair clone() {
		return new StringPair(l, r);
	}

	@XmlElement(name = "l", namespace = "http://www.w3.org/2001/XMLSchema")
	public String getKey() {
		return l;
	}

	public void setKey(String l) {
		this.l = l;
	}

	@XmlElement(name = "r")
	// , namespace = "http://www.w3.org/2001/XMLSchema"
	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getValue() {
		return r;
	}

	@Override
	public String toString() {
		return "(" + (l != null ? l.toString() : "null") + ", " + (r != null ? r.toString() : "null") + ")";
	}
}